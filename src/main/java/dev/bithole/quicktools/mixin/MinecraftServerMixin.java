package dev.bithole.quicktools.mixin;

import dev.bithole.quicktools.QuickTools;
import dev.bithole.quicktools.TickrateManager;
import net.minecraft.Util;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// MinecraftServer has a static MS_PER_TICK field, but due to constant folding changing it wouldn't actually change the tickrate
// Injecting into waitUntilNextTick() lets us adjust the tickrate without too much redundant code.
@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {

    @Shadow private long nextTickTime;
    @Shadow private long delayedTasksMaxNextTickTime;
    @Shadow protected abstract void runAllTasks();
    @Shadow protected abstract boolean runningTask();
    @Shadow private boolean mayHaveDelayedTasks;

    @Inject(at = @At("HEAD"), method="waitUntilNextTick()V", cancellable = true)
    protected void waitUntilNextTick(CallbackInfo info) {

        TickrateManager tickrateManager = QuickTools.getInstance().getTickrateManager();
        float msPerTick = tickrateManager.getServerMsPerTick();
        nextTickTime += msPerTick - 50;
        delayedTasksMaxNextTickTime += msPerTick - 50;

        runAllTasks();
        if(tickrateManager.shouldTickWarp()) {
            ((MinecraftServer)(Object)this).managedBlock(() -> this.runningTask());
            tickrateManager.decrementTickWarp();
        } else {
            ((MinecraftServer)(Object)this).managedBlock(() -> this.runningTask() || Util.getMillis() < (this.mayHaveDelayedTasks ? this.delayedTasksMaxNextTickTime : this.nextTickTime));
        }

        info.cancel();

    }

}
