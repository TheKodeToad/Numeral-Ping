package io.toadlabs.numeralping.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import io.toadlabs.numeralping.config.NumeralConfig;
import io.toadlabs.numeralping.util.Utils;
import net.minecraft.client.MinecraftClient;
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
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

// a priority of 2000 means it will apply later
// this is combined with `require = 0` to allow other mods to apply more integral functionality first without the game crashing
@Mixin(value = MultiplayerServerListWidget.ServerEntry.class, priority = 0)
public class ServerEntryMixin {

	@ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawText(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;IIIZ)I", ordinal = 0))
	public void shiftText(Args args) {
		NumeralConfig config = NumeralConfig.instance();

		if (config.serverList) {
			args.set(2, ((int) args.get(2)) + 10 - client.textRenderer.getWidth(getPingText(config, server.ping)));
		}
	}

	// hide the tooltip if it's redundant
	@WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/multiplayer/MultiplayerScreen;setTooltip(Lnet/minecraft/text/Text;)V"))
	public boolean hideTooltip(MultiplayerScreen instance, Text text) {
		return !(NumeralConfig.instance().serverList && text.getContent() instanceof TranslatableTextContent content && content.getKey().equalsIgnoreCase("multiplayer.status.ping"));
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V", ordinal = 0))
	public void renderDetailedLatency(DrawContext instance, Identifier texture, int x, int y, int width, int height) {
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

		instance.drawGuiTexture(texture, x, y, width, height);
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
