package com.chen1335.immersiveEngineeringJs.kubejs.factories;

import blusunrize.immersiveengineering.api.IEApi;
import dev.latvian.mods.kubejs.recipe.KubeRecipe;
import dev.latvian.mods.kubejs.recipe.schema.KubeRecipeFactory;


public class ArcFurnaceFactory extends KubeRecipe {
    public static final KubeRecipeFactory RECIPE_FACTORY = new KubeRecipeFactory(IEApi.ieLoc("arc_furnace"), ArcFurnaceFactory.class, ArcFurnaceFactory::new);

}
