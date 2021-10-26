package eu.builderscoffee.api.common.redisson.packets.types;

import eu.builderscoffee.api.common.redisson.packets.Packet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ResponsePacket extends Packet {


    protected ResponsePacket() {
    }

    public ResponsePacket(String packetId) {
        this.packetId = packetId;
    }

    public ResponsePacket(RequestPacket requestPacket) {
        this.packetId = requestPacket.getPacketId();
    }
}
