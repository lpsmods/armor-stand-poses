package dev.lpsmods.poses;

import dev.lpsmods.poses.data.PoseLoader;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.server.packs.PackType;

/**
 * Author: legopitstop
 **/
public class ArmorStandPoses implements ModInitializer {

    @Override
    public void onInitialize() {
        ResourceLoader.get(PackType.SERVER_DATA).registerReloadListener(dev.lpsmods.poses.core.ModUtils.makeId("poses"), new PoseLoader());
        Bootstrap.init();
    }
}
