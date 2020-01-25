package com.landslide.redsteelmod.blocks.tileentity;

import com.landslide.redsteelmod.RedSteelMain;
import com.landslide.redsteelmod.init.ModTileEntities;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CombinationSmelterTile extends TileEntity implements ITickableTileEntity {
    private LazyOptional<IItemHandler> handler = LazyOptional.of(this::createHandler);

    public CombinationSmelterTile() {
        super(ModTileEntities.COMBINATION_SMELTER_TILE);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        CompoundNBT invTag = tag.getCompound("inv");
        handler.ifPresent(h -> ((INBTSerializable<CompoundNBT>)h).deserializeNBT(invTag));
        return super.write(tag);
    }

    private IItemHandler createHandler() {
        return new ItemStackHandler(1) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return FurnaceTileEntity.isFuel(stack);
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (!isItemValid(slot, stack)) {
                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void read(CompoundNBT tag) {
        handler.ifPresent(h -> {
            tag.put("inv", ((INBTSerializable<CompoundNBT>)h).serializeNBT());
        });
        super.read(tag);
    }

    @Override
    public void tick() {

    }
}
