package io.toadlabs.numeralping;

import io.toadlabs.numeralping.config.NumeralConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Environment(EnvType.CLIENT)
public final class NumeralPingMod implements ClientModInitializer {

	public static final String ID = "numeralping";
	public static final String NAME = FabricLoader.getInstance().getModContainer(ID).get().getMetadata().getName();
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
		ResourceLoader.registerBuiltinPack(Identifier.fromNamespaceAndPath(ID, "font_fix"), container, PackActivationType.NORMAL);
		ResourceLoader.registerBuiltinPack(Identifier.fromNamespaceAndPath(ID, "font_fix_high_res"), container, PackActivationType.NORMAL);
	}

	public void saveConfig() {
		try {
			config.save(configFile);
		} catch (IOException error) {
			LOGGER.error("Failed to save config", error);
		}
	}

	public NumeralConfig getConfig() {
		return config;
	}

	public static NumeralPingMod instance() {
		return instance;
	}

}
