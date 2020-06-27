package blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;

public class ModBlocks {
	
	public static List<Block> BLOCKS = new ArrayList<Block>();
	
	public static Block BARLEY_CROP = new BarleyCrop();
	public static Block BARREL = new Barrel();
	
	public static void modBlock(Block block, String name) {
		block.setRegistryName(name);
		block.setUnlocalizedName(block.getRegistryName().toString());
		ModBlocks.BLOCKS.add(block);
	}
	
}
