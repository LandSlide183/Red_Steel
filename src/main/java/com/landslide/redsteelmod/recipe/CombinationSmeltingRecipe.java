package com.landslide.redsteelmod.recipe;

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

    protected final IRecipeType<?> type;
    protected final ResourceLocation id;
    protected final String group;
    protected final Ingredient ingredient;
    protected final HashMap<Ingredient, ItemStack> metallurgies;

    public CombinationSmeltingRecipe(IRecipeType<?> typeIn, ResourceLocation idIn, String groupIn, Ingredient ingredientIn, HashMap<Ingredient, ItemStack> map) {
        type = typeIn;
        id = idIn;
        group = groupIn;
        ingredient = ingredientIn;
        metallurgies = map;
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(ModBlocks.COMBINATION_SMELTER);
    }

    public static final IRecipeType<CombinationSmeltingRecipe> commbination_smelting = IRecipeType.register("combination_smelting");


    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return ingredient.test(inv.getStackInSlot(0)) && !(metallurgies.get(inv.getStackInSlot(1)) == null);
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return metallurgies.get(inv.getStackInSlot(1));
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
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
        return null;
    }
}
