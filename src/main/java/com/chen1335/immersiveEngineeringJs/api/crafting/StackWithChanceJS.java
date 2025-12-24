package com.chen1335.immersiveEngineeringJs.api.crafting;

import blusunrize.immersiveengineering.api.crafting.StackWithChance;
import net.minecraft.world.item.ItemStack;

public interface StackWithChanceJS {
    static StackWithChance of(ItemStack itemStack, float chance) {
        return new StackWithChance(itemStack, chance);
    }
}
