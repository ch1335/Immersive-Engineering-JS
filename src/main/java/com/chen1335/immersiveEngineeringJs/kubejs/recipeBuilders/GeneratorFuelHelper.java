package com.chen1335.immersiveEngineeringJs.kubejs.recipeBuilders;

import blusunrize.immersiveengineering.api.IEApi;
import dev.latvian.mods.kubejs.recipe.RecipesEventJS;
import dev.latvian.mods.rhino.Context;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

/**
 * Script helper that registers Immersive Engineering generator fuels.
 * <p>
 * Immersive Engineering 1.20.1 can only express a generator fuel as a fluid tag in datapack JSON
 * ({@code fluidTag} + {@code burnTime}), so the 1.21.1 {@code addFluids} helper, which relied on the
 * newer {@code fluidList} field, is not available on 1.20.1.
 */
public class GeneratorFuelHelper
{
	private final RecipesEventJS recipesEvent;

	public GeneratorFuelHelper(RecipesEventJS recipesEvent)
	{
		this.recipesEvent = recipesEvent;
	}

	public static GeneratorFuelHelper create(Context cx, RecipesEventJS recipesEvent)
	{
		return new GeneratorFuelHelper(recipesEvent);
	}

	public GeneratorFuelHelper addTag(TagKey<Fluid> tagKey, int burnTime)
	{
		recipesEvent.getRecipeFunction(IEApi.ieLoc("generator_fuel").toString())
				.createRecipe(new Object[]{burnTime, tagKey});
		return this;
	}
}
