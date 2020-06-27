package items;

import common.IrishMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ModItem extends Item implements IHasModel {
	
	public ModItem(String name) {
		setRegistryName(name);
		setUnlocalizedName(getRegistryName().toString());
		ModItems.ITEMS.add(this);
		setCreativeTab(CreativeTabs.MISC);
	}

	@Override
	public void registerModels() {
		IrishMod.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
}
