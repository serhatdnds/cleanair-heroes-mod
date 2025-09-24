package com.cleanairheroes.common.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;

import java.util.HashSet;
import java.util.Set;

public class PlayerProgressImpl implements PlayerProgress {
    private final Set<String> visitedRegions = new HashSet<>();
    private final Set<String> completedRegions = new HashSet<>();
    private final Set<String> completedTasks = new HashSet<>();
    private final Set<String> achievements = new HashSet<>();
    
    private int cleanAirPoints = 0;
    private float totalPollutionCleaned = 0.0f;
    private int treesPlanted = 0;
    private int filtersInstalled = 0;

    @Override
    public boolean hasVisitedRegion(String regionId) {
        return visitedRegions.contains(regionId);
    }

    @Override
    public void visitRegion(String regionId) {
        visitedRegions.add(regionId);
    }

    @Override
    public boolean hasCompletedRegion(String regionId) {
        return completedRegions.contains(regionId);
    }

    @Override
    public void completeRegion(String regionId) {
        completedRegions.add(regionId);
        visitRegion(regionId); // Also mark as visited
    }

    @Override
    public boolean hasCompletedTask(String regionId, String taskId) {
        return completedTasks.contains(regionId + ":" + taskId);
    }

    @Override
    public void completeTask(String regionId, String taskId) {
        completedTasks.add(regionId + ":" + taskId);
    }

    @Override
    public int getCompletedTaskCount(String regionId) {
        int count = 0;
        String prefix = regionId + ":";
        for (String task : completedTasks) {
            if (task.startsWith(prefix)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int getCleanAirPoints() {
        return cleanAirPoints;
    }

    @Override
    public void addCleanAirPoints(int points) {
        this.cleanAirPoints += points;
    }

    @Override
    public void setCleanAirPoints(int points) {
        this.cleanAirPoints = points;
    }

    @Override
    public boolean hasAchievement(String achievementId) {
        return achievements.contains(achievementId);
    }

    @Override
    public void unlockAchievement(String achievementId) {
        achievements.add(achievementId);
    }

    @Override
    public float getTotalPollutionCleaned() {
        return totalPollutionCleaned;
    }

    @Override
    public void addPollutionCleaned(float amount) {
        this.totalPollutionCleaned += amount;
    }

    @Override
    public int getTreesPlanted() {
        return treesPlanted;
    }

    @Override
    public void addTreePlanted() {
        this.treesPlanted++;
    }

    @Override
    public int getFiltersInstalled() {
        return filtersInstalled;
    }

    @Override
    public void addFilterInstalled() {
        this.filtersInstalled++;
    }

    @Override
    public void saveNBTData(CompoundTag nbt) {
        // Save visited regions
        ListTag visitedList = new ListTag();
        for (String region : visitedRegions) {
            visitedList.add(StringTag.valueOf(region));
        }
        nbt.put("visited_regions", visitedList);

        // Save completed regions
        ListTag completedList = new ListTag();
        for (String region : completedRegions) {
            completedList.add(StringTag.valueOf(region));
        }
        nbt.put("completed_regions", completedList);

        // Save completed tasks
        ListTag tasksList = new ListTag();
        for (String task : completedTasks) {
            tasksList.add(StringTag.valueOf(task));
        }
        nbt.put("completed_tasks", tasksList);

        // Save achievements
        ListTag achievementsList = new ListTag();
        for (String achievement : achievements) {
            achievementsList.add(StringTag.valueOf(achievement));
        }
        nbt.put("achievements", achievementsList);

        // Save statistics
        nbt.putInt("clean_air_points", cleanAirPoints);
        nbt.putFloat("total_pollution_cleaned", totalPollutionCleaned);
        nbt.putInt("trees_planted", treesPlanted);
        nbt.putInt("filters_installed", filtersInstalled);
    }

    @Override
    public void loadNBTData(CompoundTag nbt) {
        // Load visited regions
        if (nbt.contains("visited_regions", Tag.TAG_LIST)) {
            ListTag visitedList = nbt.getList("visited_regions", Tag.TAG_STRING);
            visitedRegions.clear();
            for (int i = 0; i < visitedList.size(); i++) {
                visitedRegions.add(visitedList.getString(i));
            }
        }

        // Load completed regions
        if (nbt.contains("completed_regions", Tag.TAG_LIST)) {
            ListTag completedList = nbt.getList("completed_regions", Tag.TAG_STRING);
            completedRegions.clear();
            for (int i = 0; i < completedList.size(); i++) {
                completedRegions.add(completedList.getString(i));
            }
        }

        // Load completed tasks
        if (nbt.contains("completed_tasks", Tag.TAG_LIST)) {
            ListTag tasksList = nbt.getList("completed_tasks", Tag.TAG_STRING);
            completedTasks.clear();
            for (int i = 0; i < tasksList.size(); i++) {
                completedTasks.add(tasksList.getString(i));
            }
        }

        // Load achievements
        if (nbt.contains("achievements", Tag.TAG_LIST)) {
            ListTag achievementsList = nbt.getList("achievements", Tag.TAG_STRING);
            achievements.clear();
            for (int i = 0; i < achievementsList.size(); i++) {
                achievements.add(achievementsList.getString(i));
            }
        }

        // Load statistics
        cleanAirPoints = nbt.getInt("clean_air_points");
        totalPollutionCleaned = nbt.getFloat("total_pollution_cleaned");
        treesPlanted = nbt.getInt("trees_planted");
        filtersInstalled = nbt.getInt("filters_installed");
    }

    @Override
    public void copyFrom(PlayerProgress source) {
        if (source instanceof PlayerProgressImpl impl) {
            this.visitedRegions.clear();
            this.visitedRegions.addAll(impl.visitedRegions);
            
            this.completedRegions.clear();
            this.completedRegions.addAll(impl.completedRegions);
            
            this.completedTasks.clear();
            this.completedTasks.addAll(impl.completedTasks);
            
            this.achievements.clear();
            this.achievements.addAll(impl.achievements);
            
            this.cleanAirPoints = impl.cleanAirPoints;
            this.totalPollutionCleaned = impl.totalPollutionCleaned;
            this.treesPlanted = impl.treesPlanted;
            this.filtersInstalled = impl.filtersInstalled;
        }
    }
}