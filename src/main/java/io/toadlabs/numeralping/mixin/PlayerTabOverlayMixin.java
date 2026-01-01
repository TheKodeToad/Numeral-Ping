package io.toadlabs.numeralping.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.toadlabs.numeralping.config.NumeralConfig;
import io.toadlabs.numeralping.util.Utils;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;

@Mixin(PlayerTabOverlay.class)
public class PlayerTabOverlayMixin {

	@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I", ordinal = 0))
	private int adjustTablistEntryWidth(int a, int b, Operation<Integer> original, @Local(ordinal = 0) List<PlayerInfo> playerListEntries) {
		NumeralConfig config = NumeralConfig.instance();
		int vanillaPingWidth = 9;
		int maxPingWidth = vanillaPingWidth;

		for (PlayerInfo playerListEntry : playerListEntries) {
			String pingString = Integer.toString(playerListEntry.getLatency());
			pingString = config.shiftPing(pingString);

			maxPingWidth = Math.max(maxPingWidth, minecraft.font.width(pingString));
		}

		return original.call(a + maxPingWidth - vanillaPingWidth, b);
	}

	@Inject(method = "renderPingIcon", at = @At("HEAD"), cancellable = true)
	public void renderDetailedLatency(GuiGraphics context, int width, int x, int y, PlayerInfo entry,
			CallbackInfo callback) {
		NumeralConfig config = NumeralConfig.instance();

		if (config.playerList) {
			callback.cancel();

			String pingString = Integer.toString(entry.getLatency());
			pingString = config.shiftPing(pingString);

			context.pose().pushMatrix();

			context.drawString(minecraft.font, pingString,
					x + width - minecraft.font.width(pingString) - 1, y - (config.smallPing ? 2 : 0),
					Utils.getPingColour(entry.getLatency()));

			context.pose().popMatrix();
		}
	}

	@Shadow
	private @Final Minecraft minecraft;

}
