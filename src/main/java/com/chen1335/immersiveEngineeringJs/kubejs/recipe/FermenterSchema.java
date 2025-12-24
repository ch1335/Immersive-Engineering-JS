package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import blusunrize.immersiveengineering.api.crafting.TagOutput;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.FluidStackComponent;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.neoforged.neoforge.fluids.FluidStack;

public interface FermenterSchema {
    RecipeKey<FluidStack> FLUID = FluidStackComponent.OPTIONAL_FLUID_STACK.inputKey("fluid");

    RecipeKey<TagOutput> RESULT = Schemas.TAG_OUTPUT.outputKey("result");
    RecipeKey<IngredientWithSize> INPUT = Schemas.INGREDIENT_WITH_SIZE.inputKey("input");
    RecipeKey<Integer> ENERGY = NumberComponent.IntRange.INT.inputKey("energy");

    RecipeSchema SCHEMA = new RecipeSchema(
            INPUT,
            ENERGY,
            FLUID,
            RESULT
    );
}
