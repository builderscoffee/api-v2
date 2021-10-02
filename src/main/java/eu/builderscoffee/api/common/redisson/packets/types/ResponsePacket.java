package eu.builderscoffee.api.common.redisson.packets.types;

import eu.builderscoffee.api.common.redisson.packets.Packet;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public abstract class ResponsePacket extends Packet {

    protected String destinationServerName;

    public ResponsePacket(String packetId) {
        this.packetId = packetId;
    }

    public ResponsePacket(RequestPacket requestPacket) {
        this.packetId = requestPacket.getPacketId();
    }
}
