package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import blusunrize.immersiveengineering.api.crafting.ClocheRenderFunction;
import blusunrize.immersiveengineering.api.crafting.StackWithChance;
import blusunrize.immersiveengineering.common.crafting.serializers.ClocheRecipeSerializer;
import com.chen1335.immersiveEngineeringJs.kubejs.factories.ClocheRecipeFactory;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.FluidIngredientComponent;
import dev.latvian.mods.kubejs.recipe.component.IngredientComponent;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;

import java.util.List;

public interface ClocheRecipeSchema {
    RecipeKey<List<StackWithChance>> RESULT = Schemas.CHANCE_LIST.outputKey("results");

    RecipeKey<Ingredient> SEED = IngredientComponent.INGREDIENT.inputKey("input");

    RecipeKey<Ingredient> SOIL = IngredientComponent.INGREDIENT.inputKey("soil");

    RecipeKey<Integer> TIME = NumberComponent.IntRange.INT.inputKey("time");

    RecipeKey<FluidIngredient> FLUID = FluidIngredientComponent.FLUID_INGREDIENT.inputKey("fluid").optional(ClocheRecipeSerializer.DEFAULT_FLUID).alwaysWrite();

    RecipeKey<ClocheRenderFunction> RENDER = Schemas.CLOCHE_RENDER_FUNCTION.inputKey("render").defaultOptional();

    RecipeSchema SCHEMA = new RecipeSchema(
            RESULT,
            SEED,
            SOIL,
            TIME,
            FLUID,
            RENDER
    ).factory(ClocheRecipeFactory.RECIPE_FACTORY);
}
