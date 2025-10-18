package shadrin.dev.network;
import mymod.messages.MessageOuterClass;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.network.PacketByteBuf;
import shadrin.dev.db.DatabaseManager;

import java.util.UUID;

public final class ModPackets {
    public static final Identifier CHANNEL_ID = Identifier.of("shadrin_dev", "message");
    private ModPackets() {}
    public static void register() {
        PayloadTypeRegistry.playC2S().register(MessagePayload.ID, MessagePayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(MessagePayload.ID,
                (payload, context) -> {
                    context.server().execute(() ->
                            handleMessage(context.player(), payload.playerId(), payload.protoBytes()));
                });
    }

    private static void handleMessage(ServerPlayerEntity player,
                                      UUID playerId,
                                      byte[] protoBytes) {
        try {
            MessageOuterClass.Message msg = MessageOuterClass.Message.parseFrom(protoBytes);
            String text = msg.getText();
            DatabaseManager.sendMessage(playerId,text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
