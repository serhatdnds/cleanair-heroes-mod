package com.cleanairheroes.common.items;

import com.cleanairheroes.common.world.pollution.PollutionManager;
import com.cleanairheroes.client.gui.AirQualityScreen;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class AirQualityMeterItem extends Item {
    public AirQualityMeterItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide) {
            openAirQualityScreen(player);
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();

        if (!level.isClientSide && player != null) {
            // Get pollution level at clicked position
            float pollutionLevel = getPollutionLevel(level, pos);
            String qualityDescription = getAirQualityDescription(pollutionLevel);
            
            player.sendSystemMessage(Component.literal("Hava Kalitesi: " + qualityDescription)
                .withStyle(getQualityColor(pollutionLevel)));
            player.sendSystemMessage(Component.literal("Kirlilik Seviyesi: " + String.format("%.1f", pollutionLevel))
                .withStyle(ChatFormatting.GRAY));
        }

        return InteractionResult.SUCCESS;
    }

    @OnlyIn(Dist.CLIENT)
    private void openAirQualityScreen(Player player) {
        Minecraft.getInstance().setScreen(new AirQualityScreen(player));
    }

    private float getPollutionLevel(Level level, BlockPos pos) {
        // Get pollution from PollutionManager
        if (level.getServer() != null) {
            return PollutionManager.getPollutionLevel(level, pos);
        }
        return 0.0f;
    }

    private String getAirQualityDescription(float pollutionLevel) {
        if (pollutionLevel < 20) return "Çok İyi";
        if (pollutionLevel < 40) return "İyi";
        if (pollutionLevel < 60) return "Orta";
        if (pollutionLevel < 80) return "Kötü";
        return "Çok Kötü";
    }

    private ChatFormatting getQualityColor(float pollutionLevel) {
        if (pollutionLevel < 20) return ChatFormatting.GREEN;
        if (pollutionLevel < 40) return ChatFormatting.YELLOW;
        if (pollutionLevel < 60) return ChatFormatting.GOLD;
        if (pollutionLevel < 80) return ChatFormatting.RED;
        return ChatFormatting.DARK_RED;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("Hava kalitesini ölçmek için kullanın").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("Sağ tık: Detaylı görünüm aç").withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.literal("Bloka sağ tık: O noktadaki kaliteyi ölç").withStyle(ChatFormatting.BLUE));
    }
}