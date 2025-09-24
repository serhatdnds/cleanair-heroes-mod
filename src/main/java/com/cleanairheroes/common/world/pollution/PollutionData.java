package com.cleanairheroes.common.world.pollution;

import net.minecraft.nbt.CompoundTag;

public class PollutionData {
    private static final int CHUNK_SIZE = 16;
    
    // Pollution levels for each block in the chunk (16x16 grid)
    private final float[][] pollutionLevels = new float[CHUNK_SIZE][CHUNK_SIZE];
    
    public PollutionData() {
        // Initialize with clean air
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {
                pollutionLevels[x][z] = 0.0f;
            }
        }
    }

    public float getPollutionLevel(int x, int z) {
        if (x < 0 || x >= CHUNK_SIZE || z < 0 || z >= CHUNK_SIZE) {
            return 0.0f;
        }
        return pollutionLevels[x][z];
    }

    public void setPollutionLevel(int x, int z, float level) {
        if (x < 0 || x >= CHUNK_SIZE || z < 0 || z >= CHUNK_SIZE) {
            return;
        }
        pollutionLevels[x][z] = Math.max(0.0f, Math.min(100.0f, level));
    }

    public void addPollution(int x, int z, float amount) {
        if (x < 0 || x >= CHUNK_SIZE || z < 0 || z >= CHUNK_SIZE) {
            return;
        }
        pollutionLevels[x][z] = Math.min(100.0f, pollutionLevels[x][z] + amount);
    }

    public void reducePollution(int x, int z, float amount) {
        if (x < 0 || x >= CHUNK_SIZE || z < 0 || z >= CHUNK_SIZE) {
            return;
        }
        pollutionLevels[x][z] = Math.max(0.0f, pollutionLevels[x][z] - amount);
    }

    public float getAveragePollution() {
        float total = 0.0f;
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {
                total += pollutionLevels[x][z];
            }
        }
        return total / (CHUNK_SIZE * CHUNK_SIZE);
    }

    public float getMaxPollution() {
        float max = 0.0f;
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {
                max = Math.max(max, pollutionLevels[x][z]);
            }
        }
        return max;
    }

    public CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();
        
        // Store pollution data as a byte array for efficiency
        byte[] data = new byte[CHUNK_SIZE * CHUNK_SIZE];
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {
                // Convert float (0-100) to byte (0-255) for storage
                data[x * CHUNK_SIZE + z] = (byte) Math.round(pollutionLevels[x][z] * 2.55f);
            }
        }
        
        tag.putByteArray("levels", data);
        return tag;
    }

    public void deserialize(CompoundTag tag) {
        if (tag.contains("levels")) {
            byte[] data = tag.getByteArray("levels");
            if (data.length == CHUNK_SIZE * CHUNK_SIZE) {
                for (int x = 0; x < CHUNK_SIZE; x++) {
                    for (int z = 0; z < CHUNK_SIZE; z++) {
                        // Convert byte (0-255) back to float (0-100)
                        int byteValue = data[x * CHUNK_SIZE + z] & 0xFF;
                        pollutionLevels[x][z] = byteValue / 2.55f;
                    }
                }
            }
        }
    }

    // Natural pollution decay over time
    public void decay(float decayRate) {
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {
                pollutionLevels[x][z] = Math.max(0.0f, pollutionLevels[x][z] - decayRate);
            }
        }
    }

    // Spread pollution to neighboring areas
    public void spread(float spreadRate) {
        float[][] newLevels = new float[CHUNK_SIZE][CHUNK_SIZE];
        
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {
                newLevels[x][z] = pollutionLevels[x][z];
                
                // Spread to adjacent cells within chunk
                float spreadAmount = pollutionLevels[x][z] * spreadRate;
                int neighbors = 0;
                
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        if (dx == 0 && dz == 0) continue;
                        
                        int nx = x + dx;
                        int nz = z + dz;
                        
                        if (nx >= 0 && nx < CHUNK_SIZE && nz >= 0 && nz < CHUNK_SIZE) {
                            neighbors++;
                        }
                    }
                }
                
                if (neighbors > 0) {
                    float spreadPerNeighbor = spreadAmount / neighbors;
                    newLevels[x][z] -= spreadAmount;
                    
                    for (int dx = -1; dx <= 1; dx++) {
                        for (int dz = -1; dz <= 1; dz++) {
                            if (dx == 0 && dz == 0) continue;
                            
                            int nx = x + dx;
                            int nz = z + dz;
                            
                            if (nx >= 0 && nx < CHUNK_SIZE && nz >= 0 && nz < CHUNK_SIZE) {
                                newLevels[nx][nz] += spreadPerNeighbor;
                            }
                        }
                    }
                }
            }
        }
        
        // Apply new levels
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {
                pollutionLevels[x][z] = Math.max(0.0f, Math.min(100.0f, newLevels[x][z]));
            }
        }
    }
}