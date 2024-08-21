package net.beholderface.magicdance;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import org.jetbrains.annotations.NotNull;

public class KeyData {
    public final int code;
    private long lastPressed;
    private long lastReleased;
    public KeyData(int code){
        this.code = code;
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
}
