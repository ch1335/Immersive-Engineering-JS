package com.chen1335.immersiveEngineeringJs.api.crafting;

import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface IngredientWithSizeJS {
    static IngredientWithSize ofItemStack(ItemStack itemStack) {
        return IngredientWithSize.of(itemStack);
    }

    static IngredientWithSize ofItem(Item item, int count) {
        return IngredientWithSize.of(new ItemStack(item, count));
    }

    static IngredientWithSize ofTag(TagKey<Item> tagKey) {
        return new IngredientWithSize(tagKey);
    }

    static IngredientWithSize ofTag(TagKey<Item> tagKey, int count) {
        return new IngredientWithSize(tagKey, count);
    }
}
