package dev.lpsmods.poses.mixin;

import dev.lpsmods.poses.Constants;
import dev.lpsmods.poses.core.PoseManager;
import dev.lpsmods.poses.data.ArmorStandPose;
import dev.lpsmods.poses.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Map;

/**
 * Author: legopitstop
 **/
@Mixin(ArmorStand.class)
public abstract class ArmorStandMixin {
    private static ResourceLocation DEFAULT_POSE_TYPE = PoseManager.getDefaultPoseId();
    private ResourceLocation poseType = DEFAULT_POSE_TYPE;
    private int lastPower;
    private int power;

    @Inject(at=@At("TAIL"), method= "<init>")
    public void initInject(CallbackInfo io) {
        ArmorStand entity = (ArmorStand)(Object)this;
        if (!entity.level().isClientSide) {
            this.init(entity);
        }
    }

    private void init(ArmorStand entity) {
        this.setPoseType(PoseManager.getDefaultPoseId(), false);
        entity.setShowArms(true);
    }

    @Inject(at=@At(value="TAIL"), method="addAdditionalSaveData")
    private void writeNbt(CompoundTag nbt, CallbackInfo ci) {
        nbt.putString("PoseType", this.poseType.toString());
        nbt.putInt("Power", this.power);
    }

    @Inject(at=@At(value="TAIL"), method="readAdditionalSaveData")
    private void readNbt(CompoundTag nbt, CallbackInfo ci) {
        // If PoseType exists.
        this.poseType = ResourceLocation.parse(nbt.getStringOr("PoseType", "default"));
        this.power = nbt.getIntOr("Power", 0);
    }

    @Inject(at=@At(value = "HEAD"), method = "interactAt", cancellable = true)
    private void injectInteractAt(Player player, Vec3 vec, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        // Ignore if armorposer mod is installed.
        if (!Services.PLATFORM.isModLoaded("armorposer")) {
            if (player.level().isClientSide) {return;}
            ArmorStand entity = (ArmorStand)(Object)this;
            ItemStack itemStack = player.getItemInHand(hand);
            if (itemStack.isEmpty() && player.isShiftKeyDown() && entity.canBeSeenByAnyone() && this.power == 0) {
                ResourceLocation poseId = setNextPoseType(true, cir);
                if (poseId != null) {
                    ArmorStandPose pose = PoseManager.POSES.get(poseId);
                    player.displayClientMessage(Component.translatableWithFallback(EntityType.ARMOR_STAND.getDescriptionId() + ".pose", pose.getFallback(poseId), pose.getName(poseId)), true);
                }
            }
        }
    }

    @Unique
    public ResourceLocation getPoseType() {
        return this.poseType == null ? DEFAULT_POSE_TYPE : this.poseType;
    }

    @Unique
    public ResourceLocation setNextPoseType(boolean notify, CallbackInfoReturnable<InteractionResult> cir) {
        ResourceLocation poseId = PoseManager.getDefaultPoseId();
        ArrayList<ResourceLocation> list = new ArrayList<ResourceLocation>();
        for (Map.Entry<ResourceLocation, ArmorStandPose> entry : PoseManager.POSES.entrySet()) {
            list.add(entry.getKey());
        }
        for (int i=0; i<list.size(); i++) {
            ResourceLocation id = list.get(i);
            if (id.equals(this.getPoseType())) {
                ResourceLocation next = i<list.size()-1 ? list.get(i+1) : null;
                if (next != null) {
                    poseId = this.setPoseType(next, notify);
                } else {
                    poseId = this.setPoseType(list.get(0), notify);
                }
                cir.setReturnValue(InteractionResult.SUCCESS);
                break;
            }
        }
        return poseId;
    }

    @Unique
    public void setPowerPoseType(int power, boolean notify) {
        for (Map.Entry<ResourceLocation, ArmorStandPose> entry : PoseManager.POSES.entrySet()) {
            ArmorStandPose pose = entry.getValue();
            if (!pose.power().isPresent()) continue;
            if (pose.power().get() ==  power) {
                this.setPoseType(entry.getKey(), notify);
                break;
            }
        }
    }

    @Unique
    public ResourceLocation setPoseType(ResourceLocation poseId, boolean notify) {
        ArmorStand entity = (ArmorStand)(Object)this;
        ArmorStandPose pose = PoseManager.POSES.get(poseId);
        if (pose != null) {
            pose.setPose(entity);
            this.poseType = poseId;
        } else {
            Constants.LOG.warn("Unknown pose '{}'", poseId);
            return null;
        }
        return poseId;
    }

    @Inject(at=@At("TAIL"), method="tick")
    public void injectTick(CallbackInfo ci) {
        ArmorStand entity = (ArmorStand)(Object)this;
        if (!entity.level().isClientSide && !entity.isIgnoringBlockTriggers()) {
            BlockPos sourcePos = entity.getOnPos();
            this.power = entity.level().getDirectSignalTo(sourcePos);
            if (this.power != this.lastPower) {
                this.lastPower = power;
                this.setPowerPoseType(this.power, false);
            }
        }
    }
}
