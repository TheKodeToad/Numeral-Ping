package me.mcblueparrot.numeralping;

import me.mcblueparrot.numeralping.config.NumeralConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class NumeralPingMod implements ClientModInitializer {

	public static final String MOD_ID = "numeralping";
	public static NumeralPingMod INSTANCE;

	private NumeralConfig config;

	@Override
	public void onInitializeClient() {
		INSTANCE = this;

		AutoConfig.register(NumeralConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(NumeralConfig.class).getConfig();

		ModContainer container = FabricLoader.getInstance().getModContainer(MOD_ID).get();
		ResourceManagerHelper.registerBuiltinResourcePack(new Identifier(MOD_ID, "font_fix"), container,
				ResourcePackActivationType.NORMAL);
		ResourceManagerHelper.registerBuiltinResourcePack(new Identifier(MOD_ID, "font_fix_high_res"), container,
				ResourcePackActivationType.NORMAL);
	}

	public NumeralConfig getConfig() {
		return config;
	}

}
