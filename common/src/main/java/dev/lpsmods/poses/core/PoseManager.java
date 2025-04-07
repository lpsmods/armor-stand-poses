package dev.lpsmods.poses.core;

import dev.lpsmods.poses.data.ArmorStandPose;
import net.minecraft.resources.ResourceLocation;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: legopitstop
 **/
public class PoseManager {
    public static final ConcurrentHashMap<ResourceLocation, ArmorStandPose> POSES = new ConcurrentHashMap<>();

    public static ArmorStandPose getDefaultPose() {
        return POSES.get(getDefaultPoseId());
    }

    public static ResourceLocation getDefaultPoseId() {
        LocalDate localdate = LocalDate.now();
        int month = localdate.get(ChronoField.MONTH_OF_YEAR);
        int day = localdate.get(ChronoField.DAY_OF_MONTH);
        if (month == 10) return ResourceLocation.withDefaultNamespace("zombie");
        if ((month == 11 && day == 11) || (month == 05 && day == 26)) return ResourceLocation.withDefaultNamespace("salute");
        return ResourceLocation.withDefaultNamespace("default");
    }
}
