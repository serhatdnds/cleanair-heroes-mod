package com.cleanairheroes.client.gui;

import com.cleanairheroes.common.capabilities.PlayerProgressCapability;
import com.cleanairheroes.common.world.regions.RegionData;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class TaskDetailsScreen extends Screen {
    private static final int SCREEN_WIDTH = 350;
    private static final int SCREEN_HEIGHT = 200;
    
    private final Player player;
    private final RegionData region;
    private final String taskId;
    
    public TaskDetailsScreen(Player player, RegionData region, String taskId) {
        super(Component.literal(region.getTaskDisplayName(taskId)));
        this.player = player;
        this.region = region;
        this.taskId = taskId;
    }

    @Override
    protected void init() {
        super.init();
        
        int startX = (this.width - SCREEN_WIDTH) / 2;
        int startY = (this.height - SCREEN_HEIGHT) / 2;
        
        // Back button
        addRenderableWidget(new Button(startX + 10, startY + 10, 60, 20,
            Component.literal("← Geri"),
            button -> minecraft.setScreen(new RegionDetailsScreen(player, region))));
        
        // Complete task button (if not completed)
        boolean isCompleted = PlayerProgressCapability.getProgress(player).hasCompletedTask(region.id, taskId);
        if (!isCompleted) {
            addRenderableWidget(new Button(startX + SCREEN_WIDTH - 120, startY + 10, 110, 20,
                Component.literal("Görevi Tamamla"),
                button -> completeTask()));
        } else {
            addRenderableWidget(new Button(startX + SCREEN_WIDTH - 120, startY + 10, 110, 20,
                Component.literal("✓ Tamamlandı"),
                button -> {}));
        }
        
        // Close button
        addRenderableWidget(new Button(startX + SCREEN_WIDTH - 60, startY + SCREEN_HEIGHT - 30, 50, 20,
            Component.literal("Kapat"),
            button -> this.onClose()));
    }

    private void completeTask() {
        PlayerProgressCapability.getProgress(player).completeTask(region.id, taskId);
        PlayerProgressCapability.getProgress(player).addCleanAirPoints(10); // Award points
        
        // Check if all tasks in region are completed
        if (PlayerProgressCapability.getProgress(player).getCompletedTaskCount(region.id) >= region.tasks.length) {
            PlayerProgressCapability.getProgress(player).completeRegion(region.id);
            PlayerProgressCapability.getProgress(player).addCleanAirPoints(50); // Bonus for completing region
        }
        
        // Refresh screen
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
        
        // Draw task description
        List<String> descriptionLines = wrapText(region.getTaskDescription(taskId), SCREEN_WIDTH - 20);
        for (int i = 0; i < descriptionLines.size(); i++) {
            drawString(poseStack, this.font, descriptionLines.get(i), startX + 10, startY + 40 + (i * 10), 0xFFFFFF);
        }
        
        // Draw completion status
        boolean isCompleted = PlayerProgressCapability.getProgress(player).hasCompletedTask(region.id, taskId);
        String statusText = isCompleted ? "✓ Bu görev tamamlandı!" : "Bu görev henüz tamamlanmadı.";
        int color = isCompleted ? 0x00FF00 : 0xFFFF00;
        drawString(poseStack, this.font, statusText, startX + 10, startY + SCREEN_HEIGHT - 60, color);
        
        // Draw instructions
        if (!isCompleted) {
            drawString(poseStack, this.font, "Bu görevi tamamlamak için gerekli blokları yerleştirin", startX + 10, startY + SCREEN_HEIGHT - 45, 0xFFFFFF);
            drawString(poseStack, this.font, "ve ekipmanları kullanın, ardından 'Görevi Tamamla' butonuna basın.", startX + 10, startY + SCREEN_HEIGHT - 35, 0xFFFFFF);
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