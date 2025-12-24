package com.chen1335.immersiveEngineeringJs.kubejs.factories;

import blusunrize.immersiveengineering.api.IEApi;
import com.chen1335.immersiveEngineeringJs.kubejs.recipe.GeneratorFuelSchema;
import com.google.gson.JsonElement;
import dev.latvian.mods.kubejs.error.KubeRuntimeException;
import dev.latvian.mods.kubejs.error.MissingComponentException;
import dev.latvian.mods.kubejs.error.RecipeComponentException;
import dev.latvian.mods.kubejs.recipe.KubeRecipe;
import dev.latvian.mods.kubejs.recipe.RecipesKubeEvent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentValue;
import dev.latvian.mods.kubejs.recipe.schema.KubeRecipeFactory;
import dev.latvian.mods.kubejs.recipe.schema.RecipeOptional;
import dev.latvian.mods.kubejs.script.ConsoleJS;
import dev.latvian.mods.kubejs.util.Cast;

import java.util.List;

public class GeneratorFuelFactory extends KubeRecipe {
    public static final KubeRecipeFactory RECIPE_FACTORY = new KubeRecipeFactory(IEApi.ieLoc("generator_fuel"), GeneratorFuelFactory.class, GeneratorFuelFactory::new);

    @Override
    public void serialize() {
        for (RecipeComponentValue<?> v : getRecipeComponentValues()) {
            boolean skip = false;
            if (v.getKey().equals(GeneratorFuelSchema.FLUID_LIST) && ((List<?>) v.getValue()).isEmpty()) {
                skip = true;
            }

            if (v.getKey().equals(GeneratorFuelSchema.FLUID_TAG) && (v.getValue() == null || v.getValue().equals(RecipeOptional.DEFAULT))) {
                skip = true;
            }

            if (v.shouldWrite() && !skip) {
                if (v.value == null) {
                    throw new KubeRuntimeException("Value not set for " + v.key + " in recipe " + this).source(sourceLine);
                }

                v.key.component.writeToJson(this, Cast.to(v), json);
            }
        }
    }

    @Override
    public void deserialize(boolean merge) {
        for (var v : getRecipeComponentValues()) {
            try {
                JsonElement jsonElement = json.get(v.key.name);
                if (jsonElement != null) {
                    v.key.component.readFromJson(this, Cast.to(v), json);
                }
            } catch (Exception ex) {
                if (v.key.optional()) {
                    ConsoleJS.SERVER.warn("Failed to read component '%s' from recipe %s, falling back to default value".formatted(v.key, this), sourceLine, ex, RecipesKubeEvent.POST_SKIP_ERROR);
                } else {
                    throw new RecipeComponentException("Failed to read required component '%s'".formatted(v.key), ex, v).source(sourceLine);
                }
            }

            if (v.value != null) {
                if (merge) {
                    v.write();
                }
            }
        }
    }
}
