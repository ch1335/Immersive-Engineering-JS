package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import blusunrize.immersiveengineering.api.crafting.TagOutput;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.Item;

public interface MetalPressSchema {
    RecipeKey<TagOutput> RESULT = Schemas.TAG_OUTPUT.outputKey("result");

    RecipeKey<IngredientWithSize> INPUT = Schemas.INGREDIENT_WITH_SIZE.inputKey("input");

    RecipeKey<Item> MOLD = Schemas.ITEM.inputKey("mold");

    RecipeKey<Integer> ENERGY = NumberComponent.IntRange.INT.inputKey("energy");

    RecipeSchema SCHEMA = new RecipeSchema(
            RESULT,
            INPUT,
            MOLD,
            ENERGY
    );
}
