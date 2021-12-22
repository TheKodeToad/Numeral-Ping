package me.mcblueparrot.numeralping.config;

import jdk.jfr.Description;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "numeralping")
public class NumeralConfig implements ConfigData {

	@Comment("Enable numeral ping in tab")
	public boolean enabled = true;

	@ConfigEntry.ColorPicker
	public int defaultPingColour = 0x00FF00;
	@ConfigEntry.ColorPicker
	public int levelOnePingColour = 0xFFFF00;
	@ConfigEntry.ColorPicker
	public int levelTwoPingColour = 0xFF9600;
	@ConfigEntry.ColorPicker
	public int levelThreePingColour = 0xFF6400;
	@ConfigEntry.ColorPicker
	public int levelFourPingColour = 0xFF0000;
	@ConfigEntry.ColorPicker
	public int levelFivePingColour = levelFourPingColour;

}
