package com.chen1335.immersiveEngineeringJs.api.crafting;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;

/**
 * Parses the tag syntax used by scripts ({@code "#forge:ingots/iron"} or {@code "forge:ingots/iron"})
 * into a {@link TagKey}.
 * <p>
 * KubeJS 2001 registers no type wrapper for {@code TagKey}, so a script string cannot be converted to
 * a {@code TagKey} parameter automatically (KubeJS 2101 can). Methods that take a tag therefore need a
 * {@code String} overload that goes through here.
 */
public final class CraftingTags
{
	private CraftingTags()
	{
	}

	public static TagKey<Item> item(String tag)
	{
		return TagKey.create(Registries.ITEM, parse(tag));
	}

	public static TagKey<Fluid> fluid(String tag)
	{
		return TagKey.create(Registries.FLUID, parse(tag));
	}

	private static ResourceLocation parse(String tag)
	{
		String value = tag.startsWith("#") ? tag.substring(1) : tag;
		ResourceLocation id = ResourceLocation.tryParse(value);

		if (id == null)
		{
			throw new IllegalArgumentException("Invalid tag '" + tag + "'!");
		}

		return id;
	}
}
