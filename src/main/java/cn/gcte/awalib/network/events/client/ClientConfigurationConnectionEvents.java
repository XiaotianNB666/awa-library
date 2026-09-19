package cn.gcte.awalib.network.events.client;

import cn.gcte.awalib.event.Event;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientConfigurationPacketListenerImpl;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl;

public class ClientConfigurationConnectionEvents {
    public static Event<Complete> COMPLETE = Event.Factory.createArrayBacked(Complete.class, callbacks -> (handler, server) -> {
        for (Complete callback : callbacks) {
            callback.onConfigurationComplete(handler, server);
        }
    });
    @FunctionalInterface
    public interface Complete {
        void onConfigurationComplete(ClientConfigurationPacketListenerImpl handler, Minecraft client);
    }
}
