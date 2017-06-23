package net.magicaltech.util;

import java.util.Objects;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

/**
 * IInventory implementation which saves and loads from an NBT tag
 */
public class InventoryNBT implements IInventory {

    protected ItemStack[] items;
    protected NBTTagCompound tag;

    public InventoryNBT(int size, NBTTagCompound tag) {
        this.tag = tag;
        items = new ItemStack[size];
        ArrayUtils.fillArray(items, ItemStack.EMPTY, (Objects::isNull));
        readNBT();
    }

    private void writeNBT() {
        tag.setTag("items", InventoryUtils.writeItemStacksToTag(items, getInventoryStackLimit()));
    }

    private void readNBT() {
        if (tag.hasKey("items")) {
            InventoryUtils.readItemStacksFromTag(items, tag.getTagList("items", 10));
        }
    }

    @Override
    public int getSizeInventory() {
        return items.length;
    }

    @Override
    public boolean isEmpty() {
        return ArrayUtils.count(items, (stack -> !stack.isEmpty())) <= 0;
    }

    @Override
    @Nonnull
    public ItemStack getStackInSlot(int slot) {
        return items[slot];
    }

    @Override
    @Nonnull
    public ItemStack decrStackSize(int slot, int amount) {
        return InventoryUtils.decrStackSize(this, slot, amount);
    }

    @Override
    @Nonnull
    public ItemStack removeStackFromSlot(int slot) {
        return InventoryUtils.removeStackFromSlot(this, slot);
    }

    @Override
    public void setInventorySlotContents(int slot, @Nonnull ItemStack stack) {
        items[slot] = stack;
        markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {
        writeNBT();
    }

    @Override
    public void clear() {
        for (int i = 0; i < items.length; i++) {
            items[i] = ItemStack.EMPTY;
        }
        markDirty();
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {
    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public boolean isUsableByPlayer(@Nonnull EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(@Nonnull EntityPlayer player) {
    }

    @Override
    public void closeInventory(@Nonnull EntityPlayer player) {
    }

    @Override
    public boolean isItemValidForSlot(int i, @Nonnull ItemStack itemstack) {
        return true;
    }

    @Override
    @Nonnull
    public String getName() {
        return "NBT";
    }

    @Override
    public boolean hasCustomName() {
        return true;
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        return new TextComponentString(getName());
    }
}
