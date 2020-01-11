package com.landslide.redsteelmod.init;

import com.landslide.redsteelmod.RedSteelMain;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.function.Supplier;

public class ModItemGroups {

    public static class ModItemGroup extends ItemGroup {
        private final Supplier<ItemStack> iconSupplier;
        public ModItemGroup(String name, Supplier<ItemStack> iconSupplier) {
            super(name);
            this.iconSupplier = iconSupplier;
        }


        @Override
        public ItemStack createIcon() {
            return iconSupplier.get();
        }
    }

    public static final ItemGroup MOD_ITEM_GROUP = new ModItemGroup(RedSteelMain.MODID, () -> new ItemStack(ModItems.STEEL_INGOT));
}
