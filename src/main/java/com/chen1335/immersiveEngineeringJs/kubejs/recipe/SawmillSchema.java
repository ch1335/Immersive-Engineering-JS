package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import com.chen1335.immersiveEngineeringJs.api.crafting.TagOutput;
import com.chen1335.immersiveEngineeringJs.kubejs.factories.SawmillRecipeFactory;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * JSON contract of {@code SawmillRecipeSerializer} in Immersive Engineering 1.20.1:
 * {@code result}, {@code input}, {@code energy}, optional {@code stripped} and a single
 * {@code secondaries} array whose elements carry a {@code stripping} flag.
 * <p>
 * The 1.21.1 script API splits those secondaries into two keys, so
 * {@link SawmillRecipeFactory} merges and splits them around (de)serialization.
 */
public interface SawmillSchema
{
	RecipeKey<TagOutput> RESULT = Schemas.TAG_OUTPUT.key("result");

	RecipeKey<Ingredient> INPUT = Schemas.INGREDIENT.key("input");

	RecipeKey<Integer> ENERGY = NumberComponent.INT.key("energy");

	RecipeKey<TagOutput> STRIPPED = Schemas.TAG_OUTPUT.key("stripped").optional(TagOutput.EMPTY).alwaysWrite();

	RecipeKey<TagOutput[]> STRIPPING_SECONDARIES = Schemas.TAG_OUTPUT.asArray().key("strippingSecondaries")
			.optional(new TagOutput[0]).alwaysWrite();

	RecipeKey<TagOutput[]> SECONDARY_OUTPUTS = Schemas.TAG_OUTPUT.asArray().key("secondaryOutputs")
			.optional(new TagOutput[0]).alwaysWrite();

	RecipeSchema SCHEMA = new RecipeSchema(
			SawmillRecipeFactory.class,
			SawmillRecipeFactory::new,
			RESULT,
			INPUT,
			ENERGY,
			STRIPPED,
			STRIPPING_SECONDARIES,
			SECONDARY_OUTPUTS
	);
}
