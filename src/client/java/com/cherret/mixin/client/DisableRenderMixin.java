package com.cherret.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class DisableRenderMixin {
	@Inject(method = "renderStatusBars", at = @At("HEAD"), cancellable = true)
	private void onRenderStatusBars(DrawContext context, CallbackInfo ci) {
		ci.cancel();
	}
	@Inject(method = "renderExperienceBar", at = @At("HEAD"), cancellable = true)
	private void onRenderExperienceBar(DrawContext context, int x, CallbackInfo ci) {
		ci.cancel();
	}
	@Inject(method = "renderExperienceLevel", at = @At("HEAD"), cancellable = true)
	private void onRenderExperienceLevel(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
		ci.cancel();
	}
}
