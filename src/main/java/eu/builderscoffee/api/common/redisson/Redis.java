package eu.builderscoffee.api.common.redisson;

import eu.builderscoffee.api.common.redisson.listeners.PubSubListener;
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

    public static void Initialize(@NonNull RedisCredentials credentials, int threadNumber, int nettyThreadsNumber){
        final Config config = new Config();
        config.setCodec(new JsonJacksonCodec());
        config.setThreads(threadNumber);
        config.setNettyThreads(nettyThreadsNumber);
        config.useSingleServer()
                .setAddress(credentials.toRedisUrl())
                .setPassword(credentials.getPassword())
                .setClientName(credentials.getClientName());

        redissonClient = Redisson.create(config);
    }

    /***
     * Récupère le topic du client redisson
     * @param topic -
     * @return Retourne le topic
     */
    public static RTopic getTopic(@NonNull RedisTopic topic){
        return redissonClient.getTopic(topic.name);
    }

    /***
     * Ferme la connexion aux client
     */
    public static void close() {
        redissonClient.shutdown();
    }

    /***
     * S'abonner à un topic
     * @param <T>
     * @param rtopics
     * @param listener
     */
    public static <T> void subscribe(@NonNull RedisTopic rtopics, @NonNull PubSubListener listener) {
        RTopic topic = redissonClient.getTopic(rtopics.name);
        val regId = topic.addListener(String.class, (channel, msg) -> listener.onMessage(msg));
        topics.add(rtopics);
    }

    /***
     * Se désabonner d'un topic
     * @param topics
     */
    public static void unsubscribe(@NonNull RedisTopic topics) {
        RTopic topic = redissonClient.getTopic(topics.name);
        topic.removeAllListeners();
    }

    /***
     * Se désabonner de tout les topics
     */
    public void unsubscribeAll() {
        topics.forEach(topics -> {
            RTopic topic = redissonClient.getTopic(topics.name);
            topic.removeAllListeners();
        });
    }
}
