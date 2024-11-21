package co.ooci.keysender.keysendermod;

import net.minecraft.network.PacketBuffer;

public class MessageFromClient {
    private final String message;

    public MessageFromClient(String message) {
        this.message = message;
    }

    public static void encode(MessageFromClient msg, PacketBuffer buf) {
        buf.writeUtf(msg.message);
    }

    public static MessageFromClient decode(PacketBuffer buf) {
        return new MessageFromClient(buf.readUtf());
    }

    public String getMessage() {
        return message;
    }


}