package eu.builderscoffee.api.common.redisson.packets.types.redisson.responses;

import eu.builderscoffee.api.common.redisson.packets.types.redisson.RedissonRequestPacket;
import eu.builderscoffee.api.common.redisson.packets.types.redisson.RedissonResponsePacket;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;

@Getter
@Setter
@Accessors(chain = true)
public class ResponsePackagesPacket extends RedissonResponsePacket {

    private ArrayList<String> packages = new ArrayList<>();

    public ResponsePackagesPacket(String packetId) {
        super(packetId);
    }

    public ResponsePackagesPacket(RedissonRequestPacket requestPacket) {
        super(requestPacket);
    }
}
