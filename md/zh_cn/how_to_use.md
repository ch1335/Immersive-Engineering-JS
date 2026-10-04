# Immersive Engineering JS 使用文档（1.21.1）

## 值的两种写法

```js
// ── 产出 ──
'4x minecraft:iron_ingot'                             // 字符串
'#c:ingots/iron'                                  // 字符串（标签）
TagOutputJS.ofItem('minecraft:iron_ingot', 4)         // 对象
TagOutputJS.ofItemStack('minecraft:iron_ingot')       // 对象
TagOutputJS.ofTag('c:ingots/iron')                // 对象

// ── 带数量材料 ──
'3x minecraft:iron_ingot'                             // 字符串
'#c:ingots/iron'                                  // 字符串（标签）
IngredientWithSizeJS.ofItem('minecraft:iron_ingot', 3)     // 对象
IngredientWithSizeJS.ofItemStack('minecraft:iron_ingot')   // 对象
IngredientWithSizeJS.ofTag('c:ingots/iron', 2)         // 对象

// ── 普通材料 ──
'minecraft:oak_log'                                   // 字符串
'#c:ores/iron'                                     // 字符串（标签）
Ingredient.of('minecraft:oak_log')                    // 对象

// ── 加权产出 ──
'minecraft:flint'                                     // 字符串
'#c:dusts/wood'                                   // 字符串（标签）
StackWithChanceJS.of('minecraft:flint', 0.5)          // 对象

// ── 流体产出 ──
'minecraft:water 1000'                                // 字符串
Fluid.of('minecraft:water', 1000)                     // 对象

// ── 流体材料 ──
'#c:water'                                        // 字符串（标签，数量默认 1000）
Fluid.of('minecraft:water', 1000)                     // 对象

// ── 物品 / 方块 / 标签 ──
'immersiveengineering:mold_plate'                     // 字符串
Item.of('immersiveengineering:mold_plate')            // 对象
'minecraft:stone'                                     // 字符串
Blocks.STONE                                          // 对象
'c:ethanol'                                       // 字符串（流体标签）
'minecraft:is_overworld'                              // 字符串（生物群系标签）
```

## alloy 合金炉

```js
// alloy: (result, input0, input1, time?)

// 字符串
ie.alloy('minecraft:iron_ingot', '3x minecraft:iron_ingot', 'minecraft:coal')

// 对象
ie.alloy(
    TagOutputJS.ofItemStack('minecraft:iron_ingot'),
    IngredientWithSizeJS.ofItem('minecraft:iron_ingot', 3),
    IngredientWithSizeJS.ofItemStack('minecraft:coal'),
    200
)
```

## arc_furnace 电弧炉

```js
// arc_furnace: (results, input, time, energy, additives?, secondaries?, slag?)

// 字符串
ie.arc_furnace(['minecraft:iron_ingot'], 'minecraft:iron_block', 100, 1000)

// 对象
ie.arc_furnace(
    [TagOutputJS.ofItemStack('minecraft:iron_ingot')],
    IngredientWithSizeJS.ofItemStack('minecraft:iron_block'),
    100, 1000,
    [IngredientWithSizeJS.ofItemStack('minecraft:coal')],
    [StackWithChanceJS.of('minecraft:flint', 0.5)],
    TagOutputJS.ofItem('minecraft:gravel', 1)
)
```

## blast_furnace 高炉

```js
// blast_furnace: (result, input, time, slag?)

// 字符串
ie.blast_furnace('minecraft:iron_ingot', 'minecraft:iron_ore', 200, 'minecraft:gravel')

// 对象
ie.blast_furnace(
    TagOutputJS.ofItemStack('minecraft:iron_ingot'),
    IngredientWithSizeJS.ofItemStack('minecraft:iron_ore'),
    200,
    TagOutputJS.ofItem('minecraft:gravel', 1)
)
```

## blast_furnace_fuel 高炉燃料

```js
// blast_furnace_fuel: (input, time)

// 字符串
ie.blast_furnace_fuel('minecraft:coal', 1200)

// 对象
ie.blast_furnace_fuel(Ingredient.of('minecraft:coal'), 1200)
```

## blueprint 工程师蓝图

```js
// blueprint: (category, result, inputs)

// 字符串
ie.blueprint('components', 'minecraft:iron_ingot', ['minecraft:iron_ingot', '2x minecraft:stick'])

// 对象
ie.blueprint(
    'components',
    TagOutputJS.ofItemStack('minecraft:iron_ingot'),
    [
        IngredientWithSizeJS.ofItemStack('minecraft:iron_ingot'),
        IngredientWithSizeJS.ofItem('minecraft:stick', 2)
    ]
)
```

## bottling_machine 灌装机

```js
// bottling_machine: (results, inputs, fluid)

// 字符串
ie.bottling_machine(['minecraft:potion'], ['minecraft:glass_bottle'], Fluid.of('minecraft:water', 1000))

// 对象
ie.bottling_machine(
    [TagOutputJS.ofItemStack('minecraft:potion')],
    [IngredientWithSizeJS.ofItemStack('minecraft:glass_bottle')],
    Fluid.of('minecraft:water', 1000)
)
```

## cloche 钟形温室

```js
// cloche: (results, input, soil, time, fluid?, render?)

// 字符串
ie.cloche(['minecraft:wheat'], 'minecraft:wheat_seeds', 'minecraft:dirt', 800)

// 对象
ie.cloche(
    [TagOutputJS.ofItemStack('minecraft:wheat')],
    Ingredient.of('minecraft:wheat_seeds'),
    Ingredient.of('minecraft:dirt'),
    800,
    Fluid.of('minecraft:water', 1000),
    ClocheRenderFunctionsJS.ofCrop(Blocks.WHEAT)
)
```

## coke_oven 焦炉

```js
// coke_oven: (result, input, time, creosote)

// 字符串
ie.coke_oven('minecraft:coal', '4x minecraft:coal_ore', 1000, 500)

// 对象
ie.coke_oven(
    TagOutputJS.ofItem('minecraft:coal', 1),
    IngredientWithSizeJS.ofItem('minecraft:coal_ore', 4),
    1000, 500
)
```

## crusher 粉碎机

```js
// crusher: (result, input, energy?, secondaries?)

// 字符串
ie.crusher('minecraft:gravel', 'minecraft:cobblestone', 3200, ['#c:dusts/wood'])

// 对象
ie.crusher(
    TagOutputJS.ofItemStack('minecraft:gravel'),
    Ingredient.of('minecraft:cobblestone'),
    3200,
    [StackWithChanceJS.of('minecraft:flint', 0.5)]
)
```

## fermenter 发酵桶

```js
// fermenter: (input, energy, fluid, result)

// 字符串
ie.fermenter('minecraft:sugar_cane', 6400, Fluid.of('minecraft:water', 1000), 'minecraft:sugar')

// 对象
ie.fermenter(
    IngredientWithSizeJS.ofItemStack('minecraft:sugar_cane'),
    6400,
    Fluid.of('minecraft:water', 1000),
    TagOutputJS.ofItemStack('minecraft:sugar')
)
```

## fertilizer 肥料

```js
// fertilizer: (input, growthModifier)

// 字符串
ie.fertilizer('minecraft:bone_meal', 1.5)

// 对象
ie.fertilizer(Ingredient.of('minecraft:bone_meal'), 1.5)
```

## generator_fuel 发电机燃料

```js
// generator_fuel: (burnTime, fluidTag?, fluidList?)

// 字符串
ie.generator_fuel(1200, 'c:ethanol', ['minecraft:lava'])

// 对象
GeneratorFuelHelper.create(event)
    .addTag('c:ethanol', 1200)
    .addFluids(['minecraft:lava'], 1000)
```

## metal_press 金属冲压机

```js
// metal_press: (result, input, mold, energy)

// 字符串
ie.metal_press('minecraft:iron_ingot', 'minecraft:iron_ingot',
    'immersiveengineering:mold_plate', 2400)

// 对象
ie.metal_press(
    TagOutputJS.ofItemStack('minecraft:iron_ingot'),
    IngredientWithSizeJS.ofItem('minecraft:iron_ingot', 1),
    Item.of('immersiveengineering:mold_plate'),
    2400
)
```

## mineral_mix 矿脉混合

```js
// mineral_mix: (ores, spoils, weight, biome_predicates, fail_chance?, sample_background?)

// 字符串（第四个参数为空数组表示不限生物群系）
ie.mineral_mix(['minecraft:iron_ore'], ['minecraft:gravel'], 30, [])

// 对象
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

## mixer 混合器

```js
// mixer: (result, fluid, inputs, energy)

// 字符串
ie.mixer(Fluid.of('minecraft:water', 250), '#c:water', ['minecraft:sand'], 1500)

// 对象
ie.mixer(
    Fluid.of('minecraft:water', 250),
    Fluid.of('minecraft:water', 500),
    [IngredientWithSizeJS.ofItemStack('minecraft:sand')],
    1500
)
```

## refinery 蒸馏塔

```js
// refinery: (result, energy, input0, catalyst?, input1?)

// 字符串
ie.refinery('minecraft:water 1000', 2000, '#c:water')

// 对象
ie.refinery(
    Fluid.of('minecraft:water', 1000),
    2000,
    Fluid.of('minecraft:water', 1000),
    Ingredient.of('minecraft:dirt'),
    Fluid.of('minecraft:lava', 1000)
)
```

## sawmill 锯木机

```js
// sawmill: (result, input, energy, stripped?, strippingSecondaries?, secondaryOutputs?)

// 字符串
ie.sawmill('minecraft:oak_planks', 'minecraft:oak_log', 1000,
    'minecraft:stripped_oak_log', ['minecraft:oak_planks'], ['minecraft:stick'])

// 对象
ie.sawmill(
    TagOutputJS.ofItem('minecraft:oak_planks', 6),
    Ingredient.of('minecraft:oak_log'),
    1000,
    TagOutputJS.ofItemStack('minecraft:stripped_oak_log'),
    [TagOutputJS.ofItemStack('minecraft:oak_planks')],
    [TagOutputJS.ofItemStack('minecraft:stick')]
)
```

## squeezer 压榨机

```js
// squeezer: (input, energy, fluid, result)

// 字符串
ie.squeezer('minecraft:wheat_seeds', 6400, Fluid.of('minecraft:water', 100), 'minecraft:wheat')

// 对象
ie.squeezer(
    IngredientWithSizeJS.ofItemStack('minecraft:wheat_seeds'),
    6400,
    Fluid.of('minecraft:water', 100),
    TagOutputJS.ofItemStack('minecraft:wheat')
)
```

## IEEvents.createStructure 多方块成型前

```js
IEEvents.createStructure(event => {
    // event.multiblock.uniqueName 形如 immersiveengineering:multiblocks/crusher
    if (event.multiblock.uniqueName.toString().includes('crusher')) {
        event.setCanceled(true) // 取消成型
    }
})
```

## MineralMixBuilder 矿脉混合构建器

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

## GeneratorFuelHelper 发电机燃料构建器

```js
GeneratorFuelHelper.create(event)
    .addTag('c:ethanol', 1200)
    .addFluids(['minecraft:lava'], 1000)
```
