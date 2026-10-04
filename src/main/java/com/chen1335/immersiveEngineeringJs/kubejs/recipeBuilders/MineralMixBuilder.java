package com.chen1335.immersiveEngineeringJs.kubejs.recipeBuilders;

import blusunrize.immersiveengineering.api.IEApi;
import com.chen1335.immersiveEngineeringJs.api.crafting.ChanceOutput;
import com.chen1335.immersiveEngineeringJs.api.crafting.TagOutput;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipesEventJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Script helper that builds an Immersive Engineering mineral mix.
 * <p>
 * Immersive Engineering 1.20.1 restricts mineral mixes by dimension ({@code dimensions}) instead of
 * the biome tag predicates used from 1.21.1, so {@code biomeCondition} is replaced by
 * {@code dimension}. Ores and spoils are stored as {@link ChanceOutput}, which keeps tag outputs
 * such as {@code forge:ores/iron} intact in the generated JSON; IE resolves them to a concrete stack
 * when it loads the recipe.
 */
public class MineralMixBuilder
{
	private final List<ChanceOutput> outputs = new ArrayList<>();
	private final List<ChanceOutput> spoils = new ArrayList<>();
	private final Set<ResourceLocation> dimensions = new LinkedHashSet<>();
	private int weight;
	private float failChance;
	private Block background = Blocks.STONE;

	public static MineralMixBuilder builder()
	{
		return new MineralMixBuilder();
	}

	public MineralMixBuilder dimension(String... dimensionIds)
	{
		for (String id : dimensionIds)
		{
			dimensions.add(ResourceLocation.parse(id));
		}

		return this;
	}

	public MineralMixBuilder dimensionOverworld()
	{
		return dimension("minecraft:overworld");
	}

	public MineralMixBuilder dimensionNether()
	{
		return dimension("minecraft:the_nether");
	}

	public MineralMixBuilder dimensionEnd()
	{
		return dimension("minecraft:the_end");
	}

	public MineralMixBuilder background(Block background)
	{
		this.background = background;
		return this;
	}

	public MineralMixBuilder spoil(ItemLike output, float weight)
	{
		this.spoils.add(ChanceOutput.of(TagOutput.of(new ItemStack(output)), weight));
		return this;
	}

	public MineralMixBuilder ore(ItemLike output, float weight)
	{
		this.outputs.add(ChanceOutput.of(TagOutput.of(new ItemStack(output)), weight));
		return this;
	}

	public MineralMixBuilder ore(TagKey<Item> output, float weight)
	{
		this.outputs.add(ChanceOutput.of(TagOutput.of(output), weight));
		return this;
	}

	public MineralMixBuilder weight(int weight)
	{
		this.weight = weight;
		return this;
	}

	public MineralMixBuilder failChance(float failChance)
	{
		this.failChance = failChance;
		return this;
	}

	public MineralMixBuilder addOverworldSpoils()
	{
		return spoil(Items.GRAVEL, 0.2F)
				.spoil(Items.COBBLESTONE, 0.5F)
				.spoil(Items.COBBLED_DEEPSLATE, 0.3F);
	}

	public MineralMixBuilder addSoilSpoils()
	{
		return spoil(Items.GRAVEL, 0.6F)
				.spoil(Items.COBBLESTONE, 0.3F)
				.spoil(Items.COARSE_DIRT, 0.1F);
	}

	public MineralMixBuilder addSeabedSpoils()
	{
		return spoil(Items.SANDSTONE, 0.6F)
				.spoil(Items.GRAVEL, 0.3F)
				.spoil(Items.SAND, 0.1F);
	}

	public MineralMixBuilder addNetherSpoils()
	{
		return spoil(Items.NETHERRACK, 0.5F)
				.spoil(Blocks.BASALT, 0.3F)
				.spoil(Blocks.GRAVEL, 0.2F);
	}

	/**
	 * Registers the mix. Called as {@code build(event, 'name')} from scripts.
	 * <p>
	 * Note there is no Rhino {@code Context} parameter: KubeJS 2101 injects one for script calls, but
	 * KubeJS 2001 does not, so the 1.21.1 signature {@code build(Context, RecipesEventJS, String)} is
	 * not callable from a 1.20.1 script at all.
	 */
	public void build(RecipesEventJS recipesEvent, String name)
	{
		RecipeJS recipe = recipesEvent.getRecipeFunction(IEApi.ieLoc("mineral_mix").toString())
				.createRecipe(new Object[]{
						outputs.toArray(new ChanceOutput[0]),
						spoils.toArray(new ChanceOutput[0]),
						weight,
						dimensions.toArray(new ResourceLocation[0]),
						failChance,
						background
				});

		recipe.id(recipeId(name));
	}

	static ResourceLocation recipeId(String name)
	{
		return name.contains(":") ? ResourceLocation.parse(name)
				: ResourceLocation.fromNamespaceAndPath("kubejs", name);
	}
}
