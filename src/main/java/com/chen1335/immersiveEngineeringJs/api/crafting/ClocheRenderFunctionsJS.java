package com.chen1335.immersiveEngineeringJs.api.crafting;

import blusunrize.immersiveengineering.api.crafting.ClocheRenderFunction;
import blusunrize.immersiveengineering.client.utils.ClocheRenderFunctions;
import net.minecraft.world.level.block.Block;

public interface ClocheRenderFunctionsJS {
    static ClocheRenderFunction ofGeneric(Block cropBlock) {
        return new ClocheRenderFunctions.RenderFunctionGeneric(cropBlock);
    }

    static ClocheRenderFunction ofCrop(Block cropBlock) {
        return new ClocheRenderFunctions.RenderFunctionCrop(cropBlock);
    }

    static ClocheRenderFunction ofDoubleCrop(Block cropBlock, int doublingAge) {
        return new ClocheRenderFunctions.RenderFunctionDoubleCrop(cropBlock, doublingAge);
    }

    static ClocheRenderFunction ofStacking(Block cropBlock) {
        return new ClocheRenderFunctions.RenderFunctionStacking(cropBlock);
    }

    static ClocheRenderFunction ofChorus() {
        return new ClocheRenderFunctions.RenderFunctionChorus();
    }

    static ClocheRenderFunction ofStem(Block cropBlock, Block stemBlock, Block attachedStemBlock) {
        return new ClocheRenderFunctions.RenderFunctionStem(cropBlock, stemBlock, attachedStemBlock);
    }

    static ClocheRenderFunction ofDoubleFlower(Block cropBlock) {
        return new ClocheRenderFunctions.RenderFunctionDoubleFlower(cropBlock);
    }

}
