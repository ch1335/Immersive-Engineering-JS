package com.chen1335.immersiveEngineeringJs.kubejs;

import blusunrize.immersiveengineering.api.IEApi;
import com.chen1335.immersiveEngineeringJs.api.crafting.ClocheRenderFunctionsJS;
import com.chen1335.immersiveEngineeringJs.api.crafting.IngredientWithSizeJS;
import com.chen1335.immersiveEngineeringJs.api.crafting.StackWithChanceJS;
import com.chen1335.immersiveEngineeringJs.api.crafting.TagOutputJS;
import com.chen1335.immersiveEngineeringJs.kubejs.event.IEEvents;
import com.chen1335.immersiveEngineeringJs.kubejs.recipe.AlloyRecipeSchema;
import com.chen1335.immersiveEngineeringJs.kubejs.recipe.ArcFurnaceSchema;
import com.chen1335.immersiveEngineeringJs.kubejs.recipe.BlastFurnaceFuelSchema;
import com.chen1335.immersiveEngineeringJs.kubejs.recipe.BlastFurnaceSchema;
import com.chen1335.immersiveEngineeringJs.kubejs.recipe.BlueprintSchema;
import com.chen1335.immersiveEngineeringJs.kubejs.recipe.BottlingMachineSchema;
import com.chen1335.immersiveEngineeringJs.kubejs.recipe.ClocheRecipeSchema;
import com.chen1335.immersiveEngineeringJs.kubejs.recipe.CokeOvenSchema;
import com.chen1335.immersiveEngineeringJs.kubejs.recipe.CrusherSchema;
import com.chen1335.immersiveEngineeringJs.kubejs.recipe.FermenterSchema;
import com.chen1335.immersiveEngineeringJs.kubejs.recipe.FertilizerSchema;
import com.chen1335.immersiveEngineeringJs.kubejs.recipe.GeneratorFuelSchema;
import com.chen1335.immersiveEngineeringJs.kubejs.recipe.MetalPressSchema;
import com.chen1335.immersiveEngineeringJs.kubejs.recipe.MineralMixSchema;
import com.chen1335.immersiveEngineeringJs.kubejs.recipe.MixerSchema;
import com.chen1335.immersiveEngineeringJs.kubejs.recipe.RefinerySchema;
import com.chen1335.immersiveEngineeringJs.kubejs.recipe.SawmillSchema;
import com.chen1335.immersiveEngineeringJs.kubejs.recipe.SqueezerSchema;
import com.chen1335.immersiveEngineeringJs.kubejs.recipeBuilders.GeneratorFuelHelper;
import com.chen1335.immersiveEngineeringJs.kubejs.recipeBuilders.MineralMixBuilder;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RegisterRecipeSchemasEvent;
import dev.latvian.mods.kubejs.script.BindingsEvent;

public class ImmersiveEngineeringJsPlugin extends KubeJSPlugin
{
	@Override
	public void registerRecipeSchemas(RegisterRecipeSchemasEvent event)
	{
		event.register(IEApi.ieLoc("alloy"), AlloyRecipeSchema.SCHEMA);
		event.register(IEApi.ieLoc("cloche"), ClocheRecipeSchema.SCHEMA);
		event.register(IEApi.ieLoc("blast_furnace_fuel"), BlastFurnaceFuelSchema.SCHEMA);
		event.register(IEApi.ieLoc("blast_furnace"), BlastFurnaceSchema.SCHEMA);
		event.register(IEApi.ieLoc("coke_oven"), CokeOvenSchema.SCHEMA);
		event.register(IEApi.ieLoc("fertilizer"), FertilizerSchema.SCHEMA);
		event.register(IEApi.ieLoc("blueprint"), BlueprintSchema.SCHEMA);
		event.register(IEApi.ieLoc("metal_press"), MetalPressSchema.SCHEMA);
		event.register(IEApi.ieLoc("arc_furnace"), ArcFurnaceSchema.SCHEMA);
		event.register(IEApi.ieLoc("bottling_machine"), BottlingMachineSchema.SCHEMA);
		event.register(IEApi.ieLoc("crusher"), CrusherSchema.SCHEMA);
		event.register(IEApi.ieLoc("sawmill"), SawmillSchema.SCHEMA);
		event.register(IEApi.ieLoc("fermenter"), FermenterSchema.SCHEMA);
		event.register(IEApi.ieLoc("squeezer"), SqueezerSchema.SCHEMA);
		event.register(IEApi.ieLoc("refinery"), RefinerySchema.SCHEMA);
		event.register(IEApi.ieLoc("mixer"), MixerSchema.SCHEMA);
		event.register(IEApi.ieLoc("mineral_mix"), MineralMixSchema.SCHEMA);
		event.register(IEApi.ieLoc("generator_fuel"), GeneratorFuelSchema.SCHEMA);
	}

	@Override
	public void registerBindings(BindingsEvent event)
	{
		event.add("TagOutputJS", TagOutputJS.class);
		event.add("IngredientWithSizeJS", IngredientWithSizeJS.class);
		event.add("StackWithChanceJS", StackWithChanceJS.class);
		event.add("ClocheRenderFunctionsJS", ClocheRenderFunctionsJS.class);
		event.add("MineralMixBuilder", MineralMixBuilder.class);
		event.add("GeneratorFuelHelper", GeneratorFuelHelper.class);
	}

	@Override
	public void registerEvents()
	{
		IEEvents.GROUP.register();
	}
}
