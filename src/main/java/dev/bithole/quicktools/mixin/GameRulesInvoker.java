package dev.bithole.quicktools.mixin;

import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GameRules.class)
public interface GameRulesInvoker {

    @Invoker("register")
    public static <T extends GameRules.Value<T>> GameRules.Key<T> register(String $$0, GameRules.Category $$1, GameRules.Type<T> $$2) {
        throw new AssertionError();
    }

}
