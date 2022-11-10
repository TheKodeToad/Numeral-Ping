package io.toadlabs.numeralping.integration;

import static io.toadlabs.numeralping.NumeralPingMod.ID;

import java.awt.Color;

import com.terraformersmc.modmenu.api.*;

import dev.isxander.yacl.api.*;
import dev.isxander.yacl.gui.controllers.*;
import io.toadlabs.numeralping.NumeralPingMod;
import io.toadlabs.numeralping.config.NumeralConfig;
import net.minecraft.text.Text;

public final class ModMenuIntegration implements ModMenuApi {

	public static final String OPTION = ID + ".option";

	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return (parent) -> {
			NumeralConfig config = NumeralConfig.instance();
			return YetAnotherConfigLib.createBuilder()
					.title(Text.translatable(ID + ".title"))

					.category(ConfigCategory.createBuilder()
							.name(Text.translatable(ID + ".title"))

							// Enabled
							.option(Option.createBuilder(boolean.class)
									.name(Text.translatable(OPTION + ".enabled"))
									.tooltip(Text.translatable(OPTION + ".enabled.desc"))
									.binding(NumeralConfig.DEFAULTS.enabled,
											() -> config.enabled,
											(value) -> config.enabled = value)
									.controller(TickBoxController::new)
									.build())

							// Ping < 150 Colour
							.option(Option.createBuilder(Color.class)
									.name(Text.translatable(OPTION + ".defaultPingColour"))
									.binding(NumeralConfig.DEFAULTS.defaultPingColour,
											() -> config.defaultPingColour,
											(value) -> config.defaultPingColour = value)
									.controller(ColorController::new)
									.build())

							// Ping < 300 Colour
							.option(Option.createBuilder(Color.class)
									.name(Text.translatable(OPTION + ".levelOnePingColour"))
									.binding(NumeralConfig.DEFAULTS.levelOnePingColour,
											() -> config.levelOnePingColour,
											(value) -> config.levelOnePingColour = value)
									.controller(ColorController::new)
									.build())

							// Ping < 600 Colour
							.option(Option.createBuilder(Color.class)
									.name(Text.translatable(OPTION + ".levelTwoPingColour"))
									.binding(NumeralConfig.DEFAULTS.levelTwoPingColour,
											() -> config.levelTwoPingColour,
											(value) -> config.levelTwoPingColour = value)
									.controller(ColorController::new)
									.build())

							// Ping < 1000 Colour
							.option(Option.createBuilder(Color.class)
									.name(Text.translatable(OPTION + ".levelThreePingColour"))
									.binding(NumeralConfig.DEFAULTS.levelThreePingColour,
											() -> config.levelThreePingColour,
											(value) -> config.levelThreePingColour = value)
									.controller(ColorController::new)
									.build())

							// Ping ≥ 1000 Colour
							.option(Option.createBuilder(Color.class)
									.name(Text.translatable(OPTION + ".levelFourPingColour"))
									.binding(NumeralConfig.DEFAULTS.levelFourPingColour,
											() -> config.levelFourPingColour,
											(value) -> config.levelFourPingColour = value)
									.controller(ColorController::new)
									.build())

							// Timed Out Ping Colour
							.option(Option.createBuilder(Color.class)
									.name(Text.translatable(OPTION + ".levelFivePingColour"))
									.binding(NumeralConfig.DEFAULTS.levelFivePingColour,
											() -> config.levelFivePingColour,
											(value) -> config.levelFivePingColour = value)
									.controller(ColorController::new)
									.build())

							// Small Ping
							.option(Option.createBuilder(boolean.class)
									.name(Text.translatable(OPTION + ".smallPing"))
									.tooltip(Text.translatable(OPTION + ".smallPing.desc"))
									.binding(NumeralConfig.DEFAULTS.smallPing,
											() -> config.smallPing,
											(value) -> config.smallPing = value)
									.controller(TickBoxController::new)
									.build())

							.build())

					.save(NumeralPingMod.instance()::saveConfig)
					.build()
					.generateScreen(parent);
		};
	}

}
