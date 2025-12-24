package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.IngredientComponent;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.crafting.Ingredient;

public interface FertilizerSchema {
    RecipeKey<Ingredient> INPUT = IngredientComponent.INGREDIENT.inputKey("input");
    RecipeKey<Float> GROWTH_MODIFIER = NumberComponent.FloatRange.FLOAT.inputKey("growthModifier");

    RecipeSchema SCHEMA = new RecipeSchema(
            INPUT,
            GROWTH_MODIFIER
    );


}
