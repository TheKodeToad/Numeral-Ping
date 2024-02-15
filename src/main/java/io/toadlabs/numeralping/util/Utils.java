package io.toadlabs.numeralping.util;

import java.awt.Color;

import io.toadlabs.numeralping.config.NumeralConfig;

public final class Utils {

	public static int getPingColour(int latency) {
		int level;

		NumeralConfig config = NumeralConfig.instance();

		if (latency == -2) {
			level = latency;
		} else if (latency < 0) {
			level = 5;
		} else if (latency < config.defaultPingThreshold) {
			level = 0;
		} else if (latency < config.levelOnePingThreshold) {
			level = 1;
		} else if (latency < config.levelTwoPingThreshold) {
			level = 2;
		} else if (latency < config.levelThreePingThreshold) {
			level = 3;
		} else {
			level = 4;
		}

		// What is this crazy syntax?
		// Thanks IntelliJ
		Color colour = switch (level) {
			case 1 -> config.levelOnePingColour;
			case 2 -> config.levelTwoPingColour;
			case 3 -> config.levelThreePingColour;
			case 4 -> config.levelFourPingColour;
			case 5 -> config.levelFivePingColour;
			default -> config.defaultPingColour;
		};

		return colour.getRGB();
	}
}
