package eu.builderscoffee.api.common.redisson.events;

import eu.builderscoffee.api.common.redisson.serverinfos.ServerInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeartBeatEvent {

    private ServerInfo serverInfo;
    private boolean canceled = false;

    public HeartBeatEvent(ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
    }
}
