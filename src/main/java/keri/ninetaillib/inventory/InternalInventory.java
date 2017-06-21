package keri.ninetaillib.inventory;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;

public class InternalInventory implements IInventory {

    private TileEntity tile;
    private ItemStack[] inventory;

    public InternalInventory(TileEntity tile, int size){
        this.tile = tile;
        this.inventory = new ItemStack[size];
    }

    @Override
    public int getSizeInventory() {
        return this.inventory.length;
    }

    @Nullable
    @Override
    public ItemStack getStackInSlot(int index) {
        return this.inventory[index];
    }

    /*@Nullable
    @Override
    public ItemStack decrStackSize(int index, int count) {
        if(this.tile instanceof IInventoryAction){
            EntityPlayer player = Minecraft.getMinecraft().player;
            IInventoryAction listener = (IInventoryAction)this.tile;
            listener.actionPerformed(player, EnumInventoryAction.DECR_STACK_SIZE, index);
        }

        return InventoryUtils.decrStackSize(this, index, count);
    }*/
    
    @Override
    public boolean isEmpty() {
    	return false;
    }

   /* @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index) {
        if(this.tile instanceof IInventoryAction){
            EntityPlayer player = Minecraft.getMinecraft().player;
            IInventoryAction listener = (IInventoryAction)this.tile;
            listener.actionPerformed(player, EnumInventoryAction.REMOVE_STACK_FROM_SLOT, index);
        }

        return InventoryUtils.removeStackFromSlot(this, index);
    }*/

    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack) {
        this.inventory[index] = stack;

        if(this.tile instanceof IInventoryAction){
            EntityPlayer player = Minecraft.getMinecraft().player;
            IInventoryAction listener = (IInventoryAction)this.tile;
            listener.actionPerformed(player, EnumInventoryAction.SET_SLOT_CONTENTS, index);
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {
        if(this.tile instanceof IInventoryAction){
            EntityPlayer player = Minecraft.getMinecraft().player;
            IInventoryAction listener = (IInventoryAction)this.tile;
            listener.actionPerformed(player, EnumInventoryAction.MARK_DIRTY, -1);
        }
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return player.getDistanceSq(this.tile.getPos().add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    @Override
    public void openInventory(EntityPlayer player) {
        if(this.tile instanceof IInventoryAction){
            IInventoryAction listener = (IInventoryAction)this.tile;
            listener.actionPerformed(player, EnumInventoryAction.OPEN, -1);
        }
    }

    @Override
    public void closeInventory(EntityPlayer player) {
        if(this.tile instanceof IInventoryAction){
            IInventoryAction listener = (IInventoryAction)this.tile;
            listener.actionPerformed(player, EnumInventoryAction.CLOSE, -1);
        }
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
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
    public void clear() {
        for(int i = 0; i < this.inventory.length; i++){
            this.setInventorySlotContents(i, null);
        }

        if(this.tile instanceof IInventoryAction){
            EntityPlayer player = Minecraft.getMinecraft().player;
            IInventoryAction listener = (IInventoryAction)this.tile;
            listener.actionPerformed(player, EnumInventoryAction.CLEAR, -1);
        }

        this.tile.markDirty();
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return null;
    }

    /*public void readInventoryFromNBT(NBTTagCompound tag){
        InventoryUtils.readItemStacksFromTag(this.inventory, tag.getTagList("internal_inventory", Constants.NBT.TAG_COMPOUND));
    }

    public void writeInventoryToNBT(NBTTagCompound tag){
        tag.setTag("internal_inventory", InventoryUtils.writeItemStacksToTag(this.inventory));
    }*/

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[InternalInventory Content Dump]");
        builder.append('|');
        int counter = 0;

        for(int i = 0; i < this.inventory.length; i++){
            ItemStack stack = this.getStackInSlot(i);

            if(stack != null){
                if(counter < i){
                    builder.append('|');
                }

                builder.append(stack.toString());
            }

            counter++;
        }

        return builder.toString();
    }

	@Override
	public ItemStack decrStackSize(int index, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		// TODO Auto-generated method stub
		return null;
	}

}
