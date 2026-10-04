package com.chen1335.immersiveEngineeringJs.api.crafting;

import blusunrize.immersiveengineering.api.crafting.StackWithChance;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

/**
 * A weighted recipe output.
 * <p>
 * Immersive Engineering 1.20.1 models these as {@code StackWithChance}, which only keeps a resolved
 * {@code ItemStack} and therefore loses tag outputs. From 1.21.1 onwards IE replaced that field with a
 * {@link TagOutput}, and the ported KubeJS components need the same fidelity: IE's own recipes use
 * tags such as {@code forge:dusts/wood} for crusher secondaries and mineral mix ores. This record
 * keeps the {@link TagOutput} plus the raw {@code conditions} array so that reading and re-writing an
 * existing IE recipe is lossless.
 */
public record ChanceOutput(TagOutput output, float chance, @Nullable JsonElement conditions)
{
	public static ChanceOutput of(TagOutput output, float chance)
	{
		return new ChanceOutput(output, chance, null);
	}

	public static ChanceOutput of(StackWithChance value)
	{
		return new ChanceOutput(TagOutput.of(value.stack().get()), value.chance(), null);
	}

	/**
	 * @return {@code null} when the wrapped output is empty; see {@link TagOutput#toJson()} for why an
	 * empty output cannot be represented in Immersive Engineering 1.20.1 JSON
	 */
	@Nullable
	public JsonElement toJson()
	{
		if (output.isEmpty())
		{
			return null;
		}

		JsonObject json = new JsonObject();
		json.add("output", output.toJson());
		json.addProperty("chance", chance);

		if (conditions != null)
		{
			json.add("conditions", conditions);
		}

		return json;
	}

	public static ChanceOutput fromJson(JsonElement json)
	{
		if (json == null || !json.isJsonObject())
		{
			throw new IllegalArgumentException("Cannot read '" + json + "' as a stack with chance!");
		}

		JsonObject object = json.getAsJsonObject();
		JsonElement output = object.has("output") ? object.get("output") : json;
		float chance = object.has("chance") ? object.get("chance").getAsFloat() : 1F;
		JsonElement conditions = object.has("conditions") ? object.get("conditions") : null;
		return new ChanceOutput(TagOutput.fromJson(output), chance, conditions);
	}
}
