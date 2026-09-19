package cn.gcte.awalib.mixin.client.network;


import cn.gcte.awalib.network.events.client.ClientConfigurationConnectionEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.client.multiplayer.ClientConfigurationPacketListenerImpl;
import net.minecraft.client.multiplayer.CommonListenerCookie;
import net.minecraft.network.Connection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConfigurationPacketListenerImpl.class)
public abstract class ClientConfigurationPacketListenerMixin extends ClientCommonPacketListenerImpl {


    private ClientConfigurationPacketListenerMixin(Minecraft minecraft, Connection connection, CommonListenerCookie commonListenerCookie) {
        super(minecraft, connection, commonListenerCookie);
    }

    @Inject(method = "handleConfigurationFinished", at = @At("HEAD"))
    public void handleConfigurationFinished(CallbackInfo ci) {
        ClientConfigurationConnectionEvents.COMPLETE.invoker().onConfigurationComplete(
                (ClientConfigurationPacketListenerImpl)(Object)(this),
                this.minecraft
        );
    }
}
