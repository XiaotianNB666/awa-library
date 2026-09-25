package cn.gcte.awalib.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static cn.gcte.awalib.AWALibrary.MOD_ID;

public record AwAHandshakePayload(String modVersion) implements CustomPacketPayload {

    public static final Type<AwAHandshakePayload> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(MOD_ID, "shake_hand")
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, AwAHandshakePayload> CODEC =
            StreamCodec.of(
                    AwAHandshakePayload::write,
                    AwAHandshakePayload::read
            );

    private static void write(RegistryFriendlyByteBuf buf, AwAHandshakePayload data) {
        buf.writeUtf(data.modVersion(), 255);
    }

    private static AwAHandshakePayload read(RegistryFriendlyByteBuf buf) {
        return new AwAHandshakePayload(buf.readUtf(255));
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
