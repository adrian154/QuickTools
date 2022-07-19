package dev.bithole.quicktools.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.tree.CommandNode;
import dev.bithole.quicktools.QuickTools;
import dev.bithole.quicktools.TickrateManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

// The CLI here is basically identical to Carpet's, which seems to be the most intuitive option.
public class TickCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("tick")
                .requires(source -> source.hasPermission(1))
                .then(Commands.literal("rate")
                        .then(Commands.argument("tickrate", IntegerArgumentType.integer(1))
                                .executes(ctx -> setTickRate(ctx.getSource(), IntegerArgumentType.getInteger(ctx, "tickrate")))))
                .then(Commands.literal("warp")
                        .executes(ctx -> tickWarp(ctx.getSource(), 0))
                        .then(Commands.argument("duration", IntegerArgumentType.integer(1))
                                .executes(ctx -> tickWarp(ctx.getSource(), IntegerArgumentType.getInteger(ctx, "duration")))))
                .then(Commands.literal("freeze")
                        .executes(ctx -> toggleTick(ctx.getSource())))
                .then(Commands.literal("step")
                        .executes(ctx -> stepTick(ctx.getSource()))));
    }

    private static int stepTick(CommandSourceStack source) {
        QuickTools.getInstance().getTickrateManager().setStepTick();
        source.sendSuccess(Component.literal("Ticked server once"), true);
        return 0;
    }

    private static int toggleTick(CommandSourceStack source) {
        if(QuickTools.getInstance().getTickrateManager().toggleTicking()) {
            source.sendSuccess(Component.literal("Unpaused the server"), true);
        } else {
            source.sendSuccess(Component.literal("Paused the server"), true);
        }
        return 0;
    }

    private static int setTickRate(CommandSourceStack source, int tickRate) {
        QuickTools.getInstance().getTickrateManager().setServerTickrate(tickRate);
        source.sendSuccess(Component.literal("Set tick rate to " + tickRate), true);
        return 0;
    }

    private static int tickWarp(CommandSourceStack source, int duration) {

        TickrateManager tickrateManager = QuickTools.getInstance().getTickrateManager();

        if(tickrateManager.shouldTickWarp()) {
            if(duration == 0) {
                int ticks = tickrateManager.getElapsedWarpTicks();
                tickrateManager.setTickWarpRemainingTicks(0);
                source.sendSuccess(Component.literal("Stopped the tick warp after " + ticks + " ticks"), true);
            } else {
                source.sendFailure(Component.literal("A tick warp is already running"));
            }
            return 0;
        }

        if(duration == 0) {
            tickrateManager.setTickWarpRemainingTicks(Integer.MAX_VALUE);
            source.sendSuccess(Component.literal("Began unlimited tick warp"), true);
        } else {
            tickrateManager.setTickWarpRemainingTicks(duration);
            source.sendSuccess(Component.literal("Began tick warp for " + duration + " ticks"), true);
        }

        return 0;

    }

}
