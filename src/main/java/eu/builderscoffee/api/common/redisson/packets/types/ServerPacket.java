package eu.builderscoffee.api.common.redisson.packets.types;

import eu.builderscoffee.api.common.redisson.packets.Packet;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public abstract class ServerPacket extends Packet {

    protected String serverId;
    protected String serverName;
    protected String hostAddress;
    protected int hostPort;
    protected int playerCount;
    protected int playerMaximum;

}
