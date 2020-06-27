package items;

import java.util.UUID;

import com.google.common.collect.Multimap;

import common.IrishMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class IrishStick extends ModItem implements IHasModel {
	
	private static final UUID STAFF_SPEED_MODIFIER = UUID.fromString("b052fd0e-07f9-4993-9ee9-44d00a2fed24");
	
	public IrishStick() {
		super("irish_stick");
		setCreativeTab(CreativeTabs.COMBAT);
		setMaxStackSize(1);
		setMaxDamage(500);
	}
	
	/**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot, stack);

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 4.0f, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
            // Not using a unique UUID led to the effect of switching to the stick (speed) being cumulative with
            // each switch
            multimap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(STAFF_SPEED_MODIFIER, "Staff Speed Boost", 0.02, 0));
        }

        return multimap;
    }
    
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        stack.damageItem(1, attacker);
        return true;
    }
	
	@Override
	public void registerModels() {
		IrishMod.proxy.registerItemRenderer(this, 0, "handheld");
	}
	
}
