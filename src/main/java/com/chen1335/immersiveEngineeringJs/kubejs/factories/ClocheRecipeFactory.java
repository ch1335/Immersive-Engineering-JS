package com.chen1335.immersiveEngineeringJs.kubejs.factories;

import blusunrize.immersiveengineering.api.crafting.ClocheRenderFunction.ClocheRenderReference;
import com.chen1335.immersiveEngineeringJs.kubejs.recipe.ClocheRecipeSchema;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

/**
 * Port of the 1.21.1 {@code ClocheRecipeFactory}: Immersive Engineering 1.20.1 requires a
 * {@code render} object in the cloche recipe JSON, so this fills in a sensible default based on the
 * seed block whenever a script does not provide one.
 */
public class ClocheRecipeFactory extends RecipeJS
{
	@Override
	public void serialize()
	{
		if (getValue(ClocheRecipeSchema.RENDER) == null)
		{
			Ingredient seed = getValue(ClocheRecipeSchema.SEED);

			if (seed != null)
			{
				ItemStack[] stacks = seed.getItems();

				if (stacks.length > 0)
				{
					Block block = Block.byItem(stacks[0].getItem());
					String type = block.defaultBlockState().is(BlockTags.CROPS) ? "crop" : "generic";
					setValue(ClocheRecipeSchema.RENDER, new ClocheRenderReference(type, block));
				}
			}
		}

		super.serialize();
	}
}
