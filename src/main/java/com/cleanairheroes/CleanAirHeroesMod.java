package com.cleanairheroes;

import com.cleanairheroes.client.gui.AirQualityScreen;
import com.cleanairheroes.common.blocks.ModBlocks;
import com.cleanairheroes.common.items.ModItems;
import com.cleanairheroes.common.world.regions.RegionManager;
import com.cleanairheroes.common.world.pollution.PollutionManager;
import com.cleanairheroes.common.capabilities.PlayerProgressCapability;
import com.cleanairheroes.common.network.NetworkHandler;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(CleanAirHeroesMod.MODID)
public class CleanAirHeroesMod {
    public static final String MODID = "cleanairheroes";
    public static final Logger LOGGER = LogManager.getLogger();

    // Managers
    public static RegionManager regionManager;
    public static PollutionManager pollutionManager;

    public CleanAirHeroesMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        // Register mod contents
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        
        // Setup events
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        
        LOGGER.info("Clean Air Heroes mod loading...");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Initialize managers
        regionManager = new RegionManager();
        pollutionManager = new PollutionManager();
        
        // Initialize network handler
        NetworkHandler.init();
        
        // Register capabilities
        PlayerProgressCapability.register();
        
        LOGGER.info("Clean Air Heroes common setup complete!");
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("Clean Air Heroes client setup complete!");
    }

    public static ResourceLocation modLoc(String path) {
        return new ResourceLocation(MODID, path);
    }
}