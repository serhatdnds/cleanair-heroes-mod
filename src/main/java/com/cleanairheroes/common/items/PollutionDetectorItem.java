package com.cleanairheroes.common.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class PollutionDetectorItem extends Item {
    public PollutionDetectorItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            // Scan area for pollution sources
            scanForPollutionSources(level, player);
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    private void scanForPollutionSources(Level level, Player player) {
        // TODO: Implement pollution source detection logic
        // This will scan nearby blocks for pollution-causing blocks and entities
        
        player.sendSystemMessage(Component.literal("Kirlilik kaynakları taranıyor...")
            .withStyle(ChatFormatting.YELLOW));
        
        // Simulate detection
        player.sendSystemMessage(Component.literal("3 kirlilik kaynağı tespit edildi!")
            .withStyle(ChatFormatting.RED));
        player.sendSystemMessage(Component.literal("- Endüstriyel baca (15 blok kuzeyde)")
            .withStyle(ChatFormatting.GRAY));
        player.sendSystemMessage(Component.literal("- Kirli araç (8 blok batıda)")
            .withStyle(ChatFormatting.GRAY));
        player.sendSystemMessage(Component.literal("- Atık yakma alanı (22 blok güneyde)")
            .withStyle(ChatFormatting.GRAY));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("Yakındaki kirlilik kaynaklarını tespit eder").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("Sağ tık: Çevreyi tara").withStyle(ChatFormatting.BLUE));
    }
}