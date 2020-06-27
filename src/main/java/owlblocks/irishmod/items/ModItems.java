package items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;

public class ModItems {
	
	public static List<Item> ITEMS = new ArrayList<Item>();
	
	public static Item IRISH_STICK = new IrishStick();
	public static Item BARLEY_SEEDS = new BarleySeeds();
	public static Item BARLEY_GRAIN = new ModItem("barley");
	public static Item MALT = new ModItem("malt");
	public static Item ALE = new Ale();
	
}
