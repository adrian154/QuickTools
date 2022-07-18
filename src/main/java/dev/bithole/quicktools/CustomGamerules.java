package dev.bithole.quicktools;

import dev.bithole.quicktools.mixin.BooleanValueInvoker;
import dev.bithole.quicktools.mixin.GameRulesInvoker;
import net.minecraft.world.level.GameRules;

public class CustomGamerules {

    public static final GameRules.Key<GameRules.BooleanValue> RULE_EXPLOSIONS_BREAK_BLOCKS = GameRulesInvoker.register(
        "explosionsBreakBlocks", GameRules.Category.UPDATES, BooleanValueInvoker.create(true)
    );

    public static final void doNothing() {}


}
