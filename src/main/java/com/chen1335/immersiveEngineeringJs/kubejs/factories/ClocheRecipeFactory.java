package com.chen1335.immersiveEngineeringJs.kubejs.factories;

import blusunrize.immersiveengineering.api.IEApi;
import blusunrize.immersiveengineering.api.crafting.ClocheRenderFunction;
import blusunrize.immersiveengineering.client.utils.ClocheRenderFunctions;
import com.chen1335.immersiveEngineeringJs.kubejs.recipe.ClocheRecipeSchema;
import dev.latvian.mods.kubejs.recipe.KubeRecipe;
import dev.latvian.mods.kubejs.recipe.component.RecipeValidationContext;
import dev.latvian.mods.kubejs.recipe.schema.KubeRecipeFactory;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ClocheRecipeFactory extends KubeRecipe {
    public static final KubeRecipeFactory RECIPE_FACTORY = new KubeRecipeFactory(IEApi.ieLoc("cloche"), ClocheRecipeFactory.class, ClocheRecipeFactory::new);

    @Override
    public void validate(RecipeValidationContext cx) {
        super.validate(cx);
        @Nullable ClocheRenderFunction renderFunction = getValue(ClocheRecipeSchema.RENDER);
        if (renderFunction == null) {
            Ingredient seed = Objects.requireNonNull(getValue(ClocheRecipeSchema.SEED));
            Block block = Block.byItem(seed.getItems()[0].getItem());
            if (block.defaultBlockState().is(BlockTags.CROPS)) {
                setValue(ClocheRecipeSchema.RENDER, new ClocheRenderFunctions.RenderFunctionCrop(block));
            }else {
                setValue(ClocheRecipeSchema.RENDER, new ClocheRenderFunctions.RenderFunctionGeneric(block));
            }

        }
    }
}
