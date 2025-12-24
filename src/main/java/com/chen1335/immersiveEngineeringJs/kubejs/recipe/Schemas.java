package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import blusunrize.immersiveengineering.api.IEApi;
import blusunrize.immersiveengineering.api.crafting.*;
import blusunrize.immersiveengineering.api.excavator.MineralMix;
import blusunrize.immersiveengineering.common.crafting.serializers.MineralMixSerializer;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentType;
import dev.latvian.mods.rhino.type.TypeInfo;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;

import java.util.List;
import java.util.Set;

public class Schemas {
    public static final RecipeComponent<TagOutput> TAG_OUTPUT = new RecipeComponent<>() {

        @Override
        public RecipeComponentType<?> type() {
            return RecipeComponentType.Unit.unit(IEApi.ieLoc("tag_output"), TAG_OUTPUT);
        }

        @Override
        public Codec<TagOutput> codec() {
            return TagOutput.CODECS.codec();
        }

        @Override
        public TypeInfo typeInfo() {
            return TypeInfo.of(TagOutput.class);
        }

        @Override
        public boolean allowEmpty() {
            return true;
        }
    };

    public static final RecipeComponent<IngredientWithSize> INGREDIENT_WITH_SIZE = new RecipeComponent<>() {

        @Override
        public RecipeComponentType<?> type() {
            return RecipeComponentType.Unit.unit(IEApi.ieLoc("ingredient_with_size"), INGREDIENT_WITH_SIZE);
        }

        @Override
        public Codec<IngredientWithSize> codec() {
            return IngredientWithSize.CODECS.codec();
        }

        @Override
        public TypeInfo typeInfo() {
            return TypeInfo.of(IngredientWithSize.class);
        }

        @Override
        public boolean allowEmpty() {
            return true;
        }
    };

    public static final RecipeComponent<StackWithChance> STACK_WITH_CHANCE = new RecipeComponent<>() {


        @Override
        public RecipeComponentType<?> type() {
            return RecipeComponentType.Unit.unit(IEApi.ieLoc("stack_with_chance"), STACK_WITH_CHANCE);
        }

        @Override
        public Codec<StackWithChance> codec() {
            return StackWithChance.CODECS.codec();
        }

        @Override
        public TypeInfo typeInfo() {
            return TypeInfo.of(StackWithChance.class);
        }

        @Override
        public boolean allowEmpty() {
            return true;
        }
    };

    public static final RecipeComponent<List<StackWithChance>> CHANCE_LIST = new RecipeComponent<>() {

        @Override
        public RecipeComponentType<?> type() {
            return RecipeComponentType.Unit.unit(IEApi.ieLoc("stack_with_chance"), STACK_WITH_CHANCE);
        }

        @Override
        public Codec<List<StackWithChance>> codec() {
            return IERecipeSerializer.CHANCE_LIST_CODEC;
        }

        @Override
        public TypeInfo typeInfo() {
            return TypeInfo.RAW_LIST.withParams(TypeInfo.of(StackWithChance.class));
        }

        @Override
        public boolean allowEmpty() {
            return true;
        }
    };

    public static final RecipeComponent<ClocheRenderFunction> CLOCHE_RENDER_FUNCTION = new RecipeComponent<>() {


        @Override
        public RecipeComponentType<?> type() {
            return RecipeComponentType.Unit.unit(IEApi.ieLoc("cloche_render_function"), CLOCHE_RENDER_FUNCTION);
        }

        @Override
        public Codec<ClocheRenderFunction> codec() {
            return ClocheRenderFunction.CODECS.codec();
        }

        @Override
        public TypeInfo typeInfo() {
            return TypeInfo.of(ClocheRenderFunction.class);
        }
    };

    public static final RecipeComponent<Item> ITEM = new RecipeComponent<>() {
        @Override
        public RecipeComponentType<?> type() {
            return RecipeComponentType.Unit.unit(IEApi.ieLoc("item"), ITEM);

        }

        @Override
        public Codec<Item> codec() {
            return BuiltInRegistries.ITEM.byNameCodec();
        }

        @Override
        public TypeInfo typeInfo() {
            return TypeInfo.of(Item.class);
        }
    };

    public static final RecipeComponent<Fluid> FLUID = new RecipeComponent<>() {
        @Override
        public RecipeComponentType<?> type() {
            return RecipeComponentType.Unit.unit(IEApi.ieLoc("fluid"), FLUID);

        }

        @Override
        public Codec<Fluid> codec() {
            return BuiltInRegistries.FLUID.byNameCodec();
        }

        @Override
        public TypeInfo typeInfo() {
            return TypeInfo.of(Fluid.class);
        }
    };

    public static final RecipeComponent<List<IngredientWithSize>> LIST_OR_SINGLE = new RecipeComponent<>() {
        @Override
        public RecipeComponentType<?> type() {
            return RecipeComponentType.Unit.unit(IEApi.ieLoc("list_or_single"), LIST_OR_SINGLE);
        }

        @Override
        public Codec<List<IngredientWithSize>> codec() {
            return new Codec<>() {
                @Override
                public <T> DataResult<Pair<List<IngredientWithSize>, T>> decode(DynamicOps<T> ops, T input) {
                    JsonElement jsonElement = ops.convertTo(JsonOps.INSTANCE, input);
                    if (jsonElement.isJsonArray()) {
                        return IngredientWithSize.CODECS.listOf().codec().decode(ops, input);
                    } else {
                        return IngredientWithSize.CODECS.codec().xmap(List::of, List::getFirst).decode(ops, input);
                    }
                }

                @Override
                public <T> DataResult<T> encode(List<IngredientWithSize> input, DynamicOps<T> ops, T prefix) {
                    return IngredientWithSize.CODECS.listOf().codec().encode(input, ops, prefix);
                }
            };
        }

        @Override
        public TypeInfo typeInfo() {
            return TypeInfo.RAW_LIST.withParams(TypeInfo.of(IngredientWithSize.class));
        }
    };

    public static final RecipeComponent<Set<MineralMix.BiomeTagPredicate>> BIOME_TAG_PREDICATES = new RecipeComponent<>() {
        @Override
        public RecipeComponentType<?> type() {
            return RecipeComponentType.Unit.unit(IEApi.ieLoc("biome_tag_predicates"), BIOME_TAG_PREDICATES);
        }

        @Override
        public Codec<Set<MineralMix.BiomeTagPredicate>> codec() {
            return MineralMixSerializer.BIOME_TAG_PREDICATE_CODECS.setOf().codec();
        }

        @Override
        public TypeInfo typeInfo() {
            return TypeInfo.RAW_SET.withParams(TypeInfo.of(MineralMix.BiomeTagPredicate.class));
        }
    };
}
