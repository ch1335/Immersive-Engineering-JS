package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import blusunrize.immersiveengineering.api.crafting.TagOutput;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.IngredientComponent;
import dev.latvian.mods.kubejs.recipe.component.ListRecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.util.IntBounds;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.Optional;

public interface SawmillSchema {
    RecipeKey<TagOutput> RESULT = Schemas.TAG_OUTPUT.outputKey("result");
    RecipeKey<Ingredient> INPUT = IngredientComponent.INGREDIENT.inputKey("input");
    RecipeKey<TagOutput> STRIPPED = Schemas.TAG_OUTPUT.outputKey("stripped").optional(TagOutput.EMPTY).alwaysWrite();
    RecipeKey<Integer> ENERGY = NumberComponent.IntRange.INT.inputKey("energy");

    RecipeKey<List<TagOutput>> STRIPPING_SECONDARIES = ListRecipeComponent.create(Schemas.TAG_OUTPUT, false, false, IntBounds.OPTIONAL, Optional.empty()).outputKey("strippingSecondaries").optional(List.of()).alwaysWrite();
    RecipeKey<List<TagOutput>> SECONDARY_OUTPUTS = ListRecipeComponent.create(Schemas.TAG_OUTPUT, false, false, IntBounds.OPTIONAL, Optional.empty()).outputKey("secondaryOutputs").optional(List.of()).alwaysWrite();

    RecipeSchema SCHEMA = new RecipeSchema(
            RESULT,
            INPUT,
            ENERGY,
            STRIPPED,
            STRIPPING_SECONDARIES,
            SECONDARY_OUTPUTS
    ).factory(IEEnergyRecipe.RECIPE_FACTORY);
}
