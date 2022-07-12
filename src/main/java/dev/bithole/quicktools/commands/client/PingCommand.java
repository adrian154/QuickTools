package dev.bithole.quicktools.commands.client;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class PingCommand {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(ClientCommandManager.literal("ping")
                .then(ClientCommandManager.argument("playerName", StringArgumentType.word())
                        .executes(ctx -> showPing(ctx.getSource(), StringArgumentType.getString(ctx, "playerName"))))
                .executes(ctx -> showPing(ctx.getSource())));
    }

    private static int showPing(FabricClientCommandSource source, String playerName) {
        return showPing(source, Minecraft.getInstance().getConnection().getPlayerInfo(playerName));
    }

    private static int showPing(FabricClientCommandSource source) {
        return showPing(source, Minecraft.getInstance().getConnection().getPlayerInfo(source.getPlayer().getUUID()));
    }

    private static int showPing(FabricClientCommandSource source, PlayerInfo info) {
        if(info == null) {
            source.sendError(Component.translatable("commands.ping.noInfo"));
            return 1;
        }
        ChatFormatting color;
        if(info.getLatency() < 100) {
            color = ChatFormatting.GREEN;
        } else if(info.getLatency() < 200) {
            color = ChatFormatting.YELLOW;
        } else {
            color = ChatFormatting.RED;
        }
        source.sendFeedback(Component.translatable("commands.ping.result", info.getProfile().getName()).append(Component.literal(info.getLatency() + "ms").withStyle(color)));
        return 0;
    }

}
