// End-to-end check for the Immersive Engineering JS 1.20.1 port.
// Every registered recipe type is exercised once so that Immersive Engineering's own
// serializers have to parse the JSON this mod generates.

ServerEvents.recipes(event => {
    const ie = event.recipes.immersiveengineering

    // alloy: (result, input0, input1, time?)
    ie.alloy('minecraft:iron_ingot', 'minecraft:iron_ingot', 'minecraft:coal', 200)

    // arc_furnace: (results[], input, time, energy, additives?, secondaries?, slag?)
    ie.arc_furnace(['minecraft:iron_ingot'], 'minecraft:iron_block', 100, 1000)

    // blast_furnace: (result, input, time?, slag?)
    ie.blast_furnace('minecraft:iron_ingot', 'minecraft:iron_ore', 200, 'minecraft:gravel')

    // blast_furnace_fuel: (input, time)
    ie.blast_furnace_fuel('minecraft:coal', 1200)

    // blueprint: (category, result, inputs[])
    ie.blueprint('components', 'minecraft:iron_ingot', ['minecraft:iron_ingot'])

    // bottling_machine: (results[], inputs[], fluid)
    ie.bottling_machine(['minecraft:potion'], ['minecraft:glass_bottle'], { tag: 'forge:water', amount: 1000 })

    // cloche: (results[], seed, soil, time, render?)
    ie.cloche(['minecraft:wheat'], 'minecraft:wheat_seeds', 'minecraft:dirt', 800,
        ClocheRenderFunctionsJS.ofCrop('minecraft:wheat'))

    // coke_oven: (result, input, time, creosote)
    ie.coke_oven('minecraft:coal', 'minecraft:coal_ore', 1000, 500)

    // crusher: (result, input, energy?, secondaries?)
    ie.crusher('minecraft:gravel', 'minecraft:cobblestone', 3200,
        [StackWithChanceJS.of(Item.of('minecraft:flint'), 0.1)])

    // fertilizer: (input, growthModifier)
    ie.fertilizer('minecraft:bone_meal', 1.5)

    // fermenter: (input, energy, fluid?, result?)
    ie.fermenter('minecraft:sugar_cane', 4000, { fluid: 'minecraft:water', amount: 1000 }, 'minecraft:sugar')

    // generator_fuel: (burnTime, fluidTag)
    ie.generator_fuel(1200, 'forge:ethanol')

    // metal_press: (result, input, mold, energy)
    ie.metal_press('minecraft:iron_ingot', 'minecraft:iron_ingot', 'immersiveengineering:mold_plate', 2400)

    // mineral_mix: (ores[], spoils[], weight, dimensions[], failChance?, background?)
    ie.mineral_mix(
        [StackWithChanceJS.of(Item.of('minecraft:iron_ore'), 0.5)],
        [StackWithChanceJS.of(Item.of('minecraft:gravel'), 1.0)],
        30,
        ['minecraft:overworld'],
        0,
        'minecraft:stone'
    )

    // mixer: (result, fluid, inputs[], energy)
    ie.mixer({ fluid: 'minecraft:water', amount: 1000 }, { tag: 'forge:water', amount: 1000 },
        ['minecraft:sand'], 2000)

    // refinery: (result, energy, input0, catalyst?, input1?)
    ie.refinery({ fluid: 'minecraft:water', amount: 1000 }, 2000, { tag: 'forge:water', amount: 1000 })

    // sawmill: (result, input, energy, stripped?, strippingSecondaries?, secondaryOutputs?)
    ie.sawmill('minecraft:oak_planks', 'minecraft:oak_log', 1000, 'minecraft:stripped_oak_log',
        ['minecraft:oak_planks'], ['minecraft:stick'])

    // squeezer: (input, energy, fluid?, result?)
    ie.squeezer('minecraft:wheat_seeds', 2000, { fluid: 'minecraft:water', amount: 100 }, 'minecraft:wheat')

    // Tag based results and secondaries, using the same "#tag" syntax as KubeJS ingredients.
    ie.alloy('#forge:ingots/iron', 'minecraft:iron_ingot', 'minecraft:coal', 200)
    ie.crusher('minecraft:gravel', 'minecraft:cobblestone', 3200, ['#forge:dusts/wood'])
})

// The structure event must be registered without errors.
IEEvents.createStructure(event => {
    // intentionally does nothing; this only checks that the event group loads
})
