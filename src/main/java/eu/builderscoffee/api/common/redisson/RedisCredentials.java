package eu.builderscoffee.api.common.redisson;

import eu.builderscoffee.api.common.configuration.annotation.Configuration;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RedisCredentials {

    private String clientName,ip,password;
    private int port;

    public boolean isAuth() {
        return password !=null;
    }

    public String toRedisUrl() {
        return "redis://" + ip + ":" + port;
    }
}
