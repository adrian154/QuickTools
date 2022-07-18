package dev.bithole.quicktools.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;
import dev.bithole.quicktools.mixin.LocalMobCapCalculatorAccessor;
import dev.bithole.quicktools.mixin.MobCountsAccessor;
import dev.bithole.quicktools.mixin.SpawnStateAccessor;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.LocalMobCapCalculator;
import net.minecraft.world.level.NaturalSpawner;

public class MobCapCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
         CommandNode<CommandSourceStack> mobcapCommand = dispatcher.register(Commands.literal("mobcap")
                 .requires(source -> source.hasPermission(1))
                 .executes(ctx -> showMobCaps(ctx.getSource()))
                .then(Commands.argument("player", EntityArgument.player())
                        .executes(ctx -> showMobCaps(ctx.getSource(), EntityArgument.getPlayer(ctx, "player")))));
         dispatcher.register(Commands.literal("mc").redirect(mobcapCommand));
    }

    private static void writeList(CommandSourceStack source, Object2IntMap<MobCategory> list, int spawnableChunks, boolean local) {
        for(Object2IntMap.Entry<MobCategory> entry: list.object2IntEntrySet()) {
            int max = local ? entry.getKey().getMaxInstancesPerChunk() : entry.getKey().getMaxInstancesPerChunk() * spawnableChunks / 289;
            source.sendSuccess(Component.literal(String.format(" - %s: %d/%d", entry.getKey().getName(), entry.getIntValue(), max)), false);
        }
    }

    private static int showMobCaps(CommandSourceStack source, ServerPlayer player) {
        NaturalSpawner.SpawnState state = source.getLevel().getChunkSource().getLastSpawnState();
        LocalMobCapCalculator localMobCapCalculator = ((SpawnStateAccessor)(Object)state).getLocalMobCapCalculator();
        LocalMobCapCalculator.MobCounts counts = ((LocalMobCapCalculatorAccessor)(Object)localMobCapCalculator).getMobCounts().get(player);
        source.sendSuccess(Component.literal("Mobcaps for ").append(player.getName()).withStyle(ChatFormatting.BOLD), false);
        writeList(source, ((MobCountsAccessor)(Object)counts).getCounts(), state.getSpawnableChunkCount(), true);
        return 0;
    }

    private static int showMobCaps(CommandSourceStack source) {
        NaturalSpawner.SpawnState state = source.getLevel().getChunkSource().getLastSpawnState();
        source.sendSuccess(Component.literal("Global Mobcaps").withStyle(ChatFormatting.BOLD), false);
        writeList(source, state.getMobCategoryCounts(), state.getSpawnableChunkCount(), false);
        return 0;
    }

}
