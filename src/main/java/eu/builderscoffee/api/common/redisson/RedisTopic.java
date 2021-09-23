package eu.builderscoffee.api.common.redisson;

import lombok.Data;

@Data
public class RedisTopic {

    private static int Amount = 0;

    public static RedisTopic LOGS = new RedisTopic("logs","Logs des messages test");

    public int Id;
    public String name, description;

    public RedisTopic(String name, String description) {
        this.Id = Amount++;
        this.name = name;
        this.description = description;
    }
}
