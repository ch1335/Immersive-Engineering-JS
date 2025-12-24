package com.chen1335.immersiveEngineeringJs.kubejs.recipeBuilders;

import blusunrize.immersiveengineering.api.IEApi;
import dev.latvian.mods.kubejs.recipe.KubeRecipe;
import dev.latvian.mods.kubejs.recipe.RecipesKubeEvent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeOptional;
import dev.latvian.mods.kubejs.script.SourceLine;
import dev.latvian.mods.kubejs.util.ErrorStack;
import dev.latvian.mods.rhino.Context;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

import java.util.List;

public class GeneratorFuelHelper {

    private final Context cx;
    private final RecipesKubeEvent recipesKubeEvent;

    public GeneratorFuelHelper(Context cx, RecipesKubeEvent recipesKubeEvent) {
        this.cx = cx;
        this.recipesKubeEvent = recipesKubeEvent;
    }

    public static GeneratorFuelHelper create(Context cx, RecipesKubeEvent recipesKubeEvent) {
        return new GeneratorFuelHelper(cx, recipesKubeEvent);
    }


    public GeneratorFuelHelper addTag(TagKey<Fluid> tagKey, int burnTime) {
        recipesKubeEvent.getRecipeFunction(IEApi.ieLoc("generator_fuel").toString()).createRecipe(cx, SourceLine.of(cx), new ErrorStack(), new Object[]{
                burnTime, tagKey, List.of()
        });
        return this;
    }

    public GeneratorFuelHelper addFluids(List<Fluid> fluids, int burnTime) {
        recipesKubeEvent.getRecipeFunction(IEApi.ieLoc("generator_fuel").toString()).createRecipe(cx, SourceLine.of(cx), new ErrorStack(), new Object[]{
                burnTime, null, fluids
        });
        return this;
    }
}
