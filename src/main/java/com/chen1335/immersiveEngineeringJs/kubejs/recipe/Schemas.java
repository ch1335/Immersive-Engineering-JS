package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import blusunrize.immersiveengineering.api.crafting.ClocheRenderFunction.ClocheRenderReference;
import blusunrize.immersiveengineering.api.crafting.FluidTagInput;
import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import blusunrize.immersiveengineering.api.crafting.StackWithChance;
import com.chen1335.immersiveEngineeringJs.api.crafting.ChanceOutput;
import com.chen1335.immersiveEngineeringJs.api.crafting.TagOutput;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import dev.latvian.mods.kubejs.fluid.OutputFluid;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.component.ComponentRole;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentValue;
import dev.latvian.mods.kubejs.util.MapJS;
import dev.latvian.mods.rhino.Wrapper;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Recipe components for the Immersive Engineering 1.20.1 recipe types.
 * <p>
 * KubeJS 2001 components are Gson based and carry their {@link ComponentRole} directly (there is no
 * {@code inputKey}/{@code outputKey} helper like in KubeJS 2101), so every component below declares
 * whether it is an input or an output.
 */
public class Schemas
{
	private Schemas()
	{
	}

	/**
	 * Parses Immersive Engineering's {@code IngredientWithSize} JSON, which is
	 * {@code <ingredient>} for size 1 and {@code {"count": n, "base_ingredient": <ingredient>}} otherwise.
	 */
	public static IngredientWithSize ingredientWithSizeFromJson(JsonElement json)
	{
		if (json != null && json.isJsonObject())
		{
			JsonObject object = json.getAsJsonObject();

			if (object.has("base_ingredient"))
			{
				int count = object.has("count") ? object.get("count").getAsInt() : 1;
				return new IngredientWithSize(Ingredient.fromJson(object.get("base_ingredient")), Math.max(1, count));
			}
		}

		return new IngredientWithSize(Ingredient.fromJson(json), 1);
	}

	public static JsonElement ingredientWithSizeToJson(IngredientWithSize value)
	{
		Ingredient base = value.getBaseIngredient();

		if (value.getCount() == 1)
		{
			return base.toJson();
		}

		JsonObject json = new JsonObject();
		json.addProperty("count", value.getCount());
		json.add("base_ingredient", base.toJson());
		return json;
	}

	/**
	 * Mirrors {@code IERecipeSerializer#readOutput}: an object with an {@code item} key is a concrete
	 * stack, anything else is an ingredient whose preferred stack is used.
	 */
	public static ItemStack readOutput(JsonElement json)
	{
		TagOutput output = TagOutput.fromJson(json);
		return output.isEmpty() ? ItemStack.EMPTY : output.getStack();
	}

	/**
	 * Reads a string from either a script value or a JSON primitive. A component's {@code read} gets
	 * the raw script object or a {@link JsonElement}, and calling {@code String.valueOf} on a
	 * {@link JsonPrimitive} keeps its quotes, which would break every id lookup.
	 */
	public static String asString(Object from)
	{
		if (from instanceof JsonPrimitive primitive)
		{
			return primitive.getAsString();
		}

		if (from instanceof JsonElement element)
		{
			return element.getAsString();
		}

		return String.valueOf(from);
	}

	/**
	 * Accepts both the {@code "#forge:water"} tag syntax used in scripts and the plain
	 * {@code "forge:water"} form used in Immersive Engineering's JSON.
	 */
	public static String stripTagPrefix(String value)
	{
		return value.startsWith("#") ? value.substring(1) : value;
	}

	/**
	 * Resolves a script value into an item tag, accepting either a {@link TagKey} or the
	 * {@code "#forge:ingots/iron"} string syntax. Returns {@code null} when the value is not a tag.
	 */
	@SuppressWarnings("unchecked")
	public static TagKey<Item> itemTagOrNull(Object from)
	{
		if (from instanceof TagKey<?> tag)
		{
			return (TagKey<Item>) tag;
		}

		if (from instanceof CharSequence sequence)
		{
			String value = sequence.toString();

			if (value.startsWith("#"))
			{
				ResourceLocation id = ResourceLocation.tryParse(value.substring(1));
				return id == null ? null : TagKey.create(Registries.ITEM, id);
			}
		}

		return null;
	}

	public static final RecipeComponent<TagOutput> TAG_OUTPUT = new RecipeComponent<>()
	{
		@Override
		public String componentType()
		{
			return "tag_output";
		}

		@Override
		public ComponentRole role()
		{
			return ComponentRole.OUTPUT;
		}

		@Override
		public Class<?> componentClass()
		{
			return TagOutput.class;
		}

		@Override
		public boolean hasPriority(RecipeJS recipe, Object from)
		{
			return from instanceof TagOutput || from instanceof ItemStack || from instanceof JsonElement;
		}

		@Override
		@Nullable
		public JsonElement write(RecipeJS recipe, TagOutput value)
		{
			return value == null ? null : value.toJson();
		}

		/**
		 * Optional single outputs such as {@code slag}, {@code stripped} and {@code result} default to
		 * {@link TagOutput#EMPTY} and are marked {@code alwaysWrite}. Writing that empty value would
		 * produce {@code {"item":"minecraft:air","count":0}}, which Immersive Engineering turns into a
		 * {@code Lazy} that throws as soon as it is read. Omit the key instead: every 1.20.1 serializer
		 * reads these outputs behind a {@code json.has(...)} check.
		 */
		@Override
		public void writeToJson(RecipeJS recipe, RecipeComponentValue<TagOutput> cv, JsonObject json)
		{
			JsonElement element = cv.value == null ? null : cv.value.toJson();

			for (String name : cv.key.names)
			{
				json.remove(name);
			}

			if (element != null)
			{
				json.add(cv.key.name, element);
			}
		}

		@Override
		@SuppressWarnings("unchecked")
		public TagOutput read(RecipeJS recipe, Object from)
		{
			if (from instanceof TagOutput output)
			{
				return output;
			}

			if (from instanceof JsonElement json)
			{
				return TagOutput.fromJson(json);
			}

			TagKey<Item> tag = itemTagOrNull(from);

			if (tag != null)
			{
				return TagOutput.of(tag);
			}

			OutputItem output = OutputItem.of(from);

			if (!output.isEmpty())
			{
				return TagOutput.of(output.item.copyWithCount(output.getCount()));
			}

			throw new IllegalArgumentException("Cannot read '" + from + "' as an Immersive Engineering result!");
		}

		@Override
		public String toString()
		{
			return componentType();
		}
	};

	public static final RecipeComponent<IngredientWithSize> INGREDIENT_WITH_SIZE = new RecipeComponent<>()
	{
		@Override
		public String componentType()
		{
			return "ingredient_with_size";
		}

		@Override
		public ComponentRole role()
		{
			return ComponentRole.INPUT;
		}

		@Override
		public Class<?> componentClass()
		{
			return IngredientWithSize.class;
		}

		@Override
		public boolean hasPriority(RecipeJS recipe, Object from)
		{
			return from instanceof IngredientWithSize || from instanceof ItemStack || from instanceof JsonElement;
		}

		@Override
		public JsonElement write(RecipeJS recipe, IngredientWithSize value)
		{
			return ingredientWithSizeToJson(value);
		}

		@Override
		@SuppressWarnings("unchecked")
		public IngredientWithSize read(RecipeJS recipe, Object from)
		{
			if (from instanceof IngredientWithSize value)
			{
				// See INGREDIENT#read: an empty base ingredient would be written as [].
				if (value.getBaseIngredient().isEmpty())
				{
					throw new IllegalArgumentException("Cannot use an empty ingredient!");
				}

				return value;
			}

			if (from instanceof JsonElement json)
			{
				return ingredientWithSizeFromJson(json);
			}

			if (from instanceof ItemStack stack)
			{
				return IngredientWithSize.of(stack);
			}

			if (from instanceof TagKey<?> tag)
			{
				return new IngredientWithSize((TagKey<Item>) tag, 1);
			}

			if (from instanceof Ingredient ingredient)
			{
				return new IngredientWithSize(ingredient, 1);
			}

			InputItem input = InputItem.of(from);

			if (input.isEmpty())
			{
				throw new IllegalArgumentException("Cannot read '" + from + "' as an Immersive Engineering ingredient!");
			}

			return new IngredientWithSize(input.ingredient, input.count);
		}

		@Override
		public String toString()
		{
			return componentType();
		}
	};

	/**
	 * A plain {@link Ingredient}, serialized with {@link Ingredient#toJson()} so that Immersive
	 * Engineering's {@code Ingredient.fromJson} can read it back directly.
	 */
	public static final RecipeComponent<Ingredient> INGREDIENT = new RecipeComponent<>()
	{
		@Override
		public String componentType()
		{
			return "ingredient";
		}

		@Override
		public ComponentRole role()
		{
			return ComponentRole.INPUT;
		}

		@Override
		public Class<?> componentClass()
		{
			return Ingredient.class;
		}

		@Override
		public boolean hasPriority(RecipeJS recipe, Object from)
		{
			return from instanceof Ingredient;
		}

		@Override
		public JsonElement write(RecipeJS recipe, Ingredient value)
		{
			return value.toJson();
		}

		@Override
		public Ingredient read(RecipeJS recipe, Object from)
		{
			if (from instanceof Ingredient ingredient)
			{
				// Store-bought empty ingredients fall through to write(), where Ingredient.EMPTY
				// serializes as [], and Immersive Engineering's Ingredient.fromJson rejects an empty
				// array. Reject it here so the script fails with a usable message instead of the
				// recipe failing to load later.
				if (ingredient.isEmpty())
				{
					throw new IllegalArgumentException("Cannot use an empty ingredient!");
				}

				return ingredient;
			}

			if (from instanceof JsonElement json)
			{
				return Ingredient.fromJson(json);
			}

			InputItem input = InputItem.of(from);

			if (input.isEmpty())
			{
				throw new IllegalArgumentException("Cannot read '" + from + "' as an ingredient!");
			}

			return input.ingredient;
		}

		@Override
		public String toString()
		{
			return componentType();
		}
	};

	/**
	 * Like {@link #INGREDIENT}, but for the two 1.20.1 keys that Immersive Engineering reads with
	 * {@code GsonHelper.getAsJsonObject(...)} instead of {@code Ingredient.fromJson(json.get(...))}:
	 * {@code crusher.input} and {@code blast_furnace_fuel.input}.
	 * <p>
	 * A multi entry ingredient serializes to a JSON array through {@code Ingredient#toJson()}, which
	 * those readers reject ({@code Expected input to be a JsonObject, was an array} /
	 * {@code JsonArray cannot be cast to JsonObject}). The recipe then fails during datapack load and
	 * silently does not exist, so reject the value at recipe creation time instead, where the script
	 * author still gets a usable message. IE 1.21.1 reads both keys with an ingredient codec and has
	 * no such restriction.
	 */
	public static final RecipeComponent<Ingredient> SINGLE_ENTRY_INGREDIENT = new RecipeComponent<>()
	{
		@Override
		public String componentType()
		{
			// The name shows up verbatim in KubeJS's "Unable to cast '<key>' value '<v>' to
			// '<componentType>'!" message, so make it describe the constraint.
			return "single_entry_ingredient";
		}

		@Override
		public ComponentRole role()
		{
			return ComponentRole.INPUT;
		}

		@Override
		public Class<?> componentClass()
		{
			return Ingredient.class;
		}

		@Override
		public boolean hasPriority(RecipeJS recipe, Object from)
		{
			return INGREDIENT.hasPriority(recipe, from);
		}

		@Override
		public JsonElement write(RecipeJS recipe, Ingredient value)
		{
			return INGREDIENT.write(recipe, value);
		}

		@Override
		public Ingredient read(RecipeJS recipe, Object from)
		{
			Ingredient ingredient = INGREDIENT.read(recipe, from);

			if (ingredient.toJson().isJsonArray())
			{
				throw new IllegalArgumentException("'" + from + "' expands to more than one ingredient entry, but "
						+ "this recipe type accepts only a single one: Immersive Engineering 1.20.1 reads it with "
						+ "getAsJsonObject, and an array makes the recipe fail to load. Use a tag or a single item.");
			}

			return ingredient;
		}

		@Override
		public String toString()
		{
			return componentType();
		}
	};

	public static final RecipeComponent<ChanceOutput> CHANCE_OUTPUT = new RecipeComponent<>()
	{
		@Override
		public String componentType()
		{
			return "stack_with_chance";
		}

		@Override
		public ComponentRole role()
		{
			return ComponentRole.OUTPUT;
		}

		@Override
		public Class<?> componentClass()
		{
			return ChanceOutput.class;
		}

		@Override
		public boolean hasPriority(RecipeJS recipe, Object from)
		{
			return from instanceof ChanceOutput || from instanceof StackWithChance;
		}

		@Override
		@Nullable
		public JsonElement write(RecipeJS recipe, ChanceOutput value)
		{
			// Returning null makes the surrounding array component drop this entry, which is what an
			// output-less entry has to become: see TAG_OUTPUT#writeToJson.
			return value == null || value.output().isEmpty() ? null : value.toJson();
		}

		@Override
		public void writeToJson(RecipeJS recipe, RecipeComponentValue<ChanceOutput> cv, JsonObject json)
		{
			JsonElement element = cv.value == null ? null : write(recipe, cv.value);

			for (String name : cv.key.names)
			{
				json.remove(name);
			}

			if (element != null)
			{
				json.add(cv.key.name, element);
			}
		}

		@Override
		public ChanceOutput read(RecipeJS recipe, Object from)
		{
			if (from instanceof ChanceOutput value)
			{
				return value;
			}

			if (from instanceof StackWithChance value)
			{
				return ChanceOutput.of(value);
			}

			if (from instanceof JsonElement json)
			{
				// Never resolves the output stack, so tag outputs whose tag is currently empty are
				// kept exactly as they were written by Immersive Engineering.
				return ChanceOutput.fromJson(json);
			}

			TagKey<Item> tag = itemTagOrNull(from);

			if (tag != null)
			{
				return ChanceOutput.of(TagOutput.of(tag), 1F);
			}

			OutputItem output = OutputItem.of(from);

			if (!output.isEmpty())
			{
				return ChanceOutput.of(TagOutput.of(output.item.copyWithCount(output.getCount())),
						output.hasChance() ? (float) output.getChance() : 1F);
			}

			throw new IllegalArgumentException("Cannot read '" + from + "' as a stack with chance!");
		}

		@Override
		public String toString()
		{
			return componentType();
		}
	};

	public static final RecipeComponent<ChanceOutput[]> CHANCE_LIST = CHANCE_OUTPUT.asArray();

	public static final RecipeComponent<ClocheRenderReference> CLOCHE_RENDER_REFERENCE = new RecipeComponent<>()
	{
		@Override
		public String componentType()
		{
			return "cloche_render_function";
		}

		@Override
		public ComponentRole role()
		{
			return ComponentRole.INPUT;
		}

		@Override
		public Class<?> componentClass()
		{
			return ClocheRenderReference.class;
		}

		@Override
		public boolean hasPriority(RecipeJS recipe, Object from)
		{
			return from instanceof ClocheRenderReference || from instanceof Block;
		}

		@Override
		public JsonElement write(RecipeJS recipe, ClocheRenderReference value)
		{
			return value.serialize();
		}

		@Override
		public ClocheRenderReference read(RecipeJS recipe, Object from)
		{
			if (from instanceof ClocheRenderReference reference)
			{
				return reference;
			}

			if (from instanceof JsonElement json && json.isJsonObject())
			{
				return ClocheRenderReference.deserialize(json.getAsJsonObject());
			}

			if (from instanceof Block block)
			{
				// Matches the 1.21.1 behaviour of picking crop/generic automatically.
				return new ClocheRenderReference(block.defaultBlockState().is(net.minecraft.tags.BlockTags.CROPS)
						? "crop" : "generic", block);
			}

			throw new IllegalArgumentException("Cannot read '" + from + "' as a cloche render function!");
		}

		@Override
		public String toString()
		{
			return componentType();
		}
	};

	public static final RecipeComponent<Item> ITEM = new RecipeComponent<>()
	{
		@Override
		public String componentType()
		{
			return "item";
		}

		@Override
		public ComponentRole role()
		{
			return ComponentRole.INPUT;
		}

		@Override
		public Class<?> componentClass()
		{
			return Item.class;
		}

		@Override
		public boolean hasPriority(RecipeJS recipe, Object from)
		{
			return from instanceof Item;
		}

		@Override
		public JsonElement write(RecipeJS recipe, Item value)
		{
			return new JsonPrimitive(ForgeRegistries.ITEMS.getKey(value).toString());
		}

		@Override
		public Item read(RecipeJS recipe, Object from)
		{
			if (from instanceof Item item)
			{
				return item;
			}

			InputItem input = InputItem.of(from);

			if (input.isEmpty() || input.ingredient.getItems().length == 0)
			{
				throw new IllegalArgumentException("Cannot read '" + from + "' as an item!");
			}

			return input.ingredient.getItems()[0].getItem();
		}

		@Override
		public String toString()
		{
			return componentType();
		}
	};

	public static final RecipeComponent<Fluid> FLUID = new RecipeComponent<>()
	{
		@Override
		public String componentType()
		{
			return "fluid";
		}

		@Override
		public ComponentRole role()
		{
			return ComponentRole.INPUT;
		}

		@Override
		public Class<?> componentClass()
		{
			return Fluid.class;
		}

		@Override
		public boolean hasPriority(RecipeJS recipe, Object from)
		{
			return from instanceof Fluid;
		}

		@Override
		public JsonElement write(RecipeJS recipe, Fluid value)
		{
			return new JsonPrimitive(ForgeRegistries.FLUIDS.getKey(value).toString());
		}

		@Override
		public Fluid read(RecipeJS recipe, Object from)
		{
			if (from instanceof Fluid fluid)
			{
				return fluid;
			}

			ResourceLocation id = ResourceLocation.tryParse(asString(from));

			if (id == null)
			{
				throw new IllegalArgumentException("Cannot read '" + from + "' as a fluid!");
			}

			Fluid fluid = ForgeRegistries.FLUIDS.getValue(id);

			if (fluid == null)
			{
				throw new IllegalArgumentException("Unknown fluid '" + id + "'!");
			}

			return fluid;
		}

		@Override
		public String toString()
		{
			return componentType();
		}
	};

	public static final RecipeComponent<Fluid[]> FLUID_LIST = FLUID.asArray();

	/**
	 * Fluid outputs in Immersive Engineering 1.20.1 are written as
	 * {@code {"fluid": "ns:id", "amount": n}} with an optional {@code tag} holding the NBT.
	 */
	public static final RecipeComponent<FluidStack> FLUID_STACK = new RecipeComponent<>()
	{
		@Override
		public String componentType()
		{
			return "fluid_stack";
		}

		@Override
		public ComponentRole role()
		{
			return ComponentRole.OUTPUT;
		}

		@Override
		public Class<?> componentClass()
		{
			return FluidStack.class;
		}

		@Override
		public boolean hasPriority(RecipeJS recipe, Object from)
		{
			return from instanceof FluidStack;
		}

		@Override
		@Nullable
		public JsonElement write(RecipeJS recipe, FluidStack value)
		{
			// Same reasoning as TAG_OUTPUT#writeToJson: Immersive Engineering reads optional fluid
			// outputs behind a json.has("fluid") check, so an unset output must be omitted. Writing
			// FluidStack.EMPTY would emit {"fluid":"minecraft:empty","amount":0}.
			return value == null || value.isEmpty() ? null : fluidStackToJson(value);
		}

		@Override
		public void writeToJson(RecipeJS recipe, RecipeComponentValue<FluidStack> cv, JsonObject json)
		{
			JsonElement element = cv.value == null ? null : write(recipe, cv.value);

			for (String name : cv.key.names)
			{
				json.remove(name);
			}

			if (element != null)
			{
				json.add(cv.key.name, element);
			}
		}

		@Override
		public FluidStack read(RecipeJS recipe, Object from)
		{
			if (from == null)
			{
				return FluidStack.EMPTY;
			}

			if (from instanceof FluidStack stack)
			{
				return stack;
			}

			if (from instanceof JsonElement json && json.isJsonObject())
			{
				return fluidStackFromJson(json.getAsJsonObject());
			}

			if (from instanceof Fluid fluid)
			{
				return new FluidStack(fluid, FluidType.BUCKET_VOLUME);
			}

			// Script values arrive as raw JS objects, so let KubeJS do the conversion: that handles
			// "{fluid: 'minecraft:water', amount: 1000}", plain id strings and FluidStackJS values.
			OutputFluid fluid = recipe.readOutputFluid(from);

			if (fluid instanceof FluidStackJS js && !js.getFluidStack().isEmpty())
			{
				// FluidStackJS is Architectury based, so rebuild a Forge stack from its parts
				// instead of converting between the two FluidStack types.
				FluidStack stack = new FluidStack(js.getFluid(), (int) js.kjs$getAmount());

				if (js.getNbt() != null)
				{
					stack.setTag(js.getNbt());
				}

				return stack;
			}

			throw new IllegalArgumentException("Cannot read '" + from + "' as a fluid stack!");
		}

		@Override
		public String toString()
		{
			return componentType();
		}
	};

	/**
	 * Fluid inputs in Immersive Engineering 1.20.1 must be tag based, because
	 * {@code FluidTagInput#deserialize} reads a mandatory {@code tag} field. There is no JSON form
	 * for a list of concrete fluids, so scripts have to supply a fluid tag here.
	 */
	public static final RecipeComponent<FluidTagInput> FLUID_TAG_INPUT = new RecipeComponent<>()
	{
		@Override
		public String componentType()
		{
			return "fluid_tag_input";
		}

		@Override
		public ComponentRole role()
		{
			return ComponentRole.INPUT;
		}

		@Override
		public Class<?> componentClass()
		{
			return FluidTagInput.class;
		}

		@Override
		public boolean hasPriority(RecipeJS recipe, Object from)
		{
			return from instanceof FluidTagInput || from instanceof TagKey;
		}

		@Override
		public JsonElement write(RecipeJS recipe, FluidTagInput value)
		{
			return value.serialize();
		}

		@Override
		@SuppressWarnings("unchecked")
		public FluidTagInput read(RecipeJS recipe, Object from)
		{
			if (from instanceof FluidTagInput input)
			{
				return input;
			}

			if (from instanceof JsonElement json)
			{
				if (json.isJsonObject() && json.getAsJsonObject().has("tag"))
				{
					return FluidTagInput.deserialize(json);
				}

				from = json.getAsString();
			}

			if (from instanceof TagKey<?> tag)
			{
				return new FluidTagInput((TagKey<Fluid>) tag, FluidType.BUCKET_VOLUME);
			}

			// "{tag: 'forge:water', amount: 1000}" from a script.
			Map<?, ?> map = MapJS.of(Wrapper.unwrapped(from));

			if (map != null && map.get("tag") != null)
			{
				ResourceLocation id = ResourceLocation.tryParse(stripTagPrefix(String.valueOf(map.get("tag"))));

				if (id != null)
				{
					int amount = map.get("amount") instanceof Number number
							? number.intValue() : FluidType.BUCKET_VOLUME;
					return new FluidTagInput(TagKey.create(Registries.FLUID, id), Math.max(1, amount));
				}
			}

			// "#forge:water" or "forge:water" from a script.
			if (from instanceof CharSequence)
			{
				ResourceLocation id = ResourceLocation.tryParse(stripTagPrefix(from.toString()));

				if (id != null)
				{
					return new FluidTagInput(TagKey.create(Registries.FLUID, id), FluidType.BUCKET_VOLUME);
				}
			}

			throw new IllegalArgumentException("Cannot read '" + from + "' as a fluid tag input! "
					+ "Immersive Engineering 1.20.1 only accepts fluid tags in recipe JSON.");
		}

		@Override
		public String toString()
		{
			return componentType();
		}
	};

	public static JsonElement fluidStackToJson(FluidStack stack)
	{
		JsonObject json = new JsonObject();
		json.addProperty("fluid", ForgeRegistries.FLUIDS.getKey(stack.getFluid()).toString());
		json.addProperty("amount", stack.getAmount());

		if (stack.hasTag())
		{
			json.addProperty("tag", stack.getTag().getAsString());
		}

		return json;
	}

	public static FluidStack fluidStackFromJson(JsonObject json)
	{
		ResourceLocation id = ResourceLocation.tryParse(json.get("fluid").getAsString());

		if (id == null)
		{
			throw new IllegalArgumentException("Invalid fluid id in " + json);
		}

		Fluid fluid = ForgeRegistries.FLUIDS.getValue(id);

		if (fluid == null)
		{
			throw new IllegalArgumentException("Unknown fluid '" + id + "'!");
		}

		FluidStack stack = new FluidStack(fluid, json.has("amount") ? json.get("amount").getAsInt() : FluidType.BUCKET_VOLUME);

		if (json.has("tag"))
		{
			try
			{
				CompoundTag tag = TagParser.parseTag(json.get("tag").getAsString());
				stack.setTag(tag);
			}
			catch (Exception ex)
			{
				throw new IllegalArgumentException("Invalid fluid NBT in " + json, ex);
			}
		}

		return stack;
	}

	public static final RecipeComponent<Block> BLOCK = new RecipeComponent<>()
	{
		@Override
		public String componentType()
		{
			return "block";
		}

		@Override
		public ComponentRole role()
		{
			return ComponentRole.INPUT;
		}

		@Override
		public Class<?> componentClass()
		{
			return Block.class;
		}

		@Override
		public boolean hasPriority(RecipeJS recipe, Object from)
		{
			return from instanceof Block;
		}

		@Override
		public JsonElement write(RecipeJS recipe, Block value)
		{
			return new JsonPrimitive(ForgeRegistries.BLOCKS.getKey(value).toString());
		}

		@Override
		public Block read(RecipeJS recipe, Object from)
		{
			if (from instanceof Block block)
			{
				return block;
			}

			ResourceLocation id = ResourceLocation.tryParse(asString(from));

			if (id == null)
			{
				throw new IllegalArgumentException("Cannot read '" + from + "' as a block!");
			}

			Block block = ForgeRegistries.BLOCKS.getValue(id);

			if (block == null)
			{
				throw new IllegalArgumentException("Unknown block '" + id + "'!");
			}

			return block;
		}

		@Override
		public String toString()
		{
			return componentType();
		}
	};

	/**
	 * A single dimension id. Immersive Engineering 1.20.1 mineral mixes are restricted by dimension,
	 * while 1.21.1 uses biome tag predicates.
	 */
	public static final RecipeComponent<ResourceLocation> DIMENSION = new RecipeComponent<>()
	{
		@Override
		public String componentType()
		{
			return "dimension";
		}

		@Override
		public ComponentRole role()
		{
			return ComponentRole.INPUT;
		}

		@Override
		public Class<?> componentClass()
		{
			return ResourceLocation.class;
		}

		@Override
		public boolean hasPriority(RecipeJS recipe, Object from)
		{
			return from instanceof ResourceLocation;
		}

		@Override
		public JsonElement write(RecipeJS recipe, ResourceLocation value)
		{
			return new JsonPrimitive(value.toString());
		}

		@Override
		public ResourceLocation read(RecipeJS recipe, Object from)
		{
			if (from instanceof ResourceLocation id)
			{
				return id;
			}

			ResourceLocation id = ResourceLocation.tryParse(asString(from));

			if (id == null)
			{
				throw new IllegalArgumentException("Cannot read '" + from + "' as a dimension!");
			}

			return id;
		}

		@Override
		public String toString()
		{
			return componentType();
		}
	};

	public static final RecipeComponent<ResourceLocation[]> DIMENSION_LIST = DIMENSION.asArray();
}
