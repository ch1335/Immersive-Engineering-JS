package com.chen1335.immersiveEngineeringJs.mixins;

import blusunrize.immersiveengineering.api.excavator.MineralMix;
import blusunrize.immersiveengineering.common.items.CoresampleItem;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Objects;

@Mixin(CoresampleItem.class)
public class CoresampleItemMixin {
    @Inject(method = "getMineralMixes", at = @At("RETURN"), cancellable = true)
    private static void filterMineralMixes(Level level, ItemStack coresample, CallbackInfoReturnable<List<RecipeHolder<MineralMix>>> cir) {
        cir.setReturnValue(cir.getReturnValue().stream().filter(Objects::nonNull).toList());
    }


    @Inject(method = "getCoresampleInfo", at = @At("HEAD"))
    private static void getCoresampleInfo(CoresampleItem.ItemData data, List<Component> list, ChatFormatting baseColor, Level level, boolean showYield, boolean showTimestamp, CallbackInfo ci, @Local(argsOnly = true) LocalRef<CoresampleItem.ItemData> dataLocalRef) {
        if (level != null) {
            dataLocalRef.set(new CoresampleItem.ItemData(data.position(), data.veins().stream().filter(data1 -> MineralMix.RECIPES.getById(level, data1.mineral()) != null).toList(), data.timestamp()));
        }
    }
}
