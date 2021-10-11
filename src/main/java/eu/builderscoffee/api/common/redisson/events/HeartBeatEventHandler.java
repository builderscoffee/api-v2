package eu.builderscoffee.api.common.redisson.events;

import eu.builderscoffee.api.common.redisson.Redis;
import eu.builderscoffee.api.common.redisson.serverinfos.Server;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.val;
import org.redisson.api.RSortedSet;

import java.util.ArrayList;

@UtilityClass
public final class HeartBeatEventHandler {

    private static ArrayList<HeartBeatListener> listeners = new ArrayList<>();

    public static void sendHeartBeatResponse(@NonNull Server server) {
        val event = new HeartBeatEvent(server);
        listeners.forEach(listener -> listener.onSendHeartBeat(event));

        if (event.isCanceled()) return;

        final RSortedSet<Server> servers = Redis.getRedissonClient().getSortedSet("servers");
        if(servers != null) {
            servers.add(event.getServer());
        }
    }

    public static boolean addListener(@NonNull HeartBeatListener heartBeatListener) {
        return listeners.add(heartBeatListener);
    }

    public static boolean removeListener(@NonNull HeartBeatListener heartBeatListener) {
        return listeners.contains(heartBeatListener) ? listeners.remove(heartBeatListener) : false;
    }
}
