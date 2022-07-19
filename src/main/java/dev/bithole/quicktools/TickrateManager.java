package dev.bithole.quicktools;

import dev.bithole.quicktools.mixin.TimerAccessor;
import net.minecraft.client.Timer;

// Store tickrate state and sync tickrate with clients that support it
public class TickrateManager {

    private Timer timer;
    private float serverMsPerTick;
    private int tickWarpRemainingTicks = 0;
    private int elapsedWarpTicks = 0;
    private boolean ticking = true;
    private boolean stepTick = false;

    public TickrateManager() {
        this.setClientTickrate(20);
        this.setServerTickrate(20);
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public void setClientTickrate(int tickrate) {
        if(timer != null) {
            ((TimerAccessor)timer).setMsPerTick(1000F / tickrate);
        }
    }

    public void setServerTickrate(int tickrate) {
        serverMsPerTick = 1000F / tickrate;
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

    public boolean shouldTick() {
        return ticking;
    }

    public boolean toggleTicking() {
        this.ticking = !this.ticking;
        return this.ticking;
    }

    public void setStepTick() {
        this.stepTick = true;
    }

    public boolean shouldStepTick() {
        if(this.stepTick) {
            this.stepTick = false;
            return true;
        }
        return false;
    }

}
