package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import com.chen1335.immersiveEngineeringJs.api.crafting.TagOutput;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.Item;

/**
 * JSON contract of {@code MetalPressRecipeSerializer} in Immersive Engineering 1.20.1:
 * {@code result}, {@code input}, {@code mold} and {@code energy}.
 */
public interface MetalPressSchema
{
	RecipeKey<TagOutput> RESULT = Schemas.TAG_OUTPUT.key("result");

	RecipeKey<IngredientWithSize> INPUT = Schemas.INGREDIENT_WITH_SIZE.key("input");

	RecipeKey<Item> MOLD = Schemas.ITEM.key("mold");

	RecipeKey<Integer> ENERGY = NumberComponent.INT.key("energy");

	RecipeSchema SCHEMA = new RecipeSchema(
			RESULT,
			INPUT,
			MOLD,
			ENERGY
	);
}
