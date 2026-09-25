package cn.gcte.awalib.client;

import cn.gcte.awalib.AWALibrary;
import cn.gcte.awalib.network.AwAHandshakePayload;
import cn.gcte.awalib.network.events.client.ClientConfigurationConnectionNetworking;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;

@Environment(EnvType.CLIENT)
public class AwALibraryClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientConfigurationConnectionNetworking.COMPLETE.register(((handler, client) -> {
            handler.send(new ServerboundCustomPayloadPacket(new AwAHandshakePayload(AWALibrary.getModVersion())));
        }));
    }
}
