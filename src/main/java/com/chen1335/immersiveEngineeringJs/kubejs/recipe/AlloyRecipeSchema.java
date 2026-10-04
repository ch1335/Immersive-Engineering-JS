package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import com.chen1335.immersiveEngineeringJs.api.crafting.TagOutput;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

/**
 * JSON contract of {@code AlloyRecipeSerializer} in Immersive Engineering 1.20.1:
 * {@code result}, {@code input0}, {@code input1}, {@code time} (default 200).
 */
public interface AlloyRecipeSchema
{
	RecipeKey<TagOutput> RESULT = Schemas.TAG_OUTPUT.key("result");

	RecipeKey<IngredientWithSize> INPUT0 = Schemas.INGREDIENT_WITH_SIZE.key("input0");

	RecipeKey<IngredientWithSize> INPUT1 = Schemas.INGREDIENT_WITH_SIZE.key("input1");

	RecipeKey<Integer> TIME = NumberComponent.INT.key("time").optional(200).alwaysWrite();

	RecipeSchema SCHEMA = new RecipeSchema(
			RESULT,
			INPUT0,
			INPUT1,
			TIME
	);
}
