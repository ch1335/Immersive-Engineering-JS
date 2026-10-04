package com.chen1335.immersiveEngineeringJs.api.crafting;

import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface IngredientWithSizeJS
{
	static IngredientWithSize ofItemStack(ItemStack itemStack)
	{
		return IngredientWithSize.of(itemStack);
	}

	static IngredientWithSize ofItem(Item item, int count)
	{
		return IngredientWithSize.of(new ItemStack(item, count));
	}

	static IngredientWithSize ofItem(Item item)
	{
		return IngredientWithSize.of(new ItemStack(item, 1));
	}

	static IngredientWithSize ofTag(TagKey<Item> tagKey, int count)
	{
		return new IngredientWithSize(tagKey, count);
	}

	static IngredientWithSize ofTag(TagKey<Item> tagKey)
	{
		return new IngredientWithSize(tagKey);
	}

	/**
	 * String overload, so scripts can write {@code IngredientWithSizeJS.ofTag('#forge:ingots/iron', 2)}.
	 */
	static IngredientWithSize ofTag(String tag, int count)
	{
		return new IngredientWithSize(CraftingTags.item(tag), count);
	}

	static IngredientWithSize ofTag(String tag)
	{
		return new IngredientWithSize(CraftingTags.item(tag));
	}
}
