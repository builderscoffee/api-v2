package eu.builderscoffee.api.common.redisson.events;

public interface HeartBeatListener {
    void onSendHeartBeat(HeartBeatEvent event);
}
