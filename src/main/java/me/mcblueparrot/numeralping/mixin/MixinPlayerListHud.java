package me.mcblueparrot.numeralping.mixin;

import me.mcblueparrot.numeralping.NumeralPingMod;
import me.mcblueparrot.numeralping.config.NumeralConfig;
import me.mcblueparrot.numeralping.util.Utils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerListHud.class)
public class MixinPlayerListHud extends DrawableHelper {

	@Shadow
	@Final
	private MinecraftClient client;

	@Inject(method = "renderLatencyIcon", at = @At("HEAD"), cancellable = true)
	public void renderDetailedLatency(MatrixStack matrices, int width, int x, int y, PlayerListEntry entry,
									  CallbackInfo callback) {
		NumeralConfig config = NumeralPingMod.INSTANCE.getConfig();

		if(config.enabled) {
			callback.cancel();

			String pingString = Utils.unicodeShift(Integer.toString(entry.getLatency()), config.smallPing ? 8272 : 0);

			setZOffset(getZOffset() + 100);
			drawStringWithShadow(matrices, client.textRenderer, pingString, x + width -
					client.textRenderer.getWidth(pingString) - 1, y - (config.smallPing ? 2 : 0), Utils.getPingGrade(entry.getLatency()));
			setZOffset(getZOffset() - 100);
		}
	}

}
