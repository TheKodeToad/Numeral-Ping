package io.toadlabs.numeralping.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

import io.toadlabs.numeralping.config.NumeralConfig;
import io.toadlabs.numeralping.util.Utils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.multiplayer.*;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.*;
import net.minecraft.util.Identifier;

@Mixin(MultiplayerServerListWidget.ServerEntry.class)
public class ServerEntryMixin {

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawText(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;IIIZ)I", ordinal = 0))
	public int shiftText(DrawContext instance, TextRenderer renderer, Text text, int x, int y, int color, boolean shadow) {
		NumeralConfig config = NumeralConfig.instance();

		if (config.serverList) {
			x += 10 - renderer.getWidth(getPingText(config, server.ping));
		}

		return instance.drawText(renderer, text, x, y, color, false);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/multiplayer/MultiplayerScreen;setMultiplayerScreenTooltip(Ljava/util/List;)V"))
	public void setTooltip(MultiplayerScreen screen, List<Text> tooltip) {
		if (tooltip != null && tooltip.size() == 1 && NumeralConfig.instance().serverList
				&& tooltip.get(0).getContent() instanceof TranslatableTextContent translatable
				&& translatable.getKey().equals("multiplayer.status.ping")) {
			tooltip = null;
		}

		screen.setMultiplayerScreenTooltip(tooltip);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIFFIIII)V", ordinal = 0))
	public void renderDetailedLatency(DrawContext instance, Identifier id, int x, int y, float u, float v, int width,
			int height, int textureWidth, int textureHeight) {
		NumeralConfig config = NumeralConfig.instance();

		if (server.ping >= 0 && config.serverList) {
			String text = getPingText(config, server.ping);

			if (config.smallPing) {
				y--;
			} else {
				y++;
			}

			instance.drawText(client.textRenderer, text, x + 11 - client.textRenderer.getWidth(text), y,
					Utils.getPingColour((int) server.ping), false);
			return;
		}

		instance.drawTexture(id, x, y, u, v, width, height, textureWidth, textureHeight);
	}

	private String getPingText(NumeralConfig config, long ping) {
		return config.shiftPing(Long.toString(ping));
	}

	@Shadow
	private @Final ServerInfo server;

	@Shadow
	private @Final MinecraftClient client;

}
