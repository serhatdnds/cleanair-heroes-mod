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

public class RegionDetailsScreen extends Screen {
    private static final int SCREEN_WIDTH = 320;
    private static final int SCREEN_HEIGHT = 240;
    
    private final Player player;
    private final RegionData region;
    
    public RegionDetailsScreen(Player player, RegionData region) {
        super(Component.literal(region.displayName));
        this.player = player;
        this.region = region;
    }

    @Override
    protected void init() {
        super.init();
        
        int startX = (this.width - SCREEN_WIDTH) / 2;
        int startY = (this.height - SCREEN_HEIGHT) / 2;
        
        // Back button
        addRenderableWidget(new Button(startX + 10, startY + 10, 60, 20,
            Component.literal("← Geri"),
            button -> minecraft.setScreen(new AirQualityScreen(player))));
        
        // Travel button (if accessible)
        if (RegionManager.canPlayerAccessRegion(player, region.id)) {
            addRenderableWidget(new Button(startX + SCREEN_WIDTH - 90, startY + 10, 80, 20,
                Component.literal("Bölgeye Git"),
                button -> {
                    RegionManager.teleportPlayerToRegion(player, region.id);
                    this.onClose();
                }));
        }
        
        // Task buttons
        for (int i = 0; i < region.tasks.length; i++) {
            String taskId = region.tasks[i];
            boolean completed = PlayerProgressCapability.getProgress(player).hasCompletedTask(region.id, taskId);
            
            String buttonText = region.getTaskDisplayName(taskId);
            if (completed) {
                buttonText += " ✓";
            }
            
            Button taskButton = new Button(startX + 10, startY + 80 + (i * 25), SCREEN_WIDTH - 20, 20,
                Component.literal(buttonText),
                button -> openTaskDetails(taskId));
            
            addRenderableWidget(taskButton);
        }
        
        // Close button
        addRenderableWidget(new Button(startX + SCREEN_WIDTH - 60, startY + SCREEN_HEIGHT - 30, 50, 20,
            Component.literal("Kapat"),
            button -> this.onClose()));
    }

    private void openTaskDetails(String taskId) {
        minecraft.setScreen(new TaskDetailsScreen(player, region, taskId));
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
        
        // Draw description
        List<String> descriptionLines = wrapText(region.description, SCREEN_WIDTH - 20);
        for (int i = 0; i < descriptionLines.size(); i++) {
            drawString(poseStack, this.font, descriptionLines.get(i), startX + 10, startY + 40 + (i * 10), 0xFFFFFF);
        }
        
        // Draw task header
        drawString(poseStack, this.font, "Görevler:", startX + 10, startY + 65, 0xFFFFFF);
        
        // Draw progress
        int completedTasks = PlayerProgressCapability.getProgress(player).getCompletedTaskCount(region.id);
        int totalTasks = region.tasks.length;
        String progressText = String.format("İlerleme: %d/%d görev tamamlandı", completedTasks, totalTasks);
        drawString(poseStack, this.font, progressText, startX + 10, startY + SCREEN_HEIGHT - 50, 0xFFFFFF);
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