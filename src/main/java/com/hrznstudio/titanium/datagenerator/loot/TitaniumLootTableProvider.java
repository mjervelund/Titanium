/*
 * This file is part of Titanium
 * Copyright (C) 2026, Horizon Studio <contact@hrznstudio.com>.
 *
 * This code is licensed under GNU Lesser General Public License v3.0, the full license text can be found in LICENSE.txt
 */

package com.hrznstudio.titanium.datagenerator.loot;

import com.hrznstudio.titanium.datagenerator.loot.block.BasicBlockLootTables;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Since 26.3 loot tables are generated as datapack registry entries. Register it with
 * {@code GatherDataRegistryEntriesEvent#add(Registries.LOOT_TABLE, new TitaniumLootTableProvider(blocks))}.
 */
public class TitaniumLootTableProvider extends LootTableProvider {

    public TitaniumLootTableProvider(Supplier<List<Block>> blocks) {
        super(Set.of(), List.of(new SubProviderEntry(context -> new BasicBlockLootTables(blocks, context), LootContextParamSets.BLOCK)));
    }
}
