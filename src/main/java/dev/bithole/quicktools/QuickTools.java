package dev.bithole.quicktools;

import dev.bithole.quicktools.commands.MobCapCommand;
import dev.bithole.quicktools.commands.TPSCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.commands.DebugCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuickTools implements ModInitializer  {

    public static final Logger LOGGER = LoggerFactory.getLogger("QuickTools");

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            MobCapCommand.register(dispatcher);
            TPSCommand.register(dispatcher);
        });
    }

}
