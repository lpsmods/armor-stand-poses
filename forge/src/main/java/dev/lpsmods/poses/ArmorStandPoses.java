package dev.lpsmods.poses;

import dev.lpsmods.poses.data.PoseLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * Author: legopitstop
 **/
@Mod(Constants.MOD_ID)
public class ArmorStandPoses {

    public ArmorStandPoses(FMLJavaModLoadingContext ctx) {
        IEventBus bus = ctx.getModEventBus();
        bus.addListener(this::onCommonSetup);
        MinecraftForge.EVENT_BUS.addListener(this::onServerReloadListeners);
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(Bootstrap::init);
    }

    private void onServerReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new PoseLoader());
    }
}