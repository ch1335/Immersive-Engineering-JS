package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import blusunrize.immersiveengineering.api.crafting.TagOutput;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.StringComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

import java.util.List;

public interface BlueprintSchema {
    RecipeKey<String> CATEGORY = StringComponent.STRING.inputKey("category");
    RecipeKey<TagOutput> RESULT = Schemas.TAG_OUTPUT.outputKey("result");
    RecipeKey<List<IngredientWithSize>> INPUTS = Schemas.INGREDIENT_WITH_SIZE.asList().inputKey("inputs");

    RecipeSchema SCHEMA = new RecipeSchema(
            CATEGORY,
            RESULT,
            INPUTS
    );
}
