package com.chen1335.immersiveEngineeringJs.api.crafting;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface TagOutputJS
{
	static TagOutput ofItemStack(ItemStack itemStack)
	{
		return TagOutput.of(itemStack);
	}

	static TagOutput ofItem(Item item)
	{
		return TagOutput.of(item);
	}

	static TagOutput ofItem(Item item, int count)
	{
		return TagOutput.of(item, count);
	}

	static TagOutput ofTag(TagKey<Item> tagKey)
	{
		return TagOutput.of(tagKey);
	}

	static TagOutput ofTag(TagKey<Item> tagKey, int count)
	{
		return TagOutput.of(tagKey, count);
	}
}
