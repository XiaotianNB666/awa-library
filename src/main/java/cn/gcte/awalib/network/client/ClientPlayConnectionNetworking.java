package cn.gcte.awalib.network.client;

import cn.gcte.awalib.event.Event;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.game.*;

@Environment(EnvType.CLIENT)
public class ClientPlayConnectionNetworking {
    public static Event<OnTick> TICK = Event.Factory.createArrayBacked(OnTick.class, callbacks -> (handler, server) -> {
        for (OnTick callback : callbacks) {
            callback.onTick(handler, server);
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

    public static Event<Login> LOGIN = Event.Factory.createArrayBacked(Login.class, callbacks -> (handler, packet, client) -> {
        for (Login callback : callbacks) {
            callback.onLogin(handler, packet, client);
        }
    });

    public static final Event<SystemChat> SYSTEM_CHAT = Event.Factory.createArrayBacked(SystemChat.class, callbacks -> (handler, payload, client) -> {
        for (SystemChat callback : callbacks) {
            callback.onSystemChat(handler, payload, client);
        }
    });

    public static final Event<PlayerChat> PLAYER_CHAT = Event.Factory.createArrayBacked(PlayerChat.class, callbacks -> (handler, payload, client) -> {
        for (PlayerChat callback : callbacks) {
            callback.onPlayerChat(handler, payload, client);
        }
    });

    public static final Event<Commands> COMMANDS = Event.Factory.createArrayBacked(Commands.class, callbacks -> (handler, payload, client) -> {
        for (Commands callback : callbacks) {
            callback.onCommands(handler, payload, client);
        }
    });

    public static final Event<ServerData> SERVER_DATA = Event.Factory.createArrayBacked(ServerData.class, callbacks -> (handler, payload, client) -> {
        for (ServerData callback : callbacks) {
            callback.onServerData(handler, payload, client);
        }
    });

    @SuppressWarnings({"unchecked", "unused"})
    public static <T extends CustomPacketPayload> void registerGlobalReceiver(CustomPacketPayload.Type<T> packetType, HandlePayload<T> callback) {
        HANDLE_CUSTOM_PAYLOAD.register((handler, payload, client) -> {
            if (payload.type() == packetType) {
                callback.onHandlePayload(handler, (T) payload, client);
                return true;
            }
            return false;
        });
    }

    @FunctionalInterface
    public interface OnTick {
        void onTick(ClientPacketListener handler, Minecraft client);
    }

    @FunctionalInterface
    public interface HandleCustomPayload {
        boolean onHandleCustomPayload(ClientPacketListener handler, CustomPacketPayload payload, Minecraft client);
    }

    @FunctionalInterface
    public interface HandlePayload<T extends CustomPacketPayload> {
        void onHandlePayload(ClientPacketListener handler, T payload, Minecraft client);
    }

    @FunctionalInterface
    public interface Login {
        void onLogin(ClientPacketListener handler, ClientboundLoginPacket packet, Minecraft client);
    }

    @FunctionalInterface
    public interface SystemChat {
        void onSystemChat(ClientPacketListener handler, ClientboundSystemChatPacket packet, Minecraft client);
    }

    @FunctionalInterface
    public interface PlayerChat {
        void onPlayerChat(ClientPacketListener handler, ClientboundPlayerChatPacket packet, Minecraft client);
    }

    @FunctionalInterface
    public interface Commands {
        void onCommands(ClientPacketListener handler, ClientboundCommandsPacket packet, Minecraft client);
    }

    @FunctionalInterface
    public interface ServerData {
        void onServerData(ClientPacketListener handler, ClientboundServerDataPacket packet, Minecraft client);
    }

}
