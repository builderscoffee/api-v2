package eu.builderscoffee.api.common.redisson.packets.types;

import eu.builderscoffee.api.common.redisson.packets.Packet;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public abstract class RedissonPacket extends Packet {

    private String serverName;
    private ServerType serverType;

    public enum ServerType {
        NONE,
        PROXY,
        BUKKIT
    }
}
