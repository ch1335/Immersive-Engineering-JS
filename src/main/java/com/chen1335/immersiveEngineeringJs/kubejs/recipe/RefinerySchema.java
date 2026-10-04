package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import blusunrize.immersiveengineering.api.crafting.FluidTagInput;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

/**
 * JSON contract of {@code RefineryRecipeSerializer} in Immersive Engineering 1.20.1:
 * {@code result}, {@code energy}, {@code input0}, optional {@code input1} and optional {@code catalyst}.
 */
public interface RefinerySchema
{
	RecipeKey<FluidStack> RESULT = Schemas.FLUID_STACK.key("result");

	RecipeKey<FluidTagInput> INPUT0 = Schemas.FLUID_TAG_INPUT.key("input0");

	RecipeKey<FluidTagInput> INPUT1 = Schemas.FLUID_TAG_INPUT.key("input1").defaultOptional();

	RecipeKey<Integer> ENERGY = NumberComponent.INT.key("energy");

	RecipeKey<Ingredient> CATALYST = Schemas.INGREDIENT.key("catalyst").defaultOptional();

	RecipeSchema SCHEMA = new RecipeSchema(
			RESULT,
			ENERGY,
			INPUT0,
			CATALYST,
			INPUT1
	);
}
