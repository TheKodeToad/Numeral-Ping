package io.toadlabs.numeralping.integration;

import static io.toadlabs.numeralping.NumeralPingMod.ID;

import java.awt.Color;

import com.terraformersmc.modmenu.api.*;

import dev.isxander.yacl.api.*;
import dev.isxander.yacl.gui.controllers.*;
import dev.isxander.yacl.gui.controllers.string.number.IntegerFieldController;
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

							// Player List
							.option(Option.createBuilder(boolean.class)
									.name(Text.translatable(OPTION + ".playerList"))
									.tooltip(Text.translatable(OPTION + ".playerList.desc"))
									.binding(NumeralConfig.DEFAULTS.playerList,
											() -> config.playerList,
											(value) -> config.playerList = value)
									.controller(TickBoxController::new)
									.build())

							// Server List
							.option(Option.createBuilder(boolean.class)
									.name(Text.translatable(OPTION + ".serverList"))
									.tooltip(Text.translatable(OPTION + ".serverList.desc"))
									.binding(NumeralConfig.DEFAULTS.serverList,
											() -> config.serverList,
											(value) -> config.serverList = value)
									.controller(TickBoxController::new)
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

							// Ping Thresholds

							// Default
							.option(Option.createBuilder(int.class)
									.name(Text.translatable(OPTION + ".defaultPingThreshold"))
									.tooltip(Text.translatable(OPTION + ".defaultPingThreshold.desc"))
									.binding(NumeralConfig.DEFAULTS.defaultPingThreshold,
											() -> config.defaultPingThreshold,
											(value) -> config.defaultPingThreshold = value)
									.controller(IntegerFieldController::new)
									.build())

							// levelOnePingThreshold
							.option(Option.createBuilder(int.class)
									.name(Text.translatable(OPTION + ".levelOnePingThreshold"))
									.tooltip(Text.translatable(OPTION + ".levelOnePingThreshold.desc"))
									.binding(NumeralConfig.DEFAULTS.levelOnePingThreshold,
											() -> config.levelOnePingThreshold,
											(value) -> config.levelOnePingThreshold = value)
									.controller(IntegerFieldController::new)
									.build())

							// levelTwoPingThreshold
							.option(Option.createBuilder(int.class)
									.name(Text.translatable(OPTION + ".levelTwoPingThreshold"))
									.tooltip(Text.translatable(OPTION + ".levelTwoPingThreshold.desc"))
									.binding(NumeralConfig.DEFAULTS.levelTwoPingThreshold,
											() -> config.levelTwoPingThreshold,
											(value) -> config.levelTwoPingThreshold = value)
									.controller(IntegerFieldController::new)
									.build())

							// levelThreePingThreshold
							.option(Option.createBuilder(int.class)
									.name(Text.translatable(OPTION + ".levelThreePingThreshold"))
									.tooltip(Text.translatable(OPTION + ".levelThreePingThreshold.desc"))
									.binding(NumeralConfig.DEFAULTS.levelThreePingThreshold,
											() -> config.levelThreePingThreshold,
											(value) -> config.levelThreePingThreshold = value)
									.controller(IntegerFieldController::new)
									.build())

							// Ping < defaultPingThreshold Colour
							.option(Option.createBuilder(Color.class)
									.name(Text.translatable(OPTION + ".defaultPingColour"))
									.binding(NumeralConfig.DEFAULTS.defaultPingColour,
											() -> config.defaultPingColour,
											(value) -> config.defaultPingColour = value)
									.controller(ColorController::new)
									.build())

							// Ping < levelOnePingThreshold Colour
							.option(Option.createBuilder(Color.class)
									.name(Text.translatable(OPTION + ".levelOnePingColour"))
									.binding(NumeralConfig.DEFAULTS.levelOnePingColour,
											() -> config.levelOnePingColour,
											(value) -> config.levelOnePingColour = value)
									.controller(ColorController::new)
									.build())

							// Ping < levelTwoPingThreshold Colour
							.option(Option.createBuilder(Color.class)
									.name(Text.translatable(OPTION + ".levelTwoPingColour"))
									.binding(NumeralConfig.DEFAULTS.levelTwoPingColour,
											() -> config.levelTwoPingColour,
											(value) -> config.levelTwoPingColour = value)
									.controller(ColorController::new)
									.build())

							// Ping < levelThreePingThreshold Colour
							.option(Option.createBuilder(Color.class)
									.name(Text.translatable(OPTION + ".levelThreePingColour"))
									.binding(NumeralConfig.DEFAULTS.levelThreePingColour,
											() -> config.levelThreePingColour,
											(value) -> config.levelThreePingColour = value)
									.controller(ColorController::new)
									.build())

							// Ping ≥ levelThreePingThreshold Colour
							.option(Option.createBuilder(Color.class)
									.name(Text.translatable(OPTION + ".levelFourPingColour"))
									.tooltip(Text.translatable(OPTION + ".levelFourPingColour.desc"))
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

							.build())

					.save(NumeralPingMod.instance()::saveConfig)
					.build()
					.generateScreen(parent);
		};
	}

}
