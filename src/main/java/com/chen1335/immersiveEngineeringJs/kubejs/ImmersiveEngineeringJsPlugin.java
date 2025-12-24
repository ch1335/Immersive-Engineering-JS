package com.chen1335.immersiveEngineeringJs.kubejs;

import blusunrize.immersiveengineering.api.IEApi;
import com.chen1335.immersiveEngineeringJs.api.crafting.ClocheRenderFunctionsJS;
import com.chen1335.immersiveEngineeringJs.api.crafting.IngredientWithSizeJS;
import com.chen1335.immersiveEngineeringJs.api.crafting.StackWithChanceJS;
import com.chen1335.immersiveEngineeringJs.api.crafting.TagOutputJS;
import com.chen1335.immersiveEngineeringJs.kubejs.event.IEEvents;
import com.chen1335.immersiveEngineeringJs.kubejs.recipe.*;
import com.chen1335.immersiveEngineeringJs.kubejs.recipeBuilders.GeneratorFuelHelper;
import com.chen1335.immersiveEngineeringJs.kubejs.recipeBuilders.MineralMixBuilder;
import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchemaRegistry;
import dev.latvian.mods.kubejs.script.BindingRegistry;

public class ImmersiveEngineeringJsPlugin implements KubeJSPlugin {


    @Override
    public void registerRecipeSchemas(RecipeSchemaRegistry registry) {
        registry.register(IEApi.ieLoc("alloy"), AlloyRecipeSchema.SCHEMA);
        registry.register(IEApi.ieLoc("cloche"), ClocheRecipeSchema.SCHEMA);
        registry.register(IEApi.ieLoc("blast_furnace_fuel"), BlastFurnaceFuelSchema.SCHEMA);
        registry.register(IEApi.ieLoc("blast_furnace"), BlastFurnaceSchema.SCHEMA);
        registry.register(IEApi.ieLoc("coke_oven"), CokeOvenSchema.SCHEMA);
        registry.register(IEApi.ieLoc("fertilizer"), FertilizerSchema.SCHEMA);
        registry.register(IEApi.ieLoc("blueprint"), BlueprintSchema.SCHEMA);
        registry.register(IEApi.ieLoc("metal_press"), MetalPressSchema.SCHEMA);
        registry.register(IEApi.ieLoc("arc_furnace"), ArcFurnaceSchema.SCHEMA);
        registry.register(IEApi.ieLoc("bottling_machine"), BottlingMachineSchema.SCHEMA);
        registry.register(IEApi.ieLoc("crusher"), CrusherSchema.SCHEMA);
        registry.register(IEApi.ieLoc("sawmill"), SawmillSchema.SCHEMA);
        registry.register(IEApi.ieLoc("fermenter"), FermenterSchema.SCHEMA);
        registry.register(IEApi.ieLoc("squeezer"), SqueezerSchema.SCHEMA);
        registry.register(IEApi.ieLoc("refinery"), RefinerySchema.SCHEMA);
        registry.register(IEApi.ieLoc("mixer"), MixerSchema.SCHEMA);
        registry.register(IEApi.ieLoc("mineral_mix"), MineralMixSchema.SCHEMA);
        registry.register(IEApi.ieLoc("generator_fuel"), GeneratorFuelSchema.SCHEMA);
    }

    @Override
    public void registerBindings(BindingRegistry bindings) {
        bindings.add("TagOutputJS", TagOutputJS.class);
        bindings.add("IngredientWithSizeJS", IngredientWithSizeJS.class);
        bindings.add("StackWithChanceJS", StackWithChanceJS.class);
        bindings.add("ClocheRenderFunctionsJS", ClocheRenderFunctionsJS.class);
        bindings.add("MineralMixBuilder", MineralMixBuilder.class);
        bindings.add("GeneratorFuelHelper", GeneratorFuelHelper.class);
    }

    @Override
    public void registerEvents(EventGroupRegistry registry) {
        registry.register(IEEvents.GROUP);
    }
}
