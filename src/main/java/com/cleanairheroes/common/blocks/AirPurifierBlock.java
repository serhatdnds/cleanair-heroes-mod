package com.cleanairheroes.common.blocks;

import com.cleanairheroes.common.world.pollution.PollutionManager;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class AirPurifierBlock extends Block {
    private static final int PURIFICATION_RADIUS = 10;
    private static final float PURIFICATION_STRENGTH = 5.0f;

    public AirPurifierBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);
        
        // Purify air in surrounding area
        purifyArea(level, pos);
    }

    private void purifyArea(Level level, BlockPos centerPos) {
        if (level.isClientSide) return;

        // Reduce pollution in radius around this block
        for (int x = -PURIFICATION_RADIUS; x <= PURIFICATION_RADIUS; x++) {
            for (int y = -PURIFICATION_RADIUS; y <= PURIFICATION_RADIUS; y++) {
                for (int z = -PURIFICATION_RADIUS; z <= PURIFICATION_RADIUS; z++) {
                    BlockPos targetPos = centerPos.offset(x, y, z);
                    double distance = centerPos.distSqr(targetPos);
                    
                    if (distance <= PURIFICATION_RADIUS * PURIFICATION_RADIUS) {
                        float reductionAmount = PURIFICATION_STRENGTH * (1.0f - (float)(distance / (PURIFICATION_RADIUS * PURIFICATION_RADIUS)));
                        PollutionManager.reducePollution(level, targetPos, reductionAmount);
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