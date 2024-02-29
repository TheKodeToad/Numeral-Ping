package io.toadlabs.numeralping.util;

import java.awt.Color;

import io.toadlabs.numeralping.config.NumeralConfig;

public final class Utils {

	public static int getPingColour(int latency) {
		Color colour;
		NumeralConfig config = NumeralConfig.instance();

		if (latency == -2) {
			colour = config.defaultPingColour;
		} else if (latency < 0) {
			colour = config.levelFivePingColour;
		} else if (latency < config.defaultPingThreshold) {
			colour = config.defaultPingColour;
		} else if (latency < config.levelOnePingThreshold) {
			colour = config.levelOnePingColour;
		} else if (latency < config.levelTwoPingThreshold) {
			colour = config.levelTwoPingColour;
		} else if (latency < config.levelThreePingThreshold) {
			colour = config.levelThreePingColour;
		} else {
			colour = config.levelFourPingColour;
		}

		return colour.getRGB();
	}

}
