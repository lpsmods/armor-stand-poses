package dev.lpsmods.poses.core;

import dev.lpsmods.poses.Constants;
import dev.lpsmods.poses.data.CustomPose;
import dev.lpsmods.poses.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ArmorStandHandler {
    private static final Map<UUID, ArmorStandHandler> INSTANCES = new HashMap<>();
    private static final ResourceLocation DEFAULT_POSE_TYPE = PoseManager.getDefaultPoseId();

    private final ArmorStand entity;
    public ResourceLocation poseType = DEFAULT_POSE_TYPE;
    public int power = 0;
    private int lastPower = 0;

    public ArmorStandHandler(ArmorStand entity) {
        this.entity = entity;
    }

    public void prune() {
        boolean loaded = this.entity.level().isLoaded(this.entity.getOnPos());
        if (loaded) return;
        this.unload();
    }

    public static void clear() {
        ArmorStandHandler.INSTANCES.clear();
    }

    public static ArmorStandHandler get(ArmorStand entity) {
        return ArmorStandHandler.get(entity, true);
    }

    public static ArmorStandHandler get(ArmorStand entity, boolean warn) {
        ArmorStandHandler handle = ArmorStandHandler.INSTANCES.get(entity.getUUID());
        if (handle==null && warn) Constants.LOG.warn("Failed to get handle for \"" + entity.getStringUUID() + "\"");
        return handle;
    }

    public static ArmorStandHandler getOrCreate(ArmorStand entity) {
        UUID id = entity.getUUID();
        if (ArmorStandHandler.INSTANCES.containsKey(id)) {
            return ArmorStandHandler.get(entity);
        }
        return ArmorStandHandler.create(entity);
    }

    public static ArmorStandHandler create(ArmorStand entity) {
        ArmorStandHandler handler = new ArmorStandHandler(entity);
        ArmorStandHandler.INSTANCES.put(entity.getUUID(), handler);
        return handler;
    }

    public void onRead(ValueInput input) {
        this.poseType = ResourceLocation.parse(input.getStringOr("PoseType", "default"));
        this.power = input.getIntOr("Power", 0);
    }

    public void onWrite(ValueOutput output) {
        output.putString("PoseType", this.poseType.toString());
        output.putInt("Power", this.power);
        this.prune();
    }

    public void load() {
        this.setPoseType(PoseManager.getDefaultPoseId(), false);
    }

    public void unload() {
        ArmorStandHandler.INSTANCES.remove(entity.getUUID());
    }

    public void tick() {
        if (!entity.level().isClientSide && !entity.isIgnoringBlockTriggers()) {
            BlockPos sourcePos = entity.getOnPos();
            int power = entity.level().getDirectSignalTo(sourcePos);
            if (power != this.lastPower) {
                this.lastPower = power;
                this.power = power;
                this.setPowerPoseType(power, false);
            }
        }
    }

    public void interact(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        // Skip if armor poser mod is installed.
        if (Services.PLATFORM.isModLoaded("armorposer")) return;
        if (player.level().isClientSide) {return;}
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.isEmpty() && player.isShiftKeyDown() && entity.canBeSeenByAnyone() && this.power == 0) {
            ResourceLocation poseId = this.setNextPoseType( true, cir);
            if (poseId != null) {
                CustomPose pose = PoseManager.POSES.get(poseId);
                player.displayClientMessage(Component.translatableWithFallback(EntityType.ARMOR_STAND.getDescriptionId() + ".pose", pose.getFallback(poseId), pose.getName(poseId)), true);
            }
        }
    }

    public ResourceLocation setNextPoseType(boolean notify, CallbackInfoReturnable<InteractionResult> cir) {
        ResourceLocation poseId = PoseManager.getDefaultPoseId();
        ArrayList<ResourceLocation> list = new ArrayList<ResourceLocation>();
        for (Map.Entry<ResourceLocation, CustomPose> entry : PoseManager.POSES.entrySet()) {
            list.add(entry.getKey());
        }
        for (int i=0; i<list.size(); i++) {
            ResourceLocation id = list.get(i);
            if (id.equals(this.getPoseType())) {
                ResourceLocation next = i<list.size()-1 ? list.get(i+1) : null;
                if (next != null) {
                    poseId = this.setPoseType( next, notify);
                } else {
                    poseId = this.setPoseType( list.getFirst(), notify);
                }
                cir.setReturnValue(InteractionResult.SUCCESS);
                break;
            }
        }
        return poseId;
    }

    public void setPowerPoseType(int power, boolean notify) {
        for (Map.Entry<ResourceLocation, CustomPose> entry : PoseManager.POSES.entrySet()) {
            CustomPose pose = entry.getValue();
            if (pose.power().isEmpty()) continue;
            if (pose.power().get() ==  power) {
                this.setPoseType( entry.getKey(), notify);
                break;
            }
        }
    }

    public ResourceLocation getPoseType() {
        return poseType == null ? DEFAULT_POSE_TYPE : this.poseType;
    }

    public ResourceLocation setPoseType(ResourceLocation poseId, boolean notify) {
        CustomPose pose = PoseManager.POSES.get(poseId);
        if (pose != null) {
            pose.setPose(entity);
            poseType = poseId;
        } else {
            Constants.LOG.warn("Unknown pose '{}'", poseId);
            return null;
        }
        return poseId;
    }
}
