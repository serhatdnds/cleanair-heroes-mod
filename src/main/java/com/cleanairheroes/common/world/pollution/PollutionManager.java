package com.cleanairheroes.common.world.pollution;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import java.util.HashMap;
import java.util.Map;

public class PollutionManager extends SavedData {
    private static final String DATA_NAME = "cleanairheroes_pollution";
    
    // Pollution data stored per chunk
    private final Map<ChunkPos, PollutionData> pollutionMap = new HashMap<>();
    
    public PollutionManager() {
        super();
    }

    public static PollutionManager get(ServerLevel level) {
        DimensionDataStorage storage = level.getDataStorage();
        return storage.computeIfAbsent(PollutionManager::load, PollutionManager::new, DATA_NAME);
    }

    public static PollutionManager load(CompoundTag tag) {
        PollutionManager manager = new PollutionManager();
        
        CompoundTag pollutionTag = tag.getCompound("pollution");
        for (String chunkKey : pollutionTag.getAllKeys()) {
            String[] coords = chunkKey.split(",");
            if (coords.length == 2) {
                try {
                    int x = Integer.parseInt(coords[0]);
                    int z = Integer.parseInt(coords[1]);
                    ChunkPos chunkPos = new ChunkPos(x, z);
                    
                    PollutionData data = new PollutionData();
                    data.deserialize(pollutionTag.getCompound(chunkKey));
                    manager.pollutionMap.put(chunkPos, data);
                } catch (NumberFormatException e) {
                    // Skip invalid entries
                }
            }
        }
        
        return manager;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        CompoundTag pollutionTag = new CompoundTag();
        
        for (Map.Entry<ChunkPos, PollutionData> entry : pollutionMap.entrySet()) {
            ChunkPos chunkPos = entry.getKey();
            String chunkKey = chunkPos.x + "," + chunkPos.z;
            pollutionTag.put(chunkKey, entry.getValue().serialize());
        }
        
        tag.put("pollution", pollutionTag);
        return tag;
    }

    // Static methods for easy access
    public static float getPollutionLevel(Level level, BlockPos pos) {
        if (level.isClientSide || !(level instanceof ServerLevel serverLevel)) {
            return 0.0f;
        }
        
        PollutionManager manager = get(serverLevel);
        ChunkPos chunkPos = new ChunkPos(pos);
        PollutionData data = manager.pollutionMap.get(chunkPos);
        
        if (data == null) {
            return 0.0f;
        }
        
        return data.getPollutionLevel(pos.getX() & 15, pos.getZ() & 15);
    }

    public static void addPollution(Level level, BlockPos pos, float amount) {
        if (level.isClientSide || !(level instanceof ServerLevel serverLevel)) {
            return;
        }
        
        PollutionManager manager = get(serverLevel);
        ChunkPos chunkPos = new ChunkPos(pos);
        PollutionData data = manager.pollutionMap.computeIfAbsent(chunkPos, k -> new PollutionData());
        
        data.addPollution(pos.getX() & 15, pos.getZ() & 15, amount);
        manager.setDirty();
    }

    public static void reducePollution(Level level, BlockPos pos, float amount) {
        if (level.isClientSide || !(level instanceof ServerLevel serverLevel)) {
            return;
        }
        
        PollutionManager manager = get(serverLevel);
        ChunkPos chunkPos = new ChunkPos(pos);
        PollutionData data = manager.pollutionMap.get(chunkPos);
        
        if (data != null) {
            data.reducePollution(pos.getX() & 15, pos.getZ() & 15, amount);
            manager.setDirty();
        }
    }

    public static void setPollutionLevel(Level level, BlockPos pos, float pollutionLevel) {
        if (level.isClientSide || !(level instanceof ServerLevel serverLevel)) {
            return;
        }
        
        PollutionManager manager = get(serverLevel);
        ChunkPos chunkPos = new ChunkPos(pos);
        PollutionData data = manager.pollutionMap.computeIfAbsent(chunkPos, k -> new PollutionData());
        
        data.setPollutionLevel(pos.getX() & 15, pos.getZ() & 15, pollutionLevel);
        manager.setDirty();
    }

    // Get average pollution for a chunk
    public static float getChunkAveragePollution(Level level, ChunkPos chunkPos) {
        if (level.isClientSide || !(level instanceof ServerLevel serverLevel)) {
            return 0.0f;
        }
        
        PollutionManager manager = get(serverLevel);
        PollutionData data = manager.pollutionMap.get(chunkPos);
        
        if (data == null) {
            return 0.0f;
        }
        
        return data.getAveragePollution();
    }

    // Clean up chunks with low pollution
    public void cleanupLowPollution() {
        pollutionMap.entrySet().removeIf(entry -> {
            PollutionData data = entry.getValue();
            return data.getAveragePollution() < 1.0f;
        });
        setDirty();
    }
}