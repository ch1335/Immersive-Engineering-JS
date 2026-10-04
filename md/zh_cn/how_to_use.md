# Immersive Engineering JS 使用文档（1.20.1）

## 值的两种写法

```js
// ── 产出 ──
'4x minecraft:iron_ingot'                            // 字符串
'#forge:ingots/iron'                                 // 字符串（标签）
TagOutputJS.ofItem('minecraft:iron_ingot', 4)        // 对象
TagOutputJS.ofItemStack('minecraft:iron_ingot')      // 对象
TagOutputJS.ofTag('#forge:ingots/iron')              // 对象

// ── 带数量材料 ──
'3x minecraft:iron_ingot'                            // 字符串
'#forge:ingots/iron'                                 // 字符串（标签）
IngredientWithSizeJS.ofItem('minecraft:iron_ingot', 3)    // 对象
IngredientWithSizeJS.ofItemStack('minecraft:iron_ingot')  // 对象
IngredientWithSizeJS.ofTag('#forge:ingots/iron', 2)       // 对象

// ── 普通材料 ──
'minecraft:oak_log'                                  // 字符串
'#minecraft:logs'                                    // 字符串（标签）
Ingredient.of('minecraft:oak_log')                   // 对象

// ── 加权产出 ──
'minecraft:flint'                                    // 字符串（权重 1.0）
StackWithChanceJS.of(Item.of('minecraft:flint'), 0.5)     // 对象

// ── 流体产出 ──
'minecraft:water 1000'                               // 字符串
{ fluid: 'minecraft:water', amount: 1000 }           // 对象

// ── 流体材料（只能写标签） ──
'#forge:water'                                       // 字符串
{ tag: 'forge:water', amount: 1000 }                 // 对象

// ── 物品 / 方块 / 维度 ──
'immersiveengineering:mold_plate'                    // 字符串
Item.of('immersiveengineering:mold_plate')           // 对象
'minecraft:stone'                                    // 字符串
Blocks.STONE                                         // 对象
'minecraft:overworld'                                // 字符串
```

## alloy: (result, input0, input1, time?) — 合金炉

```js
// 字符串
ie.alloy('minecraft:iron_ingot', '3x minecraft:iron_ingot', 'minecraft:coal')

// 对象
ie.alloy(
    TagOutputJS.ofItemStack('minecraft:iron_ingot'),
    IngredientWithSizeJS.ofItemStack('minecraft:copper_ingot'),
    IngredientWithSizeJS.ofItem('minecraft:gold_ingot', 3),
    200
)
```

## arc_furnace: (results, input, time, energy, additives?, secondaries?, slag?) — 电弧炉

```js
// 字符串
ie.arc_furnace(['minecraft:iron_ingot'], 'minecraft:iron_block', 100, 1000)

// 对象
ie.arc_furnace(
    [TagOutputJS.ofItemStack('minecraft:iron_ingot')],
    IngredientWithSizeJS.ofItemStack('minecraft:iron_block'),
    100, 1000,
    [IngredientWithSizeJS.ofItem('minecraft:coal', 1)],
    [StackWithChanceJS.of(Item.of('minecraft:flint'), 0.1)],
    TagOutputJS.ofItem('minecraft:gravel')
)
```

## blast_furnace: (result, input, time?, slag?) — 高炉

```js
// 字符串
ie.blast_furnace('minecraft:iron_ingot', 'minecraft:iron_ore', 200, 'minecraft:gravel')

// 对象
ie.blast_furnace(
    TagOutputJS.ofItemStack('minecraft:iron_ingot'),
    IngredientWithSizeJS.ofItemStack('minecraft:iron_ore'),
    200,
    TagOutputJS.ofItem('minecraft:gravel')
)
```

## blast_furnace_fuel: (input, time) — 高炉燃料

```js
// 字符串
ie.blast_furnace_fuel('minecraft:coal', 1200)

// 对象（材料只能用单个条目，标签除外）
ie.blast_furnace_fuel(Ingredient.of('minecraft:coal'), 1200)
```

## blueprint: (category, result, inputs) — 工程师蓝图

```js
// 字符串
ie.blueprint('components', 'minecraft:iron_ingot', ['minecraft:iron_ingot', 'minecraft:stick'])

// 对象
ie.blueprint(
    'components',
    TagOutputJS.ofItemStack('minecraft:iron_ingot'),
    [
        IngredientWithSizeJS.ofItem('minecraft:iron_ingot', 1),
        IngredientWithSizeJS.ofItem('minecraft:stick', 2)
    ]
)
```

## bottling_machine: (results, inputs, fluid) — 灌装机

```js
// 字符串
ie.bottling_machine(['minecraft:potion'], ['minecraft:glass_bottle'], '#forge:water')

// 对象
ie.bottling_machine(
    [TagOutputJS.ofItemStack('minecraft:potion')],
    [IngredientWithSizeJS.ofItemStack('minecraft:glass_bottle')],
    { tag: 'forge:water', amount: 1000 }
)
```

## cloche: (results, input, soil, time, render?) — 钟形温室

```js
// 字符串（渲染方式省略时按种子方块推断）
ie.cloche(['minecraft:wheat'], 'minecraft:wheat_seeds', 'minecraft:dirt', 800)

// 对象
ie.cloche(
    [TagOutputJS.ofItemStack('minecraft:wheat')],
    Ingredient.of('minecraft:wheat_seeds'),
    Ingredient.of('minecraft:dirt'),
    800,
    ClocheRenderFunctionsJS.ofCrop(Blocks.WHEAT)
)
```

## coke_oven: (result, input, time, creosote) — 焦炉

```js
// 字符串
ie.coke_oven('minecraft:coal', 'minecraft:coal_ore', 1000, 500)

// 对象
ie.coke_oven(
    TagOutputJS.ofItemStack('minecraft:coal'),
    IngredientWithSizeJS.ofItemStack('minecraft:coal_ore'),
    1000, 500
)
```

## crusher: (result, input, energy?, secondaries?) — 粉碎机

```js
// 字符串
ie.crusher('minecraft:gravel', 'minecraft:cobblestone', 3200, ['#forge:dusts/wood'])

// 对象（材料只能用单个条目，标签除外）
ie.crusher(
    TagOutputJS.ofItemStack('minecraft:gravel'),
    Ingredient.of('minecraft:cobblestone'),
    3200,
    [StackWithChanceJS.of(Item.of('minecraft:flint'), 0.1)]
)
```

## fermenter: (input, energy, fluid?, result?) — 发酵桶

```js
// 字符串
ie.fermenter('minecraft:sugar_cane', 4000, 'minecraft:water 1000', 'minecraft:sugar')

// 对象
ie.fermenter(
    IngredientWithSizeJS.ofItemStack('minecraft:sugar_cane'),
    4000,
    { fluid: 'minecraft:water', amount: 1000 },
    TagOutputJS.ofItemStack('minecraft:sugar')
)
```

## fertilizer: (input, growthModifier) — 肥料

```js
// 字符串
ie.fertilizer('minecraft:bone_meal', 1.5)

// 对象
ie.fertilizer(Ingredient.of('minecraft:bone_meal'), 1.5)
```

## generator_fuel: (burnTime, fluidTag) — 发电机燃料

```js
// 字符串
ie.generator_fuel(1200, 'forge:ethanol')

// 对象
GeneratorFuelHelper.create(event).addTag('forge:ethanol', 1200)
```

## metal_press: (result, input, mold, energy) — 金属冲压机

```js
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

## mineral_mix: (ores, spoils, weight, dimensions, fail_chance?, sample_background?) — 矿脉混合

```js
// 字符串（权重默认 1.0）
ie.mineral_mix(
    ['minecraft:iron_ore'],
    ['minecraft:gravel'],
    30,
    ['minecraft:overworld'],
    0,
    'minecraft:stone'
)

// 对象
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

## mixer: (result, fluid, inputs, energy) — 混合器

```js
// 字符串
ie.mixer('minecraft:water 1000', '#forge:water', ['minecraft:sand'], 2000)

// 对象
ie.mixer(
    { fluid: 'minecraft:water', amount: 1000 },
    { tag: 'forge:water', amount: 1000 },
    [IngredientWithSizeJS.ofItemStack('minecraft:sand')],
    2000
)
```

## refinery: (result, energy, input0, catalyst?, input1?) — 蒸馏塔

```js
// 字符串（催化剂与第二路流体可省略）
ie.refinery('minecraft:water 1000', 2000, '#forge:water')

// 对象
ie.refinery(
    { fluid: 'minecraft:water', amount: 1000 },
    2000,
    { tag: 'forge:water', amount: 1000 },
    Ingredient.of('minecraft:dirt'),
    { tag: 'forge:lava', amount: 1000 }
)
```

## sawmill: (result, input, energy, stripped?, strippingSecondaries?, secondaryOutputs?) — 锯木机

```js
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

## squeezer: (input, energy, fluid?, result?) — 压榨机

```js
// 字符串
ie.squeezer('minecraft:wheat_seeds', 2000, 'minecraft:water 100', 'minecraft:wheat')

// 对象
ie.squeezer(
    IngredientWithSizeJS.ofItemStack('minecraft:wheat_seeds'),
    2000,
    { fluid: 'minecraft:water', amount: 100 },
    TagOutputJS.ofItemStack('minecraft:wheat')
)
```

## IEEvents.createStructure 多方块成型前

```js
IEEvents.createStructure(event => {
    // event.multiblock.uniqueName 形如 immersiveengineering:multiblocks/crusher
    if (event.multiblock.uniqueName.toString().includes('crusher') && !event.player.isCreative()) {
        event.cancel()
    }
})
```

## MineralMixBuilder 矿脉混合构建器

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

## GeneratorFuelHelper 发电机燃料构建器

```js
GeneratorFuelHelper.create(event)
    .addTag('forge:ethanol', 1200)
    .addTag('forge:biodiesel', 800)
```
