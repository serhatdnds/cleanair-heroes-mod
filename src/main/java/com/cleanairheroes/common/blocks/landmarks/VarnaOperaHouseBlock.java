package com.cleanairheroes.common.blocks.landmarks;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

/**
 * Varna Opera House - İkonik kültür merkezi
 * Varna'nın simgesi olan opera binası, şehrin kültürel hayatının merkezi (1932)
 * Neo-klasik mimari tarzında inşa edilmiş Karadeniz'in önemli kültür merkezi
 */
public class VarnaOperaHouseBlock extends Block {
    
    public VarnaOperaHouseBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, 
                               Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            player.sendSystemMessage(Component.literal("§6Varna Opera Binası"));
            player.sendSystemMessage(Component.literal("§7Varna'nın kültürel kalbi, 1932'de inşa edilmiş neo-klasik mimari şaheser."));
            player.sendSystemMessage(Component.literal("§7Temiz hava, şehrin kültürel mirasını korumaya yardımcı olur."));
        }
        return InteractionResult.SUCCESS;
    }
}