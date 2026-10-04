package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import com.chen1335.immersiveEngineeringJs.api.crafting.TagOutput;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

/**
 * JSON contract of {@code BlastFurnaceRecipeSerializer} in Immersive Engineering 1.20.1:
 * {@code result}, {@code input}, {@code time} (default 200) and optional {@code slag}.
 */
public interface BlastFurnaceSchema
{
	RecipeKey<TagOutput> RESULT = Schemas.TAG_OUTPUT.key("result");

	RecipeKey<TagOutput> SLAG = Schemas.TAG_OUTPUT.key("slag").optional(TagOutput.EMPTY).alwaysWrite();

	RecipeKey<IngredientWithSize> INPUT = Schemas.INGREDIENT_WITH_SIZE.key("input");

	RecipeKey<Integer> TIME = NumberComponent.INT.key("time").optional(200).alwaysWrite();

	RecipeSchema SCHEMA = new RecipeSchema(
			RESULT,
			INPUT,
			TIME,
			SLAG
	);
}
