package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import blusunrize.immersiveengineering.api.crafting.TagOutput;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface AlloyRecipeSchema {

    RecipeKey<IngredientWithSize> INPUT0 = Schemas.INGREDIENT_WITH_SIZE.inputKey("input0");

    RecipeKey<IngredientWithSize> INPUT1 = Schemas.INGREDIENT_WITH_SIZE.inputKey("input1");

    RecipeKey<Integer> TIME = NumberComponent.IntRange.INT.inputKey("time").optional(200).alwaysWrite();

    RecipeKey<TagOutput> RESULT = Schemas.TAG_OUTPUT.outputKey("result");

    RecipeSchema SCHEMA = new RecipeSchema(
            RESULT,
            INPUT0,
            INPUT1,
            TIME
    );
}
