package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import blusunrize.immersiveengineering.api.crafting.TagOutput;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface CokeOvenSchema {
    RecipeKey<TagOutput> RESULT = Schemas.TAG_OUTPUT.outputKey("result");

    RecipeKey<IngredientWithSize> INPUT = Schemas.INGREDIENT_WITH_SIZE.inputKey("input");

    RecipeKey<Integer> CREOSOTE = NumberComponent.IntRange.INT.inputKey("creosote");

    RecipeKey<Integer> TIME = NumberComponent.IntRange.INT.inputKey("time").alwaysWrite();


    RecipeSchema SCHEMA = new RecipeSchema(
            RESULT,
            INPUT,
            TIME,
            CREOSOTE
    );
}
