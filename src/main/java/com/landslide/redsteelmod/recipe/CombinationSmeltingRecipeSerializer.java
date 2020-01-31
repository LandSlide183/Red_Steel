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
        HashMap<Ingredient, ItemStack> metallurgies = new HashMap<Ingredient, ItemStack>();
        for (JsonElement obj : jsonMetallurgies) {
            metallurgies.put(Ingredient.deserialize(JSONUtils.getJsonArray(obj, "modifier")), ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(obj, "result")));
        }

        return factory.create(recipeId, group, ingredient, metallurgies);
    }

    @Nullable
    @Override
    public T read(ResourceLocation recipeId, PacketBuffer buffer) {
        return null;
    }

    @Override
    public void write(PacketBuffer buffer, T recipe) {

    }
    public interface IFactory<T extends CombinationSmeltingRecipe> {
        T create(ResourceLocation resourceLocation, String group, Ingredient ingredient, HashMap<Ingredient, ItemStack> map);
    }
}
