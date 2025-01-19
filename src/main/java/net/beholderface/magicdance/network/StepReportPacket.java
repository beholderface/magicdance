package net.beholderface.magicdance.network;

import io.netty.buffer.ByteBuf;
import net.beholderface.magicdance.MagicDance;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class StepReportPacket implements IMessage{
    public static final Identifier ID = new Identifier(MagicDance.MOD_ID, "stepdata");
    private final String steps;
    private final long duration;
    public StepReportPacket(String steps, long duration){
        this.steps = steps;
        this.duration = duration;
    }
    @Override
    public void serialize(PacketByteBuf buf) {
        buf.writeString(this.steps);
        buf.writeLong(this.duration);
    }
    public static StepReportPacket deserialize(ByteBuf buffer){
        PacketByteBuf buf = new PacketByteBuf(buffer);
        return new StepReportPacket(buf.readString(), buf.readLong());
    }

    @Override
    public Identifier getFabricId() {
        return ID;
    }

    public void handle(MinecraftServer server, ServerPlayerEntity sender){
        MagicDance.LOGGER.info("Step report packet received, bearing string " + this.steps + " and duration " + duration);
    }
}
