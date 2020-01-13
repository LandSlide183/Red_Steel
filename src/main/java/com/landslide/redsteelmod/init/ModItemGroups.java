package com.landslide.redsteelmod.init;

import com.landslide.redsteelmod.RedSteelMain;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.function.Supplier;

public class ModItemGroups {

    public static class ModItemGroup extends ItemGroup {
        private final Supplier<ItemStack> iconSupplier;
        public ModItemGroup(String name, Supplier<ItemStack> icon) {
            super(name);
            iconSupplier = icon;
        }


        @Override
        public ItemStack createIcon() {
            return iconSupplier.get();
        }
    }

    public static final ItemGroup MOD_MAIN_GROUP = new ModItemGroup(RedSteelMain.MODID, () -> new ItemStack(ModItems.RED_STEEL_INGOT));
}
