package io.toadlabs.numeralping.config;

import java.awt.Color;
import java.io.*;
import java.nio.charset.StandardCharsets;

import com.google.gson.*;

import io.toadlabs.numeralping.NumeralPingMod;

// boilerplate
public final class NumeralConfig {

	// dirty tricks
	public static final NumeralConfig DEFAULTS = new NumeralConfig();
	private static final Gson GSON = new GsonBuilder().registerTypeAdapter(Color.class, ColorAdapter.INSTANCE).create();

	public boolean enabled = true;
	public Color defaultPingColour = new Color(0x00FF00);
	public Color levelOnePingColour = new Color(0xFFFF00);
	public Color levelTwoPingColour = new Color(0xFF9600);
	public Color levelThreePingColour = new Color(0xFF6400);
	public Color levelFourPingColour = new Color(0xFF0000);
	public Color levelFivePingColour = levelFourPingColour;
	public boolean smallPing = true;

	public static NumeralConfig instance() {
		return NumeralPingMod.instance().getConfig();
	}

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

}
