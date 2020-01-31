package com.landslide.redsteelmod.blocks.screen.container;

import com.landslide.redsteelmod.RedSteelMain;
import com.landslide.redsteelmod.init.ModBlocks;
import com.landslide.redsteelmod.init.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class CombinationSmelterContainer extends Container {
    public static final int PRIMARY_SLOT = 0;
    public static final int SECONDARY_SLOT = 1;
    public static final int FUEL_SLOT = 2;
    public static final int PRIMARY_OUTPUT_SLOT = 3;
    public static final int SECONDARY_OUTPUT_SLOT = 4;

    private TileEntity tileEntity;
    private PlayerEntity playerEntity;
    private PlayerInventory playerInventory;
    private IItemHandler inventoryHandler;
    private int playerInvSlotStartIndex;

    public CombinationSmelterContainer(PlayerInventory inventory, int windowId, BlockPos pos) {
        super(ModContainers.COMBINATION_SMELTER_CONTAINER, windowId);
        playerInventory = inventory;
        inventoryHandler = new InvWrapper(inventory);
        playerEntity = inventory.player;
        tileEntity = playerEntity.world.getTileEntity(pos);
        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            addSlot(h, PRIMARY_SLOT, 37, 17);
            addSlot(h, SECONDARY_SLOT, 55, 17);
            addSlot(h, FUEL_SLOT, 47, 53);
            addSlot(h, PRIMARY_OUTPUT_SLOT, 100, 38);
            addSlot(h, SECONDARY_OUTPUT_SLOT, 124, 38);
        });
        layoutPlayerInventorySlots(8, 84);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, ModBlocks.COMBINATION_SMELTER) ;
    }

    public void addSlot(IItemHandler handler, int index, int x, int y) {
        addSlot(new SlotItemHandler(handler, index, x, y));
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(handler, index, x, y);
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        playerInvSlotStartIndex = inventorySlots.size();
        // Player inventory
        addSlotBox(inventoryHandler, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(inventoryHandler, 0, leftCol, topRow, 9, 18);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();
            if (index < playerInvSlotStartIndex) {
                if (!this.mergeItemStack(stack, playerInvSlotStartIndex, playerInvSlotStartIndex + 36, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack, itemstack);
            } else {
                if (FurnaceTileEntity.isFuel(stack)) {
                    if (!this.mergeItemStack(stack, FUEL_SLOT, FUEL_SLOT + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < playerInvSlotStartIndex + 27) {
                    if (!this.mergeItemStack(stack, playerInvSlotStartIndex + 27, playerInvSlotStartIndex + 36, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < playerInvSlotStartIndex + 36 && !this.mergeItemStack(stack, playerInvSlotStartIndex, playerInvSlotStartIndex + 1, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }

        return itemstack;
    }
}
