package dev.bithole.quicktools.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;

public class NetworkHelper {

    // For use with non-vanilla plugin channels, since there may be clients that don't support them which we want to avoid communicating with
    public static void sendToAll(ServerLevel world, FriendlyByteBuf buf, ResourceLocation channel) {
        sendToAll(world.players(), buf, channel);
    }

    public static void sendToAll(MinecraftServer server, FriendlyByteBuf buf, ResourceLocation channel) {
        sendToAll(server.getPlayerList().getPlayers(), buf, channel);
    }

    public static void sendToAll(List<ServerPlayer> players, FriendlyByteBuf buf, ResourceLocation channel) {
        ClientboundCustomPayloadPacket packet = new ClientboundCustomPayloadPacket(channel, buf);
        for(ServerPlayer player: players) {
            if(ServerPlayNetworking.canSend(player, channel)) {
                player.connection.send(packet);
            }
        }
    }

}