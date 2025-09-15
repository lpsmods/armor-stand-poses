package dev.lpsmods.poses;

import com.mrbysco.armorposer.Reference;
import com.mrcrayfish.framework.api.event.FrameworkServerEvents;
import com.mrcrayfish.framework.api.event.FrameworkTickEvents;
import dev.lpsmods.poses.core.ArmorStandHandler;
import dev.lpsmods.poses.core.PoseManager;
import dev.lpsmods.poses.data.CustomPose;
import dev.lpsmods.poses.platform.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;

import java.util.LinkedHashMap;
import java.util.Map;

public class Bootstrap {
    private static final Map<String, String> armorPoser = new LinkedHashMap<>();

    public static void init() {
        if (Services.PLATFORM.isModLoaded("armorposer")) {
            armorPoser.putAll(Reference.defaultPoseMap); // Copy armorposer poses.
        }

        FrameworkTickEvents.END_LIVING_ENTITY.register((LivingEntity entity) -> {
            if (!(entity instanceof ArmorStand armorStand)) return;
            ArmorStandHandler handler = ArmorStandHandler.getOrCreate(armorStand);
            if (handler==null) return;
            handler.tick();
        });

        FrameworkServerEvents.STOPPED.register((server) -> {
            ArmorStandHandler.clear();
        });

    }

    public static void onReload() {
        if (Services.PLATFORM.isModLoaded("armorposer")) {
            Reference.defaultPoseMap.clear();
            Reference.defaultPoseMap.putAll(armorPoser);
            for (Map.Entry<ResourceLocation, CustomPose> pose : PoseManager.POSES.entrySet()) {
                ResourceLocation id = pose.getKey();
                String key = id.getNamespace() + "." + id.getPath();
                Reference.defaultPoseMap.put(key, pose.getValue().save().toString());
            }
        }
    }
}
