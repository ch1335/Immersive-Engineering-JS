package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * JSON contract of {@code BlastFurnaceFuelSerializer} in Immersive Engineering 1.20.1:
 * {@code input} and {@code time} (default 1200).
 */
public interface BlastFurnaceFuelSchema
{
	// IE reads blast furnace fuel "input" with GsonHelper.getAsJsonObject("input"), so only a single
	// entry ingredient is accepted here (see Schemas.SINGLE_ENTRY_INGREDIENT).
	RecipeKey<Ingredient> INPUT = Schemas.SINGLE_ENTRY_INGREDIENT.key("input");

	RecipeKey<Integer> BURN_TIME = NumberComponent.INT.key("time");

	RecipeSchema SCHEMA = new RecipeSchema(
			INPUT,
			BURN_TIME
	);
}
