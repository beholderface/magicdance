package net.beholderface.magicdance;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.world.ClientWorld;
import org.lwjgl.glfw.GLFW;

import java.util.HashSet;

public class KeyTrackingManager {
    private static long handle = -1;
    private static final HashSet<KeyData> KEY_DATA = new HashSet<>();
    public static void init(){
        MinecraftClient client = MinecraftClient.getInstance();
        KeyBinding[] monitoredKeybinds = {client.options.forwardKey, client.options.leftKey, client.options.backKey,
                client.options.rightKey, client.options.sneakKey, client.options.jumpKey};
        handle = client.getWindow().getHandle();
        for (KeyBinding binding : monitoredKeybinds){
            KEY_DATA.add(new KeyData(KeyBindingHelper.getBoundKeyOf(binding).getCode()));
        }
    }

    public static void tick(){
        MinecraftClient client = MinecraftClient.getInstance();
        KeyBindingHelper.getBoundKeyOf(client.options.sneakKey).getCode();
        ClientWorld world = client.world;
        if (handle != -1 && world != null){
            for (KeyData data : KEY_DATA){
                int code = data.code;
                boolean press = InputUtil.isKeyPressed(handle, code);
                if (data.isPressed() != press){
                    if (press){
                        data.press(world);
                        MagicDance.LOGGER.info("Key " + GLFW.glfwGetKeyName(code, GLFW.glfwGetKeyScancode(code)) + " pressed");
                    } else {
                        data.release(world);
                        MagicDance.LOGGER.info("Key " + GLFW.glfwGetKeyName(code, GLFW.glfwGetKeyScancode(code)) + " released");
                    }
                }
            }
        }
    }
}
