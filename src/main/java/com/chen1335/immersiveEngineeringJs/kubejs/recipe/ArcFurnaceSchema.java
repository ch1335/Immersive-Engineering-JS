package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import com.chen1335.immersiveEngineeringJs.api.crafting.ChanceOutput;
import com.chen1335.immersiveEngineeringJs.api.crafting.TagOutput;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

/**
 * JSON contract of {@code ArcFurnaceRecipeSerializer} in Immersive Engineering 1.20.1:
 * {@code input}, {@code additives}, {@code time}, {@code energy}, {@code slag},
 * {@code results} and {@code secondaries}.
 */
public interface ArcFurnaceSchema
{
	RecipeKey<TagOutput[]> RESULT = Schemas.TAG_OUTPUT.asArray().key("results");

	RecipeKey<TagOutput> SLAG = Schemas.TAG_OUTPUT.key("slag").optional(TagOutput.EMPTY).alwaysWrite();

	RecipeKey<ChanceOutput[]> SECONDARIES = Schemas.CHANCE_LIST.key("secondaries")
			.optional(new ChanceOutput[0]).alwaysWrite();

	RecipeKey<Integer> TIME = NumberComponent.INT.key("time");

	RecipeKey<Integer> ENERGY = NumberComponent.INT.key("energy");

	RecipeKey<IngredientWithSize> INPUT = Schemas.INGREDIENT_WITH_SIZE.key("input");

	RecipeKey<IngredientWithSize[]> ADDITIVES = Schemas.INGREDIENT_WITH_SIZE.asArray().key("additives")
			.optional(new IngredientWithSize[0]).alwaysWrite();

	RecipeSchema SCHEMA = new RecipeSchema(
			RESULT,
			INPUT,
			TIME,
			ENERGY,
			ADDITIVES,
			SECONDARIES,
			SLAG
	);
}
