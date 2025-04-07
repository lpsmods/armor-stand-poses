package dev.lpsmods.poses;

import dev.lpsmods.poses.data.PoseLoader;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

/**
 * Author: legopitstop
 **/
@Mod(Constants.MOD_ID)
public class ArmorStandPoses {
    public ArmorStandPoses(IEventBus eventBus) {
        eventBus.addListener(this::onCommonSetup);
        NeoForge.EVENT_BUS.addListener(this::onServerReloadListeners);
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(Bootstrap::init);
    }

    private void onServerReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new PoseLoader());
    }
}