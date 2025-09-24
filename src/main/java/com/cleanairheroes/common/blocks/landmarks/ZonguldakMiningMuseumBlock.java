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

public class ZonguldakMiningMuseumBlock extends Block {

    public ZonguldakMiningMuseumBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            // Send cultural and historical information about Zonguldak Mining Museum
            player.sendSystemMessage(Component.literal("§6=== Zonguldak Mining Museum ==="));
            player.sendSystemMessage(Component.literal("§eBuilt: 1920s (Former mining headquarters)"));
            player.sendSystemMessage(Component.literal("§eArchitecture: Industrial heritage building"));
            player.sendSystemMessage(Component.literal(""));
            player.sendSystemMessage(Component.literal("§7Historical Significance:"));
            player.sendSystemMessage(Component.literal("§f• Turkey's largest coal mining center"));
            player.sendSystemMessage(Component.literal("§f• Industrial revolution heritage site"));
            player.sendSystemMessage(Component.literal("§f• Black Sea coal mining history"));
            player.sendSystemMessage(Component.literal(""));
            player.sendSystemMessage(Component.literal("§9Museum Features:"));
            player.sendSystemMessage(Component.literal("§f• Original mining equipment displays"));
            player.sendSystemMessage(Component.literal("§f• Underground mining tunnels recreation"));
            player.sendSystemMessage(Component.literal("§f• Historical photographs and documents"));
            player.sendSystemMessage(Component.literal("§f• Miner's life exhibitions"));
            player.sendSystemMessage(Component.literal(""));
            player.sendSystemMessage(Component.literal("§aEnvironmental Education:"));
            player.sendSystemMessage(Component.literal("§f• Mining industry environmental impact"));
            player.sendSystemMessage(Component.literal("§f• Clean energy transition awareness"));
            player.sendSystemMessage(Component.literal("§f• Regional air quality improvements"));
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, BlockGetter level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("§6Zonguldak Mining Museum"));
        tooltip.add(Component.literal("§7Historic industrial heritage site"));
        tooltip.add(Component.literal("§7Showcases Turkey's coal mining history"));
        tooltip.add(Component.literal("§9Right-click for detailed information"));
        super.appendHoverText(stack, level, tooltip, flag);
    }
}