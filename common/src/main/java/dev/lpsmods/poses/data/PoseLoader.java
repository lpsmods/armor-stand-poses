package dev.lpsmods.poses.data;

import dev.lpsmods.poses.Bootstrap;
import dev.lpsmods.poses.Constants;
import dev.lpsmods.poses.core.PoseManager;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

public class PoseLoader extends SimpleJsonResourceReloadListener<ArmorStandPose> {
    private static final FileToIdConverter PATH = FileToIdConverter.json("pose");

    public PoseLoader() {
        super(ArmorStandPose.CODEC, PATH);
    }

    @Override
    protected void apply(Map<ResourceLocation, ArmorStandPose> map, ResourceManager manager, ProfilerFiller profiler) {
        PoseManager.POSES.clear();
        for (Map.Entry<ResourceLocation, ArmorStandPose> entry : map.entrySet()) {
            PoseManager.POSES.put(entry.getKey(), entry.getValue());
        }
        Constants.LOG.info("Loaded {} poses", PoseManager.POSES.size());
        Bootstrap.onReload();
    }
}
