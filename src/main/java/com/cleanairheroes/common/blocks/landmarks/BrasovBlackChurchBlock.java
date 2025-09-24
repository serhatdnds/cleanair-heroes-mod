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

public class BrasovBlackChurchBlock extends Block {

    public BrasovBlackChurchBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            // Send cultural and historical information about Brașov Black Church
            player.sendSystemMessage(Component.literal("§6=== Black Church of Brașov ==="));
            player.sendSystemMessage(Component.literal("§eBuilt: 1383-1477"));
            player.sendSystemMessage(Component.literal("§eArchitecture: Gothic style"));
            player.sendSystemMessage(Component.literal("§eOriginal name: Saint Mary's Church"));
            player.sendSystemMessage(Component.literal(""));
            player.sendSystemMessage(Component.literal("§7Historical Significance:"));
            player.sendSystemMessage(Component.literal("§f• Largest Gothic church between Vienna and Istanbul"));
            player.sendSystemMessage(Component.literal("§f• Symbol of Saxon heritage in Transylvania"));
            player.sendSystemMessage(Component.literal("§f• Survived Great Fire of 1689 (hence 'Black' name)"));
            player.sendSystemMessage(Component.literal("§f• UNESCO World Heritage consideration"));
            player.sendSystemMessage(Component.literal(""));
            player.sendSystemMessage(Component.literal("§9Architectural Features:"));
            player.sendSystemMessage(Component.literal("§f• Impressive Gothic spires and buttresses"));
            player.sendSystemMessage(Component.literal("§f• Massive stone construction"));
            player.sendSystemMessage(Component.literal("§f• Beautiful stained glass windows"));
            player.sendSystemMessage(Component.literal("§f• Famous Buchholz organ (4,000 pipes)"));
            player.sendSystemMessage(Component.literal(""));
            player.sendSystemMessage(Component.literal("§aTransylvanian Heritage:"));
            player.sendSystemMessage(Component.literal("§f• Represents multicultural Black Sea region"));
            player.sendSystemMessage(Component.literal("§f• Bridge between Western and Eastern Europe"));
            player.sendSystemMessage(Component.literal("§f• Promotes cultural preservation awareness"));
            player.sendSystemMessage(Component.literal("§f• Educational site for sustainable tourism"));
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, BlockGetter level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("§6Black Church of Brașov"));
        tooltip.add(Component.literal("§7Gothic masterpiece (1383-1477)"));
        tooltip.add(Component.literal("§7Largest church between Vienna and Istanbul"));
        tooltip.add(Component.literal("§9Right-click for detailed information"));
        super.appendHoverText(stack, level, tooltip, flag);
    }
}