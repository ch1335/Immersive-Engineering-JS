package com.chen1335.immersiveEngineeringJs.mixins;

import blusunrize.immersiveengineering.api.multiblocks.TemplateMultiblock;
import com.chen1335.immersiveEngineeringJs.kubejs.event.CreateStructureEvent;
import com.chen1335.immersiveEngineeringJs.kubejs.event.IEEvents;
import dev.latvian.mods.kubejs.event.EventResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TemplateMultiblock.class)
public class TemplateMultiblockMixin
{
	@Inject(
			method = "createStructure",
			at = @At(
					value = "INVOKE",
					target = "Lblusunrize/immersiveengineering/api/multiblocks/TemplateMultiblock;form(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Rotation;Lnet/minecraft/world/level/block/Mirror;Lnet/minecraft/core/Direction;)V"
			),
			cancellable = true,
			// createStructure and form belong to Immersive Engineering, not to Minecraft, so their
			// names are not obfuscated and must not be looked up in the refmap.
			remap = false
	)
	private void onCreateStructure(Level world, BlockPos pos, Direction side, Player player, CallbackInfoReturnable<Boolean> cir)
	{
		CreateStructureEvent event = new CreateStructureEvent(TemplateMultiblock.class.cast(this), world, pos, side, player);
		EventResult result = IEEvents.CREATE_STRUCTURE.post(event);

		if (result != null && result.interruptFalse())
		{
			cir.setReturnValue(false);
		}
	}
}
