package sx.keepwalking.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sx.keepwalking.config.KeepWalkingConfig;
// KEIN "import sx.keepwalking.config.SprintMode;"

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void keepwalking$onTick(CallbackInfo ci) {

        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;

        // toggle sprint in GUI
        if (KeepWalkingConfig.keepSprinting) {
            if (client.currentScreen != null && KeepWalkingConfig.wasSprintingOnGuiOpen) {
                if (client.options.forwardKey.isPressed()) {
                    player.setSprinting(true);
                }
            }
        }
    }
}