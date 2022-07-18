package dev.bithole.quicktools;

import dev.bithole.quicktools.commands.MobCapCommand;
import dev.bithole.quicktools.commands.TPSCommand;
import dev.bithole.quicktools.commands.TickCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuickTools implements ModInitializer  {

    // It feels weird to say this, but IMO a singleton is the best way to access instances of the mod from across mixin classes
    private static QuickTools INSTANCE;
    public static QuickTools getInstance() {
        return INSTANCE;
    }

    public static final Logger LOGGER = LoggerFactory.getLogger("QuickTools");
    private TickrateManager tickrateManager;

    public TickrateManager getTickrateManager() {
        return tickrateManager;
    }

    @Override
    public void onInitialize() {

        INSTANCE = this;
        this.tickrateManager = new TickrateManager();

        // make sure this static initializer runs
        CustomGamerules.doNothing();

        // register commands
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            MobCapCommand.register(dispatcher);
            TPSCommand.register(dispatcher);
            TickCommand.register(dispatcher);
        });

    }

}
