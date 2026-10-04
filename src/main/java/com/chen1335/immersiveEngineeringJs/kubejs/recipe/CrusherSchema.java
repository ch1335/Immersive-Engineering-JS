package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import com.chen1335.immersiveEngineeringJs.api.crafting.ChanceOutput;
import com.chen1335.immersiveEngineeringJs.api.crafting.TagOutput;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * JSON contract of {@code CrusherRecipeSerializer} in Immersive Engineering 1.20.1:
 * {@code result}, {@code input}, {@code energy} and {@code secondaries}.
 */
public interface CrusherSchema
{
	RecipeKey<TagOutput> RESULT = Schemas.TAG_OUTPUT.key("result");

	// IE reads crusher "input" with GsonHelper.getAsJsonObject, so only a single entry ingredient is
	// accepted here (see Schemas.SINGLE_ENTRY_INGREDIENT).
	RecipeKey<Ingredient> INPUT = Schemas.SINGLE_ENTRY_INGREDIENT.key("input");

	RecipeKey<Integer> ENERGY = NumberComponent.INT.key("energy").optional(3200).alwaysWrite();

	RecipeKey<ChanceOutput[]> SECONDARIES = Schemas.CHANCE_LIST.key("secondaries")
			.optional(new ChanceOutput[0]).alwaysWrite();

	RecipeSchema SCHEMA = new RecipeSchema(
			RESULT,
			INPUT,
			ENERGY,
			SECONDARIES
	);
}
