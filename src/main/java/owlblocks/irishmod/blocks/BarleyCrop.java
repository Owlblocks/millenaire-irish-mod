package blocks;

import items.ModItems;
import net.minecraft.block.BlockCrops;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;

public class BarleyCrop extends BlockCrops {
	
	public BarleyCrop() {
		ModBlocks.modBlock(this, "barley_crop");
	}
	
	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
		return EnumPlantType.Crop;
	}
	
	@Override
	protected Item getSeed() {
		return ModItems.BARLEY_SEEDS;
	}
	
	protected Item getCrop() {
		return ModItems.BARLEY_GRAIN;
	}
	
}
