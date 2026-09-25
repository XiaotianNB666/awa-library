package cn.gcte.awalib.client;

import cn.gcte.awalib.AWALibrary;
import cn.gcte.awalib.network.AwAHandshakePayload;
import cn.gcte.awalib.network.events.client.ClientConfigurationConnectionNetworking;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;

@Environment(EnvType.CLIENT)
public class AwALibraryClient implements ClientModInitializer {

    // -2: unknown
    // -1: client version is lower than server version
    //  0: client version is equal to server version
    //  1: client version is higher than server version

    private static int serverStatus = -2;

    @Override
    public void onInitializeClient() {
        ClientConfigurationConnectionNetworking.COMPLETE.register(((handler, client) -> {
            handler.send(new ServerboundCustomPayloadPacket(new AwAHandshakePayload(AWALibrary.getModVersion())));
        }));

        ClientConfigurationConnectionNetworking.HANDLE_CUSTOM_PAYLOAD.register((handler, payload, client) -> {
            if (payload instanceof AwAHandshakePayload(String modVersion)) {
                try {
                    serverStatus = Version.parse(modVersion).compareTo(Version.parse(AWALibrary.getModVersion()));
                    switch (serverStatus) {
                        case 0 -> // Versions are equal
                                AWALibrary.LOGGER.debug("Client and server versions are equal: {}", modVersion);
                        case 1 -> // Server version is newer than client version
                                AWALibrary.LOGGER.warn("Server version {} is newer than client version {}. Some features may not work as expected.", modVersion, AWALibrary.getModVersion());
                        case -1 -> // Client version is lower than server version
                                AWALibrary.LOGGER.warn("Client version {} is lower than server version {}. Some features may not work as expected.", AWALibrary.getModVersion(), modVersion);
                    }
                } catch (VersionParsingException e) {
                    AWALibrary.LOGGER.warn("Failed to parse version from server: {}", modVersion);
                }
                return true;
            } else {

                return false;
            }
        });

    }

    public static boolean isServerVersionSuitable() {
        return serverStatus >= 0;
    }

    public static boolean isServerInstalled() {
        return serverStatus != -2;
    }

}
