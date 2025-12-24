package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import blusunrize.immersiveengineering.api.crafting.StackWithChance;
import blusunrize.immersiveengineering.api.excavator.MineralMix;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.BlockComponent;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.Set;

public interface MineralMixSchema {
    RecipeKey<List<StackWithChance>> ORES = Schemas.CHANCE_LIST.outputKey("ores");
    RecipeKey<List<StackWithChance>> SPOILS = Schemas.CHANCE_LIST.outputKey("spoils");

    RecipeKey<Integer> WEIGHT = NumberComponent.IntRange.INT.inputKey("weight");

    RecipeKey<Set<MineralMix.BiomeTagPredicate>> BIOME_PREDICATES = Schemas.BIOME_TAG_PREDICATES.inputKey("biome_predicates");

    RecipeKey<Float> FAIL_CHANCE = NumberComponent.FloatRange.FLOAT.inputKey("fail_chance").optional(0f);

    RecipeKey<Block> BACKGROUND = BlockComponent.BLOCK.inputKey("sample_background").optional(Blocks.STONE);

    RecipeSchema SCHEMA = new RecipeSchema(
            ORES,
            SPOILS,
            WEIGHT,
            BIOME_PREDICATES,
            FAIL_CHANCE,
            BACKGROUND
    );
}
