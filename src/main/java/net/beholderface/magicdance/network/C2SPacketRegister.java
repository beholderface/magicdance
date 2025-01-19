package net.beholderface.magicdance.network;

import net.beholderface.magicdance.MagicDance;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.function.Function;

public class C2SPacketRegister {
    public static void init(){
        ServerPlayNetworking.registerGlobalReceiver(Identifier.of(MagicDance.MOD_ID, "stepdata"), makeServerBoundHandler(StepReportPacket::deserialize, StepReportPacket::handle));
    }

    private static <T> ServerPlayNetworking.PlayChannelHandler makeServerBoundHandler(
            Function<PacketByteBuf, T> decoder, TriConsumer<T, MinecraftServer, ServerPlayerEntity> handle) {
        return (server, player, _handler, buf, _responseSender) -> handle.accept(decoder.apply(buf), server, player);
    }
}
