package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import com.chen1335.immersiveEngineeringJs.api.crafting.ChanceOutput;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

/**
 * JSON contract of {@code MineralMixSerializer} in Immersive Engineering 1.20.1:
 * {@code ores}, {@code spoils}, {@code weight}, {@code dimensions}, {@code fail_chance} and
 * {@code sample_background}.
 * <p>
 * 1.21.1 replaced the dimension list with biome tag predicates; 1.20.1 has no such predicate, so the
 * schema exposes {@code dimensions} instead.
 */
public interface MineralMixSchema
{
	RecipeKey<ChanceOutput[]> ORES = Schemas.CHANCE_LIST.key("ores");

	RecipeKey<ChanceOutput[]> SPOILS = Schemas.CHANCE_LIST.key("spoils");

	RecipeKey<Integer> WEIGHT = NumberComponent.INT.key("weight");

	RecipeKey<ResourceLocation[]> DIMENSIONS = Schemas.DIMENSION_LIST.key("dimensions");

	RecipeKey<Float> FAIL_CHANCE = NumberComponent.FLOAT.key("fail_chance").optional(0F).alwaysWrite();

	RecipeKey<Block> BACKGROUND = Schemas.BLOCK.key("sample_background").optional(Blocks.STONE).alwaysWrite();

	RecipeSchema SCHEMA = new RecipeSchema(
			ORES,
			SPOILS,
			WEIGHT,
			DIMENSIONS,
			FAIL_CHANCE,
			BACKGROUND
	);
}
