package eu.builderscoffee.api.common.redisson;

import lombok.Data;
import lombok.Getter;

@Getter
public class RedisTopic {

    private static int Amount = 0;

    public static RedisTopic LOGS = new RedisTopic("logs","Logs des messages test");

    private int Id;
    private String name, description;

    public RedisTopic(String name, String description) {
        this.Id = Amount++;
        this.name = name;
        this.description = description;
    }
}
