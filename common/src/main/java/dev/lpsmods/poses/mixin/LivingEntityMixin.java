package dev.lpsmods.poses.mixin;

import dev.lpsmods.poses.core.PosedArmorStand;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    private void posesTick(CallbackInfo ci) {
        if ((Object) this instanceof PosedArmorStand stand) stand.posesTick();
    }
}
