package eu.builderscoffee.api.common.redisson.listeners;

public interface PubSubListener {

    /***
     * Lors ce qu'un message est envoyé sur un topic, cette fonction se déclanchement
     * @param json - Message sous json
     */
    void onMessage(String json);

}