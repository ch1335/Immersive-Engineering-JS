package com.chen1335.immersiveEngineeringJs.api.crafting;

import blusunrize.immersiveengineering.api.IEApi;
import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

/**
 * 1.20.1 replacement for {@code blusunrize.immersiveengineering.api.crafting.TagOutput}, which only
 * exists from Immersive Engineering 1.20.2 onwards.
 * <p>
 * IE 1.20.1 resolves every recipe output through {@code IERecipeSerializer#readOutput}, which accepts
 * either an object carrying an {@code item} key or an {@link IngredientWithSize} shaped object
 * ({@code tag} / {@code base_ingredient}). This class models both and serializes back to exactly those
 * two shapes, so scripts can keep writing tag based results.
 */
public final class TagOutput
{
	public static final TagOutput EMPTY = new TagOutput(ItemStack.EMPTY, null, 0);

	private final ItemStack stack;
	@Nullable
	private final TagKey<Item> tag;
	private final int count;

	private TagOutput(ItemStack stack, @Nullable TagKey<Item> tag, int count)
	{
		this.stack = stack;
		this.tag = tag;
		this.count = count;
	}

	public static TagOutput of(ItemStack stack)
	{
		return stack.isEmpty() ? EMPTY : new TagOutput(stack, null, stack.getCount());
	}

	public static TagOutput of(Item item)
	{
		return of(item, 1);
	}

	public static TagOutput of(Item item, int count)
	{
		return count <= 0 ? EMPTY : new TagOutput(new ItemStack(item, count), null, count);
	}

	public static TagOutput of(TagKey<Item> tag)
	{
		return of(tag, 1);
	}

	public static TagOutput of(TagKey<Item> tag, int count)
	{
		return count <= 0 ? EMPTY : new TagOutput(ItemStack.EMPTY, tag, count);
	}

	public boolean isEmpty()
	{
		return this == EMPTY || (tag == null && stack.isEmpty());
	}

	public int getCount()
	{
		return Math.max(1, count);
	}

	@Nullable
	public TagKey<Item> getTag()
	{
		return tag;
	}

	/**
	 * @return the concrete stack, or the preferred stack of the tag when this output is tag based
	 */
	public ItemStack getStack()
	{
		if (tag == null)
		{
			return stack;
		}

		ItemStack[] matching = Ingredient.of(tag).getItems();

		if (matching.length == 0)
		{
			return ItemStack.EMPTY;
		}

		ItemStack preferred = IEApi.getPreferredStackbyMod(matching);
		preferred.setCount(getCount());
		return preferred;
	}

	/**
	 * Serializes this output for Immersive Engineering 1.20.1.
	 * <p>
	 * Returns {@code null} when this output is empty, because 1.20.1 has no JSON representation for
	 * "no output": {@code IERecipeSerializer#readOutput} takes its {@code has("item")} branch for an
	 * object and builds a {@code Lazy} that throws {@code JsonSyntaxException: Invalid item:
	 * minecraft:air} the moment something reads it (JEI does, while rendering the recipe), while an
	 * empty ingredient array is rejected by {@code Ingredient.fromJson}. Every 1.20.1 serializer
	 * guards such outputs with {@code json.has(...)}, so the caller must simply omit the key.
	 */
	@Nullable
	public JsonElement toJson()
	{
		if (isEmpty())
		{
			return null;
		}

		if (tag != null)
		{
			JsonObject base = new JsonObject();
			base.addProperty("tag", tag.location().toString());

			int amount = getCount();

			if (amount == 1)
			{
				return base;
			}

			JsonObject wrapper = new JsonObject();
			wrapper.addProperty("count", amount);
			wrapper.add("base_ingredient", base);
			return wrapper;
		}

		JsonObject json = new JsonObject();
		json.addProperty("item", ForgeRegistries.ITEMS.getKey(stack.getItem()).toString());
		json.addProperty("count", stack.getCount());

		if (stack.hasTag())
		{
			json.addProperty("nbt", stack.getTag().getAsString());
		}

		return json;
	}

	public static TagOutput fromJson(JsonElement json)
	{
		if (json == null || json.isJsonNull())
		{
			return EMPTY;
		}

		if (!json.isJsonObject())
		{
			return EMPTY;
		}

		JsonObject object = json.getAsJsonObject();

		if (object.has("item"))
		{
			return of(ShapedRecipe.itemStackFromJson(object));
		}

		int count = object.has("count") ? object.get("count").getAsInt() : 1;

		if (object.has("base_ingredient"))
		{
			return parseIngredientJson(object.get("base_ingredient"), count);
		}

		return parseIngredientJson(object, count);
	}

	private static TagOutput parseIngredientJson(JsonElement json, int count)
	{
		if (!json.isJsonObject())
		{
			return EMPTY;
		}

		JsonObject object = json.getAsJsonObject();

		if (object.has("tag"))
		{
			ResourceLocation id = ResourceLocation.tryParse(object.get("tag").getAsString());

			if (id == null)
			{
				return EMPTY;
			}

			return of(TagKey.create(Registries.ITEM, id), count);
		}

		if (object.has("item"))
		{
			ResourceLocation id = ResourceLocation.tryParse(object.get("item").getAsString());

			if (id == null)
			{
				return EMPTY;
			}

			Item item = ForgeRegistries.ITEMS.getValue(id);

			if (item != null && item != Items.AIR)
			{
				return of(new ItemStack(item, Math.max(1, count)));
			}
		}

		return EMPTY;
	}

	@Override
	public String toString()
	{
		if (tag != null)
		{
			return getCount() + "x #" + tag.location();
		}

		return stack.toString();
	}
}
