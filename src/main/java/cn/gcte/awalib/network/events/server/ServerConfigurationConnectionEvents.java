package cn.gcte.awalib.network.events.server;

import cn.gcte.awalib.event.Event;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl;

public class ServerConfigurationConnectionEvents {
    public static Event<Configure> CONFIGURE = Event.Factory.createArrayBacked(Configure.class, callbacks -> (handler, server) -> {
        for (Configure callback : callbacks) {
            callback.onSendConfiguration(handler, server);
        }
    });
    @FunctionalInterface
    public interface Configure {
        void onSendConfiguration(ServerConfigurationPacketListenerImpl handler, MinecraftServer server);
    }
}
