package com.landslide.redsteelmod.recipe;

import net.minecraft.item.ItemStack;

public class ResultBundle {
    private ItemStack resultItems;
    private int smeltTime;
    private float experience;

    public ItemStack getResult() {
        return resultItems;
    }

    public int getSmeltTime() {
        return smeltTime;
    }

    public float getExperience() {
        return experience;
    }

    private ResultBundle(ItemStack resultItems, int smeltTime, float experience) {
        this.resultItems = resultItems;
        this.smeltTime = smeltTime;
        this.experience = experience;
    }

    public static ResultBundle create(ItemStack resultItems, int smeltTime, float experience) {
        return new ResultBundle(resultItems, smeltTime, experience);
    }
}
