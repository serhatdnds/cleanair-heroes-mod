package com.cleanairheroes.common.blocks.landmarks;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public class TrabzonHagiaSophiaBlock extends Block {

    public TrabzonHagiaSophiaBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            // Send cultural and historical information about Trabzon Hagia Sophia
            player.sendSystemMessage(Component.literal("§6=== Hagia Sophia of Trabzon ==="));
            player.sendSystemMessage(Component.literal("§eBuilt: 1238-1263 CE"));
            player.sendSystemMessage(Component.literal("§eArchitecture: Byzantine style"));
            player.sendSystemMessage(Component.literal("§eOriginal: Church of the Holy Wisdom"));
            player.sendSystemMessage(Component.literal(""));
            player.sendSystemMessage(Component.literal("§7Historical Significance:"));
            player.sendSystemMessage(Component.literal("§f• Symbol of Byzantine Empire on Black Sea"));
            player.sendSystemMessage(Component.literal("§f• Built during reign of Manuel I"));
            player.sendSystemMessage(Component.literal("§f• Survived Ottoman conquest and transformations"));
            player.sendSystemMessage(Component.literal("§f• Now serves as a museum"));
            player.sendSystemMessage(Component.literal(""));
            player.sendSystemMessage(Component.literal("§9Architectural Features:"));
            player.sendSystemMessage(Component.literal("§f• Iconic Byzantine dome structure"));
            player.sendSystemMessage(Component.literal("§f• Beautiful frescoes and mosaics"));
            player.sendSystemMessage(Component.literal("§f• Stone masonry and brick construction"));
            player.sendSystemMessage(Component.literal("§f• Cross-in-square floor plan"));
            player.sendSystemMessage(Component.literal(""));
            player.sendSystemMessage(Component.literal("§aBlack Sea Heritage:"));
            player.sendSystemMessage(Component.literal("§f• Represents cultural bridge between civilizations"));
            player.sendSystemMessage(Component.literal("§f• Symbol of religious and architectural heritage"));
            player.sendSystemMessage(Component.literal("§f• Educational site for environmental awareness"));
            player.sendSystemMessage(Component.literal("§f• Promotes sustainable cultural preservation"));
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, BlockGetter level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("§6Hagia Sophia of Trabzon"));
        tooltip.add(Component.literal("§7Ancient Byzantine church (1238-1263)"));
        tooltip.add(Component.literal("§7Symbol of Black Sea cultural heritage"));
        tooltip.add(Component.literal("§9Right-click for detailed information"));
        super.appendHoverText(stack, level, tooltip, flag);
    }
}