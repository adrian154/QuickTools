package dev.bithole.quicktools.mixin;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.LocalMobCapCalculator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(LocalMobCapCalculator.class)
public interface LocalMobCapCalculatorAccessor {
    @Accessor("playerMobCounts")
    public Map<ServerPlayer, LocalMobCapCalculator.MobCounts> getMobCounts();
}
