package eu.builderscoffee.api.common.redisson.packets.types.redisson.responses;

import eu.builderscoffee.api.common.redisson.packets.types.redisson.RedissonRequestPacket;
import eu.builderscoffee.api.common.redisson.packets.types.redisson.RedissonResponsePacket;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ResponseServerInfoPacket extends RedissonResponsePacket {

    // TODO Add more server information
    protected ServerStatus serverStatus = ServerStatus.UNKNOWN;

    public ResponseServerInfoPacket(String packetId) {
        super(packetId);
    }

    public ResponseServerInfoPacket(RedissonRequestPacket requestPacket) {
        super(requestPacket);
    }

    private enum ServerStatus {
        UNKNOWN,
        RUNNING,
        FREEZED
    }
}
