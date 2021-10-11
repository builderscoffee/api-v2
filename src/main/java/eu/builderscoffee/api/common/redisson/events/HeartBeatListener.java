package eu.builderscoffee.api.common.redisson.events;

import lombok.NonNull;

public interface HeartBeatListener {
    void onSendHeartBeat(@NonNull HeartBeatEvent event);
}
