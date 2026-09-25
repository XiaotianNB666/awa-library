package cn.gcte.awalib.network.events.client;

import cn.gcte.awalib.event.Event;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientConfigurationPacketListenerImpl;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class ClientConfigurationConnectionEvents {
    public static Event<Complete> COMPLETE = Event.Factory.createArrayBacked(Complete.class, callbacks -> (handler, server) -> {
        for (Complete callback : callbacks) {
            callback.onConfigurationComplete(handler, server);
        }
    });

    public static Event<HandleCustomPayload> HANDLE_CUSTOM_PAYLOAD = Event.Factory.createArrayBacked(HandleCustomPayload.class, callbacks -> (handler, payload, client) -> {
        boolean handled = false;
        for (HandleCustomPayload callback : callbacks) {
            if (callback.onHandleCustomPayload(handler, payload, client)) {
                handled = true;
            }
        }
        return handled;
    });

    @FunctionalInterface
    public interface Complete {
        void onConfigurationComplete(ClientConfigurationPacketListenerImpl handler, Minecraft client);
    }

    @FunctionalInterface
    public interface HandleCustomPayload {
        boolean onHandleCustomPayload(ClientConfigurationPacketListenerImpl handler, CustomPacketPayload payload, Minecraft client);
    }

}
