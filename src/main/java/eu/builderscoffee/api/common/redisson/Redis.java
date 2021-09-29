package eu.builderscoffee.api.common.redisson;

import eu.builderscoffee.api.common.redisson.listeners.PubSubListener;
import eu.builderscoffee.api.common.redisson.packets.Packet;
import lombok.NonNull;
import lombok.val;
import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;

import java.util.HashSet;

public class Redis {

    private static final HashSet<RedisTopic> topics = new HashSet<>();
    public static RedissonClient redissonClient;

    /***
     * Initialiser redisson pour la connexion
     * @param credentials - Identifiants au network
     * @param threadNumber - Nombres de thread
     * @param nettyThreadsNumber - Numéro du thread netty
     */
    public static void Initialize(@NonNull RedisCredentials credentials, int threadNumber, int nettyThreadsNumber){
        val config = new Config()
                .setCodec(new JsonJacksonCodec())
                .setThreads(threadNumber)
                .setNettyThreads(nettyThreadsNumber);
        config.useSingleServer()
                .setAddress(credentials.toRedisUrl())
                .setPassword(credentials.getPassword())
                .setClientName(credentials.getClientName());

        redissonClient = Redisson.create(config);
    }

    /***
     * Récupère le topic du client redisson
     * @param topic - Channel de message
     * @return Retourne le topic
     */
    /*public static RTopic getTopic(@NonNull RedisTopic topic){
        return redissonClient.getTopic(topic.getName());
    }*/

    /***
     * Ferme la connexion aux client
     */
    public static void close() {
        redissonClient.shutdown();
    }

    /***
     * S'abonner à un topic
     * @param topic - Channel de message
     * @param listener - Message listener
     */
    public static void subscribe(@NonNull RedisTopic topic, @NonNull PubSubListener listener) {
        RTopic rTopic = redissonClient.getTopic(topic.getName());
        rTopic.addListener(String.class, (channel, msg) -> listener.onMessage(msg));
        topics.add(topic);
    }

    /***
     * Se désabonner d'un topic
     * @param topic - Channel de message
     */
    public static void unsubscribe(@NonNull RedisTopic topic) {
        RTopic rtopic = redissonClient.getTopic(topic.getName());
        rtopic.removeAllListeners();
    }

    /***
     * Se désabonner de tout les topics
     */
    public void unsubscribeAll() {
        topics.forEach(Redis::unsubscribe);
    }

    public static void publish(RedisTopic topic, Packet packet){
        redissonClient.getTopic(topic.getName()).publish(packet.serialize());
    }
}
