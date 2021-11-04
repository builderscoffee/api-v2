package eu.builderscoffee.api.common.redisson.packets.types.playpen.actions;

import eu.builderscoffee.api.common.redisson.packets.types.ActionPacket;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeprovisionServerPacket extends ActionPacket {

    private String serverToStop;
}
