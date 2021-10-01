package eu.builderscoffee.api.common.redisson.packets.types.redisson;

import eu.builderscoffee.api.common.redisson.packets.types.RedissonPacket;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public abstract class RedissonResponsePacket extends RedissonPacket {

    private String destinationServerName;

    public RedissonResponsePacket(String packetId) {
        this.packetId = packetId;
    }

    public RedissonResponsePacket(RedissonRequestPacket requestPacket) {
        this.packetId = requestPacket.getPacketId();
    }
}
