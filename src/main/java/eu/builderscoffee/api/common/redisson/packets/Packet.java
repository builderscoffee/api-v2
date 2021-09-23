package eu.builderscoffee.api.common.redisson.packets;

import com.google.gson.Gson;

public abstract class Packet {

    /***
     * Sérialiser un packet en Gson
     * @return - Retourne le json
     */
    public String serialize() {
        return new Gson().toJson(this);
    }

    /***
     * Désérialiser un packet en Gson
     * @param json - Message à désérialiser
     * @param clazz - Class du packet
     * @param <T> - L'object dépendent de la classe du packet donnée
     * @return - Retourne l'object du packet
     */
    public static <T> T deserialize(String json, Class<T> clazz) {
        return new Gson().fromJson(json, clazz);
    }
}
