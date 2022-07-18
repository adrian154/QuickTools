package dev.bithole.quicktools.mixin;

import net.minecraft.client.Timer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Timer.class)
public interface TimerAccessor {

    @Accessor("msPerTick")
    public void setMsPerTick(float value);

}
