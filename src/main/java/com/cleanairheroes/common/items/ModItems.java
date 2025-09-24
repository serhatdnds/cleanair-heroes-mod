package com.cleanairheroes.common.items;

import com.cleanairheroes.CleanAirHeroesMod;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = 
        DeferredRegister.create(ForgeRegistries.ITEMS, CleanAirHeroesMod.MODID);

    // Air Quality Tools
    public static final RegistryObject<Item> AIR_QUALITY_METER = ITEMS.register("air_quality_meter",
        () -> new AirQualityMeterItem(new Item.Properties().tab(ModCreativeTabs.CLEAN_AIR_TAB).stacksTo(1)));
    
    public static final RegistryObject<Item> POLLUTION_DETECTOR = ITEMS.register("pollution_detector",
        () -> new PollutionDetectorItem(new Item.Properties().tab(ModCreativeTabs.CLEAN_AIR_TAB).stacksTo(1)));

    // Filter Systems
    public static final RegistryObject<Item> SHIP_FILTER = ITEMS.register("ship_filter",
        () -> new Item(new Item.Properties().tab(ModCreativeTabs.CLEAN_AIR_TAB)));
    
    public static final RegistryObject<Item> INDUSTRIAL_FILTER = ITEMS.register("industrial_filter",
        () -> new Item(new Item.Properties().tab(ModCreativeTabs.CLEAN_AIR_TAB)));
    
    public static final RegistryObject<Item> DUST_CONTROL_SYSTEM = ITEMS.register("dust_control_system",
        () -> new Item(new Item.Properties().tab(ModCreativeTabs.CLEAN_AIR_TAB)));

    // Transportation Items
    public static final RegistryObject<Item> ELECTRIC_BUS_PART = ITEMS.register("electric_bus_part",
        () -> new Item(new Item.Properties().tab(ModCreativeTabs.CLEAN_AIR_TAB)));
    
    public static final RegistryObject<Item> BICYCLE_PATH_KIT = ITEMS.register("bicycle_path_kit",
        () -> new Item(new Item.Properties().tab(ModCreativeTabs.CLEAN_AIR_TAB)));

    // Energy Systems
    public static final RegistryObject<Item> SOLAR_PANEL = ITEMS.register("solar_panel",
        () -> new Item(new Item.Properties().tab(ModCreativeTabs.CLEAN_AIR_TAB)));
    
    public static final RegistryObject<Item> WIND_TURBINE_BLADE = ITEMS.register("wind_turbine_blade",
        () -> new Item(new Item.Properties().tab(ModCreativeTabs.CLEAN_AIR_TAB)));

    // Recycling Items
    public static final RegistryObject<Item> RECYCLING_BIN = ITEMS.register("recycling_bin",
        () -> new Item(new Item.Properties().tab(ModCreativeTabs.CLEAN_AIR_TAB)));
    
    public static final RegistryObject<Item> WASTE_SORTING_KIT = ITEMS.register("waste_sorting_kit",
        () -> new Item(new Item.Properties().tab(ModCreativeTabs.CLEAN_AIR_TAB)));

    // Seeds and Plants
    public static final RegistryObject<Item> POLLUTION_ABSORBING_TREE_SAPLING = ITEMS.register("pollution_absorbing_tree_sapling",
        () -> new Item(new Item.Properties().tab(ModCreativeTabs.CLEAN_AIR_TAB)));

    // Region Access Items
    public static final RegistryObject<Item> VARNA_ACCESS_CARD = ITEMS.register("varna_access_card",
        () -> new RegionAccessItem(new Item.Properties().tab(ModCreativeTabs.CLEAN_AIR_TAB).stacksTo(1), "varna"));
    
    public static final RegistryObject<Item> ZONGULDAK_ACCESS_CARD = ITEMS.register("zonguldak_access_card",
        () -> new RegionAccessItem(new Item.Properties().tab(ModCreativeTabs.CLEAN_AIR_TAB).stacksTo(1), "zonguldak"));
    
    public static final RegistryObject<Item> ODESA_ACCESS_CARD = ITEMS.register("odesa_access_card",
        () -> new RegionAccessItem(new Item.Properties().tab(ModCreativeTabs.CLEAN_AIR_TAB).stacksTo(1), "odesa"));
    
    public static final RegistryObject<Item> TRABZON_ACCESS_CARD = ITEMS.register("trabzon_access_card",
        () -> new RegionAccessItem(new Item.Properties().tab(ModCreativeTabs.CLEAN_AIR_TAB).stacksTo(1), "trabzon"));
    
    public static final RegistryObject<Item> ROMANIA_ACCESS_CARD = ITEMS.register("romania_access_card",
        () -> new RegionAccessItem(new Item.Properties().tab(ModCreativeTabs.CLEAN_AIR_TAB).stacksTo(1), "romania"));

    // Progress Tracking
    public static final RegistryObject<Item> CLEAN_AIR_CERTIFICATE = ITEMS.register("clean_air_certificate",
        () -> new Item(new Item.Properties().tab(ModCreativeTabs.CLEAN_AIR_TAB).stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}