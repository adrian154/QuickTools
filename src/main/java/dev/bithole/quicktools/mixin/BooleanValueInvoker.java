package dev.bithole.quicktools.mixin;

import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GameRules.BooleanValue.class)
public interface BooleanValueInvoker {

    @Invoker("create")
    public static GameRules.Type<GameRules.BooleanValue> create(boolean $$0) {
        throw new AssertionError();
    }

}
