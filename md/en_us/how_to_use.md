# Immersive Engineering JS Usage (1.21.1)

## Value forms

```js
// ── output ──
'4x minecraft:iron_ingot'                             // string
'#c:ingots/iron'                                  // string (tag)
TagOutputJS.ofItem('minecraft:iron_ingot', 4)         // object
TagOutputJS.ofItemStack('minecraft:iron_ingot')       // object
TagOutputJS.ofTag('c:ingots/iron')                // object

// ── sized ingredient ──
'3x minecraft:iron_ingot'                             // string
'#c:ingots/iron'                                  // string (tag)
IngredientWithSizeJS.ofItem('minecraft:iron_ingot', 3)     // object
IngredientWithSizeJS.ofItemStack('minecraft:iron_ingot')   // object
IngredientWithSizeJS.ofTag('c:ingots/iron', 2)         // object

// ── plain ingredient ──
'minecraft:oak_log'                                   // string
'#c:ores/iron'                                     // string (tag)
Ingredient.of('minecraft:oak_log')                    // object

// ── weighted output ──
'minecraft:flint'                                     // string
'#c:dusts/wood'                                   // string (tag)
StackWithChanceJS.of('minecraft:flint', 0.5)          // object

// ── fluid output ──
'minecraft:water 1000'                                // string
Fluid.of('minecraft:water', 1000)                     // object

// ── fluid ingredient ──
'#c:water'                                        // string (tag, amount defaults to 1000)
Fluid.of('minecraft:water', 1000)                     // object

// ── item / block / tag ──
'immersiveengineering:mold_plate'                     // string
Item.of('immersiveengineering:mold_plate')            // object
'minecraft:stone'                                     // string
Blocks.STONE                                          // object
'c:ethanol'                                       // string (fluid tag)
'minecraft:is_overworld'                              // string (biome tag)
```

## alloy — Alloy Smelter

```js
// alloy: (result, input0, input1, time?)

// string
ie.alloy('minecraft:iron_ingot', '3x minecraft:iron_ingot', 'minecraft:coal')

// object
ie.alloy(
    TagOutputJS.ofItemStack('minecraft:iron_ingot'),
    IngredientWithSizeJS.ofItem('minecraft:iron_ingot', 3),
    IngredientWithSizeJS.ofItemStack('minecraft:coal'),
    200
)
```

## arc_furnace — Arc Furnace

```js
// arc_furnace: (results, input, time, energy, additives?, secondaries?, slag?)

// string
ie.arc_furnace(['minecraft:iron_ingot'], 'minecraft:iron_block', 100, 1000)

// object
ie.arc_furnace(
    [TagOutputJS.ofItemStack('minecraft:iron_ingot')],
    IngredientWithSizeJS.ofItemStack('minecraft:iron_block'),
    100, 1000,
    [IngredientWithSizeJS.ofItemStack('minecraft:coal')],
    [StackWithChanceJS.of('minecraft:flint', 0.5)],
    TagOutputJS.ofItem('minecraft:gravel', 1)
)
```

## blast_furnace — Blast Furnace

```js
// blast_furnace: (result, input, time, slag?)

// string
ie.blast_furnace('minecraft:iron_ingot', 'minecraft:iron_ore', 200, 'minecraft:gravel')

// object
ie.blast_furnace(
    TagOutputJS.ofItemStack('minecraft:iron_ingot'),
    IngredientWithSizeJS.ofItemStack('minecraft:iron_ore'),
    200,
    TagOutputJS.ofItem('minecraft:gravel', 1)
)
```

## blast_furnace_fuel — Blast Furnace Fuel

```js
// blast_furnace_fuel: (input, time)

// string
ie.blast_furnace_fuel('minecraft:coal', 1200)

// object
ie.blast_furnace_fuel(Ingredient.of('minecraft:coal'), 1200)
```

## blueprint — Engineer's Blueprint

```js
// blueprint: (category, result, inputs)

// string
ie.blueprint('components', 'minecraft:iron_ingot', ['minecraft:iron_ingot', '2x minecraft:stick'])

// object
ie.blueprint(
    'components',
    TagOutputJS.ofItemStack('minecraft:iron_ingot'),
    [
        IngredientWithSizeJS.ofItemStack('minecraft:iron_ingot'),
        IngredientWithSizeJS.ofItem('minecraft:stick', 2)
    ]
)
```

## bottling_machine — Bottling Machine

```js
// bottling_machine: (results, inputs, fluid)

// string
ie.bottling_machine(['minecraft:potion'], ['minecraft:glass_bottle'], Fluid.of('minecraft:water', 1000))

// object
ie.bottling_machine(
    [TagOutputJS.ofItemStack('minecraft:potion')],
    [IngredientWithSizeJS.ofItemStack('minecraft:glass_bottle')],
    Fluid.of('minecraft:water', 1000)
)
```

## cloche — Cloche

```js
// cloche: (results, input, soil, time, fluid?, render?)

// string
ie.cloche(['minecraft:wheat'], 'minecraft:wheat_seeds', 'minecraft:dirt', 800)

// object
ie.cloche(
    [TagOutputJS.ofItemStack('minecraft:wheat')],
    Ingredient.of('minecraft:wheat_seeds'),
    Ingredient.of('minecraft:dirt'),
    800,
    Fluid.of('minecraft:water', 1000),
    ClocheRenderFunctionsJS.ofCrop(Blocks.WHEAT)
)
```

## coke_oven — Coke Oven

```js
// coke_oven: (result, input, time, creosote)

// string
ie.coke_oven('minecraft:coal', '4x minecraft:coal_ore', 1000, 500)

// object
ie.coke_oven(
    TagOutputJS.ofItem('minecraft:coal', 1),
    IngredientWithSizeJS.ofItem('minecraft:coal_ore', 4),
    1000, 500
)
```

## crusher — Crusher

```js
// crusher: (result, input, energy?, secondaries?)

// string
ie.crusher('minecraft:gravel', 'minecraft:cobblestone', 3200, ['#c:dusts/wood'])

// object
ie.crusher(
    TagOutputJS.ofItemStack('minecraft:gravel'),
    Ingredient.of('minecraft:cobblestone'),
    3200,
    [StackWithChanceJS.of('minecraft:flint', 0.5)]
)
```

## fermenter — Fermenter

```js
// fermenter: (input, energy, fluid, result)

// string
ie.fermenter('minecraft:sugar_cane', 6400, Fluid.of('minecraft:water', 1000), 'minecraft:sugar')

// object
ie.fermenter(
    IngredientWithSizeJS.ofItemStack('minecraft:sugar_cane'),
    6400,
    Fluid.of('minecraft:water', 1000),
    TagOutputJS.ofItemStack('minecraft:sugar')
)
```

## fertilizer — Fertilizer

```js
// fertilizer: (input, growthModifier)

// string
ie.fertilizer('minecraft:bone_meal', 1.5)

// object
ie.fertilizer(Ingredient.of('minecraft:bone_meal'), 1.5)
```

## generator_fuel — Generator Fuel

```js
// generator_fuel: (burnTime, fluidTag?, fluidList?)

// string
ie.generator_fuel(1200, 'c:ethanol', ['minecraft:lava'])

// object
GeneratorFuelHelper.create(event)
    .addTag('c:ethanol', 1200)
    .addFluids(['minecraft:lava'], 1000)
```

## metal_press — Metal Press

```js
// metal_press: (result, input, mold, energy)

// string
ie.metal_press('minecraft:iron_ingot', 'minecraft:iron_ingot',
    'immersiveengineering:mold_plate', 2400)

// object
ie.metal_press(
    TagOutputJS.ofItemStack('minecraft:iron_ingot'),
    IngredientWithSizeJS.ofItem('minecraft:iron_ingot', 1),
    Item.of('immersiveengineering:mold_plate'),
    2400
)
```

## mineral_mix — Mineral Mix

```js
// mineral_mix: (ores, spoils, weight, biome_predicates, fail_chance?, sample_background?)

// string (an empty array as the 4th argument means "any biome")
ie.mineral_mix(['minecraft:iron_ore'], ['minecraft:gravel'], 30, [])

// object
MineralMixBuilder.builder()
    .biomeCondition('minecraft:is_overworld')
    .ore('minecraft:iron_ore', 0.5)
    .spoil('minecraft:gravel', 0.5)
    .addOverworldSpoils()
    .weight(30)
    .failChance(0.1)
    .background(Blocks.STONE)
    .build(event, 'my_iron_mix')
```

## mixer — Mixer

```js
// mixer: (result, fluid, inputs, energy)

// string
ie.mixer(Fluid.of('minecraft:water', 250), '#c:water', ['minecraft:sand'], 1500)

// object
ie.mixer(
    Fluid.of('minecraft:water', 250),
    Fluid.of('minecraft:water', 500),
    [IngredientWithSizeJS.ofItemStack('minecraft:sand')],
    1500
)
```

## refinery — Refinery

```js
// refinery: (result, energy, input0, catalyst?, input1?)

// string
ie.refinery('minecraft:water 1000', 2000, '#c:water')

// object
ie.refinery(
    Fluid.of('minecraft:water', 1000),
    2000,
    Fluid.of('minecraft:water', 1000),
    Ingredient.of('minecraft:dirt'),
    Fluid.of('minecraft:lava', 1000)
)
```

## sawmill — Sawmill

```js
// sawmill: (result, input, energy, stripped?, strippingSecondaries?, secondaryOutputs?)

// string
ie.sawmill('minecraft:oak_planks', 'minecraft:oak_log', 1000,
    'minecraft:stripped_oak_log', ['minecraft:oak_planks'], ['minecraft:stick'])

// object
ie.sawmill(
    TagOutputJS.ofItem('minecraft:oak_planks', 6),
    Ingredient.of('minecraft:oak_log'),
    1000,
    TagOutputJS.ofItemStack('minecraft:stripped_oak_log'),
    [TagOutputJS.ofItemStack('minecraft:oak_planks')],
    [TagOutputJS.ofItemStack('minecraft:stick')]
)
```

## squeezer — Squeezer

```js
// squeezer: (input, energy, fluid, result)

// string
ie.squeezer('minecraft:wheat_seeds', 6400, Fluid.of('minecraft:water', 100), 'minecraft:wheat')

// object
ie.squeezer(
    IngredientWithSizeJS.ofItemStack('minecraft:wheat_seeds'),
    6400,
    Fluid.of('minecraft:water', 100),
    TagOutputJS.ofItemStack('minecraft:wheat')
)
```

## IEEvents.createStructure — before a multiblock forms

```js
IEEvents.createStructure(event => {
    // event.multiblock.uniqueName looks like immersiveengineering:multiblocks/crusher
    if (event.multiblock.uniqueName.toString().includes('crusher')) {
        event.setCanceled(true) // stop the multiblock from forming
    }
})
```

## MineralMixBuilder

```js
MineralMixBuilder.builder()
    .biomeCondition('minecraft:is_overworld')
    .ore('minecraft:iron_ore', 0.5)
    .spoil('minecraft:gravel', 0.5)
    .addOverworldSpoils()
    .weight(30)
    .failChance(0.1)
    .background(Blocks.STONE)
    .build(event, 'my_iron_mix2')
```

## GeneratorFuelHelper

```js
GeneratorFuelHelper.create(event)
    .addTag('c:ethanol', 1200)
    .addFluids(['minecraft:lava'], 1000)
```
