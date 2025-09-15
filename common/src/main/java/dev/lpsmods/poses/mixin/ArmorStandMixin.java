package dev.lpsmods.poses.mixin;

import dev.lpsmods.poses.core.ArmorStandHandler;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Author: legopitstop
 **/
@Mixin(ArmorStand.class)
public abstract class ArmorStandMixin {
    @Inject(at=@At(value="TAIL"), method="addAdditionalSaveData")
    private void onWriteNbt(ValueOutput output, CallbackInfo ci) {
        ArmorStand entity = (ArmorStand)(Object)this;
        ArmorStandHandler handler = ArmorStandHandler.get(entity);
        if (handler==null) return;
        handler.onWrite(output);
    }

    @Inject(at=@At(value="HEAD"), method="readAdditionalSaveData")
    private void onReadNbt(ValueInput input, CallbackInfo ci) {
        ArmorStand entity = (ArmorStand) (Object) this;
        ArmorStandHandler handler = ArmorStandHandler.getOrCreate(entity);
        handler.load();
        handler.onRead(input);
    }

    @Inject(at=@At(value = "HEAD"), method = "interactAt", cancellable = true)
    private void onInteract(Player player, Vec3 vec, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        ArmorStand entity = (ArmorStand)(Object)this;
        ArmorStandHandler handler = ArmorStandHandler.get(entity, false);
        if (handler==null) return;
        handler.interact(player, hand, cir);
    }

    /**
     * Always show arms.
     * @author legopitstop
     */
    @Overwrite
    public boolean showArms() {
        return true;
    }
}
