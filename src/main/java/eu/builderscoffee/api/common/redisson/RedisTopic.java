package eu.builderscoffee.api.common.redisson;

import lombok.Getter;

/**
 * This class is used to create/store channels in redisson
 */
@Getter
public final class RedisTopic {

    public static RedisTopic LOGS = new RedisTopic("logs", "Logs des messages test");
    public static RedisTopic PLAYPEN = new RedisTopic("playpen", "Actions playpen");
    public static RedisTopic HEARTBEATS = new RedisTopic("heartbeats", "heartbeats");
    public static RedisTopic BUNGEECORD = new RedisTopic("bungeecord", "bungeecord");
    private static int Amount = 0;
    private int Id;
    private String name, description;

    /**
     * @param name Channel name
     * @param description Channel description
     */
    public RedisTopic(String name, String description) {
        this.Id = Amount++;
        this.name = name;
        this.description = description;
    }
}
