/*
 * This file is part of Titanium
 * Copyright (C) 2026, Horizon Studio <contact@hrznstudio.com>.
 *
 * This code is licensed under GNU Lesser General Public License v3.0, the full license text can be found in LICENSE.txt
 */

package com.hrznstudio.titanium.network.messages;

import com.hrznstudio.titanium.network.IButtonHandler;
import com.hrznstudio.titanium.network.Message;
import com.hrznstudio.titanium.network.locator.ILocatable;
import com.hrznstudio.titanium.network.locator.LocatorInstance;
import com.hrznstudio.titanium.util.CastingUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Optional;


public class ButtonClickNetworkMessage extends Message {
    private LocatorInstance locatorInstance;
    private int id;
    private CompoundTag data;

    public ButtonClickNetworkMessage(LocatorInstance locatorInstance, int id, CompoundTag data) {
        this.locatorInstance = locatorInstance;
        this.id = id;
        this.data = data;
    }

    public ButtonClickNetworkMessage() {

    }

    @Override
    protected void handleServerMessage(IPayloadContext context) {
        Player player = context.player();
        // The locator comes from the client: only act on the menu the player has open, so a client
        // can't press buttons on block entities it isn't using (or load chunks by naming far positions).
        if (locatorInstance == null || !(player.containerMenu instanceof ILocatable locatable)
            || !player.containerMenu.stillValid(player) || !locatable.getLocatorInstance().matches(locatorInstance)) {
            return;
        }
        Optional.of(player)
            .flatMap(locatable.getLocatorInstance()::locale)
            .flatMap(CastingUtil.attemptCast(IButtonHandler.class))
            .ifPresent(iButtonHandler -> iButtonHandler.handleButtonMessage(id, player, data));
    }
}
