package dev.bithole.quicktools;

import dev.bithole.quicktools.mixin.TimerAccessor;
import net.minecraft.client.Timer;

// Store tickrate state and sync tickrate with clients that support it
public class TickrateManager {

    private Timer timer;
    private float serverMsPerTick;
    private int tickWarpRemainingTicks = 0;
    private int elapsedWarpTicks = 0;

    public TickrateManager() {
        this.setClientTickrate(20);
        this.setServerTickrate(20);
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public void setClientTickrate(int tickrate) {
        if(timer != null) {
            ((TimerAccessor)timer).setMsPerTick(1000 / tickrate);
        }
    }

    public void setServerTickrate(int tickrate) {
        serverMsPerTick = 1000 / tickrate;
    }

    public float getServerMsPerTick() {
        return serverMsPerTick;
    }

    public boolean shouldTickWarp() {
        return this.tickWarpRemainingTicks > 0;
    }

    public int getElapsedWarpTicks() {
        return elapsedWarpTicks;
    }

    public void setTickWarpRemainingTicks(int ticks) {
        tickWarpRemainingTicks = ticks;
        elapsedWarpTicks = 0;
    }

    public void decrementTickWarp() {
        if(tickWarpRemainingTicks > 0) {
            tickWarpRemainingTicks--;
            elapsedWarpTicks++;
        }
    }

}
