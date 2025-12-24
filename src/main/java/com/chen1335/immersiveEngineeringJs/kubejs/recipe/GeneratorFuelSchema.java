package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import com.chen1335.immersiveEngineeringJs.kubejs.factories.GeneratorFuelFactory;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ListRecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.TagKeyComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.util.IntBounds;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

import java.util.List;
import java.util.Optional;

public interface GeneratorFuelSchema {
    RecipeKey<TagKey<Fluid>> FLUID_TAG = TagKeyComponent.FLUID.inputKey("fluidTag").defaultOptional();

    RecipeKey<List<Fluid>> FLUID_LIST = ListRecipeComponent.create(Schemas.FLUID, false, false, IntBounds.OPTIONAL, Optional.empty()).inputKey("fluidList").optional(List.of());

    RecipeKey<Integer> BURN_TIME = NumberComponent.IntRange.INT.inputKey("burnTime");

    RecipeSchema SCHEMA = new RecipeSchema(
            BURN_TIME,
            FLUID_TAG,
            FLUID_LIST
    ).factory(GeneratorFuelFactory.RECIPE_FACTORY);
}


