package com.cleanairheroes.common.network;

import com.cleanairheroes.CleanAirHeroesMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
        new ResourceLocation(CleanAirHeroesMod.MODID, "main"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    public static void init() {
        // Register packets here when needed
        // INSTANCE.registerMessage(packetId++, PacketClass.class, PacketClass::encode, PacketClass::decode, PacketClass::handle);
        
        CleanAirHeroesMod.LOGGER.info("Network handler initialized");
    }

    private static int nextId() {
        return packetId++;
    }
}