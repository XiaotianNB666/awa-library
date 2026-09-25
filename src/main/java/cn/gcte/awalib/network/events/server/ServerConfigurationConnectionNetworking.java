package cn.gcte.awalib.network.events.server;

import cn.gcte.awalib.event.Event;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientConfigurationPacketListenerImpl;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl;

public class ServerConfigurationConnectionNetworking {
    public static Event<Configure> CONFIGURE = Event.Factory.createArrayBacked(Configure.class, callbacks -> (handler, server) -> {
        for (Configure callback : callbacks) {
            callback.onSendConfiguration(handler, server);
        }
    });

    public static Event<OnTick> TICK = Event.Factory.createArrayBacked(OnTick.class, callbacks -> (handler, server) -> {
        for (OnTick callback : callbacks) {
            callback.onTick(handler, server);
        }
    });

    @FunctionalInterface
    public interface Configure {
        void onSendConfiguration(ServerConfigurationPacketListenerImpl handler, MinecraftServer server);
    }

    @FunctionalInterface
    public interface OnTick {
        void onTick(ServerConfigurationPacketListenerImpl handler, MinecraftServer server);
    }
}
