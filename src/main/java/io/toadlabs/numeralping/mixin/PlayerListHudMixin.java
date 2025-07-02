package io.toadlabs.numeralping.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.toadlabs.numeralping.config.NumeralConfig;
import io.toadlabs.numeralping.util.Utils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;

import java.util.List;

@Mixin(PlayerListHud.class)
public class PlayerListHudMixin {

	@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I", ordinal = 0))
	private int adjustTablistEntryWidth(int a, int b, Operation<Integer> original, @Local(ordinal = 0) List<PlayerListEntry> playerListEntries) {
		NumeralConfig config = NumeralConfig.instance();
		int vanillaPingWidth = 9;
		int maxPingWidth = vanillaPingWidth;

		for (PlayerListEntry playerListEntry : playerListEntries) {
			String pingString = Integer.toString(playerListEntry.getLatency());
			pingString = config.shiftPing(pingString);

			maxPingWidth = Math.max(maxPingWidth, client.textRenderer.getWidth(pingString));
		}

		return a + maxPingWidth - vanillaPingWidth;
	}

	@Inject(method = "renderLatencyIcon", at = @At("HEAD"), cancellable = true)
	public void renderDetailedLatency(DrawContext context, int width, int x, int y, PlayerListEntry entry,
			CallbackInfo callback) {
		NumeralConfig config = NumeralConfig.instance();

		if (config.playerList) {
			callback.cancel();

			String pingString = Integer.toString(entry.getLatency());
			pingString = config.shiftPing(pingString);

			context.getMatrices().pushMatrix();

			context.drawTextWithShadow(client.textRenderer, pingString,
					x + width - client.textRenderer.getWidth(pingString) - 1, y - (config.smallPing ? 2 : 0),
					Utils.getPingColour(entry.getLatency()));

			context.getMatrices().popMatrix();
		}
	}

	@Shadow
	private @Final MinecraftClient client;

}
