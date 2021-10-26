package eu.builderscoffee.api.common.redisson.packets;

import com.fasterxml.uuid.Generators;
import eu.builderscoffee.api.common.redisson.Redis;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * Common base packet
 */
@Setter
@Getter
@Accessors(chain = true)
public abstract class Packet implements Serializable {

    @Setter(AccessLevel.NONE)
    protected String serverName = Redis.getServerName();
    protected String packetId = Generators.timeBasedGenerator().generate().toString();
    protected String targetServerName;
    @Setter(AccessLevel.NONE)
    private Date creationDate = new Date();
}
