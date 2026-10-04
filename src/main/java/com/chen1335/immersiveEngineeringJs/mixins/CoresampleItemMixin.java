package com.chen1335.immersiveEngineeringJs.mixins;

import blusunrize.immersiveengineering.api.excavator.MineralMix;
import blusunrize.immersiveengineering.common.items.CoresampleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.Objects;

/**
 * Drops mineral mixes that no longer exist from a core sample, mirroring the 1.21.1
 * {@code CoresampleItemMixin}. 1.20.1 returns a bare {@code MineralMix[]} instead of a list of
 * {@code RecipeHolder}s and has no {@code ItemData} record, so only the filtering injection is
 * needed here.
 */
@Mixin(CoresampleItem.class)
public class CoresampleItemMixin
{
	// getMineralMixes belongs to Immersive Engineering, not to Minecraft, so its name is not
	// obfuscated and must not be looked up in the refmap.
	@Inject(method = "getMineralMixes", at = @At("RETURN"), cancellable = true, remap = false)
	private static void filterMineralMixes(Level level, ItemStack coresample, CallbackInfoReturnable<MineralMix[]> cir)
	{
		MineralMix[] mixes = cir.getReturnValue();

		if (mixes == null || mixes.length == 0)
		{
			cir.setReturnValue(new MineralMix[0]);
			return;
		}

		boolean hasNull = false;

		for (MineralMix mix : mixes)
		{
			if (mix == null)
			{
				hasNull = true;
				break;
			}
		}

		if (hasNull)
		{
			cir.setReturnValue(Arrays.stream(mixes).filter(Objects::nonNull).toArray(MineralMix[]::new));
		}
	}
}
