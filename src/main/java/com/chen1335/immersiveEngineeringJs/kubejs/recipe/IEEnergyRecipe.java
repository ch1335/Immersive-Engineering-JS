package com.chen1335.immersiveEngineeringJs.kubejs.recipe;

import blusunrize.immersiveengineering.api.crafting.ArcFurnaceRecipe;
import blusunrize.immersiveengineering.api.crafting.CrusherRecipe;
import blusunrize.immersiveengineering.api.crafting.FermenterRecipe;
import blusunrize.immersiveengineering.api.crafting.MetalPressRecipe;
import blusunrize.immersiveengineering.api.crafting.MixerRecipe;
import blusunrize.immersiveengineering.api.crafting.MultiblockRecipe;
import blusunrize.immersiveengineering.api.crafting.RefineryRecipe;
import blusunrize.immersiveengineering.api.crafting.SawmillRecipe;
import blusunrize.immersiveengineering.api.crafting.SqueezerRecipe;
import blusunrize.immersiveengineering.api.utils.SetRestrictedField;
import com.chen1335.immersiveEngineeringJs.ImmersiveEngineeringJs;
import dev.latvian.mods.kubejs.error.KubeRuntimeException;
import dev.latvian.mods.kubejs.recipe.KubeRecipe;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.RecipeValidationContext;
import dev.latvian.mods.kubejs.recipe.schema.KubeRecipeFactory;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.Map;

/**
 * Recipe class for the Immersive Engineering multiblock recipes that take an {@code energy} value.
 * <p>
 * Immersive Engineering turns a recipe into an energy <i>per tick</i> value with integer division:
 *
 * <pre>
 * // MultiblockRecipe
 * getTotalProcessEnergy() = (int) Math.max(1, baseEnergy * energyModifier)
 * getTotalProcessTime()   = (int) Math.max(1, baseTime   * timeModifier)
 *
 * // MultiblockProcess#populateLevelData
 * int energyPerTick = recipe.getTotalProcessEnergy() / maxTicks;
 *
 * // MultiblockProcess#doProcessTick
 * averageInsertion / levelData.energyPerTick
 * </pre>
 *
 * <p>
 * When a recipe's energy is smaller than its process time, {@code energyPerTick} comes out as
 * {@code 0} and the division in {@code doProcessTick} throws {@code ArithmeticException: / by zero}
 * while the machine is ticked. The running process is saved in the block entity, so the world then
 * fails to load again and keeps crashing every tick until the recipe is fixed or removed - the player
 * cannot rejoin. Immersive Engineering itself does not guard against it.
 * <p>
 * This class rejects such a recipe while it is being created, with a message that says what to raise.
 * See {@link #validate(RecipeValidationContext)}; {@link #RECIPE_FACTORY} is wired to the affected
 * schemas and registered by the plugin.
 */
public class IEEnergyRecipe extends KubeRecipe
{
	public static final KubeRecipeFactory RECIPE_FACTORY = new KubeRecipeFactory(
			ResourceLocation.fromNamespaceAndPath(ImmersiveEngineeringJs.MODID, "energy_checked"),
			IEEnergyRecipe.class,
			IEEnergyRecipe::new
	);

	/**
	 * Machines whose process time is a constant, taken from the {@code super(...)} call of the matching
	 * {@code blusunrize.immersiveengineering.api.crafting.*Recipe} constructor. {@code mixer} and
	 * {@code arc_furnace} are not listed - their time comes from the recipe itself, see
	 * {@link #baseTime(RecipeSchema, String)}.
	 */
	private static final Map<String, Integer> FIXED_BASE_TIME = Map.of(
			"crusher", 50,
			"fermenter", 80,
			"metal_press", 60,
			"refinery", 1,
			"sawmill", 80,
			"squeezer", 80
	);

	/** The {@code MULTIPLIERS} field of each machine, as wired up by {@code IEServerConfig}. */
	private static final Map<String, SetRestrictedField<MultiblockRecipe.RecipeMultiplier>> MULTIPLIERS = Map.of(
			"arc_furnace", ArcFurnaceRecipe.MULTIPLIERS,
			"crusher", CrusherRecipe.MULTIPLIERS,
			"fermenter", FermenterRecipe.MULTIPLIERS,
			"metal_press", MetalPressRecipe.MULTIPLIERS,
			"mixer", MixerRecipe.MULTIPLIERS,
			"refinery", RefineryRecipe.MULTIPLIERS,
			"sawmill", SawmillRecipe.MULTIPLIERS,
			"squeezer", SqueezerRecipe.MULTIPLIERS
	);

	@Override
	public void validate(RecipeValidationContext cx)
	{
		super.validate(cx);

		RecipeSchema schema = type.schemaType.schema;
		RecipeKey<Integer> energyKey = schema.getOptionalKey("energy");

		if(energyKey == null)
			return;

		Integer energy = getValue(energyKey);

		if(energy == null)
			return;

		String machine = type.id.getPath();
		Integer baseTime = baseTime(schema, machine);

		if(baseTime == null)
			return;

		// Read the same modifiers Immersive Engineering uses, so the check stays correct on packs that
		// changed them. They live in IE's server config, which is NOT readable while recipes are being
		// created ("Cannot get config value before config is loaded") - that is exactly why IE itself
		// wraps them in a Lazy and only evaluates them once the machine runs. Fall back to the defaults.
		double timeModifier = 1.0D;
		double energyModifier = 1.0D;
		SetRestrictedField<MultiblockRecipe.RecipeMultiplier> multipliers = MULTIPLIERS.get(machine);

		if(multipliers!=null&&multipliers.isInitialized())
		{
			try
			{
				MultiblockRecipe.RecipeMultiplier modifier = multipliers.get();
				timeModifier = modifier.timeModifier().getAsDouble();
				energyModifier = modifier.energyModifier().getAsDouble();
			}
			catch(Throwable ignored)
			{
				timeModifier = 1.0D;
				energyModifier = 1.0D;
			}
		}

		int maxTicks = (int)Math.max(1, baseTime*timeModifier);
		int totalEnergy = (int)Math.max(1, energy*energyModifier);

		if(totalEnergy/maxTicks > 0)
			return;

		long minimumEnergy = (long)Math.ceil(maxTicks/energyModifier);

		throw new KubeRuntimeException("energy="+energy+" is too low for this "+machine+" recipe and would crash"
				+" the machine's world: it takes "+baseTime+" ticks, so the energy per tick is "
				+totalEnergy+"/"+maxTicks+" = 0, and Immersive Engineering divides by that value on every tick"
				+" (MultiblockProcess#doProcessTick). The running process is saved in the block entity, so the"
				+" world keeps crashing on load until the recipe is fixed or removed. Use energy >= "
				+minimumEnergy+"; Immersive Engineering's own recipes are well above that (its mixer recipes use"
				+" 6x the output amount).");
	}

	private Integer baseTime(RecipeSchema schema, String machine)
	{
		if("mixer".equals(machine))
		{
			// MixerRecipe: baseTime = the output fluid amount
			RecipeKey<FluidStack> resultKey = schema.getOptionalKey("result");
			FluidStack result = resultKey==null?null: getValue(resultKey);

			return result==null?null: result.getAmount();
		}

		if("arc_furnace".equals(machine))
		{
			// ArcFurnaceRecipe: baseTime = the recipe's "time"
			RecipeKey<Integer> timeKey = schema.getOptionalKey("time");

			return timeKey==null?null: getValue(timeKey);
		}

		return FIXED_BASE_TIME.get(machine);
	}
}
