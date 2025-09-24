# 🚀 GitHub'a Yükleme Talimatları

## 📋 Adım Adım Rehber

### 1. GitHub Repository Oluşturun
1. **GitHub.com**'a gidin ve giriş yapın
2. Sağ üstte **"+"** tıklayın → **"New repository"**
3. Repository ayarları:
   - **Repository name**: `cleanair-heroes-mod`
   - **Description**: `🌬️ Clean Air Heroes - Minecraft environmental education mod`
   - **Public** seçeneğini seçin (herkes görebilsin)
   - **README.md** ekleme (biz zaten oluşturduk)
   - **License**: MIT License seçebilirsiniz

### 2. Local Repository'yi GitHub'a Bağlayın
Terminal'de şu komutları çalıştırın:

```bash
# Repository'yi GitHub'a bağlayın (YOUR_USERNAME yerine GitHub kullanıcı adınızı yazın)
git remote add origin https://github.com/YOUR_USERNAME/cleanair-heroes-mod.git

# Ana branch'i main olarak ayarlayın
git branch -M main

# İlk push'u yapın
git push -u origin main
```

### 3. GitHub Actions Otomatik Build
- Repository yüklenince GitHub Actions otomatik çalışacak
- **Actions** tab'ında build sürecini takip edebilirsiniz
- Build başarılı olursa **Artifacts** bölümünde JAR dosyasını bulabilirsiniz

### 4. Release Oluşturun (İsteğe Bağlı)
1. GitHub'da **Releases** → **Create a new release**
2. Tag: `v1.0.0`
3. Title: `🌬️ Clean Air Heroes v1.0.0 - Initial Release`
4. Description:
   ```markdown
   ## 🎮 Clean Air Heroes - Minecraft Environmental Education Mod
   
   ### ✨ Features
   - 5 Regional progression system
   - Air quality management system
   - 15+ pollution control items and blocks
   - Task completion system
   - Turkish and English localization
   - 34 custom textures (16x16 PNG)
   
   ### 🔧 Installation
   1. Install Minecraft 1.19.2 with Forge 43.3.0+
   2. Download cleanairheroes-1.0.0.jar
   3. Place in .minecraft/mods/ folder
   4. Launch Minecraft with Forge profile
   
   ### 🌍 Educational Impact
   Learn about air pollution while having fun in Minecraft!
   ```

## 📦 Beklenen Sonuçlar

### ✅ GitHub Actions ile:
- **Otomatik build** Ubuntu sunucusunda
- **JNI sorunu yok** (temiz Linux ortamında)
- **JAR dosyası** artifact olarak
- **Her commit'te** otomatik test
- **Release'de** otomatik JAR upload

### 🎯 Build Süreci:
1. **Code checkout** ✅
2. **Java 17 setup** ✅  
3. **Gradle cache** ✅
4. **Dependencies download** ✅
5. **Compile & build** ✅
6. **JAR creation** ✅
7. **Artifact upload** ✅

### 📁 Çıktı:
- `cleanairheroes-1.0.0.jar` dosyası hazır
- Minecraft 1.19.2 + Forge 43.3.0 uyumlu
- Tüm texture'lar ve model'ler dahil

## 🔗 Yararlı Linkler

- **GitHub Actions**: Otomatik build sistemi
- **Releases**: JAR dosyası indirme
- **Issues**: Bug raporu ve öneriler
- **Wiki**: Gelişmiş dokümantasyon (opsiyonel)

Bu şekilde Gradle JNI sorununu tamamen atlayarak mod'unuzu GitHub'ın sunucularında derleyeceksiniz! 🎉