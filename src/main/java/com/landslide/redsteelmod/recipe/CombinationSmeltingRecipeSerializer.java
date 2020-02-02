package com.landslide.redsteelmod.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.landslide.redsteelmod.RedSteelMain;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.HashMap;

public class CombinationSmeltingRecipeSerializer<T extends CombinationSmeltingRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {
    private final CombinationSmeltingRecipeSerializer.IFactory<T> factory;

    public CombinationSmeltingRecipeSerializer(CombinationSmeltingRecipeSerializer.IFactory<T> factoryIn) {
        factory = factoryIn;
        setRegistryName(RedSteelMain.MODID, "combination_smelting");
    }

    @Override
    public T read(ResourceLocation recipeId, JsonObject json) {

        String group = JSONUtils.getString(json, "group", "");

        Ingredient ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(json, "ingredient"));

        JsonArray jsonMetallurgies = JSONUtils.getJsonArray(json, "metallurgy");

        HashMap<Ingredient, ResultBundle> metallurgies = new HashMap<Ingredient, ResultBundle>();
        for (JsonElement obj : jsonMetallurgies) {
            Ingredient agent = Ingredient.deserialize(JSONUtils.getJsonArray(obj, "agent"));
            metallurgies.put(agent, ResultBundle.create(
                    ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(obj, "result")),
                    JSONUtils.getInt((JsonObject) obj, "time", 200),
                    JSONUtils.getFloat((JsonObject) obj, "experience", 0.0F)
            ));
        }

        return factory.create(recipeId, group, ingredient, metallurgies, json);
    }

    @Nullable
    @Override
    public T read(ResourceLocation recipeId, PacketBuffer buffer) {
        return read(recipeId, JSONUtils.fromJson(buffer.readString()));
    }

    @Override
    public void write(PacketBuffer buffer, T recipe) {

        buffer.writeString(recipe.getJson().getAsString());
    }
    public interface IFactory<T extends CombinationSmeltingRecipe> {
        T create(ResourceLocation recipeId, String group, Ingredient ingredient, HashMap<Ingredient, ResultBundle> map, JsonObject json);
    }
}
