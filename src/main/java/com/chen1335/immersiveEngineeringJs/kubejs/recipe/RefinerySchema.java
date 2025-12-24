package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.FluidStackComponent;
import dev.latvian.mods.kubejs.recipe.component.IngredientComponent;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.SizedFluidIngredientComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

public interface RefinerySchema {
    RecipeKey<FluidStack> RESULT = FluidStackComponent.FLUID_STACK.outputKey("result");

    RecipeKey<SizedFluidIngredient> INPUT0 = SizedFluidIngredientComponent.FLAT.outputKey("input0");

    RecipeKey<SizedFluidIngredient> INPUT1 = SizedFluidIngredientComponent.OPTIONAL_FLAT.outputKey("input1").defaultOptional();

    RecipeKey<Integer> ENERGY = NumberComponent.IntRange.INT.inputKey("energy");

    RecipeKey<Ingredient> CATALYST = IngredientComponent.OPTIONAL_INGREDIENT.inputKey("catalyst").defaultOptional();

    RecipeSchema SCHEMA = new RecipeSchema(
            RESULT,
            ENERGY,
            INPUT0,
            CATALYST,
            INPUT1
    );
}
