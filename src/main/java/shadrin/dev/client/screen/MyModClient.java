package shadrin.dev.client.screen;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class MyModClient implements ClientModInitializer {

    private static final KeyBinding OPEN_SCREEN_KEY = new KeyBinding(
            "key.mymod.open_screen",          // перевод
            InputUtil.Type.KEYSYM,            // тип
            GLFW.GLFW_KEY_V,                  // клавиша V
            "category.mymod.general"          // категория
    );

    @Override
    public void onInitializeClient() {
        KeyBindingHelper.registerKeyBinding(OPEN_SCREEN_KEY);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (OPEN_SCREEN_KEY.wasPressed() && client.player != null) {
                client.setScreen(new MessageScreen(client.currentScreen));
            }
        });
    }
}
