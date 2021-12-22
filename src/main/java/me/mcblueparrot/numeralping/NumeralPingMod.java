package me.mcblueparrot.numeralping;

import me.mcblueparrot.numeralping.config.NumeralConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class NumeralPingMod implements ClientModInitializer {

	public static NumeralPingMod INSTANCE;
	private NumeralConfig config;

	@Override
	public void onInitializeClient() {
		INSTANCE = this;
		AutoConfig.register(NumeralConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(NumeralConfig.class).getConfig();
	}

	public NumeralConfig getConfig() {
		return config;
	}

}
