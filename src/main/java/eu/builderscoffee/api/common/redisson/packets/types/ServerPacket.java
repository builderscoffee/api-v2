package eu.builderscoffee.api.common.redisson.packets.types;

import eu.builderscoffee.api.common.redisson.packets.Packet;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public abstract class ServerPacket extends Packet {

    private String serverId;
    private String serverName;
    private String hostAddress;
    private int hostPort;
    private int playerCount;
    private int playerMaximum;

}
