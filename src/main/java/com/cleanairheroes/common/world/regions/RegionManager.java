package com.cleanairheroes.common.world.regions;

import com.cleanairheroes.common.capabilities.PlayerProgressCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

import java.util.HashMap;
import java.util.Map;

public class RegionManager {
    private static final Map<String, RegionData> regions = new HashMap<>();
    
    static {
        initializeRegions();
    }

    private static void initializeRegions() {
        // Varna, Bulgaria - Level 1
        regions.put("varna", new RegionData(
            "varna",
            "Varna, Bulgaristan",
            "Bulgaristan'ın güzel sahilleri ve limanları kirlilikle karşı karşıya. Deniz ticareti, ulaşım ve endüstriyel faaliyetler şehrin havasını kirletiyor.",
            new BlockPos(1000, 70, 1000),
            1,
            null, // First region, no prerequisite
            new String[]{"liman_temizleme", "yesil_ulasim", "akilli_isitma", "endustriyel_filtre"}
        ));

        // Zonguldak, Turkey - Level 2
        regions.put("zonguldak", new RegionData(
            "zonguldak",
            "Zonguldak, Türkiye",
            "Türkiye'nin önemli kömür madenciliği bölgelerinden biri. Kömür madenlerinden çıkan toz, termik santraller ve yoğun karayolu taşımacılığı hava kirliliğinin ana sebepleri.",
            new BlockPos(2000, 70, 1000),
            2,
            "varna",
            new String[]{"maden_toz_kontrolu", "termik_santral_donusum", "yesil_koridor", "elektrikli_ulasim"}
        ));

        // Odesa, Ukraine - Level 3
        regions.put("odesa", new RegionData(
            "odesa",
            "Odesa, Ukrayna",
            "Odesa'nın liman bölgesi ve endüstriyel alanları hava kirliliğinden etkileniyor. Şehirde yüksek hava kirliliği seviyeleri mevcut.",
            new BlockPos(3000, 70, 1000),
            3,
            "zonguldak",
            new String[]{"deniz_ticareti_optimizasyon", "endustriyel_modernizasyon", "yesil_sehir_planlama", "hava_kalitesi_izleme"}
        ));

        // Trabzon, Turkey - Level 4
        regions.put("trabzon", new RegionData(
            "trabzon",
            "Trabzon, Türkiye",
            "Trabzon'da trafik, endüstriyel faaliyetler, kömür ve katı yakıt kullanımı, yeşil alanların azalması ve geri dönüşümün yetersizliği hava kirliğinin temel nedenleri.",
            new BlockPos(4000, 70, 1000),
            4,
            "odesa",
            new String[]{"akilli_trafik", "endustriyel_temizleme", "evsel_isitma_donusum", "yesil_kusak", "geri_donusum_merkezi"}
        ));

        // Southeast Romania - Level 5
        regions.put("romania", new RegionData(
            "romania",
            "Güneydoğu Romanya",
            "Romanya'nın güneydoğusunda endüstriyel faaliyet, yoğun trafik ve tarımsal faaliyetler hava kirliliğine neden oluyor.",
            new BlockPos(5000, 70, 1000),
            5,
            "trabzon",
            new String[]{"temiz_sanayi_donusum", "surdurulebilir_ulasim", "tarimsal_iyilestirme", "enerji_uretim_reform", "kapsamli_izleme"}
        ));
    }

    public static RegionData getRegion(String regionId) {
        return regions.get(regionId);
    }

    public static boolean canPlayerAccessRegion(Player player, String regionId) {
        RegionData region = regions.get(regionId);
        if (region == null) return false;
        
        // First region is always accessible
        if (region.prerequisiteRegion == null) return true;
        
        // Check if player has completed prerequisite region
        return PlayerProgressCapability.getProgress(player).hasCompletedRegion(region.prerequisiteRegion);
    }

    public static void teleportPlayerToRegion(Player player, String regionId) {
        RegionData region = regions.get(regionId);
        if (region == null || !(player instanceof ServerPlayer serverPlayer)) return;
        
        if (!canPlayerAccessRegion(player, regionId)) {
            player.sendSystemMessage(Component.literal("Bu bölgeye erişim için önce önceki görevleri tamamlamalısınız!"));
            return;
        }

        // Teleport player to region spawn point
        Level level = player.getLevel();
        BlockPos spawnPos = region.spawnPoint;
        
        serverPlayer.teleportTo(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
        
        // Build landmark structures for this region
        buildRegionLandmarks(level, regionId, spawnPos);
        
        // Send welcome message
        player.sendSystemMessage(Component.literal("Hoş geldiniz: " + region.displayName));
        player.sendSystemMessage(Component.literal(region.description));
        player.sendSystemMessage(Component.literal("§6Bu bölgenin iconic yapıları inşa edildi!"));
        
        // Mark region as visited
        PlayerProgressCapability.getProgress(player).visitRegion(regionId);
    }

    public static String[] getAllRegionIds() {
        return regions.keySet().toArray(new String[0]);
    }

    public static RegionData[] getAllRegions() {
        return regions.values().toArray(new RegionData[0]);
    }

    public static int getRegionCount() {
        return regions.size();
    }

    // Get next region that player can access
    public static String getNextAccessibleRegion(Player player) {
        for (RegionData region : regions.values()) {
            if (canPlayerAccessRegion(player, region.id)) {
                if (!PlayerProgressCapability.getProgress(player).hasCompletedRegion(region.id)) {
                    return region.id;
                }
            }
        }
        return null; // All regions completed
    }

    // Check if all regions are completed
    public static boolean hasPlayerCompletedAllRegions(Player player) {
        for (String regionId : regions.keySet()) {
            if (!PlayerProgressCapability.getProgress(player).hasCompletedRegion(regionId)) {
                return false;
            }
        }
        return true;
    }

    // Build landmark structures for each region
    private static void buildRegionLandmarks(Level level, String regionId, BlockPos centerPos) {
        switch (regionId) {
            case "varna":
                buildVarnaOperaHouse(level, centerPos.offset(20, 0, 0));
                break;
            case "zonguldak":
                buildZonguldakMiningMuseum(level, centerPos.offset(20, 0, 0));
                break;
            case "odesa":
                buildOdesaOperaTheatre(level, centerPos.offset(20, 0, 0));
                break;
            case "trabzon":
                buildTrabzonHagiaSophia(level, centerPos.offset(20, 0, 0));
                break;
            case "romania":
                buildBrasovBlackChurch(level, centerPos.offset(20, 0, 0));
                break;
        }
    }

    // Varna Opera House (1932) - Art Deco Architecture
    private static void buildVarnaOperaHouse(Level level, BlockPos startPos) {
        // Foundation (11x9 base) - Smooth stone for elegant base
        for (int x = 0; x < 11; x++) {
            for (int z = 0; z < 9; z++) {
                level.setBlock(startPos.offset(x, 0, z), Blocks.SMOOTH_STONE.defaultBlockState(), 3);
            }
        }
        
        // Main walls - Quartz for white Art Deco facade
        // Front wall with grand entrance
        for (int y = 1; y <= 5; y++) {
            for (int x = 0; x < 11; x++) {
                if (x >= 4 && x <= 6 && y <= 3) continue; // Grand entrance
                level.setBlock(startPos.offset(x, y, 0), Blocks.QUARTZ_BLOCK.defaultBlockState(), 3);
            }
        }
        
        // Side and back walls
        for (int y = 1; y <= 5; y++) {
            for (int z = 0; z < 9; z++) {
                level.setBlock(startPos.offset(0, y, z), Blocks.QUARTZ_BLOCK.defaultBlockState(), 3);
                level.setBlock(startPos.offset(10, y, z), Blocks.QUARTZ_BLOCK.defaultBlockState(), 3);
            }
            for (int x = 0; x < 11; x++) {
                level.setBlock(startPos.offset(x, y, 8), Blocks.QUARTZ_BLOCK.defaultBlockState(), 3);
            }
        }
        
        // Art Deco columns at entrance
        for (int y = 1; y <= 6; y++) {
            level.setBlock(startPos.offset(3, y, 0), Blocks.QUARTZ_PILLAR.defaultBlockState(), 3);
            level.setBlock(startPos.offset(7, y, 0), Blocks.QUARTZ_PILLAR.defaultBlockState(), 3);
        }
        
        // Stepped roof (Art Deco characteristic)
        for (int x = 1; x < 10; x++) {
            for (int z = 1; z < 8; z++) {
                level.setBlock(startPos.offset(x, 6, z), Blocks.SMOOTH_QUARTZ.defaultBlockState(), 3);
            }
        }
        
        // Central decoration
        level.setBlock(startPos.offset(5, 7, 4), Blocks.GOLD_BLOCK.defaultBlockState(), 3);
        
        // Windows
        level.setBlock(startPos.offset(2, 2, 0), Blocks.GLASS_PANE.defaultBlockState(), 3);
        level.setBlock(startPos.offset(8, 2, 0), Blocks.GLASS_PANE.defaultBlockState(), 3);
        level.setBlock(startPos.offset(2, 3, 0), Blocks.GLASS_PANE.defaultBlockState(), 3);
        level.setBlock(startPos.offset(8, 3, 0), Blocks.GLASS_PANE.defaultBlockState(), 3);
        
        // Interior
        for (int x = 1; x < 10; x++) {
            for (int z = 1; z < 8; z++) {
                level.setBlock(startPos.offset(x, 1, z), Blocks.POLISHED_ANDESITE.defaultBlockState(), 3);
            }
        }
        
        // Grand entrance doors
        level.setBlock(startPos.offset(5, 1, 0), Blocks.OAK_DOOR.defaultBlockState(), 3);
        level.setBlock(startPos.offset(5, 2, 0), Blocks.OAK_DOOR.defaultBlockState().setValue(DoorBlock.HALF, DoubleBlockHalf.UPPER), 3);
    }

    // Zonguldak Mining Museum - Industrial Heritage Building
    private static void buildZonguldakMiningMuseum(Level level, BlockPos startPos) {
        // Foundation - Industrial stone base
        for (int x = 0; x < 13; x++) {
            for (int z = 0; z < 10; z++) {
                level.setBlock(startPos.offset(x, 0, z), Blocks.COBBLESTONE.defaultBlockState(), 3);
            }
        }
        
        // Industrial brick walls
        for (int y = 1; y <= 4; y++) {
            // Front wall with entrance
            for (int x = 0; x < 13; x++) {
                if (x >= 5 && x <= 7 && y <= 2) continue; // Entrance
                level.setBlock(startPos.offset(x, y, 0), Blocks.BRICKS.defaultBlockState(), 3);
            }
            // Side and back walls
            for (int z = 0; z < 10; z++) {
                level.setBlock(startPos.offset(0, y, z), Blocks.BRICKS.defaultBlockState(), 3);
                level.setBlock(startPos.offset(12, y, z), Blocks.BRICKS.defaultBlockState(), 3);
            }
            for (int x = 0; x < 13; x++) {
                level.setBlock(startPos.offset(x, y, 9), Blocks.BRICKS.defaultBlockState(), 3);
            }
        }
        
        // Mining equipment displays (anvils and cauldrons)
        level.setBlock(startPos.offset(3, 2, 3), Blocks.ANVIL.defaultBlockState(), 3);
        level.setBlock(startPos.offset(9, 2, 6), Blocks.CAULDRON.defaultBlockState(), 3);
        
        // Industrial chimney
        for (int y = 1; y <= 8; y++) {
            level.setBlock(startPos.offset(13, y, 2), Blocks.BRICKS.defaultBlockState(), 3);
        }
        
        // Industrial windows
        for (int x = 2; x < 11; x += 3) {
            level.setBlock(startPos.offset(x, 2, 0), Blocks.GLASS_PANE.defaultBlockState(), 3);
            level.setBlock(startPos.offset(x, 3, 0), Blocks.GLASS_PANE.defaultBlockState(), 3);
        }
        
        // Roof - Industrial style
        for (int x = 1; x < 12; x++) {
            for (int z = 1; z < 9; z++) {
                level.setBlock(startPos.offset(x, 5, z), Blocks.STONE_BRICKS.defaultBlockState(), 3);
            }
        }
        
        // Entrance
        level.setBlock(startPos.offset(6, 1, 0), Blocks.IRON_DOOR.defaultBlockState(), 3);
        level.setBlock(startPos.offset(6, 2, 0), Blocks.IRON_DOOR.defaultBlockState().setValue(DoorBlock.HALF, DoubleBlockHalf.UPPER), 3);
    }

    // Odesa Opera Theatre (1887) - Neo-Baroque by Fellner & Helmer
    private static void buildOdesaOperaTheatre(Level level, BlockPos startPos) {
        // Grand foundation
        for (int x = 0; x < 15; x++) {
            for (int z = 0; z < 12; z++) {
                level.setBlock(startPos.offset(x, 0, z), Blocks.POLISHED_GRANITE.defaultBlockState(), 3);
            }
        }
        
        // Neo-baroque walls - White quartz with decorative elements
        for (int y = 1; y <= 6; y++) {
            // Front facade
            for (int x = 0; x < 15; x++) {
                if (x >= 6 && x <= 8 && y <= 3) continue; // Grand entrance
                level.setBlock(startPos.offset(x, y, 0), Blocks.QUARTZ_BLOCK.defaultBlockState(), 3);
            }
            // Sides and back
            for (int z = 0; z < 12; z++) {
                level.setBlock(startPos.offset(0, y, z), Blocks.QUARTZ_BLOCK.defaultBlockState(), 3);
                level.setBlock(startPos.offset(14, y, z), Blocks.QUARTZ_BLOCK.defaultBlockState(), 3);
            }
            for (int x = 0; x < 15; x++) {
                level.setBlock(startPos.offset(x, y, 11), Blocks.QUARTZ_BLOCK.defaultBlockState(), 3);
            }
        }
        
        // Baroque columns
        for (int y = 1; y <= 7; y++) {
            level.setBlock(startPos.offset(3, y, 0), Blocks.QUARTZ_PILLAR.defaultBlockState(), 3);
            level.setBlock(startPos.offset(7, y, 0), Blocks.QUARTZ_PILLAR.defaultBlockState(), 3);
            level.setBlock(startPos.offset(11, y, 0), Blocks.QUARTZ_PILLAR.defaultBlockState(), 3);
        }
        
        // Ornate roof with domes
        for (int x = 2; x < 13; x++) {
            for (int z = 2; z < 10; z++) {
                level.setBlock(startPos.offset(x, 7, z), Blocks.SMOOTH_QUARTZ.defaultBlockState(), 3);
            }
        }
        
        // Central dome
        level.setBlock(startPos.offset(7, 8, 6), Blocks.GOLD_BLOCK.defaultBlockState(), 3);
        level.setBlock(startPos.offset(7, 9, 6), Blocks.GOLD_BLOCK.defaultBlockState(), 3);
        
        // Ornate windows
        level.setBlock(startPos.offset(2, 2, 0), Blocks.GLASS_PANE.defaultBlockState(), 3);
        level.setBlock(startPos.offset(5, 2, 0), Blocks.GLASS_PANE.defaultBlockState(), 3);
        level.setBlock(startPos.offset(9, 2, 0), Blocks.GLASS_PANE.defaultBlockState(), 3);
        level.setBlock(startPos.offset(12, 2, 0), Blocks.GLASS_PANE.defaultBlockState(), 3);
        
        // Grand entrance
        level.setBlock(startPos.offset(7, 1, 0), Blocks.OAK_DOOR.defaultBlockState(), 3);
        level.setBlock(startPos.offset(7, 2, 0), Blocks.OAK_DOOR.defaultBlockState().setValue(DoorBlock.HALF, DoubleBlockHalf.UPPER), 3);
        
        // Interior with luxury floor
        for (int x = 1; x < 14; x++) {
            for (int z = 1; z < 11; z++) {
                level.setBlock(startPos.offset(x, 1, z), Blocks.POLISHED_BLACKSTONE.defaultBlockState(), 3);
            }
        }
    }

    // Trabzon Hagia Sophia (1238-1263) - Byzantine Architecture
    private static void buildTrabzonHagiaSophia(Level level, BlockPos startPos) {
        // Byzantine foundation
        for (int x = 0; x < 13; x++) {
            for (int z = 0; z < 13; z++) {
                level.setBlock(startPos.offset(x, 0, z), Blocks.SMOOTH_STONE.defaultBlockState(), 3);
            }
        }
        
        // Byzantine stone walls
        for (int y = 1; y <= 5; y++) {
            // Cross-in-square plan walls
            for (int x = 0; x < 13; x++) {
                level.setBlock(startPos.offset(x, y, 0), Blocks.STONE_BRICKS.defaultBlockState(), 3);
                level.setBlock(startPos.offset(x, y, 12), Blocks.STONE_BRICKS.defaultBlockState(), 3);
            }
            for (int z = 0; z < 13; z++) {
                level.setBlock(startPos.offset(0, y, z), Blocks.STONE_BRICKS.defaultBlockState(), 3);
                level.setBlock(startPos.offset(12, y, z), Blocks.STONE_BRICKS.defaultBlockState(), 3);
            }
        }
        
        // Central dome structure
        for (int x = 4; x < 9; x++) {
            for (int z = 4; z < 9; z++) {
                if (x == 6 && z == 6) {
                    // Central dome
                    for (int y = 6; y <= 9; y++) {
                        level.setBlock(startPos.offset(x, y, z), Blocks.SMOOTH_STONE.defaultBlockState(), 3);
                    }
                    level.setBlock(startPos.offset(x, 10, z), Blocks.GOLD_BLOCK.defaultBlockState(), 3); // Cross on top
                } else {
                    level.setBlock(startPos.offset(x, 6, z), Blocks.SMOOTH_STONE.defaultBlockState(), 3);
                }
            }
        }
        
        // Byzantine arches (using stairs)
        level.setBlock(startPos.offset(6, 3, 0), Blocks.STONE_BRICK_STAIRS.defaultBlockState(), 3);
        level.setBlock(startPos.offset(6, 4, 0), Blocks.STONE_BRICK_STAIRS.defaultBlockState(), 3);
        
        // Small Byzantine windows
        level.setBlock(startPos.offset(3, 2, 0), Blocks.GLASS_PANE.defaultBlockState(), 3);
        level.setBlock(startPos.offset(9, 2, 0), Blocks.GLASS_PANE.defaultBlockState(), 3);
        level.setBlock(startPos.offset(0, 2, 6), Blocks.GLASS_PANE.defaultBlockState(), 3);
        level.setBlock(startPos.offset(12, 2, 6), Blocks.GLASS_PANE.defaultBlockState(), 3);
        
        // Interior with marble floor
        for (int x = 1; x < 12; x++) {
            for (int z = 1; z < 12; z++) {
                level.setBlock(startPos.offset(x, 1, z), Blocks.POLISHED_DIORITE.defaultBlockState(), 3);
            }
        }
        
        // Entrance
        level.setBlock(startPos.offset(6, 1, 0), Blocks.OAK_DOOR.defaultBlockState(), 3);
        level.setBlock(startPos.offset(6, 2, 0), Blocks.OAK_DOOR.defaultBlockState().setValue(DoorBlock.HALF, DoubleBlockHalf.UPPER), 3);
    }

    // Brașov Black Church (1383-1477) - Gothic Architecture
    private static void buildBrasovBlackChurch(Level level, BlockPos startPos) {
        // Gothic foundation
        for (int x = 0; x < 17; x++) {
            for (int z = 0; z < 13; z++) {
                level.setBlock(startPos.offset(x, 0, z), Blocks.BLACKSTONE.defaultBlockState(), 3);
            }
        }
        
        // Gothic walls - Dark stone for "Black" Church
        for (int y = 1; y <= 8; y++) {
            // Main walls
            for (int x = 0; x < 17; x++) {
                level.setBlock(startPos.offset(x, y, 0), Blocks.BLACKSTONE.defaultBlockState(), 3);
                level.setBlock(startPos.offset(x, y, 12), Blocks.BLACKSTONE.defaultBlockState(), 3);
            }
            for (int z = 0; z < 13; z++) {
                level.setBlock(startPos.offset(0, y, z), Blocks.BLACKSTONE.defaultBlockState(), 3);
                level.setBlock(startPos.offset(16, y, z), Blocks.BLACKSTONE.defaultBlockState(), 3);
            }
        }
        
        // Gothic spires
        for (int y = 9; y <= 15; y++) {
            level.setBlock(startPos.offset(3, y, 3), Blocks.BLACKSTONE.defaultBlockState(), 3);
            level.setBlock(startPos.offset(13, y, 3), Blocks.BLACKSTONE.defaultBlockState(), 3);
            level.setBlock(startPos.offset(8, y, 6), Blocks.BLACKSTONE.defaultBlockState(), 3); // Central spire
        }
        
        // Spire tops
        level.setBlock(startPos.offset(3, 16, 3), Blocks.COBBLESTONE_WALL.defaultBlockState(), 3);
        level.setBlock(startPos.offset(13, 16, 3), Blocks.COBBLESTONE_WALL.defaultBlockState(), 3);
        level.setBlock(startPos.offset(8, 16, 6), Blocks.COBBLESTONE_WALL.defaultBlockState(), 3);
        
        // Gothic buttresses
        for (int y = 2; y <= 6; y++) {
            level.setBlock(startPos.offset(2, y, 1), Blocks.BLACKSTONE.defaultBlockState(), 3);
            level.setBlock(startPos.offset(14, y, 1), Blocks.BLACKSTONE.defaultBlockState(), 3);
            level.setBlock(startPos.offset(2, y, 11), Blocks.BLACKSTONE.defaultBlockState(), 3);
            level.setBlock(startPos.offset(14, y, 11), Blocks.BLACKSTONE.defaultBlockState(), 3);
        }
        
        // Gothic arched windows (stained glass)
        level.setBlock(startPos.offset(4, 3, 0), Blocks.RED_STAINED_GLASS_PANE.defaultBlockState(), 3);
        level.setBlock(startPos.offset(8, 3, 0), Blocks.BLUE_STAINED_GLASS_PANE.defaultBlockState(), 3);
        level.setBlock(startPos.offset(12, 3, 0), Blocks.YELLOW_STAINED_GLASS_PANE.defaultBlockState(), 3);
        level.setBlock(startPos.offset(4, 4, 0), Blocks.RED_STAINED_GLASS_PANE.defaultBlockState(), 3);
        level.setBlock(startPos.offset(8, 4, 0), Blocks.BLUE_STAINED_GLASS_PANE.defaultBlockState(), 3);
        level.setBlock(startPos.offset(12, 4, 0), Blocks.YELLOW_STAINED_GLASS_PANE.defaultBlockState(), 3);
        
        // Gothic pointed arch entrance
        level.setBlock(startPos.offset(8, 1, 0), Blocks.OAK_DOOR.defaultBlockState(), 3);
        level.setBlock(startPos.offset(8, 2, 0), Blocks.OAK_DOOR.defaultBlockState().setValue(DoorBlock.HALF, DoubleBlockHalf.UPPER), 3);
        level.setBlock(startPos.offset(8, 3, 0), Blocks.BLACKSTONE_STAIRS.defaultBlockState(), 3);
        
        // Interior stone floor
        for (int x = 1; x < 16; x++) {
            for (int z = 1; z < 12; z++) {
                level.setBlock(startPos.offset(x, 1, z), Blocks.POLISHED_BLACKSTONE.defaultBlockState(), 3);
            }
        }
        
        // Altar area
        level.setBlock(startPos.offset(8, 2, 10), Blocks.LECTERN.defaultBlockState(), 3);
    }
}