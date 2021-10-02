package eu.builderscoffee.api.common.redisson.packets.types.playpen.actions;

import eu.builderscoffee.api.common.redisson.packets.types.ActionPacket;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class DeprovisionServerPacket extends ActionPacket {

    protected String targetServerName;
}
