package dev.bithole.quicktools.mixin;

import dev.bithole.quicktools.QuickTools;
import dev.bithole.quicktools.TickrateManager;
import dev.bithole.quicktools.commands.TickCommand;
import net.minecraft.Util;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.util.thread.ReentrantBlockableEventLoop;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// MinecraftServer has a static MS_PER_TICK field, but due to constant folding changing it wouldn't actually change the tickrate
// Injecting into waitUntilNextTick() lets us adjust the tickrate without too much redundant code.
@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin extends ReentrantBlockableEventLoop {

    @Shadow private long nextTickTime;
    @Shadow private long delayedTasksMaxNextTickTime;

    public MinecraftServerMixin(String str) {
        super(str);
        throw new AssertionError();
    }

    private void resetTickTimes(TickrateManager tickrateManager) {
        nextTickTime = Util.getMillis() + (long)tickrateManager.getServerMsPerTick();
        delayedTasksMaxNextTickTime = nextTickTime;
    }

    @Inject(at = @At("HEAD"), method="waitUntilNextTick()V", cancellable = true)
    protected void waitUntilNextTick(CallbackInfo info) {

        TickrateManager tickrateManager = QuickTools.getInstance().getTickrateManager();
        float msPerTick = tickrateManager.getServerMsPerTick();

        if(!tickrateManager.shouldTick()) {

            // if the game is frozen, wait until the game is un-frozen
            runAllTasks();
            ((MinecraftServer)(Object)this).managedBlock(() -> tickrateManager.shouldTick() || tickrateManager.shouldStepTick());
            resetTickTimes(tickrateManager);
            info.cancel();

        } else if(tickrateManager.shouldTickWarp()) {

            // while tickwarping, don't wait for next tick; once tasks are done running, immediately continue
            runAllTasks();
            ((MinecraftServer)(Object)this).managedBlock(() -> !this.runningTask());
            tickrateManager.decrementTickWarp();
            resetTickTimes(tickrateManager);
            info.cancel();

        } else {
            nextTickTime += msPerTick - 50;
            delayedTasksMaxNextTickTime += msPerTick - 50;
        }

    }

}
