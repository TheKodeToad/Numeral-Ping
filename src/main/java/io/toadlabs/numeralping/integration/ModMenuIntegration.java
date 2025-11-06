package io.toadlabs.numeralping.integration;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.ColorControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import io.toadlabs.numeralping.NumeralPingMod;
import io.toadlabs.numeralping.config.NumeralConfig;
import java.awt.*;
import net.minecraft.network.chat.Component;

import static io.toadlabs.numeralping.NumeralPingMod.ID;

public final class ModMenuIntegration implements ModMenuApi {

	public static final String OPTION = ID + ".option";

	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> {
			NumeralConfig config = NumeralConfig.instance();
			return YetAnotherConfigLib.createBuilder()
					.title(Component.nullToEmpty(NumeralPingMod.NAME))

					.category(ConfigCategory.createBuilder()
							.name(Component.nullToEmpty(NumeralPingMod.NAME))

							// Player List
							.option(Option.<Boolean>createBuilder()
									.name(Component.translatable(OPTION + ".playerList"))
									.description(OptionDescription.of(Component.translatable(OPTION + ".playerList.desc")))
									.binding(NumeralConfig.DEFAULTS.playerList,
											() -> config.playerList,
											value -> config.playerList = value)
									.controller(TickBoxControllerBuilder::create)
									.build())

							// Server List
							.option(Option.<Boolean>createBuilder()
									.name(Component.translatable(OPTION + ".serverList"))
									.description(OptionDescription.of(Component.translatable(OPTION + ".serverList.desc")))
									.binding(NumeralConfig.DEFAULTS.serverList,
											() -> config.serverList,
											value -> config.serverList = value)
									.controller(TickBoxControllerBuilder::create)
									.build())

							// Small Ping
							.option(Option.<Boolean>createBuilder()
									.name(Component.translatable(OPTION + ".smallPing"))
									.description(OptionDescription.of(Component.translatable(OPTION + ".smallPing.desc")))
									.binding(NumeralConfig.DEFAULTS.smallPing,
											() -> config.smallPing,
											value -> config.smallPing = value)
									.controller(TickBoxControllerBuilder::create)
									.build())

							// Ping Thresholds

							// Default
							.option(Option.<Integer>createBuilder()
									.name(Component.translatable(OPTION + ".defaultPingThreshold"))
									.description(OptionDescription.of(Component.translatable(OPTION + ".defaultPingThreshold.desc")))
									.binding(NumeralConfig.DEFAULTS.defaultPingThreshold,
											() -> config.defaultPingThreshold,
											value -> config.defaultPingThreshold = value)
									.controller(IntegerFieldControllerBuilder::create)
									.build())

							// levelOnePingThreshold
							.option(Option.<Integer>createBuilder()
									.name(Component.translatable(OPTION + ".levelOnePingThreshold"))
									.description(OptionDescription.of(Component.translatable(OPTION + ".levelOnePingThreshold.desc")))
									.binding(NumeralConfig.DEFAULTS.levelOnePingThreshold,
											() -> config.levelOnePingThreshold,
											value -> config.levelOnePingThreshold = value)
									.controller(IntegerFieldControllerBuilder::create)
									.build())

							// levelTwoPingThreshold
							.option(Option.<Integer>createBuilder()
									.name(Component.translatable(OPTION + ".levelTwoPingThreshold"))
									.description(OptionDescription.of(Component.translatable(OPTION + ".levelTwoPingThreshold.desc")))
									.binding(NumeralConfig.DEFAULTS.levelTwoPingThreshold,
											() -> config.levelTwoPingThreshold,
											value -> config.levelTwoPingThreshold = value)
									.controller(IntegerFieldControllerBuilder::create)
									.build())

							// levelThreePingThreshold
							.option(Option.<Integer>createBuilder()
									.name(Component.translatable(OPTION + ".levelThreePingThreshold"))
									.description(OptionDescription.of(Component.translatable(OPTION + ".levelThreePingThreshold.desc")))
									.binding(NumeralConfig.DEFAULTS.levelThreePingThreshold,
											() -> config.levelThreePingThreshold,
											value -> config.levelThreePingThreshold = value)
									.controller(IntegerFieldControllerBuilder::create)
									.build())

							// Ping < defaultPingThreshold Colour
							.option(Option.<Color>createBuilder()
									.name(Component.translatable(OPTION + ".defaultPingColour"))
									.binding(NumeralConfig.DEFAULTS.defaultPingColour,
											() -> config.defaultPingColour,
											value -> config.defaultPingColour = value)
									.controller(ColorControllerBuilder::create)
									.build())

							// Ping < levelOnePingThreshold Colour
							.option(Option.<Color>createBuilder()
									.name(Component.translatable(OPTION + ".levelOnePingColour"))
									.binding(NumeralConfig.DEFAULTS.levelOnePingColour,
											() -> config.levelOnePingColour,
											value -> config.levelOnePingColour = value)
									.controller(ColorControllerBuilder::create)
									.build())

							// Ping < levelTwoPingThreshold Colour
							.option(Option.<Color>createBuilder()
									.name(Component.translatable(OPTION + ".levelTwoPingColour"))
									.binding(NumeralConfig.DEFAULTS.levelTwoPingColour,
											() -> config.levelTwoPingColour,
											value -> config.levelTwoPingColour = value)
									.controller(ColorControllerBuilder::create)
									.build())

							// Ping < levelThreePingThreshold Colour
							.option(Option.<Color>createBuilder()
									.name(Component.translatable(OPTION + ".levelThreePingColour"))
									.binding(NumeralConfig.DEFAULTS.levelThreePingColour,
											() -> config.levelThreePingColour,
											value -> config.levelThreePingColour = value)
									.controller(ColorControllerBuilder::create)
									.build())

							// Ping ≥ levelThreePingThreshold Colour
							.option(Option.<Color>createBuilder()
									.name(Component.translatable(OPTION + ".levelFourPingColour"))
									.description(OptionDescription.of(Component.translatable(OPTION + ".levelFourPingColour.desc")))
									.binding(NumeralConfig.DEFAULTS.levelFourPingColour,
											() -> config.levelFourPingColour,
											value -> config.levelFourPingColour = value)
									.controller(ColorControllerBuilder::create)
									.build())

							// Timed Out Ping Colour
							.option(Option.<Color>createBuilder()
									.name(Component.translatable(OPTION + ".levelFivePingColour"))
									.binding(NumeralConfig.DEFAULTS.levelFivePingColour,
											() -> config.levelFivePingColour,
											value -> config.levelFivePingColour = value)
									.controller(ColorControllerBuilder::create)
									.build())

							.build())

					.save(NumeralPingMod.instance()::saveConfig)
					.build()
					.generateScreen(parent);
		};
	}

}
