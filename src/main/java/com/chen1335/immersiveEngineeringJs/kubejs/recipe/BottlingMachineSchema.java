package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import blusunrize.immersiveengineering.api.crafting.FluidTagInput;
import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import com.chen1335.immersiveEngineeringJs.api.crafting.TagOutput;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

/**
 * JSON contract of {@code BottlingMachineRecipeSerializer} in Immersive Engineering 1.20.1:
 * {@code results}, {@code inputs} (or a single {@code input}) and {@code fluid}.
 */
public interface BottlingMachineSchema
{
	RecipeKey<TagOutput[]> RESULTS = Schemas.TAG_OUTPUT.asArray().key("results");

	RecipeKey<IngredientWithSize[]> INPUTS = Schemas.INGREDIENT_WITH_SIZE.asArray().key("inputs").alt("input");

	RecipeKey<FluidTagInput> FLUID = Schemas.FLUID_TAG_INPUT.key("fluid");

	RecipeSchema SCHEMA = new RecipeSchema(
			RESULTS,
			INPUTS,
			FLUID
	);
}
