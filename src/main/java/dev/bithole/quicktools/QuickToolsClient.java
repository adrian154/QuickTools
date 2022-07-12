package dev.bithole.quicktools;

import dev.bithole.quicktools.commands.client.PingCommand;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

public class QuickToolsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            PingCommand.register(dispatcher);
        });
    }

}
