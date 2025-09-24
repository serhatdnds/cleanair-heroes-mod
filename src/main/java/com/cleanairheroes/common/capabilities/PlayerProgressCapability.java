package com.cleanairheroes.common.capabilities;

import com.cleanairheroes.CleanAirHeroesMod;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = CleanAirHeroesMod.MODID)
public class PlayerProgressCapability {
    public static final Capability<PlayerProgress> PLAYER_PROGRESS = CapabilityManager.get(new CapabilityToken<>() {});
    public static final ResourceLocation PLAYER_PROGRESS_RL = new ResourceLocation(CleanAirHeroesMod.MODID, "player_progress");

    public static void register() {
        // Capability registration is handled automatically in modern Forge versions
    }

    public static PlayerProgress getProgress(Player player) {
        return player.getCapability(PLAYER_PROGRESS).orElse(new PlayerProgressImpl());
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(PLAYER_PROGRESS).isPresent()) {
                event.addCapability(PLAYER_PROGRESS_RL, new PlayerProgressProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(PLAYER_PROGRESS).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PLAYER_PROGRESS).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    public static class PlayerProgressProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
        private final PlayerProgress progress = new PlayerProgressImpl();
        private final LazyOptional<PlayerProgress> progressOptional = LazyOptional.of(() -> progress);

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap == PLAYER_PROGRESS) {
                return progressOptional.cast();
            }
            return LazyOptional.empty();
        }

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag tag = new CompoundTag();
            progress.saveNBTData(tag);
            return tag;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            progress.loadNBTData(nbt);
        }
    }
}