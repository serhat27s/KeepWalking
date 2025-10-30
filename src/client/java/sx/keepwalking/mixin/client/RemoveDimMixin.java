package sx.keepwalking.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sx.keepwalking.config.KeepWalkingConfig;

@Mixin(Screen.class)
public abstract class RemoveDimMixin {

    @Shadow public int width;
    @Shadow public int height;

    @Inject(method = "renderBackground(Lnet/minecraft/client/gui/DrawContext;IIF)V", at = @At("HEAD"), cancellable = true)
    private void configurableDimEffect(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {

        // get value from config
        int opacityPercent = KeepWalkingConfig.dimOpacityPercent;

        if (opacityPercent <= 0) {
            ci.cancel(); // stops default dimming effect
            return;
        }

        // convert to alpha value (0-192)
        int alpha = (int) ((opacityPercent / 100.0) * 192);
        // bit shift of 24 places to get the black color
        int color = alpha << 24; 

        context.fill(0, 0, this.width, this.height, color);

        // cancel minecraft's default dimming effect
        ci.cancel();
    }
}