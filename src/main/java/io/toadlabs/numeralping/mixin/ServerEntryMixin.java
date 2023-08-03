package io.toadlabs.numeralping.mixin;

import io.toadlabs.numeralping.config.NumeralConfig;
import io.toadlabs.numeralping.util.Utils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

// a priority of 2000 means it will apply later
// this is combined with `require = 0` to allow other mods to apply more integral functionality first without the game crashing
@Mixin(value = MultiplayerServerListWidget.ServerEntry.class, priority = 0)
public class ServerEntryMixin {

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawText(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;IIIZ)I", ordinal = 0))
	public int shiftText(DrawContext instance, TextRenderer renderer, Text text, int x, int y, int color, boolean shadow) {
		NumeralConfig config = NumeralConfig.instance();

		if (config.serverList) {
			x += 10 - renderer.getWidth(getPingText(config, server.ping));
		}

		return instance.drawText(renderer, text, x, y, color, false);
	}

	// hide the tooltip if it's redundant
	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/multiplayer/MultiplayerScreen;setMultiplayerScreenTooltip(Ljava/util/List;)V"), require = 0)
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

	@Unique
	private String getPingText(NumeralConfig config, long ping) {
		return config.shiftPing(Long.toString(ping));
	}

	@Shadow
	private @Final ServerInfo server;

	@Shadow
	private @Final MinecraftClient client;

}
