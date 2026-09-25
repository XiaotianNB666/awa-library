package cn.gcte.awalib;

import cn.gcte.awalib.network.AwAHandshakePayload;
import cn.gcte.awalib.network.events.server.ServerConfigurationConnectionNetworking;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AWALibrary implements ModInitializer {
    public static final String MOD_ID = "awalib";
    public static final String MOD_NAME = "aωa library";

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static String MOD_VERSION = null;


    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        ServerConfigurationConnectionNetworking.CONFIGURE.register(((handler, server) -> {
            handler.send(new ClientboundCustomPayloadPacket(new AwAHandshakePayload(getModVersion())));
        }));

        LOGGER.info("{} ({}) loaded!", MOD_NAME, getModVersion());
    }

    @Contract("_ -> new")
    public static @NotNull ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static String getModVersion() {
        if (MOD_VERSION == null) {
            FabricLoader.getInstance().getModContainer(MOD_ID).ifPresentOrElse(
                    modContainer -> {
                        MOD_VERSION = modContainer.getMetadata().getVersion().getFriendlyString();
                    },
                    () -> {
                        MOD_VERSION = "Unknown";
                    }
            );
        }
        return MOD_VERSION;
    }
}
