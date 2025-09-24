# Clean Air Heroes - Minecraft Mod

**Temiz Hava Kahramanları** - Minecraft Java Edition için çevre bilinci ve hava kirliliği mücadelesi temalı eğitici mod.

## 🌍 Genel Konsept

Oyuncular, 5 farklı bölgede hava kirliliğiyle mücadele eden bir çevre mühendisi olarak görev yapar. Her seviye kendine özgü hava kirliliği sorunları ve çözüm yolları içerir.

## 🗺️ Bölgeler

### Seviye 1: Varna, Bulgaristan
- **Sorunlar**: Liman kirliliği, deniz ticareti emisyonları, ulaşım kirliliği
- **Görevler**: Liman temizleme, yeşil ulaşım ağı, akıllı ısıtma sistemleri, endüstriyel filtre kurulumu

### Seviye 2: Zonguldak, Türkiye  
- **Sorunlar**: Kömür madenciliği, termik santraller, karayolu taşımacılığı
- **Görevler**: Maden toz kontrolü, termik santral dönüşümü, yeşil koridor, elektrikli ulaşım

### Seviye 3: Odesa, Ukrayna
- **Sorunlar**: Liman faaliyetleri, endüstriyel alanlar, yüksek kirlilik seviyeleri
- **Görevler**: Deniz ticareti optimizasyonu, endüstriyel modernizasyon, yeşil şehir planlaması

### Seviye 4: Trabzon, Türkiye
- **Sorunlar**: Trafik, endüstriyel faaliyetler, katı yakıt kullanımı, yeşil alan eksikliği
- **Görevler**: Akıllı trafik sistemleri, endüstriyel temizleme, evsel ısıtma dönüşümü, yeşil kuşak

### Seviye 5: Güneydoğu Romanya
- **Sorunlar**: Endüstriyel faaliyet, tarımsal kirlilik, enerji üretimi
- **Görevler**: Temiz sanayi dönüşümü, sürdürülebilir ulaşım, tarımsal iyileştirme, enerji reformu

## 🎮 Oyun Özellikleri

### Ana Araçlar
- **Hava Kalitesi Ölçer**: Çevredeki kirlilik seviyelerini ölçer
- **Kirlilik Tespit Cihazı**: Yakındaki kirlilik kaynaklarını bulur
- **Bölge Erişim Kartları**: Farklı bölgelere seyahat imkanı

### Temizleme Blokları
- **Hava Temizleyici**: Çevredeki kirliliği azaltır
- **Endüstriyel Filtre**: Fabrika emisyonlarını filtreler
- **Toz Toplayıcı**: Maden tozunu toplar

### Yeşil Teknoloji
- **Güneş Panelleri**: Temiz enerji üretir
- **Rüzgar Türbinleri**: Yenilenebilir enerji kaynağı
- **Elektrikli Ulaşım**: Temiz ulaşım seçenekleri

### Geri Dönüşüm
- **Geri Dönüşüm Merkezi**: Atıkları işler
- **Atık Ayırma İstasyonu**: Atıkları kategorize eder

## 🎯 İlerleme Sistemi

- **Temiz Hava Puanları**: Görevleri tamamlayarak puan kazanın
- **Bölgesel İlerleme**: Her bölgeyi açmak için önceki bölgeyi tamamlayın
- **Başarım Sistemi**: Özel görevleri tamamlayarak rozetler kazanın

## 🛠️ Geliştirme

### Gereksinimler
- Minecraft 1.19.2
- Minecraft Forge 43.3.0+
- Java 17+
- IntelliJ IDEA veya Eclipse (önerilen)
- Minecraft Development Kit (MDK) environment

### Kurulum ve Geliştirme
1. **Java 17+ kurulumunu doğrulayın**: `java -version`
2. **Projeyi klonlayın veya indirin**
3. **IntelliJ IDEA'da açın**: File → Open → Proje klasörünü seçin
4. **Gradle sync'i bekleyin**: IDE otomatik olarak bağımlılıkları indirecek
5. **Gradle build test edin**: Terminal'de `./gradlew build`

### Geliştirme Komutları
```bash
# Client'ı çalıştırma (Minecraft'ı mod ile başlatır)
./gradlew runClient

# Server'ı çalıştırma (test sunucusu)
./gradlew runServer

# Mod build etme (JAR dosyası oluşturur)
./gradlew build

# Clean build
./gradlew clean build
```

### IDE Kurulumu
1. **IntelliJ IDEA** (önerilen):
   - File → Open → build.gradle dosyasını seçin
   - "Import Gradle Project" seçeneğini seçin
   - Gradle sync tamamlanana kadar bekleyin

2. **Eclipse**:
   - `./gradlew genEclipseRuns` komutunu çalıştırın
   - Eclipse'de projeyi import edin

### Build Sorunları Çözümü
Eğer build hatası alıyorsanız:
1. Java 17+ kurulu olduğundan emin olun
2. İnternet bağlantınızı kontrol edin (Gradle bağımlılıkları indirecek)
3. `./gradlew clean` komutunu çalıştırıp tekrar deneyin
4. IDE'yi kapatıp açmayı deneyin

## 📋 Yapılacaklar

- [x] Temel mod yapısı
- [x] Kirlilik yönetim sistemi
- [x] Bölge sistemı (5 bölge)
- [x] Oyuncu ilerleme takibi
- [x] GUI sistemleri (3 ana ekran)
- [x] 15+ işlevsel blok
- [x] Özel items ve araçlar
- [x] Capability sistemi
- [x] Network handling
- [x] İki dil desteği (Türkçe/İngilizce)
- [x] **Mod başarıyla derlendi ve JAR oluşturuldu!**
- [ ] Texture ve model asset'leri
- [ ] Ses efektleri
- [ ] Çok oyunculu test
- [ ] Oyun içi test ve optimizasyon

## ✅ Başarıyla Tamamlandı

**Clean Air Heroes mod'u başarıyla geliştirildi ve derlenmiştir!**

- **Mod dosyası**: `build/libs/cleanairheroes-1.0.0.jar`
- **Toplam kod satırı**: 1000+ 
- **Java sınıfları**: 25+ adet
- **Desteklenen diller**: Türkçe, İngilizce
- **Minecraft sürümü**: 1.19.2
- **Forge sürümü**: 43.3.0

## 🤝 Katkıda Bulunma

Bu proje açık kaynak kodludur. Katkılarınızı bekliyoruz!

1. Projeyi fork edin
2. Feature branch oluşturun (`git checkout -b feature/YeniOzellik`)
3. Değişikliklerinizi commit edin (`git commit -am 'Yeni özellik eklendi'`)
4. Branch'inizi push edin (`git push origin feature/YeniOzellik`)
5. Pull Request oluşturun

## 📜 Lisans

Bu proje MIT lisansı altında lisanslanmıştır. Detaylar için `LICENSE` dosyasına bakın.

## 👥 Takım

Clean Air Heroes Development Team

## 🌱 Amaç

Bu mod, oyuncuları çevre bilinci konusunda eğitmeyi ve gerçek dünyadaki hava kirliliği sorunlarına farkındalık yaratmayı amaçlamaktadır. Eğlenceli bir oyun deneyimi sunarken, çevresel sorumluluk bilincini geliştirmeyi hedefler.
