package eu.builderscoffee.api.common.redisson.infos;

import eu.builderscoffee.api.common.redisson.Redis;
import eu.builderscoffee.api.common.redisson.RedisTopic;
import eu.builderscoffee.api.common.redisson.packets.types.playpen.actions.DeprovisionServerPacket;
import eu.builderscoffee.api.common.redisson.packets.types.playpen.actions.FreezeServerPacket;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.val;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class stores a server information after a {@link eu.builderscoffee.api.common.redisson.packets.types.common.HeartBeatPacket}
 * has been send.
 */
@Setter
@Getter
@Accessors(chain = true)
public class Server implements Comparable<Server> {

    private String hostName;
    private String hostAddress;
    private int hostPort;
    private int playerCount;
    private int playerMaximum;
    private Date lastHeartbeat = new Date();
    private ServerType serverType = ServerType.NONE;
    private ServerStatus serverStatus = ServerStatus.NONE;
    private ServerStartingMethod startingMethod = ServerStartingMethod.NONE;
    private Map<String, String> properties = new TreeMap<>();

    public enum ServerStatus{
        NONE,
        RUNNING,
        WAITING_PLAYERS,
        WAITING_INFO,
        PLAYING
    }

    public enum ServerStartingMethod{
        NONE,
        STATIC,
        DYNAMIC
    }

    public enum ServerType{
        NONE,
        SPIGOT,
        BUNGEECORD
    }

    public final void freeze(){
        val packet = new FreezeServerPacket();
        packet.setTargetServerName(getHostName());

        Redis.publish(RedisTopic.PLAYPEN, packet);
    }

    public final void stop(){
        val packet = new DeprovisionServerPacket();
        packet.setTargetServerName(getHostName());

        Redis.publish(RedisTopic.PLAYPEN, packet);
    }

    @Override
    public int compareTo(Server o) {
        return getHostName().compareTo(o.getHostName());
    }

    @Override
    public String toString() {
        return hostName;
    }
}
