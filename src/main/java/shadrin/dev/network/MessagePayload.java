package shadrin.dev.network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.UUID;

public record MessagePayload(UUID playerId, byte[] protoBytes) implements CustomPayload {

    public static final Id<MessagePayload> ID = new Id<>(ModPackets.CHANNEL_ID);


    public static final PacketCodec<RegistryByteBuf, MessagePayload> CODEC = new PacketCodec<>() {
        @Override
        public void encode(RegistryByteBuf buf, MessagePayload value) {
            buf.writeLong(value.playerId.getMostSignificantBits());
            buf.writeLong(value.playerId.getLeastSignificantBits());
            buf.writeByteArray(value.protoBytes);
        }

        @Override
        public MessagePayload decode(RegistryByteBuf buf) {
            long most  = buf.readLong();
            long least = buf.readLong();
            UUID id    = new UUID(most, least);
            byte[] arr = buf.readByteArray();
            return new MessagePayload(id, arr);
        }
    };

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }
}
