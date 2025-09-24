package com.cleanairheroes.common.capabilities;

import net.minecraft.nbt.CompoundTag;

public interface PlayerProgress {
    // Region progress
    boolean hasVisitedRegion(String regionId);
    void visitRegion(String regionId);
    boolean hasCompletedRegion(String regionId);
    void completeRegion(String regionId);
    
    // Task progress
    boolean hasCompletedTask(String regionId, String taskId);
    void completeTask(String regionId, String taskId);
    int getCompletedTaskCount(String regionId);
    
    // Points and achievements
    int getCleanAirPoints();
    void addCleanAirPoints(int points);
    void setCleanAirPoints(int points);
    
    // Achievements
    boolean hasAchievement(String achievementId);
    void unlockAchievement(String achievementId);
    
    // Statistics
    float getTotalPollutionCleaned();
    void addPollutionCleaned(float amount);
    
    int getTreesPlanted();
    void addTreePlanted();
    
    int getFiltersInstalled();
    void addFilterInstalled();
    
    // Data persistence
    void saveNBTData(CompoundTag nbt);
    void loadNBTData(CompoundTag nbt);
    void copyFrom(PlayerProgress source);
}