package common;

import net.minecraftforge.common.config.Config;

@Config(modid = Info.MOD_ID)
public class IrishConfig {
	
	@Config.Comment("How long (in ticks) it takes for ale to brew. 24000 ticks is one minecraft day.")
	public static int brewTime = 24000;
	
	@Config.Comment("How much malt is needed to brew ale.")
	public static int brewMaltNeeded = 5;
	
	@Config.Comment("How much ale is produced by a batch of brewing.")
	public static int brewAleProduced = 5;
	
}
