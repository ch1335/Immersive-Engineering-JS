package com.chen1335.immersiveEngineeringJs.kubejs.factories;

import com.chen1335.immersiveEngineeringJs.api.crafting.TagOutput;
import com.chen1335.immersiveEngineeringJs.kubejs.recipe.SawmillSchema;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Immersive Engineering 1.20.1 stores sawmill by-products in one {@code secondaries} array whose
 * entries carry a {@code stripping} flag, while the KubeJS script API (ported from 1.21.1) exposes
 * them as two separate keys. This factory merges them when writing and splits them when reading.
 */
public class SawmillRecipeFactory extends RecipeJS
{
	@Override
	public void serialize()
	{
		super.serialize();

		json.remove(SawmillSchema.STRIPPING_SECONDARIES.name);
		json.remove(SawmillSchema.SECONDARY_OUTPUTS.name);

		JsonArray secondaries = new JsonArray();
		appendSecondaries(secondaries, getValue(SawmillSchema.STRIPPING_SECONDARIES), true);
		appendSecondaries(secondaries, getValue(SawmillSchema.SECONDARY_OUTPUTS), false);
		json.add("secondaries", secondaries);
	}

	@Override
	public void deserialize(boolean merge)
	{
		super.deserialize(merge);

		JsonElement secondaries = json.get("secondaries");

		if (secondaries == null || !secondaries.isJsonArray())
		{
			return;
		}

		List<TagOutput> stripping = new ArrayList<>();
		List<TagOutput> outputs = new ArrayList<>();

		for (JsonElement element : secondaries.getAsJsonArray())
		{
			if (!element.isJsonObject())
			{
				continue;
			}

			JsonObject object = element.getAsJsonObject();
			TagOutput output = TagOutput.fromJson(object.get("output"));

			if (output.isEmpty())
			{
				continue;
			}

			if (object.has("stripping") && object.get("stripping").getAsBoolean())
			{
				stripping.add(output);
			}
			else
			{
				outputs.add(output);
			}
		}

		setRaw(SawmillSchema.STRIPPING_SECONDARIES, stripping.toArray(new TagOutput[0]));
		setRaw(SawmillSchema.SECONDARY_OUTPUTS, outputs.toArray(new TagOutput[0]));
	}

	private static void appendSecondaries(JsonArray target, TagOutput[] outputs, boolean stripping)
	{
		if (outputs == null)
		{
			return;
		}

		for (TagOutput output : outputs)
		{
			if (output == null || output.isEmpty())
			{
				continue;
			}

			// "output" first, then "stripping": that is the order Immersive Engineering writes, so an
			// untouched IE sawmill recipe round-trips byte for byte and KubeJS does not report it as
			// modified.
			JsonObject element = new JsonObject();
			element.add("output", output.toJson());
			element.addProperty("stripping", stripping);
			target.add(element);
		}
	}

	/**
	 * Stores a value split out of the merged {@code secondaries} array without marking it writable.
	 * {@link RecipeJS#hasChanged()} reports a recipe as modified as soon as any holder is marked for
	 * writing, and re-serializing every Immersive Engineering sawmill recipe on load would be wrong:
	 * the recipe has not actually been touched by a script.
	 */
	@SuppressWarnings("unchecked")
	private void setRaw(RecipeKey<?> key, Object value)
	{
		RecipeComponentValue<?> holder = getAllValueMap().get(key.name);

		if (holder != null)
		{
			((RecipeComponentValue<Object>) holder).value = value;
		}
	}
}
