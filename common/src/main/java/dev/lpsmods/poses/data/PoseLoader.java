package dev.lpsmods.poses.data;

import dev.lpsmods.poses.Bootstrap;
import dev.lpsmods.poses.Constants;
import dev.lpsmods.poses.core.PoseManager;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import java.util.Map;

public class PoseLoader extends SimpleJsonResourceReloadListener<ArmorStandPose> {
    public PoseLoader() {
        super(ArmorStandPose.CODEC, FileToIdConverter.json("pose"));
    }

    @Override
    protected void apply(Map<Identifier, ArmorStandPose> poses, ResourceManager manager, ProfilerFiller profiler) {
        PoseManager.POSES.clear();
        PoseManager.POSES.putAll(poses);
        Constants.LOG.info("Loaded {} poses", poses.size());
        Bootstrap.onReload();
    }
}
