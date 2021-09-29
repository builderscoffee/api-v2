package eu.builderscoffee.api.common.redisson.packets.types.redisson.actions;

import eu.builderscoffee.api.common.redisson.packets.types.redisson.RedissonActionPacket;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
public class StartServerPacket extends RedissonActionPacket {

    private String newServerName;
    private String newServerPacketId;
    private Map<String, String> newServerProperties = new HashMap<>();
}
