package com.cleanairheroes.common.items;

import com.cleanairheroes.CleanAirHeroesMod;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTabs {
    public static final CreativeModeTab CLEAN_AIR_TAB = new CreativeModeTab("cleanairheroes.clean_air_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.AIR_QUALITY_METER.get());
        }
    };
}