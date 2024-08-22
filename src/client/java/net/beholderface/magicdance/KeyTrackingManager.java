package net.beholderface.magicdance;

import com.google.common.base.Joiner;
import net.beholderface.magicdance.registry.MagicDanceItemRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;

import java.util.*;

public class KeyTrackingManager {
    private static long handle = -1;
    private static final HashSet<KeyData> KEY_DATA = new HashSet<>();
    private static final List<Character> SEQUENCE = new ArrayList<>();
    private static final Map<KeyBinding, Character> BINDING_TO_CHAR = new HashMap<>();
    private static long lastInput = -1;
    public static void init(){
        if (!KEY_DATA.isEmpty()){
            throw new IllegalStateException("KeyTrackingManager.init() was called when already initialized.");
        }
        MinecraftClient client = MinecraftClient.getInstance();
        KeyBinding[] monitoredKeybinds = {client.options.forwardKey, client.options.leftKey, client.options.backKey,
                client.options.rightKey, client.options.sneakKey, client.options.jumpKey};
        char[] labels = {'w', 'a', 's', 'd', '_', '^'};
        handle = client.getWindow().getHandle();
        int i = 0;
        for (KeyBinding binding : monitoredKeybinds){
            KEY_DATA.add(new KeyData(binding));
            BINDING_TO_CHAR.put(binding, labels[i]);
            i++;
        }
    }

    public static void tick(){
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        ClientWorld world = client.world;
        boolean active = player != null && player.getStackInHand(Hand.MAIN_HAND).getItem() == MagicDanceItemRegistry.CASTANETS;
        if (handle != -1 && world != null){
            long time = world.getTime();
            for (KeyData data : KEY_DATA){
                int code = data.code();
                boolean press = InputUtil.isKeyPressed(handle, code);
                if (data.isPressed() != press){
                    if (press){
                        data.press(world);
                        if (active){
                            SEQUENCE.add(BINDING_TO_CHAR.get(data.binding));
                        }
                        lastInput = time;
                        MagicDance.LOGGER.info("Key " + GLFW.glfwGetKeyName(code, GLFW.glfwGetKeyScancode(code)) + " pressed");
                    } else {
                        data.release(world);
                        MagicDance.LOGGER.info("Key " + GLFW.glfwGetKeyName(code, GLFW.glfwGetKeyScancode(code)) + " released");
                    }
                }
            }
            if ((time - lastInput >= 60 || !active) && !SEQUENCE.isEmpty()){
                MagicDance.LOGGER.info(Joiner.on("").join(SEQUENCE));
                SEQUENCE.clear();
            }
        }
    }
}
