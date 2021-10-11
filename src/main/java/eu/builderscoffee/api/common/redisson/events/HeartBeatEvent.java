package eu.builderscoffee.api.common.redisson.events;

import eu.builderscoffee.api.common.redisson.serverinfos.Server;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public final class HeartBeatEvent {

    @NonNull private Server server;
    private boolean canceled = false;

    public HeartBeatEvent(@NonNull Server server) {
        this.server = server;
    }
}
