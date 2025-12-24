package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import blusunrize.immersiveengineering.api.crafting.StackWithChance;
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

public interface CrusherSchema {
    RecipeKey<TagOutput> RESULT = Schemas.TAG_OUTPUT.outputKey("result");

    RecipeKey<Ingredient> INPUT = IngredientComponent.INGREDIENT.inputKey("input");

    RecipeKey<Integer> ENERGY = NumberComponent.IntRange.INT.inputKey("energy").optional(3200).alwaysWrite();

    RecipeKey<List<StackWithChance>> SECONDARIES = ListRecipeComponent.create(Schemas.STACK_WITH_CHANCE, false, false, IntBounds.OPTIONAL, Optional.empty()).outputKey("secondaries").optional(List.of()).alwaysWrite();

    RecipeSchema SCHEMA = new RecipeSchema(
            RESULT,
            INPUT,
            ENERGY,
            SECONDARIES
    );
}
