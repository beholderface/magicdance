package net.beholderface.magicdance.network;

import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

//https://github.com/FallingColors/HexMod/blob/1.19/Common/src/main/java/at/petrak/hexcasting/common/network/IMessage.java
//yoink but mapping change
public interface IMessage {
    default PacketByteBuf toBuf() {
        var ret = new PacketByteBuf(Unpooled.buffer());
        serialize(ret);
        return ret;
    }

    void serialize(PacketByteBuf buf);

    /**
     * Forge auto-assigns incrementing integers, Fabric requires us to declare an ID
     * These are sent using vanilla's custom plugin channel system and thus are written to every single packet.
     * So this ID tends to be more terse.
     */
    Identifier getFabricId();
}
