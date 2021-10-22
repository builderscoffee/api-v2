package eu.builderscoffee.api.common.events.events;

import eu.builderscoffee.api.common.events.Event;
import eu.builderscoffee.api.common.redisson.infos.Server;
import lombok.Getter;
import lombok.NonNull;

@Getter
public final class HeartBeatEvent extends Event {

    @NonNull private Server server;

    public HeartBeatEvent(@NonNull Server server) {
        this.server = server;
    }
}
