package me.mcblueparrot.numeralping.integration;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.mcblueparrot.numeralping.config.NumeralConfig;
import me.shedaniel.autoconfig.AutoConfig;

public class ModMenuIntegration implements ModMenuApi {

	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return (parent) -> AutoConfig.getConfigScreen(NumeralConfig.class, parent).get();
	}

}
