package com.chen1335.immersiveEngineeringJs.kubejs.recipeBuilders;

import blusunrize.immersiveengineering.api.IEApi;
import com.chen1335.immersiveEngineeringJs.api.crafting.CraftingTags;
import dev.latvian.mods.kubejs.recipe.RecipesEventJS;
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

	/**
	 * Called as {@code GeneratorFuelHelper.create(event)} from scripts. See
	 * {@link MineralMixBuilder#build} for why there is no Rhino {@code Context} parameter on 1.20.1.
	 */
	public static GeneratorFuelHelper create(RecipesEventJS recipesEvent)
	{
		return new GeneratorFuelHelper(recipesEvent);
	}

	public GeneratorFuelHelper addTag(TagKey<Fluid> tagKey, int burnTime)
	{
		recipesEvent.getRecipeFunction(IEApi.ieLoc("generator_fuel").toString())
				.createRecipe(new Object[]{burnTime, tagKey});
		return this;
	}

	/**
	 * String overload so scripts can write {@code addTag('forge:ethanol', 1200)}. KubeJS 2001 has no
	 * type wrapper for {@code TagKey}, so a plain string cannot be converted to the {@link TagKey}
	 * overload above.
	 */
	public GeneratorFuelHelper addTag(String tag, int burnTime)
	{
		return addTag(CraftingTags.fluid(tag), burnTime);
	}
}
