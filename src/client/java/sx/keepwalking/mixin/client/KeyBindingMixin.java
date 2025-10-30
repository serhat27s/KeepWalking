package sx.keepwalking.mixin.client;

import net.minecraft.client.MinecraftClient;
// import net.minecraft.client.Window;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sx.keepwalking.config.KeepWalkingConfig;
import sx.keepwalking.config.SneakModes;

@Mixin(KeyBinding.class)
public class KeyBindingMixin {

    @Shadow
    private InputUtil.Key boundKey;

    @Inject(method = "isPressed", at = @At("RETURN"), cancellable = true)
    private void keepwalking$onIsPressed(CallbackInfoReturnable<Boolean> cir) {

        // do nothing if mod disabled, no gui open, or already pressed
        if (cir.getReturnValueZ()) return;
        if (!KeepWalkingConfig.keepWalkingEnabled) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.currentScreen == null) return;

        KeyBinding thisBinding = (KeyBinding) (Object) this;
        GameOptions options = client.options;

        // Sneak Maintain
        if (thisBinding == options.sneakKey) {

            switch (KeepWalkingConfig.sneakMode) {
                case ON:
                    boolean isKeyDown = InputUtil.isKeyPressed(client.getWindow(), this.boundKey.getCode());
                    if (isKeyDown) cir.setReturnValue(true);
                    break;
                case MAINTAIN:
                    if (KeepWalkingConfig.wasSneakingOnGuiOpen) cir.setReturnValue(true);
                    break;
                case OFF:
                default:
                    break;
            }
            return;
        }
        // toggle sprint behaviour
        else if (thisBinding == options.sprintKey) {

            if (KeepWalkingConfig.keepSprinting) {

                boolean isKeyDown = InputUtil.isKeyPressed(client.getWindow(), this.boundKey.getCode());
                if (isKeyDown) {
                    cir.setReturnValue(true);
                }
            }
            return;
        }

        if (isMovementKey(thisBinding, options)) {
            boolean isKeyDown = InputUtil.isKeyPressed(client.getWindow(), this.boundKey.getCode());
            if (isKeyDown) {
                cir.setReturnValue(true);
            }
        }
    }

    private boolean isMovementKey(KeyBinding binding, GameOptions options) {
        if (KeepWalkingConfig.keepForward && binding == options.forwardKey) return true;
        if (KeepWalkingConfig.keepBack && binding == options.backKey) return true;
        if (KeepWalkingConfig.keepLeft && binding == options.leftKey) return true;
        if (KeepWalkingConfig.keepRight && binding == options.rightKey) return true;
        if (KeepWalkingConfig.keepJumping && binding == options.jumpKey) return true;
        if (KeepWalkingConfig.keepSprinting && binding == options.sprintKey) return true;

        return false;
    }
}