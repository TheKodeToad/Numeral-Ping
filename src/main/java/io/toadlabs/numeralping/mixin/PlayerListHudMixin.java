package io.toadlabs.numeralping.mixin;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.toadlabs.numeralping.config.NumeralConfig;
import io.toadlabs.numeralping.util.Utils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;

@Mixin(PlayerListHud.class)
public class PlayerListHudMixin {

	@Inject(method = "renderLatencyIcon", at = @At("HEAD"), cancellable = true)
	public void renderDetailedLatency(DrawContext context, int width, int x, int y, PlayerListEntry entry,
			CallbackInfo callback) {
		NumeralConfig config = NumeralConfig.instance();

		if (config.playerList) {
			callback.cancel();

			String pingString = Integer.toString(entry.getLatency());
			pingString = config.shiftPing(pingString);

			context.getMatrices().push();
			context.getMatrices().translate(0, 0, 100);

			context.drawTextWithShadow(client.textRenderer, pingString,
					x + width - client.textRenderer.getWidth(pingString) - 1, y - (config.smallPing ? 2 : 0),
					Utils.getPingColour(entry.getLatency()));

			context.getMatrices().pop();
		}
	}

	@Shadow
	private @Final MinecraftClient client;

}
