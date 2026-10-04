package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import blusunrize.immersiveengineering.api.crafting.FluidTagInput;
import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraftforge.fluids.FluidStack;

/**
 * JSON contract of {@code MixerRecipeSerializer} in Immersive Engineering 1.20.1:
 * {@code result} (fluid output), {@code fluid} (fluid tag input), {@code inputs} and {@code energy}.
 */
public interface MixerSchema
{
	RecipeKey<FluidStack> RESULT = Schemas.FLUID_STACK.key("result");

	RecipeKey<FluidTagInput> FLUID = Schemas.FLUID_TAG_INPUT.key("fluid");

	RecipeKey<IngredientWithSize[]> INPUTS = Schemas.INGREDIENT_WITH_SIZE.asArray().key("inputs");

	RecipeKey<Integer> ENERGY = NumberComponent.INT.key("energy");

	RecipeSchema SCHEMA = new RecipeSchema(
			RESULT,
			FLUID,
			INPUTS,
			ENERGY
	);
}
