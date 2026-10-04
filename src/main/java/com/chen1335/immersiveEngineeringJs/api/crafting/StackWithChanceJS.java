package com.chen1335.immersiveEngineeringJs.api.crafting;

import net.minecraft.world.item.ItemStack;

public interface StackWithChanceJS
{
	static ChanceOutput of(ItemStack itemStack, float chance)
	{
		return ChanceOutput.of(TagOutput.of(itemStack), chance);
	}
}
