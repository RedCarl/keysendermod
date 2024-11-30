package co.ooci.keysender.keysendermod;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

@Mod("keysendermod")
public class Keysendermod {

    public static final String PROTOCOL_VERSION = "1.0";
    private static final int IDX = 1;
    private static final ResourceLocation CHANNEL = new ResourceLocation("ooci:key_channel");
    public SimpleChannel channel;

    public Keysendermod() {
        channel = NetworkRegistry.newSimpleChannel(
                CHANNEL,
                () -> PROTOCOL_VERSION,
                (s) -> true,
                (s) -> true
        );
        registerMessages();
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void sendPacketToServer(String str) {
        channel.sendToServer(new MessageFromClient(str));
    }

    public void registerMessages() {
        channel.registerMessage(
                IDX,
                MessageFromClient.class,
                MessageFromClient::encode,
                MessageFromClient::decode,
                (message, context) -> {
                    // 在这里处理收到的消息
                    System.out.println("收到来自 Bukkit 服务端的消息: " + message);
                });
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Minecraft.getInstance().level != null && Minecraft.getInstance().player != null) {
            sendPacketToServer("K" + event.getKey());
        }
    }

}
