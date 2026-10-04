package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * JSON contract of {@code ClocheFertilizerSerializer} in Immersive Engineering 1.20.1:
 * {@code input} and {@code growthModifier}.
 */
public interface FertilizerSchema
{
	RecipeKey<Ingredient> INPUT = Schemas.INGREDIENT.key("input");

	RecipeKey<Float> GROWTH_MODIFIER = NumberComponent.FLOAT.key("growthModifier");

	RecipeSchema SCHEMA = new RecipeSchema(
			INPUT,
			GROWTH_MODIFIER
	);
}
