package dev.lpsmods.poses.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Rotations;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * Author: legopitstop
 **/
public record CustomPose(ArmorStandPose pose, Optional<Integer> power, Optional<Component> displayName) {
    private static final Rotations DEFAULT_ROTATION = new Rotations(0,0,0);
    public static final Codec<CustomPose> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(
                ArmorStandPose.CODEC.fieldOf("pose").forGetter(CustomPose::pose),
                ExtraCodecs.intRange(1, 15).optionalFieldOf("power").forGetter(CustomPose::power),
                ComponentSerialization.CODEC.optionalFieldOf("display_name").forGetter(CustomPose::displayName)
        ).apply(instance, CustomPose::new);
    });

    public CustomPose(ArmorStandPose pose, Optional<Integer> power, Optional<Component> displayName) {
        this.displayName = displayName;
        this.power = power;
        this.pose = pose;
    }

    public CustomPose(int power, ArmorStandPose pose) {
        this(pose, Optional.of(power), Optional.empty());
    }

    public Component getName(ResourceLocation poseId) {
        String fallback = StringUtils.capitalize(poseId.getPath().replace("_", " "));
        Component displayName = Component.translatableWithFallback("pose."+poseId.getNamespace()+"."+poseId.getPath(), fallback);
        return this.displayName.orElse(displayName);
    }

    public String getFallback(ResourceLocation poseId) {
        return "Changed Pose: " + this.getName(poseId).getString();
    }

    public void setPose(ArmorStand entity) {
        this.pose.setPose(entity);
    }

    public CompoundTag save() {
        CompoundTag compound = new CompoundTag();
        compound.putBoolean("ShowArms", true);
        compound.put("Pose", this.pose.save());
        return compound;
    }

    public static CustomPose fromStorage(CompoundTag compound) {
        ArmorStandPose pose = ArmorStandPose.fromStorage(compound.getCompoundOrEmpty("Pose"));
        int power = compound.getIntOr("power", 0);
        return new CustomPose(pose, Optional.of(power), Optional.empty());
    }

    public record ArmorStandPose(Rotations head, Rotations body, Rotations leftArm, Rotations rightArm, Rotations leftLeg, Rotations rightLeg) {
        public static final Codec<ArmorStandPose> CODEC = RecordCodecBuilder.create((instance) -> {
            return instance.group(
                    RotationsProvider.CODEC.fieldOf("head").orElse(DEFAULT_ROTATION).forGetter(ArmorStandPose::head),
                    RotationsProvider.CODEC.fieldOf("body").orElse(DEFAULT_ROTATION).forGetter(ArmorStandPose::body),
                    RotationsProvider.CODEC.fieldOf("left_arm").orElse(DEFAULT_ROTATION).forGetter(ArmorStandPose::leftArm),
                    RotationsProvider.CODEC.fieldOf("right_arm").orElse(DEFAULT_ROTATION).forGetter(ArmorStandPose::rightArm),
                    RotationsProvider.CODEC.fieldOf("left_leg").orElse(DEFAULT_ROTATION).forGetter(ArmorStandPose::leftLeg),
                    RotationsProvider.CODEC.fieldOf("right_leg").orElse(DEFAULT_ROTATION).forGetter(ArmorStandPose::rightLeg)
            ).apply(instance, ArmorStandPose::new);
        });

        public ArmorStandPose(Rotations head, Rotations body, Rotations leftArm, Rotations rightArm, Rotations leftLeg, Rotations rightLeg) {
            this.head = head;
            this.body = body;
            this.leftArm = leftArm;
            this.rightArm = rightArm;
            this.leftLeg = leftLeg;
            this.rightLeg = rightLeg;
        }

        public void setPose(ArmorStand entity) {
            if (this.head != null) {entity.setHeadPose(this.head);}
            if (this.body != null) {entity.setBodyPose(this.body);}
            if (this.leftArm != null) {entity.setLeftArmPose(this.leftArm);}
            if (this.rightArm != null) {entity.setRightArmPose(this.rightArm);}
            if (this.leftLeg != null) {entity.setLeftLegPose(this.leftLeg);}
            if (this.rightLeg != null) {entity.setRightLegPose(this.rightLeg);}
        }

        public CompoundTag save() {
            CompoundTag compound = new CompoundTag();
            compound.store("Head", Rotations.CODEC, this.head);
            compound.store("Body", Rotations.CODEC, this.body);
            compound.store("LeftArm", Rotations.CODEC, this.leftArm);
            compound.store("RightArm", Rotations.CODEC, this.rightArm);
            compound.store("LeftLeg", Rotations.CODEC, this.leftLeg);
            compound.store("RightLeg", Rotations.CODEC, this.rightLeg);
            return compound;
        }

        public static ArmorStandPose fromStorage(CompoundTag compound) {
            Rotations head = new Rotations(0,0,0);
            Rotations body = new Rotations(0,0,0);
            Rotations leftArm = new Rotations(0,0,0);
            Rotations rightArm = new Rotations(0,0,0);
            Rotations leftLeg = new Rotations(0,0,0);
            Rotations rightLeg = new Rotations(0,0,0);
            return new ArmorStandPose(head, body, leftArm, rightArm, leftLeg, rightLeg);
        }
    }
}
