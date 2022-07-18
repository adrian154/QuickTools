package dev.bithole.quicktools;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import dev.bithole.quicktools.mixin.MinecraftAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;

public class QuickToolsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        // pass Timer instance to TickrateManager
        QuickTools.getInstance().getTickrateManager().setTimer(((MinecraftAccessor)(Object) Minecraft.getInstance()).getTimer());

    }

}
