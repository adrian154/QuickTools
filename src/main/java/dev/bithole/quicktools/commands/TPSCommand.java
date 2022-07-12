package dev.bithole.quicktools.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import java.text.DecimalFormat;

public class TPSCommand {

    private static final DecimalFormat numFormat = new DecimalFormat("#.#");

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("tps").executes(TPSCommand::showTPS));
    }

    private static int showTPS(CommandContext<CommandSourceStack> ctx) {
        CommandSourceStack src = ctx.getSource();
        float avgTickTime = src.getServer().getAverageTickTime();
        src.sendSuccess(Component.literal("MSPT: ")
                .append(Component.literal(numFormat.format(avgTickTime)).withStyle(ChatFormatting.GREEN))
                .append(" / TPS: ")
                .append(Component.literal(numFormat.format(Math.min(20, 1000 / avgTickTime))).withStyle(ChatFormatting.GREEN)), false);
        return 0;
    }

}
