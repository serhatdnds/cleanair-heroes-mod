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

public class OdesaOperaTheatreBlock extends Block {

    public OdesaOperaTheatreBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            // Send cultural and historical information about Odesa Opera Theatre
            player.sendSystemMessage(Component.literal("§6=== Odesa Opera and Ballet Theatre ==="));
            player.sendSystemMessage(Component.literal("§eBuilt: 1887"));
            player.sendSystemMessage(Component.literal("§eArchitects: Ferdinand Fellner and Hermann Helmer"));
            player.sendSystemMessage(Component.literal("§eStyle: Neo-Baroque and Renaissance Revival"));
            player.sendSystemMessage(Component.literal(""));
            player.sendSystemMessage(Component.literal("§7Historical Significance:"));
            player.sendSystemMessage(Component.literal("§f• One of the most beautiful opera houses in the world"));
            player.sendSystemMessage(Component.literal("§f• Cultural symbol of Odesa and Ukraine"));
            player.sendSystemMessage(Component.literal("§f• Survived wars and political changes"));
            player.sendSystemMessage(Component.literal("§f• UNESCO World Heritage consideration"));
            player.sendSystemMessage(Component.literal(""));
            player.sendSystemMessage(Component.literal("§9Architectural Features:"));
            player.sendSystemMessage(Component.literal("§f• Magnificent baroque facade"));
            player.sendSystemMessage(Component.literal("§f• Ornate interior with gold decorations"));
            player.sendSystemMessage(Component.literal("§f• Famous crystal chandelier"));
            player.sendSystemMessage(Component.literal("§f• Exceptional acoustics"));
            player.sendSystemMessage(Component.literal(""));
            player.sendSystemMessage(Component.literal("§aBlack Sea Cultural Heritage:"));
            player.sendSystemMessage(Component.literal("§f• Symbol of multicultural Black Sea region"));
            player.sendSystemMessage(Component.literal("§f• Bridge between European and Eastern cultures"));
            player.sendSystemMessage(Component.literal("§f• Promotes arts and environmental awareness"));
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, BlockGetter level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("§6Odesa Opera and Ballet Theatre"));
        tooltip.add(Component.literal("§7Magnificent neo-baroque opera house"));
        tooltip.add(Component.literal("§7Built by Fellner and Helmer (1887)"));
        tooltip.add(Component.literal("§9Right-click for detailed information"));
        super.appendHoverText(stack, level, tooltip, flag);
    }
}