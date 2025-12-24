package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import blusunrize.immersiveengineering.api.crafting.StackWithChance;
import blusunrize.immersiveengineering.api.crafting.TagOutput;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ListRecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.util.IntBounds;

import java.util.List;
import java.util.Optional;

public interface ArcFurnaceSchema {
    RecipeKey<List<TagOutput>> RESULT = Schemas.TAG_OUTPUT.asList().outputKey("results");

    RecipeKey<TagOutput> SLAG = Schemas.TAG_OUTPUT.outputKey("slag").optional(TagOutput.EMPTY).alwaysWrite();

    RecipeKey<List<StackWithChance>> SECONDARIES = ListRecipeComponent.create(Schemas.STACK_WITH_CHANCE, false, false, IntBounds.OPTIONAL, Optional.empty()).outputKey("secondaries").optional(List.of()).alwaysWrite();

    RecipeKey<Integer> TIME = NumberComponent.IntRange.INT.inputKey("time");

    RecipeKey<Integer> ENERGY = NumberComponent.IntRange.INT.inputKey("energy");

    RecipeKey<IngredientWithSize> INPUT = Schemas.INGREDIENT_WITH_SIZE.inputKey("input");

    RecipeKey<List<IngredientWithSize>> ADDITIVES = ListRecipeComponent.create(Schemas.INGREDIENT_WITH_SIZE, false, false, IntBounds.OPTIONAL, Optional.empty()).inputKey("additives").optional(List.of()).alwaysWrite();

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
