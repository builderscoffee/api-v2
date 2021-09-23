package eu.builderscoffee.api.common.redisson.listeners;

public interface PubSubListener {

    void onMessage(String json);

}