package eu.builderscoffee.api.common.redisson;

import eu.builderscoffee.api.common.redisson.listeners.PacketListener;
import eu.builderscoffee.api.common.redisson.listeners.ProcessPacket;
import eu.builderscoffee.api.common.redisson.listeners.ResponseListener;
import eu.builderscoffee.api.common.redisson.packets.Packet;
import eu.builderscoffee.api.common.redisson.packets.types.RequestPacket;
import lombok.*;
import lombok.experimental.UtilityClass;
import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

@UtilityClass
public class Redis {


    private static final HashSet<RedisTopic> topics = new HashSet<>();
    private static final HashMap<RedisTopic, ResponseListener> topicsWithResponseListener = new HashMap<>();
    @Getter private static RedissonClient redissonClient;
    @Getter @Setter(AccessLevel.PRIVATE) private static String serverName;

    /**
     * Initialize redisson for connection
     * @param credentials Redisson credentials
     * @param threadNumber Number of threads
     * @param nettyThreadsNumber Number of netty threads
     */
    public static void Initialize(@NonNull String serverName, @NonNull RedisCredentials credentials, int threadNumber, int nettyThreadsNumber) {
        setServerName(serverName);

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
     * Ferme la connexion aux client
     */
    public static void close() {
        redissonClient.shutdown();
    }

    /**
     * Subscribe to a {@link RedisTopic}
     * @param topic - Channel de message
     * @param listener - Message listener
     */
    public static void subscribe(@NonNull RedisTopic topic, @NonNull PacketListener listener) {
        // Get the ropic
        RTopic rTopic = redissonClient.getTopic(topic.getName());
        // Add a listener of the topic
        rTopic.addListener(String.class, (channel, msg) -> {
            // Deserialize the packet
            val packet = Packet.deserialize(msg);

            // Check if the packet is for this server
            if(Objects.nonNull(packet.getTargetServerName()) && !packet.getTargetServerName().equals(serverName)) return;

            // Loop all methods of the listener
            for (Method method : listener.getClass().getDeclaredMethods()) {
                // Check if function corresponds to the packet
                if(method.isAnnotationPresent(ProcessPacket.class)
                        && method.getParameterTypes().length == 1
                        && method.getParameterTypes()[0].isAssignableFrom(packet.getClass())){
                    try {
                        // Invoke the method
                        method.invoke(listener, packet);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        topics.add(topic);
    }

    /**
     * Unsubscribe from a {@link RedisTopic}
     * @param topic Channel
     */
    public static void unsubscribe(@NonNull RedisTopic topic) {
        RTopic rtopic = redissonClient.getTopic(topic.getName());
        rtopic.removeAllListeners();
        if (topicsWithResponseListener.containsKey(topic)) topicsWithResponseListener.remove(topic);
    }

    /**
     * Publish packet to a {@link RedisTopic}
     * @param topic Channel where send
     * @param packet Packet to send
     */
    public static void publish(RedisTopic topic, Packet packet) {
        // Check if the packet needs to have a response
        if (packet instanceof RequestPacket) {
            val rPacket = (RequestPacket) packet;
            // Check if a listener already exist for responses
            if (!topicsWithResponseListener.keySet().contains(topic)) {
                // Create the listener
                val listener = new ResponseListener();
                // Add the listener into the map
                topicsWithResponseListener.put(topic, listener);
                // Add the listener to redisson
                subscribe(topic, listener);
            }
            // Add the packet to the map where is waiting for a response
            topicsWithResponseListener.get(topic).requestedPackets.put(rPacket.getPacketId(), rPacket);
        }
        // Send the packet
        redissonClient.getTopic(topic.getName()).publish(packet.serialize());
    }

    /***
     * Unsubscribe to all channels
     */
    public void unsubscribeAll() {
        topics.forEach(Redis::unsubscribe);
    }
}
