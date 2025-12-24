package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.FluidStackComponent;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.SizedFluidIngredientComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

import java.util.List;

public interface MixerSchema {
    RecipeKey<FluidStack> RESULT = FluidStackComponent.FLUID_STACK.outputKey("result");

    RecipeKey<SizedFluidIngredient> FLUID = SizedFluidIngredientComponent.FLAT.outputKey("fluid");

    RecipeKey<List<IngredientWithSize>> INPUTS = Schemas.INGREDIENT_WITH_SIZE.asList().inputKey("inputs");

    RecipeKey<Integer> ENERGY = NumberComponent.IntRange.INT.inputKey("energy");

    RecipeSchema SCHEMA = new RecipeSchema(
            RESULT,
            FLUID,
            INPUTS,
            ENERGY
    );
}
