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

	public static String getPingText(long latency) {
		NumeralConfig config = NumeralConfig.instance();

		String pingString = config.pingFormat.replace("%p", String.valueOf(latency));
		pingString = shiftPing(pingString);

		return pingString;
	}

	private static String shiftPing(String string) {
		NumeralConfig config = NumeralConfig.instance();

		if (config.smallPing) {
			// based on numeric ping
			char[] characters = new char[string.length()];

			for (int index = 0; index < string.length(); index++) {
				characters[index] = string.charAt(index);

				if (characters[index] >= '0' && characters[index] <= '9')
					characters[index] += 8272;
			}

			return String.valueOf(characters);
		}

		return string;
	}

}
