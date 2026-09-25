package cn.gcte.awalib.mixin.server.network;


import cn.gcte.awalib.network.events.server.ServerConfigurationConnectionNetworking;
import net.minecraft.network.Connection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.network.ServerCommonPacketListenerImpl;
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerConfigurationPacketListenerImpl.class)
public abstract class ServerConfigurationPacketListenerMixin extends ServerCommonPacketListenerImpl {

    private ServerConfigurationPacketListenerMixin(MinecraftServer minecraftServer, Connection connection, CommonListenerCookie commonListenerCookie) {
        super(minecraftServer, connection, commonListenerCookie);
    }

    @Inject(method = "startConfiguration", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;serverLinks()Lnet/minecraft/server/ServerLinks;"))
    public void startConfiguration(CallbackInfo ci) {
        ServerConfigurationConnectionNetworking.CONFIGURE.invoker().onSendConfiguration((ServerConfigurationPacketListenerImpl)(Object)(this), this.server);
    }

    @Inject(method = "tick", at = @At(value = "RETURN"))
    public void tick(CallbackInfo ci) {
        ServerConfigurationConnectionNetworking.TICK.invoker().onTick((ServerConfigurationPacketListenerImpl)(Object)(this), this.server);
    }

}
