package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import blusunrize.immersiveengineering.api.crafting.ClocheRenderFunction.ClocheRenderReference;
import com.chen1335.immersiveEngineeringJs.api.crafting.TagOutput;
import com.chen1335.immersiveEngineeringJs.kubejs.factories.ClocheRecipeFactory;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * JSON contract of {@code ClocheRecipeSerializer} in Immersive Engineering 1.20.1:
 * {@code results}, {@code input} (the seed), {@code soil}, {@code time} and {@code render}.
 * <p>
 * Note that unlike 1.21.1 there is no {@code fluid} field, and {@code render} is mandatory in the
 * JSON. {@link ClocheRecipeFactory} fills it in automatically when a script omits it.
 */
public interface ClocheRecipeSchema
{
	RecipeKey<TagOutput[]> RESULT = Schemas.TAG_OUTPUT.asArray().key("results");

	RecipeKey<Ingredient> SEED = Schemas.INGREDIENT.key("input");

	RecipeKey<Ingredient> SOIL = Schemas.INGREDIENT.key("soil");

	RecipeKey<Integer> TIME = NumberComponent.INT.key("time");

	RecipeKey<ClocheRenderReference> RENDER = Schemas.CLOCHE_RENDER_REFERENCE.key("render").defaultOptional();

	RecipeSchema SCHEMA = new RecipeSchema(
			ClocheRecipeFactory.class,
			ClocheRecipeFactory::new,
			RESULT,
			SEED,
			SOIL,
			TIME,
			RENDER
	);
}
