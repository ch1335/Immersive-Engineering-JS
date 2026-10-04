package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import com.chen1335.immersiveEngineeringJs.api.crafting.TagOutput;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.StringComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

/**
 * JSON contract of {@code BlueprintCraftingRecipeSerializer} in Immersive Engineering 1.20.1:
 * {@code category}, {@code result} and {@code inputs}.
 */
public interface BlueprintSchema
{
	RecipeKey<String> CATEGORY = StringComponent.NON_EMPTY.key("category");

	RecipeKey<TagOutput> RESULT = Schemas.TAG_OUTPUT.key("result");

	RecipeKey<IngredientWithSize[]> INPUTS = Schemas.INGREDIENT_WITH_SIZE.asArray().key("inputs");

	RecipeSchema SCHEMA = new RecipeSchema(
			CATEGORY,
			RESULT,
			INPUTS
	);
}
