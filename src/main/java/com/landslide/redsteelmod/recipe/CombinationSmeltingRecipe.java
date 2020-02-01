package com.landslide.redsteelmod.recipe;

import com.google.gson.JsonObject;
import com.landslide.redsteelmod.init.ModBlocks;
import com.landslide.redsteelmod.init.ModRecipes;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.HashMap;

public class CombinationSmeltingRecipe implements IRecipe<IInventory> {
    public static final int PRIMARY_SLOT = 0;
    public static final int SECONDARY_SLOT = 1;

    public static final IRecipeType<CombinationSmeltingRecipe> commbination_smelting = IRecipeType.register("combination_smelting");

    protected final JsonObject json;
    protected final IRecipeType<?> type;
    protected final ResourceLocation id;
    protected final String group;
    protected final Ingredient ingredient;
    protected final HashMap<Ingredient, ResultBundle> metallurgies;

    public CombinationSmeltingRecipe(JsonObject jsonIn, IRecipeType<?> typeIn, ResourceLocation idIn, String groupIn, Ingredient ingredientIn, HashMap<Ingredient, ResultBundle> map) {
        json = jsonIn;
        type = typeIn;
        id = idIn;
        group = groupIn;
        ingredient = ingredientIn;
        metallurgies = map;
    }

    public JsonObject getJson() {
        return json;
    }

    public HashMap<Ingredient, ResultBundle> getMetallurgies() {
        return metallurgies;
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(ModBlocks.COMBINATION_SMELTER);
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return ingredient.test(inv.getStackInSlot(PRIMARY_SLOT));
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        if (ingredient.test(inv.getStackInSlot(PRIMARY_SLOT))) {
            return metallurgies.get(Ingredient.fromStacks(inv.getStackInSlot(SECONDARY_SLOT))).getResult();
        }
        return ItemStack.EMPTY;
    }

    public ResultBundle getResultBundle(IInventory inv) {
        if (isAgentValid(inv)) {
            return metallurgies.get(Ingredient.fromStacks(inv.getStackInSlot(SECONDARY_SLOT)));
        }
        return (ResultBundle)null;
    }

    public boolean isAgentValid(IInventory inv) {
        if  (ingredient.test(inv.getStackInSlot(PRIMARY_SLOT))) {
            return metallurgies.containsKey(Ingredient.fromStacks(inv.getStackInSlot(SECONDARY_SLOT)));
        }
        return false;
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return IRecipeSerializer.register("combination_smelting", ModRecipes.COMBINATION_SMELTING);
    }

    @Override
    public IRecipeType<?> getType() {
        return type;
    }
}
