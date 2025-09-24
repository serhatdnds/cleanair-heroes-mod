package com.cleanairheroes.common.world.regions;

import net.minecraft.core.BlockPos;

public class RegionData {
    public final String id;
    public final String displayName;
    public final String description;
    public final BlockPos spawnPoint;
    public final int level;
    public final String prerequisiteRegion;
    public final String[] tasks;

    public RegionData(String id, String displayName, String description, BlockPos spawnPoint, 
                     int level, String prerequisiteRegion, String[] tasks) {
        this.id = id;
        this.displayName = displayName;
        this.description = description;
        this.spawnPoint = spawnPoint;
        this.level = level;
        this.prerequisiteRegion = prerequisiteRegion;
        this.tasks = tasks;
    }

    public boolean hasPrerequisite() {
        return prerequisiteRegion != null;
    }

    public int getTaskCount() {
        return tasks.length;
    }

    public String getTask(int index) {
        if (index < 0 || index >= tasks.length) {
            return null;
        }
        return tasks[index];
    }

    public String getTaskDisplayName(String taskId) {
        switch (taskId) {
            // Varna tasks
            case "liman_temizleme": return "Liman Temizleme Görevi";
            case "yesil_ulasim": return "Yeşil Ulaşım Ağı";
            case "akilli_isitma": return "Akıllı Isıtma Sistemleri";
            case "endustriyel_filtre": return "Endüstriyel Filtre Kurulumu";
            
            // Zonguldak tasks
            case "maden_toz_kontrolu": return "Madenlerde Toz Kontrolü";
            case "termik_santral_donusum": return "Termik Santral Dönüşümü";
            case "yesil_koridor": return "Yeşil Koridor";
            case "elektrikli_ulasim": return "Elektrikli Ulaşım Filosu";
            
            // Odesa tasks
            case "deniz_ticareti_optimizasyon": return "Deniz Ticareti Optimizasyonu";
            case "endustriyel_modernizasyon": return "Endüstriyel Alan Modernizasyonu";
            case "yesil_sehir_planlama": return "Yeşil Şehir Planlaması";
            case "hava_kalitesi_izleme": return "Hava Kalitesi İzleme Ağı";
            
            // Trabzon tasks
            case "akilli_trafik": return "Akıllı Trafik Sistemleri";
            case "endustriyel_temizleme": return "Endüstriyel Temizleme";
            case "evsel_isitma_donusum": return "Evsel Isıtma Dönüşümü";
            case "yesil_kusak": return "Yeşil Kuşak Projesi";
            case "geri_donusum_merkezi": return "Geri Dönüşüm Merkezi";
            
            // Romania tasks
            case "temiz_sanayi_donusum": return "Temiz Sanayi Dönüşümü";
            case "surdurulebilir_ulasim": return "Sürdürülebilir Ulaşım Ağı";
            case "tarimsal_iyilestirme": return "Tarımsal İyileştirme";
            case "enerji_uretim_reform": return "Enerji Üretim Reformu";
            case "kapsamli_izleme": return "Hava Kalitesi İzleme Merkezi";
            
            default: return "Bilinmeyen Görev";
        }
    }

    public String getTaskDescription(String taskId) {
        switch (taskId) {
            // Varna tasks
            case "liman_temizleme": return "Limandaki gemilerin emisyonlarını azaltmak için gemi filtreleri yerleştirin";
            case "yesil_ulasim": return "Şehirde elektrikli otobüs durakları ve bisiklet yolları inşa edin";
            case "akilli_isitma": return "Evlerdeki katı yakıt sistemlerini yenilenebilir enerji kaynaklarına dönüştürün";
            case "endustriyel_filtre": return "Fabrikalara filtreleme sistemleri yerleştirin";
            
            // Zonguldak tasks
            case "maden_toz_kontrolu": return "Madenlerde toz emisyonlarını azaltma sistemleri kurun";
            case "termik_santral_donusum": return "Santralleri yüksek teknolojili filtre sistemleriyle donatın";
            case "yesil_koridor": return "Şehir çevresine ağaçlandırma yaparak doğal filtre oluşturun";
            case "elektrikli_ulasim": return "Şehir içi ulaşımı elektrikli araçlara dönüştürün";
            
            // Odesa tasks
            case "deniz_ticareti_optimizasyon": return "Liman aktivitelerini daha çevreci hale getirin";
            case "endustriyel_modernizasyon": return "Fabrikaların emisyon seviyelerini düşürün";
            case "yesil_sehir_planlama": return "Şehir merkezinde yeşil alanlar ve parklar oluşturun";
            case "hava_kalitesi_izleme": return "Şehir genelinde izleme istasyonları kurun";
            
            // Trabzon tasks
            case "akilli_trafik": return "Şehirde trafik akışını optimize edin";
            case "endustriyel_temizleme": return "Fabrikaları modern filtreleme sistemleriyle donatın";
            case "evsel_isitma_donusum": return "Temiz enerji kaynaklarına geçiş sağlayın";
            case "yesil_kusak": return "Şehir çevresinde ağaçlandırma yaparak doğal hava filtresi oluşturun";
            case "geri_donusum_merkezi": return "Atık yönetim sistemi kurun";
            
            // Romania tasks
            case "temiz_sanayi_donusum": return "Endüstriyel tesisleri modernize edin";
            case "surdurulebilir_ulasim": return "Toplu taşıma sistemlerini geliştirin";
            case "tarimsal_iyilestirme": return "Sürdürülebilir tarım uygulamalarını destekleyin";
            case "enerji_uretim_reform": return "Kömürle çalışan santralleri yenilenebilir enerji tesislerine dönüştürün";
            case "kapsamli_izleme": return "Bölge genelinde kapsamlı bir izleme sistemi kurun";
            
            default: return "Görev açıklaması bulunamadı";
        }
    }
}