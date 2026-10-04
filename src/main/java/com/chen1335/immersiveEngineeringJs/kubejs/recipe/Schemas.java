package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import blusunrize.immersiveengineering.api.IEApi;
import blusunrize.immersiveengineering.api.crafting.*;
import blusunrize.immersiveengineering.api.excavator.MineralMix;
import blusunrize.immersiveengineering.common.crafting.serializers.MineralMixSerializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import dev.latvian.mods.kubejs.fluid.FluidWrapper;
import dev.latvian.mods.kubejs.plugin.builtin.wrapper.ItemWrapper;
import dev.latvian.mods.kubejs.plugin.builtin.wrapper.SizedIngredientWrapper;
import dev.latvian.mods.kubejs.recipe.KubeRecipe;
import dev.latvian.mods.kubejs.recipe.RecipeScriptContext;
import dev.latvian.mods.kubejs.recipe.component.ListRecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentType;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentValue;
import dev.latvian.mods.kubejs.recipe.filter.RecipeMatchContext;
import dev.latvian.mods.rhino.type.TypeInfo;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;
import java.util.Set;

public class Schemas {
    /**
     * KubeJS only knows how to turn loose script values into the types it has a type wrapper for.
     * {@code TagOutput}, {@code IngredientWithSize}, {@code StackWithChance} and {@code Fluid} are
     * Immersive Engineering / vanilla types without such a wrapper, so the default
     * {@code RecipeComponent#wrap} (which is {@code cx.cx().jsToJava(from, typeInfo())}) cannot read
     * a string such as {@code 'minecraft:iron_ingot'}, {@code '#forge:ingots/iron'} or
     * {@code '3x minecraft:iron_ingot'}.
     * <p>
     * Every component below therefore overrides {@link RecipeComponent#wrap} and hands the value to
     * the KubeJS wrapper that does understand it. {@code codec()} is left untouched so that reading
     * and writing recipe JSON keeps behaving exactly as before.
     */
    @SuppressWarnings("unchecked")
    private static SizedIngredient sizedIngredientOf(RecipeScriptContext cx, Object from) {
        return (SizedIngredient) cx.cx().jsToJava(from, SizedIngredientWrapper.TYPE_INFO);
    }

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

        @Override
        @SuppressWarnings("unchecked")
        public TagOutput wrap(RecipeScriptContext cx, Object from) {
            if (from instanceof TagOutput output) {
                return output;
            }

            // Order matters twice here: an ItemStack keeps its count and its NBT, and a "#tag"
            // string has to go through SizedIngredient so that it stays a tag instead of being
            // resolved to the first matching item.
            if (from instanceof ItemStack stack) {
                return new TagOutput(stack);
            }

            if (from instanceof ItemLike item) {
                return new TagOutput(item);
            }

            if (from instanceof IngredientWithSize ingredient) {
                return new TagOutput(ingredient);
            }

            if (from instanceof Ingredient ingredient) {
                return new TagOutput(new IngredientWithSize(ingredient, 1));
            }

            if (from instanceof TagKey<?> tag) {
                return new TagOutput((TagKey<Item>) tag);
            }

            SizedIngredient sized = sizedIngredientOf(cx, from);

            if (sized == null || sized.ingredient().isEmpty()) {
                throw new IllegalArgumentException("Cannot read '" + from + "' as an Immersive Engineering result!");
            }

            return new TagOutput(new IngredientWithSize(sized.ingredient(), sized.count()));
        }

        @Override
        public boolean hasPriority(RecipeMatchContext cx, Object from) {
            return from instanceof TagOutput || from instanceof ItemStack || from instanceof Ingredient
                    || from instanceof IngredientWithSize || from instanceof TagKey<?>;
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

        @Override
        public IngredientWithSize wrap(RecipeScriptContext cx, Object from) {
            if (from instanceof IngredientWithSize value) {
                return value;
            }

            // SizedIngredient is KubeJS's own ingredient-with-count type and is tag aware, so
            // '#forge:ingots/iron' and '3x minecraft:iron_ingot' both work here.
            SizedIngredient sized = sizedIngredientOf(cx, from);

            if (sized == null || sized.ingredient().isEmpty()) {
                throw new IllegalArgumentException("Cannot read '" + from + "' as an Immersive Engineering ingredient!");
            }

            return new IngredientWithSize(sized.ingredient(), sized.count());
        }

        @Override
        public boolean hasPriority(RecipeMatchContext cx, Object from) {
            return from instanceof IngredientWithSize || from instanceof SizedIngredient
                    || from instanceof ItemStack || from instanceof Ingredient;
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

        @Override
        public StackWithChance wrap(RecipeScriptContext cx, Object from) {
            if (from instanceof StackWithChance value) {
                return value;
            }

            // Anything the output component understands also works as a 100% chance entry.
            return new StackWithChance(TAG_OUTPUT.wrap(cx, from), 1F);
        }

        @Override
        public boolean hasPriority(RecipeMatchContext cx, Object from) {
            return from instanceof StackWithChance || TAG_OUTPUT.hasPriority(cx, from);
        }
    };

    /**
     * The element driven list wrapper, kept next to {@link #CHANCE_LIST} so that both the script
     * value conversion and the "is this a list" check stay in sync with KubeJS.
     */
    private static final ListRecipeComponent<StackWithChance> CHANCE_LIST_IMPL =
            ListRecipeComponent.create(STACK_WITH_CHANCE, false, false);

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

        @Override
        public List<StackWithChance> wrap(RecipeScriptContext cx, Object from) {
            return ListRecipeComponent.wrap0(cx, STACK_WITH_CHANCE, from);
        }

        @Override
        public boolean hasPriority(RecipeMatchContext cx, Object from) {
            return CHANCE_LIST_IMPL.hasPriority(cx, from);
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

        @Override
        public boolean hasPriority(RecipeMatchContext cx, Object from) {
            return from instanceof ClocheRenderFunction;
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

        @Override
        @SuppressWarnings("unchecked")
        public Item wrap(RecipeScriptContext cx, Object from) {
            if (from instanceof Item item) {
                return item;
            }

            Item wrapped = (Item) cx.cx().jsToJava(from, ItemWrapper.ITEM_TYPE_INFO);

            if (wrapped == null) {
                throw new IllegalArgumentException("Cannot read '" + from + "' as an item!");
            }

            return wrapped;
        }

        @Override
        public boolean hasPriority(RecipeMatchContext cx, Object from) {
            return from instanceof Item || from instanceof ItemLike || from instanceof ItemStack;
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

        @Override
        @SuppressWarnings("unchecked")
        public Fluid wrap(RecipeScriptContext cx, Object from) {
            if (from instanceof Fluid fluid) {
                return fluid;
            }

            // KubeJS registers a type wrapper for FluidStack, but not for Fluid itself.
            FluidStack stack = (FluidStack) cx.cx().jsToJava(from, FluidWrapper.TYPE_INFO);

            if (stack == null || stack.isEmpty()) {
                throw new IllegalArgumentException("Cannot read '" + from + "' as a fluid!");
            }

            return stack.getFluid();
        }

        @Override
        public boolean hasPriority(RecipeMatchContext cx, Object from) {
            return from instanceof Fluid || from instanceof FluidStack;
        }
    };

    private static final ListRecipeComponent<IngredientWithSize> LIST_OR_SINGLE_IMPL =
            ListRecipeComponent.create(INGREDIENT_WITH_SIZE, false, false);

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

        @Override
        public List<IngredientWithSize> wrap(RecipeScriptContext cx, Object from) {
            return ListRecipeComponent.wrap0(cx, INGREDIENT_WITH_SIZE, from);
        }

        @Override
        public boolean hasPriority(RecipeMatchContext cx, Object from) {
            return LIST_OR_SINGLE_IMPL.hasPriority(cx, from);
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
