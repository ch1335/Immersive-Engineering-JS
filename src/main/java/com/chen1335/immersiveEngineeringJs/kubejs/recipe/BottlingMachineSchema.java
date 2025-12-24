package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import blusunrize.immersiveengineering.api.crafting.TagOutput;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.SizedFluidIngredientComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

import java.util.List;

public interface BottlingMachineSchema {

    RecipeKey<List<TagOutput>> RESULTS = Schemas.TAG_OUTPUT.asList().outputKey("results");

    RecipeKey<List<IngredientWithSize>> INPUTS = Schemas.LIST_OR_SINGLE.inputKey("inputs").alt("input");

    RecipeKey<SizedFluidIngredient> FLUID = SizedFluidIngredientComponent.FLAT.inputKey("fluid");

    RecipeSchema SCHEMA = new RecipeSchema(
            RESULTS,
            INPUTS,
            FLUID
    );
}
