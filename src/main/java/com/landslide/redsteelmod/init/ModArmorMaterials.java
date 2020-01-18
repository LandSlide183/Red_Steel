package com.landslide.redsteelmod.init;

import com.landslide.redsteelmod.RedSteelMain;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public enum ModArmorMaterials implements IArmorMaterial {
    steel("steel", 20, new int[]  {2, 5, 6, 2}, 9, ModItems.STEEL_INGOT, "item.armor.equip_iron", 0.5F),
    redSteel("red_steel", 38, new int[] {3, 6, 8, 3}, 10, ModItems.RED_STEEL_INGOT, "item.armor.equip_iron", 2.5F);

    private static final int[] max_damage_array = new int[] {13, 15, 16, 11};
    private String name;
    private int durability, enchantability;
    private Item repairItem;
    private int[] damageReductionAmounts;
    private float toughness;
    private SoundEvent soundEvent;

    private ModArmorMaterials(String name, int durability, int[] damageReductionAmounts, int enchanatibilty, Item repairItem, String equipSound, float toughness) {
        this.name = name;
        this.durability = durability;
        this.enchantability = enchanatibilty;
        this.repairItem = repairItem;
        this.damageReductionAmounts = damageReductionAmounts;
        this.toughness = toughness;
        this.soundEvent = new SoundEvent(new ResourceLocation(equipSound));
    }

    @Override
    public int getDurability(EquipmentSlotType slot) {
        return max_damage_array[slot.getIndex()] * durability;
    }

    @Override
    public int getDamageReductionAmount(EquipmentSlotType slot) {
        return damageReductionAmounts[slot.getIndex()];
    }

    @Override
    public int getEnchantability() {
        return enchantability;
    }

    @Override
    public SoundEvent getSoundEvent() {
        return soundEvent;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return Ingredient.fromItems(repairItem);
    }

    @Override
    public String getName() {
        return RedSteelMain.MODID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return toughness;
    }
}
