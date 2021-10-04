package eu.builderscoffee.api.common.redisson.serverinfos;

import eu.builderscoffee.api.common.redisson.Redis;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.val;
import org.redisson.api.RList;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Accessors(chain = true)
public class ServerInfo {

    protected String hostName;
    protected String hostAddress;
    protected int hostPort;
    protected int playerCount;
    protected int playerMaximum;
    protected Date lastHeartbeat = new Date();
    protected ServerType serverType;
    protected ServerStartingMethod startingMethod;

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

    @Override
    public String toString() {
        return hostName;
    }

    @Override
    public boolean equals(Object o) {
        System.out.println("Equals");
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerInfo that = (ServerInfo) o;
        System.out.println("Equals: " + (hostPort == that.hostPort && Objects.equals(hostAddress, that.hostAddress)));
        return hostPort == that.hostPort && Objects.equals(hostAddress, that.hostAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hostAddress, hostPort);
    }
}
