package io.toadlabs.numeralping.config;

import java.awt.Color;
import java.io.*;
import java.nio.charset.StandardCharsets;

import com.google.gson.*;

import io.toadlabs.numeralping.NumeralPingMod;

public final class NumeralConfig {

	// dirty tricks
	public static final NumeralConfig DEFAULTS = new NumeralConfig();
	private static final Gson GSON = new GsonBuilder().registerTypeAdapter(Color.class, ColorAdapter.INSTANCE).create();

	public boolean
			playerList = true,
			serverList = false,
			smallPing = true;

	public Color
			defaultPingColour = new Color(0x00FF00),
			levelOnePingColour = new Color(0xFFFF00),
			levelTwoPingColour = new Color(0xFF9600),
			levelThreePingColour = new Color(0xFF6400),
			levelFourPingColour = new Color(0xFF0000),
			levelFivePingColour = levelFourPingColour;

	public int
			defaultPingBorder = 150,
			levelOnePingBorder = 300,
			levelTwoPingBorder = 600,
			levelThreePingBorder = 1000;

	public static NumeralConfig instance() {
		return NumeralPingMod.instance().getConfig();
	}

	// boilerplate
	public static NumeralConfig read(File file) throws IOException {
		try(Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
			return GSON.fromJson(reader, NumeralConfig.class);
		}
	}

	public void save(File file) throws IOException {
		try(Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
			GSON.toJson(this, writer);
		}
	}

	public String shiftPing(String string) {
		if(smallPing) {
			// based on numeric ping
			char[] characters = new char[string.length()];

			for(int index = 0; index < string.length(); index++) {
				characters[index] = string.charAt(index);

				if(Character.isDigit(characters[index])) {
					characters[index] += 8272;
				}
			}

			return String.valueOf(characters);
		}

		return string;
	}

}
