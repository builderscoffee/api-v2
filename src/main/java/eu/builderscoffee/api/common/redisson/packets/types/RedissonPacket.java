package eu.builderscoffee.api.common.redisson.packets.types;

import eu.builderscoffee.api.common.redisson.packets.Packet;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public abstract class RedissonPacket extends Packet {

    protected String serverName = "Undefined";
    protected ServerType serverType = ServerType.NONE;
    protected String packetId = UUID.randomUUID().toString();
    @Setter(AccessLevel.NONE)
    private Date creationDate = new Date();

    public enum ServerType {
        NONE,
        PROXY,
        BUKKIT
    }
}
