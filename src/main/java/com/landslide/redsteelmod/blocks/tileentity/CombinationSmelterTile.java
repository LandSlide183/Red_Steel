package com.landslide.redsteelmod.blocks.tileentity;

import com.landslide.redsteelmod.blocks.screen.container.CombinationSmelterContainer;
import com.landslide.redsteelmod.init.ModBlocks;
import com.landslide.redsteelmod.init.ModTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CombinationSmelterTile extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
    public static final int FUEL_SLOT = 0;
    public static final int PRIMARY_SLOT = 1;
    public static final int SECONDARY_SLOT = 2;
    public static final int PRIMARY_OUTPUT_SLOT = 3;
    public static final int SECONDARY_OUTPUT_SLOT = 4;

    public static final String INVENTORY_TAG = "inventory";

    public final ItemStackHandler inventory = new ItemStackHandler(5) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            switch (slot) {
                case FUEL_SLOT:
                    return FurnaceTileEntity.isFuel(stack);
            }
            return super.isItemValid(slot, stack);
        }

        @Override
        protected void onContentsChanged(final int slot) {
            CombinationSmelterTile.this.markDirty();
        }
    };

    private LazyOptional<IItemHandler> inventoryCapability = LazyOptional.of(() -> inventory);
    private LazyOptional<IItemHandlerModifiable> inventoryCapabilityUp = LazyOptional.of(() -> new RangedWrapper(inventory, FUEL_SLOT, FUEL_SLOT + 1));
    private LazyOptional<IItemHandlerModifiable> inventoryCapabilityFront = LazyOptional.of(() -> new RangedWrapper(inventory, PRIMARY_SLOT, PRIMARY_SLOT + 1));
    private LazyOptional<IItemHandlerModifiable> inventoryCapabilityBack = LazyOptional.of(() -> new RangedWrapper(inventory, SECONDARY_SLOT, SECONDARY_SLOT + 1));
    private LazyOptional<IItemHandlerModifiable> inventoryCapabilityDown = LazyOptional.of(() -> new RangedWrapper(inventory, PRIMARY_OUTPUT_SLOT, SECONDARY_OUTPUT_SLOT + 1));

    public CombinationSmelterTile() {
        super(ModTileEntities.COMBINATION_SMELTER_TILE);
        /*
        World world = getWorld();
        BlockPos pos = getPos();
        BlockState state = world.getBlockState(pos);
        directionFront = state.get(BlockStateProperties.FACING);
        */
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.put(INVENTORY_TAG, inventory.serializeNBT());
        return super.write(tag);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {

        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == null) {
                return inventoryCapability.cast();
            }
            switch (side) {
                case UP:
                    return inventoryCapabilityUp.cast();
                case DOWN:
                    return inventoryCapabilityDown.cast();
                default:
                    Direction facing = getWorld().getBlockState(getPos()).get(BlockStateProperties.FACING);
                    if (side.equals(facing)) {
                        return inventoryCapabilityFront.cast();
                    } else if (side.equals(facing.getOpposite())) {
                        return inventoryCapabilityBack.cast();
                    }
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void read(CompoundNBT tag) {
        inventory.deserializeNBT(tag.getCompound(INVENTORY_TAG));
        super.read(tag);
    }

    @Override
    public void tick() {

    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(ModBlocks.COMBINATION_SMELTER.getTranslationKey());
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new CombinationSmelterContainer(playerInventory, i, pos);
    }
}
