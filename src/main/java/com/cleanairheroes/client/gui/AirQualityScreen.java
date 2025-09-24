package com.cleanairheroes.client.gui;

import com.cleanairheroes.common.capabilities.PlayerProgressCapability;
import com.cleanairheroes.common.world.regions.RegionData;
import com.cleanairheroes.common.world.regions.RegionManager;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class AirQualityScreen extends Screen {
    private static final int SCREEN_WIDTH = 256;
    private static final int SCREEN_HEIGHT = 200;
    
    private final Player player;
    private List<Button> regionButtons = new ArrayList<>();
    
    public AirQualityScreen(Player player) {
        super(Component.literal("Hava Kalitesi ve Bölge Yönetimi"));
        this.player = player;
    }

    @Override
    protected void init() {
        super.init();
        
        int startX = (this.width - SCREEN_WIDTH) / 2;
        int startY = (this.height - SCREEN_HEIGHT) / 2;
        
        // Title
        addRenderableWidget(new Button(startX + 10, startY + 10, SCREEN_WIDTH - 20, 20,
            Component.literal("Temiz Hava Kahramanları"),
            button -> {}));
        
        // Player progress info
        int cleanAirPoints = PlayerProgressCapability.getProgress(player).getCleanAirPoints();
        addRenderableWidget(new Button(startX + 10, startY + 35, SCREEN_WIDTH - 20, 20,
            Component.literal("Temiz Hava Puanı: " + cleanAirPoints),
            button -> {}));
        
        // Region buttons
        RegionData[] regions = RegionManager.getAllRegions();
        for (int i = 0; i < regions.length; i++) {
            RegionData region = regions[i];
            boolean canAccess = RegionManager.canPlayerAccessRegion(player, region.id);
            boolean completed = PlayerProgressCapability.getProgress(player).hasCompletedRegion(region.id);
            
            String buttonText = region.displayName;
            if (completed) {
                buttonText += " ✓";
            } else if (!canAccess) {
                buttonText += " 🔒";
            }
            
            Button regionButton = new Button(startX + 10, startY + 60 + (i * 25), SCREEN_WIDTH - 20, 20,
                Component.literal(buttonText),
                button -> {
                    if (canAccess) {
                        openRegionDetails(region);
                    }
                });
            
            if (!canAccess) {
                regionButton.active = false;
            }
            
            addRenderableWidget(regionButton);
            regionButtons.add(regionButton);
        }
        
        // Close button
        addRenderableWidget(new Button(startX + SCREEN_WIDTH - 60, startY + SCREEN_HEIGHT - 30, 50, 20,
            Component.literal("Kapat"),
            button -> this.onClose()));
    }

    private void openRegionDetails(RegionData region) {
        minecraft.setScreen(new RegionDetailsScreen(player, region));
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTick);
        
        int startX = (this.width - SCREEN_WIDTH) / 2;
        int startY = (this.height - SCREEN_HEIGHT) / 2;
        
        // Draw background
        fill(poseStack, startX, startY, startX + SCREEN_WIDTH, startY + SCREEN_HEIGHT, 0x88000000);
        
        // Draw title
        drawCenteredString(poseStack, this.font, this.title, this.width / 2, startY + 15, 0xFFFFFF);
        
        // Draw statistics
        String statsText = String.format("İstatistikler: %.1f birim kirlilik temizlendi, %d ağaç dikildi, %d filtre kuruldu",
            PlayerProgressCapability.getProgress(player).getTotalPollutionCleaned(),
            PlayerProgressCapability.getProgress(player).getTreesPlanted(),
            PlayerProgressCapability.getProgress(player).getFiltersInstalled());
        
        // Wrap text if too long
        List<String> wrappedText = wrapText(statsText, SCREEN_WIDTH - 20);
        for (int i = 0; i < wrappedText.size(); i++) {
            drawString(poseStack, this.font, wrappedText.get(i), startX + 10, startY + SCREEN_HEIGHT - 50 + (i * 10), 0xFFFFFF);
        }
    }

    private List<String> wrapText(String text, int maxWidth) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();
        
        for (String word : words) {
            if (this.font.width(currentLine + word) > maxWidth) {
                if (currentLine.length() > 0) {
                    lines.add(currentLine.toString().trim());
                    currentLine = new StringBuilder();
                }
            }
            currentLine.append(word).append(" ");
        }
        
        if (currentLine.length() > 0) {
            lines.add(currentLine.toString().trim());
        }
        
        return lines;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}