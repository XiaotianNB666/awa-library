package cn.gcte.awalib.mixin.client.network;


import cn.gcte.awalib.network.client.ClientPlayConnectionNetworking;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.CommonListenerCookie;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.game.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public abstract class ClientPlayPacketListenerMixin extends ClientCommonPacketListenerImpl {
    private ClientPlayPacketListenerMixin(Minecraft minecraft, Connection connection, CommonListenerCookie commonListenerCookie) {
        super(minecraft, connection, commonListenerCookie);
    }

    @Inject(method = "handleLogin", at = @At(value = "HEAD"))
    public void handleLogin(ClientboundLoginPacket clientboundLoginPacket, CallbackInfo ci) {
        ClientPlayConnectionNetworking.LOGIN.invoker().onLogin((ClientPacketListener) (Object) (this), clientboundLoginPacket, this.minecraft);
    }

    @Inject(method = "handleSystemChat", at = @At("HEAD"))
    public void handleSystemChat(ClientboundSystemChatPacket packet, CallbackInfo ci) {
        ClientPlayConnectionNetworking.SYSTEM_CHAT.invoker().onSystemChat((ClientPacketListener) (Object) (this), packet, this.minecraft);
    }

    @Inject(method = "handlePlayerChat", at = @At("HEAD"))
    public void handlePlayerChat(ClientboundPlayerChatPacket packet, CallbackInfo ci) {
        ClientPlayConnectionNetworking.PLAYER_CHAT.invoker().onPlayerChat((ClientPacketListener) (Object) (this), packet, this.minecraft);
    }

    @Inject(method = "handleCommands", at = @At("HEAD"))
    public void handlePlayerChat(ClientboundCommandsPacket packet, CallbackInfo ci) {
        ClientPlayConnectionNetworking.COMMANDS.invoker().onCommands((ClientPacketListener) (Object) (this), packet, this.minecraft);
    }

    @Inject(method = "handleServerData", at = @At("HEAD"))
    public void handleServerData(ClientboundServerDataPacket packet, CallbackInfo ci) {
        ClientPlayConnectionNetworking.SERVER_DATA.invoker().onServerData((ClientPacketListener) (Object) (this), packet, this.minecraft);
    }

    @Inject(method = "tick", at = @At(value = "RETURN"))
    public void tick(CallbackInfo ci) {
        ClientPlayConnectionNetworking.TICK.invoker().onTick((ClientPacketListener) (Object) (this), this.minecraft);
    }

    @WrapMethod(method = "handleUnknownCustomPayload")
    private void handleUnknownCustomPayload(CustomPacketPayload customPacketPayload, Operation<Void> original) {
        if (!ClientPlayConnectionNetworking.HANDLE_CUSTOM_PAYLOAD.invoker().onHandleCustomPayload((ClientPacketListener) (Object) (this), customPacketPayload, this.minecraft)) {
            original.call(customPacketPayload);
        }
    }

}
