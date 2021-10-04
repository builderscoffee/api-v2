package eu.builderscoffee.api.common.redisson.events;

import eu.builderscoffee.api.common.redisson.Redis;
import eu.builderscoffee.api.common.redisson.serverinfos.ServerInfo;
import lombok.experimental.UtilityClass;
import lombok.val;
import org.redisson.api.RList;

import java.util.ArrayList;
import java.util.stream.Collectors;

@UtilityClass
public class HearBeatEventHandler {

    private static ArrayList<HeartBeatListener> listeners = new ArrayList<>();

    public static void sendHeartBeatResponse(ServerInfo serverInfo) {
        val event = new HeartBeatEvent(serverInfo);
        listeners.forEach(listener -> listener.onSendHeartBeat(event));

        if (event.isCanceled()) return;

        val servers = Redis.redissonClient.getList("servers");
        val objList = servers.stream()
                .filter(o -> o instanceof ServerInfo
                    && ((ServerInfo)o).getHostName().equals(event.getServerInfo().getHostName())
                    && ((ServerInfo)o).getHostPort() == event.getServerInfo().getHostPort())
                .collect(Collectors.toList());
        if (!objList.isEmpty()) {
            objList.forEach(servers::remove);
        }
        servers.add(event.getServerInfo());
    }

    public static boolean addListener(HeartBeatListener heartBeatListener) {
        return listeners.add(heartBeatListener);
    }

    public static boolean removeListener(HeartBeatListener heartBeatListener) {
        return listeners.contains(heartBeatListener) ? listeners.remove(heartBeatListener) : false;
    }
}
