package eu.builderscoffee.api.common.redisson.packets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class Packet {

    /***
     * Cree un gson qui enregistre le type de class pour ne rien perde lors de la deserialisation
     * @return
     */
    protected static Gson getGson(Class<? extends Packet> clazz) {
        return new GsonBuilder().registerTypeAdapter(clazz, new PacketAdapter()).create();
    }

    /***
     * Désérialiser un packet en Gson
     * @param json - Message à désérialiser
     * @param clazz - Class du packet
     * @param <T> - L'object dépendent de la classe du packet donnée
     * @return - Retourne l'object du packet
     */
    public static <T extends Packet> T deserialize(String json, Class<T> clazz) {
        return getGson(clazz).fromJson(json, clazz);
    }

    /***
     * Désérialiser un packet en Gson
     * @param json - Message à désérialiser
     * @return - Retourne l'object du packet
     */
    public static Packet deserialize(String json) {
        return getGson(Packet.class).fromJson(json, Packet.class);
    }

    /***
     * Sérialiser un packet en Gson
     * @return - Retourne le json
     */
    public String serialize() {
        return getGson(getClass()).toJson(this);
    }

    @Override
    public String toString() {
        return serialize();
    }
}
