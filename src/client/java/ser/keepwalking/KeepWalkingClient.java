package ser.keepwalking;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class KeepWalkingClient implements ClientModInitializer {

	private final MinecraftClient client = MinecraftClient.getInstance();

	@Override
	public void onInitializeClient() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.currentScreen != null && client.player != null && !(client.currentScreen instanceof ChatScreen)) {
				handleMovementKeys();
			}
		});
	}

	private void handleMovementKeys() {
		KeyBinding[] movementKeys = new KeyBinding[] {
				client.options.forwardKey,
				client.options.backKey,
				client.options.leftKey,
				client.options.rightKey,
				client.options.jumpKey,
				client.options.sprintKey // redundant if using minecraft's built-in toggle sprint
				//client.options.sneakKey | not working well with the default keybind of sneaking (lshift)
		};

		for (KeyBinding key : movementKeys) {
			key.setPressed(GLFW.glfwGetKey(client.getWindow().getHandle(), key.getDefaultKey().getCode()) == GLFW.GLFW_PRESS);
		}
	}

}
