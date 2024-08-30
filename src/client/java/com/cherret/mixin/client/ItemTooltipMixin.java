package com.cherret.mixin.client;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class ItemTooltipMixin {
    @Inject(method = "hasStatusBars", at = @At("HEAD"), cancellable = true)
    private void onHasStatusBars(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
}