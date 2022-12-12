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

							// Ping borders

							// Default
							.option(Option.createBuilder(int.class)
									.name(Text.translatable(OPTION + ".defaultPingBorder"))
									.tooltip(Text.translatable(OPTION + ".defaultPingBorder.desc"))
									.binding(NumeralConfig.DEFAULTS.defaultPingBorder,
											() -> config.defaultPingBorder,
											(value) -> config.defaultPingBorder = value)
									.controller(IntegerFieldController::new)
									.build())

							// levelOnePingBorder
							.option(Option.createBuilder(int.class)
									.name(Text.translatable(OPTION + ".levelOnePingBorder"))
									.tooltip(Text.translatable(OPTION + ".levelOnePingBorder.desc"))
									.binding(NumeralConfig.DEFAULTS.levelOnePingBorder,
											() -> config.levelOnePingBorder,
											(value) -> config.levelOnePingBorder = value)
									.controller(IntegerFieldController::new)
									.build())

							// levelTwoPingBorder
							.option(Option.createBuilder(int.class)
									.name(Text.translatable(OPTION + ".levelTwoPingBorder"))
									.tooltip(Text.translatable(OPTION + ".levelTwoPingBorder.desc"))
									.binding(NumeralConfig.DEFAULTS.levelTwoPingBorder,
											() -> config.levelTwoPingBorder,
											(value) -> config.levelTwoPingBorder = value)
									.controller(IntegerFieldController::new)
									.build())

							// levelThreePingBorder
							.option(Option.createBuilder(int.class)
									.name(Text.translatable(OPTION + ".levelThreePingBorder"))
									.tooltip(Text.translatable(OPTION + ".levelThreePingBorder.desc"))
									.binding(NumeralConfig.DEFAULTS.levelThreePingBorder,
											() -> config.levelThreePingBorder,
											(value) -> config.levelThreePingBorder = value)
									.controller(IntegerFieldController::new)
									.build())

							// Ping < defaultPingBorder Colour
							.option(Option.createBuilder(Color.class)
									.name(Text.translatable(OPTION + ".defaultPingColour").append(Text.of(String.valueOf(config.defaultPingBorder))))
									.binding(NumeralConfig.DEFAULTS.defaultPingColour,
											() -> config.defaultPingColour,
											(value) -> config.defaultPingColour = value)
									.controller(ColorController::new)
									.build())

							// Ping < levelOnePingBorder Colour
							.option(Option.createBuilder(Color.class)
									.name(Text.translatable(OPTION + ".levelOnePingColour").append(Text.of(String.valueOf(config.levelOnePingBorder))))
									.binding(NumeralConfig.DEFAULTS.levelOnePingColour,
											() -> config.levelOnePingColour,
											(value) -> config.levelOnePingColour = value)
									.controller(ColorController::new)
									.build())

							// Ping < levelTwoPingBorder Colour
							.option(Option.createBuilder(Color.class)
									.name(Text.translatable(OPTION + ".levelTwoPingColour").append(Text.of(String.valueOf(config.levelTwoPingBorder))))
									.binding(NumeralConfig.DEFAULTS.levelTwoPingColour,
											() -> config.levelTwoPingColour,
											(value) -> config.levelTwoPingColour = value)
									.controller(ColorController::new)
									.build())

							// Ping < levelThreePingBorder Colour
							.option(Option.createBuilder(Color.class)
									.name(Text.translatable(OPTION + ".levelThreePingColour").append(Text.of(String.valueOf(config.levelThreePingBorder))))
									.binding(NumeralConfig.DEFAULTS.levelThreePingColour,
											() -> config.levelThreePingColour,
											(value) -> config.levelThreePingColour = value)
									.controller(ColorController::new)
									.build())

							// Ping ≥ levelThreePingBorder Colour
							.option(Option.createBuilder(Color.class)
									.name(Text.translatable(OPTION + ".levelFourPingColour").append(Text.of(String.valueOf(config.levelThreePingBorder))))
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
