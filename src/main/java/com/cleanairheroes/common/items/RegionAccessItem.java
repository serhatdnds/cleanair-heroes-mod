package com.cleanairheroes.common.items;

import com.cleanairheroes.common.world.regions.RegionManager;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class RegionAccessItem extends Item {
    private final String regionId;

    public RegionAccessItem(Properties properties, String regionId) {
        super(properties);
        this.regionId = regionId;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            // Check if player can access this region
            if (RegionManager.canPlayerAccessRegion(player, regionId)) {
                // Teleport player to region
                RegionManager.teleportPlayerToRegion(player, regionId);
                player.sendSystemMessage(Component.literal("Bölgeye taşınıyorsunuz: " + getRegionName())
                    .withStyle(ChatFormatting.GREEN));
            } else {
                player.sendSystemMessage(Component.literal("Bu bölgeye erişim için önce önceki görevleri tamamlamalısınız!")
                    .withStyle(ChatFormatting.RED));
            }
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    private String getRegionName() {
        switch (regionId) {
            case "varna": return "Varna, Bulgaristan";
            case "zonguldak": return "Zonguldak, Türkiye";
            case "odesa": return "Odesa, Ukrayna";
            case "trabzon": return "Trabzon, Türkiye";
            case "romania": return "Güneydoğu Romanya";
            default: return "Bilinmeyen Bölge";
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("Bölge: " + getRegionName()).withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.literal("Bu kartı kullanarak bölgeye seyahat edebilirsiniz").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("Sağ tık: Bölgeye git").withStyle(ChatFormatting.BLUE));
    }

    public String getRegionId() {
        return regionId;
    }
}