// Verification script for the back-ported RecipeComponent#wrap overrides.
// Focus: loose script values (plain id strings, "#tag" strings, "3x id" counts) must be accepted
// by the Immersive Engineering components, and nothing about the existing recipe types may regress.
//
// Note: fluids are passed as Fluid.of(...) because KubeJS 2101 only accepts a Fluid/FluidStack or a
// fluid string for fluid components - a plain {fluid: "...", amount: n} object is not supported.

ServerEvents.recipes(event => {
    const ie = event.recipes.immersiveengineering

    // ---- string based values: what the wrap back-port is about ----

    // tag result + string ingredients (TAG_OUTPUT.wrap / INGREDIENT_WITH_SIZE.wrap)
    ie.alloy('#forge:ingots/iron', 'minecraft:iron_ingot', 'minecraft:coal')

    // "3x id" count syntax on an ingredient, plus an explicit time
    ie.alloy('minecraft:gold_ingot', '3x minecraft:gold_ingot', 'minecraft:coal', 100)

    // chance list built from plain strings (CHANCE_LIST.wrap -> STACK_WITH_CHANCE.wrap)
    ie.crusher('minecraft:gravel', 'minecraft:cobblestone', 3200,
        ['minecraft:flint', '#forge:dusts/wood'])

    // tagged lists for results, additives and secondaries
    ie.arc_furnace(['minecraft:iron_ingot'], 'minecraft:iron_block', 100, 1000,
        ['minecraft:coal'], ['minecraft:flint'], 'minecraft:gravel')

    ie.blueprint('components', 'minecraft:iron_ingot', ['minecraft:iron_ingot', '#forge:ingots/iron'])

    // ITEM.wrap from a string
    ie.metal_press('minecraft:iron_ingot', 'minecraft:iron_ingot', 'immersiveengineering:mold_plate', 2400)

    // cloche results are a chance list; a single string must expand to a list
    ie.cloche(['minecraft:wheat'], 'minecraft:wheat_seeds', 'minecraft:dirt', 800)

    // sawmill secondaries are lists of TagOutput
    ie.sawmill('minecraft:oak_planks', 'minecraft:oak_log', 1000, 'minecraft:stripped_oak_log',
        ['minecraft:oak_planks'], ['minecraft:stick'])

    // ---- remaining types, to confirm nothing regressed ----

    ie.blast_furnace('minecraft:iron_ingot', 'minecraft:iron_ore', 200, 'minecraft:gravel')
    ie.blast_furnace_fuel('minecraft:coal', 1200)
    ie.bottling_machine(['minecraft:potion'], ['minecraft:glass_bottle'], Fluid.of('minecraft:water', 1000))
    ie.coke_oven('minecraft:coal', 'minecraft:coal_ore', 1000, 500)
    ie.fermenter('minecraft:sugar_cane', 4000, Fluid.of('minecraft:water', 1000), 'minecraft:sugar')
    ie.fertilizer('minecraft:bone_meal', 1.5)
    ie.generator_fuel(1200, 'forge:ethanol', [])
    ie.refinery(Fluid.of('minecraft:water', 1), 100, Fluid.of('minecraft:lava', 1), 'minecraft:air', Fluid.of('immersiveengineering:biodiesel', 1))
    ie.squeezer('minecraft:wheat_seeds', 2000, Fluid.of('minecraft:water', 100), 'minecraft:wheat')
})

IEEvents.createStructure(event => {
    // only checks that the event group still registers
})
