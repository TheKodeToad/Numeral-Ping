package io.toadlabs.numeralping.mixin;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.toadlabs.numeralping.config.NumeralConfig;
import io.toadlabs.numeralping.util.Utils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;

@Mixin(PlayerListHud.class)
public class MixinPlayerListHud extends DrawableHelper {

	@Shadow
	@Final
	private MinecraftClient client;

	@Inject(method = "renderLatencyIcon", at = @At("HEAD"), cancellable = true)
	public void renderDetailedLatency(MatrixStack matrices, int width, int x, int y, PlayerListEntry entry,
			CallbackInfo callback) {
		NumeralConfig config = NumeralConfig.instance();

		if(config.enabled) {
			callback.cancel();

			String pingString = Integer.toString(entry.getLatency());

			if(config.smallPing) {
				// based on numeric ping
				pingString = Utils.unicodeShift(pingString, 8272);
			}

			setZOffset(getZOffset() + 100);

			drawStringWithShadow(matrices, client.textRenderer, pingString,
					x + width - client.textRenderer.getWidth(pingString) - 1, y - (config.smallPing ? 2 : 0),
					Utils.getPingGrade(entry.getLatency()));

			setZOffset(getZOffset() - 100);
		}
	}

}
