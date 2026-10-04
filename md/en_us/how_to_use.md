# Immersive Engineering JS Usage (1.20.1)

## Value forms

```js
// ── output ──
'4x minecraft:iron_ingot'                            // string
'#forge:ingots/iron'                                 // string (tag)
TagOutputJS.ofItem('minecraft:iron_ingot', 4)        // object
TagOutputJS.ofItemStack('minecraft:iron_ingot')      // object
TagOutputJS.ofTag('#forge:ingots/iron')              // object

// ── sized ingredient ──
'3x minecraft:iron_ingot'                            // string
'#forge:ingots/iron'                                 // string (tag)
IngredientWithSizeJS.ofItem('minecraft:iron_ingot', 3)    // object
IngredientWithSizeJS.ofItemStack('minecraft:iron_ingot')  // object
IngredientWithSizeJS.ofTag('#forge:ingots/iron', 2)       // object

// ── plain ingredient ──
'minecraft:oak_log'                                  // string
'#minecraft:logs'                                    // string (tag)
Ingredient.of('minecraft:oak_log')                   // object

// ── weighted output ──
'minecraft:flint'                                    // string (weight 1.0)
StackWithChanceJS.of(Item.of('minecraft:flint'), 0.5)     // object

// ── fluid output ──
'minecraft:water 1000'                               // string
{ fluid: 'minecraft:water', amount: 1000 }           // object

// ── fluid ingredient (tags only) ──
'#forge:water'                                       // string
{ tag: 'forge:water', amount: 1000 }                 // object

// ── item / block / dimension ──
'immersiveengineering:mold_plate'                    // string
Item.of('immersiveengineering:mold_plate')           // object
'minecraft:stone'                                    // string
Blocks.STONE                                         // object
'minecraft:overworld'                                // string
```

## alloy: (result, input0, input1, time?) — Alloy Smelter

```js
// string
ie.alloy('minecraft:iron_ingot', '3x minecraft:iron_ingot', 'minecraft:coal')

// object
ie.alloy(
    TagOutputJS.ofItemStack('minecraft:iron_ingot'),
    IngredientWithSizeJS.ofItemStack('minecraft:copper_ingot'),
    IngredientWithSizeJS.ofItem('minecraft:gold_ingot', 3),
    200
)
```

## arc_furnace: (results, input, time, energy, additives?, secondaries?, slag?) — Arc Furnace

```js
// string
ie.arc_furnace(['minecraft:iron_ingot'], 'minecraft:iron_block', 100, 1000)

// object
ie.arc_furnace(
    [TagOutputJS.ofItemStack('minecraft:iron_ingot')],
    IngredientWithSizeJS.ofItemStack('minecraft:iron_block'),
    100, 1000,
    [IngredientWithSizeJS.ofItem('minecraft:coal', 1)],
    [StackWithChanceJS.of(Item.of('minecraft:flint'), 0.1)],
    TagOutputJS.ofItem('minecraft:gravel')
)
```

## blast_furnace: (result, input, time?, slag?) — Blast Furnace

```js
// string
ie.blast_furnace('minecraft:iron_ingot', 'minecraft:iron_ore', 200, 'minecraft:gravel')

// object
ie.blast_furnace(
    TagOutputJS.ofItemStack('minecraft:iron_ingot'),
    IngredientWithSizeJS.ofItemStack('minecraft:iron_ore'),
    200,
    TagOutputJS.ofItem('minecraft:gravel')
)
```

## blast_furnace_fuel: (input, time) — Blast Furnace Fuel

```js
// string
ie.blast_furnace_fuel('minecraft:coal', 1200)

// object (a single entry only; use a tag for several items)
ie.blast_furnace_fuel(Ingredient.of('minecraft:coal'), 1200)
```

## blueprint: (category, result, inputs) — Engineer's Blueprint

```js
// string
ie.blueprint('components', 'minecraft:iron_ingot', ['minecraft:iron_ingot', 'minecraft:stick'])

// object
ie.blueprint(
    'components',
    TagOutputJS.ofItemStack('minecraft:iron_ingot'),
    [
        IngredientWithSizeJS.ofItem('minecraft:iron_ingot', 1),
        IngredientWithSizeJS.ofItem('minecraft:stick', 2)
    ]
)
```

## bottling_machine: (results, inputs, fluid) — Bottling Machine

```js
// string
ie.bottling_machine(['minecraft:potion'], ['minecraft:glass_bottle'], '#forge:water')

// object
ie.bottling_machine(
    [TagOutputJS.ofItemStack('minecraft:potion')],
    [IngredientWithSizeJS.ofItemStack('minecraft:glass_bottle')],
    { tag: 'forge:water', amount: 1000 }
)
```

## cloche: (results, input, soil, time, render?) — Cloche

```js
// string (the renderer is inferred from the seed block when omitted)
ie.cloche(['minecraft:wheat'], 'minecraft:wheat_seeds', 'minecraft:dirt', 800)

// object
ie.cloche(
    [TagOutputJS.ofItemStack('minecraft:wheat')],
    Ingredient.of('minecraft:wheat_seeds'),
    Ingredient.of('minecraft:dirt'),
    800,
    ClocheRenderFunctionsJS.ofCrop(Blocks.WHEAT)
)
```

## coke_oven: (result, input, time, creosote) — Coke Oven

```js
// string
ie.coke_oven('minecraft:coal', 'minecraft:coal_ore', 1000, 500)

// object
ie.coke_oven(
    TagOutputJS.ofItemStack('minecraft:coal'),
    IngredientWithSizeJS.ofItemStack('minecraft:coal_ore'),
    1000, 500
)
```

## crusher: (result, input, energy?, secondaries?) — Crusher

```js
// string
ie.crusher('minecraft:gravel', 'minecraft:cobblestone', 3200, ['#forge:dusts/wood'])

// object (a single entry only; use a tag for several items)
ie.crusher(
    TagOutputJS.ofItemStack('minecraft:gravel'),
    Ingredient.of('minecraft:cobblestone'),
    3200,
    [StackWithChanceJS.of(Item.of('minecraft:flint'), 0.1)]
)
```

## fermenter: (input, energy, fluid?, result?) — Fermenter

```js
// string
ie.fermenter('minecraft:sugar_cane', 4000, 'minecraft:water 1000', 'minecraft:sugar')

// object
ie.fermenter(
    IngredientWithSizeJS.ofItemStack('minecraft:sugar_cane'),
    4000,
    { fluid: 'minecraft:water', amount: 1000 },
    TagOutputJS.ofItemStack('minecraft:sugar')
)
```

## fertilizer: (input, growthModifier) — Fertilizer

```js
// string
ie.fertilizer('minecraft:bone_meal', 1.5)

// object
ie.fertilizer(Ingredient.of('minecraft:bone_meal'), 1.5)
```

## generator_fuel: (burnTime, fluidTag) — Generator Fuel

```js
// string
ie.generator_fuel(1200, 'forge:ethanol')

// object
GeneratorFuelHelper.create(event).addTag('forge:ethanol', 1200)
```

## metal_press: (result, input, mold, energy) — Metal Press

```js
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

## mineral_mix: (ores, spoils, weight, dimensions, fail_chance?, sample_background?) — Mineral Mix

```js
// string (weight defaults to 1.0)
ie.mineral_mix(
    ['minecraft:iron_ore'],
    ['minecraft:gravel'],
    30,
    ['minecraft:overworld'],
    0,
    'minecraft:stone'
)

// object
MineralMixBuilder.builder()
    .dimensionOverworld()
    .ore('minecraft:iron_ore', 0.5)
    .spoil('minecraft:gravel', 0.5)
    .addOverworldSpoils()
    .weight(30)
    .failChance(0.1)
    .background(Blocks.STONE)
    .build(event, 'my_iron_mix')
```

## mixer: (result, fluid, inputs, energy) — Mixer

```js
// string
ie.mixer('minecraft:water 1000', '#forge:water', ['minecraft:sand'], 2000)

// object
ie.mixer(
    { fluid: 'minecraft:water', amount: 1000 },
    { tag: 'forge:water', amount: 1000 },
    [IngredientWithSizeJS.ofItemStack('minecraft:sand')],
    2000
)
```

## refinery: (result, energy, input0, catalyst?, input1?) — Refinery

```js
// string (the catalyst and the second fluid input are optional)
ie.refinery('minecraft:water 1000', 2000, '#forge:water')

// object
ie.refinery(
    { fluid: 'minecraft:water', amount: 1000 },
    2000,
    { tag: 'forge:water', amount: 1000 },
    Ingredient.of('minecraft:dirt'),
    { tag: 'forge:lava', amount: 1000 }
)
```

## sawmill: (result, input, energy, stripped?, strippingSecondaries?, secondaryOutputs?) — Sawmill

```js
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

## squeezer: (input, energy, fluid?, result?) — Squeezer

```js
// string
ie.squeezer('minecraft:wheat_seeds', 2000, 'minecraft:water 100', 'minecraft:wheat')

// object
ie.squeezer(
    IngredientWithSizeJS.ofItemStack('minecraft:wheat_seeds'),
    2000,
    { fluid: 'minecraft:water', amount: 100 },
    TagOutputJS.ofItemStack('minecraft:wheat')
)
```

## IEEvents.createStructure — before a multiblock forms

```js
IEEvents.createStructure(event => {
    // event.multiblock.uniqueName looks like immersiveengineering:multiblocks/crusher
    if (event.multiblock.uniqueName.toString().includes('crusher') && !event.player.isCreative()) {
        event.cancel()
    }
})
```

## MineralMixBuilder

```js
MineralMixBuilder.builder()
    .dimensionNether()
    .ore('minecraft:nether_quartz_ore', 0.5)
    .spoil('minecraft:netherrack', 0.5)
    .addNetherSpoils()
    .weight(20)
    .failChance(0.1)
    .background(Blocks.NETHERRACK)
    .build(event, 'my_quartz_mix')
```

## GeneratorFuelHelper

```js
GeneratorFuelHelper.create(event)
    .addTag('forge:ethanol', 1200)
    .addTag('forge:biodiesel', 800)
```
