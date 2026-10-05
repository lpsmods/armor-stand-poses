package dev.lpsmods.poses;

import dev.lpsmods.poses.data.PoseLoader;

import net.minecraftforge.event.AddReloadListenerEvent;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * Author: legopitstop
 **/
@Mod(Constants.MOD_ID)
public class ArmorStandPoses {

    public ArmorStandPoses(FMLJavaModLoadingContext context) {
        var bus = context.getModBusGroup();
        FMLCommonSetupEvent.getBus(bus).addListener(this::onCommonSetup);
        AddReloadListenerEvent.BUS.addListener(this::onServerReloadListeners);
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(Bootstrap::init);
    }

    private void onServerReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new PoseLoader());
    }
}
