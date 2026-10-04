package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import com.chen1335.immersiveEngineeringJs.api.crafting.TagOutput;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

/**
 * JSON contract of {@code CokeOvenRecipeSerializer} in Immersive Engineering 1.20.1:
 * {@code result}, {@code input}, {@code time} and {@code creosote}.
 */
public interface CokeOvenSchema
{
	RecipeKey<TagOutput> RESULT = Schemas.TAG_OUTPUT.key("result");

	RecipeKey<IngredientWithSize> INPUT = Schemas.INGREDIENT_WITH_SIZE.key("input");

	RecipeKey<Integer> CREOSOTE = NumberComponent.INT.key("creosote");

	// Required rather than alwaysWrite(): KubeJS 2001's RecipeJS#initValues dereferences
	// RecipeKey#optional for every alwaysWrite key, so alwaysWrite may only be used together with
	// optional(). Immersive Engineering requires "time" in the JSON anyway, and required keys are
	// always written.
	RecipeKey<Integer> TIME = NumberComponent.INT.key("time");

	RecipeSchema SCHEMA = new RecipeSchema(
			RESULT,
			INPUT,
			TIME,
			CREOSOTE
	);
}
