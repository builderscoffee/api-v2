package eu.builderscoffee.api.common.redisson.packets.types.redisson.actions;

import eu.builderscoffee.api.common.redisson.packets.types.redisson.RedissonActionPacket;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class StopServerPacket extends RedissonActionPacket {

    protected String newServerName;
}
