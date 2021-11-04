package eu.builderscoffee.api.common.redisson.packets.types.playpen.actions;

import eu.builderscoffee.api.common.redisson.packets.types.ActionPacket;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FreezeServerPacket extends ActionPacket {

    private String serverToFreeze;
}
