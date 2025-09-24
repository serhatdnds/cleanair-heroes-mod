package com.cleanairheroes.common.blocks;

import com.cleanairheroes.common.world.pollution.PollutionManager;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class IndustrialChimneyBlock extends Block {
    private static final int POLLUTION_RADIUS = 15;
    private static final float POLLUTION_STRENGTH = 8.0f;

    public IndustrialChimneyBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);
        
        // Generate pollution in surrounding area
        generatePollution(level, pos);
    }

    private void generatePollution(Level level, BlockPos centerPos) {
        if (level.isClientSide) return;

        // Add pollution in radius around this block
        for (int x = -POLLUTION_RADIUS; x <= POLLUTION_RADIUS; x++) {
            for (int y = 0; y <= POLLUTION_RADIUS / 2; y++) { // Pollution rises up
                for (int z = -POLLUTION_RADIUS; z <= POLLUTION_RADIUS; z++) {
                    BlockPos targetPos = centerPos.offset(x, y, z);
                    double distance = centerPos.distSqr(targetPos);
                    
                    if (distance <= POLLUTION_RADIUS * POLLUTION_RADIUS) {
                        float pollutionAmount = POLLUTION_STRENGTH * (1.0f - (float)(distance / (POLLUTION_RADIUS * POLLUTION_RADIUS)));
                        PollutionManager.addPollution(level, targetPos, pollutionAmount);
                    }
                }
            }
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }
}