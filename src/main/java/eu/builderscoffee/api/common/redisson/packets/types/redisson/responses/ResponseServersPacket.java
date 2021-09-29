package eu.builderscoffee.api.common.redisson.packets.types.redisson.responses;


import eu.builderscoffee.api.common.redisson.packets.types.RedissonPacket;
import eu.builderscoffee.api.common.redisson.packets.types.redisson.RedissonResponsePacket;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
public class ResponseServersPacket extends RedissonResponsePacket {

    private ArrayList<String> servers = new ArrayList<>();
}
