package net.beholderface.magicdance;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.world.ClientWorld;
import org.jetbrains.annotations.NotNull;

public class KeyData {
    public final KeyBinding binding;
    private long lastPressed;
    private long lastReleased;
    public KeyData(KeyBinding binding){
        this.binding = binding;
        this.lastPressed = -2;
        this.lastReleased = -1;
    }
    public void press(@NotNull ClientWorld world){
        lastPressed = world.getTime();
    }
    public void release(@NotNull ClientWorld world){
        lastReleased = world.getTime();
    }
    public boolean isPressed(){
        return lastReleased < lastPressed;
    }
    public int code(){
        return KeyBindingHelper.getBoundKeyOf(binding).getCode();
    }
}
