package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import com.chen1335.immersiveEngineeringJs.api.crafting.TagOutput;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraftforge.fluids.FluidStack;

/**
 * JSON contract of {@code FermenterRecipeSerializer} in Immersive Engineering 1.20.1:
 * {@code input}, {@code energy}, {@code fluid} (optional output) and {@code result} (optional output).
 */
public interface FermenterSchema
{
	RecipeKey<FluidStack> FLUID = Schemas.FLUID_STACK.key("fluid").optional(FluidStack.EMPTY).alwaysWrite();

	RecipeKey<TagOutput> RESULT = Schemas.TAG_OUTPUT.key("result").optional(TagOutput.EMPTY).alwaysWrite();

	RecipeKey<IngredientWithSize> INPUT = Schemas.INGREDIENT_WITH_SIZE.key("input");

	RecipeKey<Integer> ENERGY = NumberComponent.INT.key("energy");

	RecipeSchema SCHEMA = new RecipeSchema(
			INPUT,
			ENERGY,
			FLUID,
			RESULT
	);
}
