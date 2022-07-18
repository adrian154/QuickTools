package dev.bithole.quicktools.mixin;

import dev.bithole.quicktools.CustomGamerules;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Explosion.class)
public class ExplosionMixin {

    @Shadow @Final private Level level;
    @Shadow @Final private ObjectArrayList<BlockPos> toBlow;

    @Inject(at=@At("HEAD"), method="finalizeExplosion(B)V")
    public void finalizeExplosion(boolean bl, CallbackInfo info) {
        if(!level.getGameRules().getBoolean(CustomGamerules.RULE_EXPLOSIONS_BREAK_BLOCKS)) {
            toBlow.clear();
        }
    }

}
