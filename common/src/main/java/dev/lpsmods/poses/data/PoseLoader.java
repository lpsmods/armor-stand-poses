package dev.lpsmods.poses.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import dev.lpsmods.poses.Bootstrap;
import dev.lpsmods.poses.Constants;
import dev.lpsmods.poses.core.PoseManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

public class PoseLoader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new Gson();
    private static final String PATH = "pose";

    public PoseLoader() {
        super(GSON, PATH);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager manager, ProfilerFiller profiler) {
        PoseManager.POSES.clear();
        for (Map.Entry<ResourceLocation, JsonElement> entry : map.entrySet()) {
            ResourceLocation id = entry.getKey();
            try {
                DataResult<ArmorStandPose> res =  ArmorStandPose.CODEC.parse(JsonOps.INSTANCE, entry.getValue());
                if (res.isError()) {
                    Constants.LOG.error("{} | parse error:\n\t{}", id, res.error().get().message());
                    continue;
                }
                res.result().ifPresent(pose -> PoseManager.POSES.put(id, pose));
            } catch (IllegalArgumentException | JsonParseException err) {
                Constants.LOG.error("{} | parse error:", id);
                err.printStackTrace();
            }
        }
        Constants.LOG.info("Loaded {} poses", PoseManager.POSES.size());
        Bootstrap.onReload();
    }
}
