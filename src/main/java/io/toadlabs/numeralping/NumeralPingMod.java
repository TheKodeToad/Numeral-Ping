package io.toadlabs.numeralping;

import java.io.IOException;
import java.nio.file.*;

import org.slf4j.*;

import io.toadlabs.numeralping.config.NumeralConfig;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.resource.*;
import net.fabricmc.loader.api.*;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public final class NumeralPingMod implements ClientModInitializer {

	public static final String ID = "numeralping";
	public static final Logger LOGGER = LoggerFactory.getLogger(NumeralPingMod.class);

	private static NumeralPingMod instance;

	private NumeralConfig config;
	private Path configFile;

	@Override
	public void onInitializeClient() {
		instance = this;

		configFile = FabricLoader.getInstance().getGameDir().resolve("config/numeralping.json");

		if (Files.exists(configFile)) {
			try {
				config = NumeralConfig.read(configFile);
			} catch (IOException error) {
				LOGGER.error("Failed to read config", error);
			}
		}

		if (config == null)
			config = new NumeralConfig();

		saveConfig();

		ModContainer container = FabricLoader.getInstance().getModContainer(ID).get();
		ResourceManagerHelper.registerBuiltinResourcePack(new Identifier(ID, "font_fix"), container,
				ResourcePackActivationType.NORMAL);
		ResourceManagerHelper.registerBuiltinResourcePack(new Identifier(ID, "font_fix_high_res"), container,
				ResourcePackActivationType.NORMAL);
	}

	public void saveConfig() {
		try {
			config.save(configFile);
		} catch (IOException error) {
			LOGGER.error("Failed to read config", error);
		}
	}

	public NumeralConfig getConfig() {
		return config;
	}

	public static NumeralPingMod instance() {
		return instance;
	}

}
