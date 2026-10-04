package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.TagKeyComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

/**
 * JSON contract of {@code GeneratorFuelSerializer} in Immersive Engineering 1.20.1:
 * {@code fluidTag} and {@code burnTime}.
 * <p>
 * 1.20.1 can only express generator fuel as a fluid tag in datapack JSON, so the 1.21.1
 * {@code fluidList} key has no counterpart here.
 */
public interface GeneratorFuelSchema
{
	RecipeKey<TagKey<Fluid>> FLUID_TAG = TagKeyComponent.FLUID.key("fluidTag");

	RecipeKey<Integer> BURN_TIME = NumberComponent.INT.key("burnTime");

	RecipeSchema SCHEMA = new RecipeSchema(
			BURN_TIME,
			FLUID_TAG
	);
}
