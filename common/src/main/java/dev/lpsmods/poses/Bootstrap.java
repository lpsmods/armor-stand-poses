package dev.lpsmods.poses;

import com.mrbysco.armorposer.Reference;
import dev.lpsmods.poses.core.PoseManager;
import dev.lpsmods.poses.data.ArmorStandPose;
import dev.lpsmods.poses.platform.Services;
import net.minecraft.resources.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public class Bootstrap {
    private static Map<String, String> armorPoser = new LinkedHashMap<>();

    public static void init() {
        if (Services.PLATFORM.isModLoaded("armorposer")) {
            armorPoser.putAll(Reference.defaultPoseMap); // Copy armorposer poses.
        }
    }

    public static void onReload() {
        if (Services.PLATFORM.isModLoaded("armorposer")) {
            Reference.defaultPoseMap.clear();
            Reference.defaultPoseMap.putAll(armorPoser);
            for (Map.Entry<Identifier, ArmorStandPose> pose : PoseManager.POSES.entrySet()) {
                Identifier id = pose.getKey();
                String key = id.getNamespace() + "." + id.getPath();
                Reference.defaultPoseMap.put(key, pose.getValue().save().toString());
            }
        }
    }
}
