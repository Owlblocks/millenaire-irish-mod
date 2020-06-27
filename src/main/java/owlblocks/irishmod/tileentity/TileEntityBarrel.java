package tileentity;

import common.IrishMod;
import items.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;

public class TileEntityBarrel extends TileEntity implements ITickable {

	public static final String ID_STEP = "barrel_brew_step";
	public static final String ID_TICK = "barrel_malt_amount";
	public static final String ID_MALT = "barrel_ferment_time";
	public static final String ID_DRINK = "barrel_drinks_left";
	
	private static final int TICK_GOAL = 240;
	private static final int MALT_GOAL = 5;
	private static final int DRINKS_MADE = 5;
	
	private BrewingStep currentStep = BrewingStep.EMPTY;
	private int maltAdded = 0;
	private int ticks = 0;
	private int drinksLeft = 0;
	
	public enum BrewingStep {
		EMPTY,
		ADD_MALT,
		FERMENT,
		ALCOHOLIC
	}
	
	public String getStatusMessage() {
		switch(currentStep) {
		case ADD_MALT:
			return "Malt Added: " + maltAdded + "/" + MALT_GOAL;
		case ALCOHOLIC:
			return "Drinks Left: " + drinksLeft + "/" + DRINKS_MADE;
		case EMPTY:
			return "Empty Barrel. Add water to begin brewing process.";
		case FERMENT:
			return (int)(((double)(ticks) / (double)(TICK_GOAL)) * 100) + "% finished";
		default:
			return "Error";
		}
	}
	
	public void barrelInteract(EntityPlayer player, EnumHand hand) {
		ItemStack held = player.getHeldItem(hand);
		
		if(held.isEmpty()) {
			player.sendMessage(new TextComponentString(getStatusMessage()));
			return;
		}
		switch(currentStep) {
		case ADD_MALT:
			if(held.getItem() == ModItems.MALT) {
				maltAdded++;
				held.shrink(1);
				if(maltAdded >= MALT_GOAL) {
					currentStep = BrewingStep.FERMENT;
				}
				markDirty();
			}
			break;
		case ALCOHOLIC:
			if(held.getItem() == Items.GLASS_BOTTLE) {
				if (!player.capabilities.isCreativeMode)
                {
                    ItemStack alcohol = new ItemStack(ModItems.ALE);
                    held.shrink(1);

                    if (held.isEmpty())
                    {
                        player.setHeldItem(hand, alcohol);
                    }
                    else if (!player.inventory.addItemStackToInventory(alcohol))
                    {
                        player.dropItem(alcohol, false);
                    }
                    else if (player instanceof EntityPlayerMP)
                    {
                        ((EntityPlayerMP)player).sendContainerToPlayer(((EntityPlayerMP)player).inventoryContainer);
                    }
                }

                world.playSound((EntityPlayer)null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                drinksLeft--;
                if(drinksLeft <= 0) {
                	drinksLeft = 0;
                	currentStep = BrewingStep.EMPTY;
                }
                markDirty();
			}
			break;
		case EMPTY:
			if(held.getItem() == Items.WATER_BUCKET) {
				if (!player.capabilities.isCreativeMode)
                {
                    player.setHeldItem(hand, new ItemStack(Items.BUCKET));
                }
				currentStep = BrewingStep.ADD_MALT;
				markDirty();
			}
			break;
		case FERMENT:
			// Nothing?
			break;
		default:
			break; // Unreachable
		}
	}
	
	@Override
	public void update() {
		if(currentStep == BrewingStep.FERMENT) {
			ticks++;
			if(ticks >= TICK_GOAL) {
				currentStep = BrewingStep.ALCOHOLIC;
				ticks = 0;
				drinksLeft = DRINKS_MADE;
			}
			markDirty();
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		IrishMod.logger.info("Reading NBT...");
		if(compound.hasKey(ID_STEP)) {
			currentStep = BrewingStep.values()[compound.getInteger(ID_STEP)];
		}
		if(currentStep == BrewingStep.ADD_MALT && compound.hasKey(ID_MALT)) {
			maltAdded = compound.getInteger(ID_MALT);
		}
		if(currentStep == BrewingStep.FERMENT && compound.hasKey(ID_TICK)) {
			ticks = compound.getInteger(ID_TICK);
		}
		if(currentStep == BrewingStep.ALCOHOLIC && compound.hasKey(ID_DRINK)) {
			drinksLeft = compound.getInteger(ID_DRINK);
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger(ID_STEP, currentStep.ordinal());
		if(currentStep == BrewingStep.ADD_MALT) {
			compound.setInteger(ID_MALT, maltAdded);
		}
		else if(currentStep == BrewingStep.FERMENT) {
			compound.setInteger(ID_TICK, ticks);
		}
		else if(currentStep == BrewingStep.ALCOHOLIC) {
			compound.setInteger(ID_DRINK, drinksLeft);
		}
		return compound;
	}
	
}
