package com.cleanairheroes.common.blocks;

import com.cleanairheroes.common.world.regions.RegionManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class RegionPortalBlock extends Block {
    public RegionPortalBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            // Open region selection GUI or teleport to default region
            player.sendSystemMessage(Component.literal("Portal aktif! Bölge seçimi için GUI açılacak..."));
            // TODO: Open region selection GUI
        }
        return InteractionResult.SUCCESS;
    }
}