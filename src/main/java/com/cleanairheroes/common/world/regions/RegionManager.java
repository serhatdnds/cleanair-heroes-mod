package com.cleanairheroes.common.world.regions;

import com.cleanairheroes.common.capabilities.PlayerProgressCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

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
        
        // Send welcome message
        player.sendSystemMessage(Component.literal("Hoş geldiniz: " + region.displayName));
        player.sendSystemMessage(Component.literal(region.description));
        
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
}