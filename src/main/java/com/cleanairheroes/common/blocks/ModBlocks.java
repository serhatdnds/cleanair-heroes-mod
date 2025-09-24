package com.cleanairheroes.common.blocks;

import com.cleanairheroes.CleanAirHeroesMod;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.cleanairheroes.common.items.ModCreativeTabs;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
        DeferredRegister.create(ForgeRegistries.BLOCKS, CleanAirHeroesMod.MODID);

    // Pollution Control Blocks
    public static final RegistryObject<Block> AIR_PURIFIER = registerBlock("air_purifier",
        () -> new AirPurifierBlock(BlockBehaviour.Properties.of(Material.METAL)
            .strength(3.0f).sound(SoundType.METAL)));

    public static final RegistryObject<Block> INDUSTRIAL_FILTER_BLOCK = registerBlock("industrial_filter_block",
        () -> new IndustrialFilterBlock(BlockBehaviour.Properties.of(Material.METAL)
            .strength(4.0f).sound(SoundType.METAL)));

    public static final RegistryObject<Block> DUST_COLLECTOR = registerBlock("dust_collector",
        () -> new DustCollectorBlock(BlockBehaviour.Properties.of(Material.METAL)
            .strength(3.5f).sound(SoundType.METAL)));

    // Transportation Infrastructure
    public static final RegistryObject<Block> ELECTRIC_BUS_STOP = registerBlock("electric_bus_stop",
        () -> new ElectricBusStopBlock(BlockBehaviour.Properties.of(Material.STONE)
            .strength(2.0f).sound(SoundType.STONE)));

    public static final RegistryObject<Block> BICYCLE_PATH = registerBlock("bicycle_path",
        () -> new BicyclePathBlock(BlockBehaviour.Properties.of(Material.STONE)
            .strength(1.5f).sound(SoundType.STONE)));

    public static final RegistryObject<Block> CHARGING_STATION = registerBlock("charging_station",
        () -> new ChargingStationBlock(BlockBehaviour.Properties.of(Material.METAL)
            .strength(3.0f).sound(SoundType.METAL)));

    // Green Energy
    public static final RegistryObject<Block> SOLAR_PANEL_BLOCK = registerBlock("solar_panel_block",
        () -> new SolarPanelBlock(BlockBehaviour.Properties.of(Material.METAL)
            .strength(2.0f).sound(SoundType.METAL)));

    public static final RegistryObject<Block> WIND_TURBINE = registerBlock("wind_turbine",
        () -> new WindTurbineBlock(BlockBehaviour.Properties.of(Material.METAL)
            .strength(5.0f).sound(SoundType.METAL)));

    // Recycling and Waste Management
    public static final RegistryObject<Block> RECYCLING_CENTER = registerBlock("recycling_center",
        () -> new RecyclingCenterBlock(BlockBehaviour.Properties.of(Material.STONE)
            .strength(3.0f).sound(SoundType.STONE)));

    public static final RegistryObject<Block> WASTE_SORTING_STATION = registerBlock("waste_sorting_station",
        () -> new WasteSortingStationBlock(BlockBehaviour.Properties.of(Material.STONE)
            .strength(2.5f).sound(SoundType.STONE)));

    // Monitoring Equipment
    public static final RegistryObject<Block> AIR_QUALITY_MONITOR = registerBlock("air_quality_monitor",
        () -> new AirQualityMonitorBlock(BlockBehaviour.Properties.of(Material.METAL)
            .strength(2.0f).sound(SoundType.METAL)));

    public static final RegistryObject<Block> POLLUTION_SENSOR = registerBlock("pollution_sensor",
        () -> new PollutionSensorBlock(BlockBehaviour.Properties.of(Material.METAL)
            .strength(1.5f).sound(SoundType.METAL)));

    // Special Trees and Plants
    public static final RegistryObject<Block> POLLUTION_ABSORBING_TREE = registerBlock("pollution_absorbing_tree",
        () -> new PollutionAbsorbingTreeBlock(BlockBehaviour.Properties.of(Material.WOOD)
            .strength(1.0f).sound(SoundType.WOOD)));

    // Regional Portals
    public static final RegistryObject<Block> REGION_PORTAL = registerBlock("region_portal",
        () -> new RegionPortalBlock(BlockBehaviour.Properties.of(Material.STONE)
            .strength(-1.0f).sound(SoundType.GLASS)));

    // Pollution Sources (for scenario building)
    public static final RegistryObject<Block> INDUSTRIAL_CHIMNEY = registerBlock("industrial_chimney",
        () -> new IndustrialChimneyBlock(BlockBehaviour.Properties.of(Material.STONE)
            .strength(4.0f).sound(SoundType.STONE)));

    public static final RegistryObject<Block> COAL_BURNING_FURNACE = registerBlock("coal_burning_furnace",
        () -> new CoalBurningFurnaceBlock(BlockBehaviour.Properties.of(Material.STONE)
            .strength(3.0f).sound(SoundType.STONE)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return com.cleanairheroes.common.items.ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
            new Item.Properties().tab(ModCreativeTabs.CLEAN_AIR_TAB)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}