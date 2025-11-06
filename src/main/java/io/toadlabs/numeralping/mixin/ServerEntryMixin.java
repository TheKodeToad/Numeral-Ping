package io.toadlabs.numeralping.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import io.toadlabs.numeralping.config.NumeralConfig;
import io.toadlabs.numeralping.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.multiplayer.ServerSelectionList;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
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
@Mixin(value = ServerSelectionList.OnlineServerEntry.class, priority = 0)
public class ServerEntryMixin {

	@ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)V", ordinal = 0))
	public void shiftText(Args args) {
		NumeralConfig config = NumeralConfig.instance();

		if (config.serverList) {
			args.set(2, ((int) args.get(2)) + 10 - minecraft.font.width(getPingText(config, serverData.ping)));
		}
	}

	// hide the tooltip if it's redundant
	@WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;setTooltipForNextFrame(Lnet/minecraft/network/chat/Component;II)V"))
	public boolean hideTooltip(GuiGraphics instance, Component text, int x, int y) {
		return !(NumeralConfig.instance().serverList && text.getContents() instanceof TranslatableContents content && content.getKey().equalsIgnoreCase("multiplayer.status.ping"));
	}

	@Redirect(
			method = "render",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V",
					ordinal = 0))
	public void renderDetailedLatency(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation sprite, int x, int y, int width, int height) {
		NumeralConfig config = NumeralConfig.instance();

		if (serverData.ping >= 0 && config.serverList) {
			String text = getPingText(config, serverData.ping);

			if (config.smallPing) {
				y--;
			} else {
				y++;
			}

			instance.drawString(minecraft.font, text, x + 11 - minecraft.font.width(text), y,
					Utils.getPingColour((int) serverData.ping), false);
			return;
		}

		instance.blitSprite(pipeline, sprite, x, y, width, height);
	}

	@Unique
	private String getPingText(NumeralConfig config, long ping) {
		return config.shiftPing(Long.toString(ping));
	}

	@Shadow
	private @Final ServerData serverData;

	@Shadow
	private @Final Minecraft minecraft;

}
