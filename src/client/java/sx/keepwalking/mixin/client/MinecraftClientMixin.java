package sx.keepwalking.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sx.keepwalking.config.KeepWalkingConfig;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(method = "setScreen", at = @At("HEAD"))
    private void onSetScreen(Screen screen, CallbackInfo ci) {

MinecraftClient client = (MinecraftClient) (Object) this;

        if (screen != null) {

            if (client.options != null && client.options.sneakKey.isPressed()) {
                KeepWalkingConfig.wasSneakingOnGuiOpen = true;
            } else {
                KeepWalkingConfig.wasSneakingOnGuiOpen = false;
            }

            // check isSprinting
            if (client.player != null && client.player.isSprinting()) {
                KeepWalkingConfig.wasSprintingOnGuiOpen = true;
            } else {
                KeepWalkingConfig.wasSprintingOnGuiOpen = false;
            }

        }
        else {
            // reset both when closing GUI
            KeepWalkingConfig.wasSneakingOnGuiOpen = false;
            KeepWalkingConfig.wasSprintingOnGuiOpen = false;
        }
    }
}