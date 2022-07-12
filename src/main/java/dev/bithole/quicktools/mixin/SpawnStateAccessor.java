package dev.bithole.quicktools.mixin;

import net.minecraft.world.level.LocalMobCapCalculator;
import net.minecraft.world.level.NaturalSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(NaturalSpawner.SpawnState.class)
public interface SpawnStateAccessor {
    @Accessor("localMobCapCalculator")
    public LocalMobCapCalculator getLocalMobCapCalculator();
}
