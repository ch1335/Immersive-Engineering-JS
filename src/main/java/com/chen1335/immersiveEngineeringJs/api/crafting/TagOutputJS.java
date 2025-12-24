package com.chen1335.immersiveEngineeringJs.api.crafting;

import blusunrize.immersiveengineering.api.crafting.TagOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface TagOutputJS {
    static TagOutput ofItemStack(ItemStack itemStack) {
        return new TagOutput(itemStack);
    }

    static TagOutput ofTag(TagKey<Item> tagKey) {
        return new TagOutput(tagKey);
    }

    static TagOutput ofTag(TagKey<Item> tagKey, int count) {
        return new TagOutput(tagKey, count);
    }
}
