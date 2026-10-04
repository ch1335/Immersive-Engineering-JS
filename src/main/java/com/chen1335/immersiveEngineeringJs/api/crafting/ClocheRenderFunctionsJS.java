package com.chen1335.immersiveEngineeringJs.api.crafting;

import blusunrize.immersiveengineering.api.crafting.ClocheRenderFunction.ClocheRenderReference;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

/**
 * Immersive Engineering 1.20.1 does not store render functions in the cloche recipe JSON; it stores a
 * {@link ClocheRenderReference} made of a factory type ({@code crop}, {@code stacking}, {@code stem},
 * {@code generic}, {@code chorus}, {@code hemp}) and the block to render. These helpers mirror the
 * 1.21.1 script API on top of that model.
 */
public interface ClocheRenderFunctionsJS
{
	static ClocheRenderReference ofGeneric(Block cropBlock)
	{
		return new ClocheRenderReference("generic", cropBlock);
	}

	static ClocheRenderReference ofCrop(Block cropBlock)
	{
		return new ClocheRenderReference("crop", cropBlock);
	}

	static ClocheRenderReference ofStacking(Block cropBlock)
	{
		return new ClocheRenderReference("stacking", cropBlock);
	}

	static ClocheRenderReference ofStem(Block cropBlock)
	{
		return new ClocheRenderReference("stem", cropBlock);
	}

	static ClocheRenderReference ofChorus()
	{
		return new ClocheRenderReference("chorus", Blocks.CHORUS_FLOWER);
	}

	static ClocheRenderReference ofHemp()
	{
		return new ClocheRenderReference("hemp", Blocks.AIR);
	}
}
