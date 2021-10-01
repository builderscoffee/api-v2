package eu.builderscoffee.api.common.redisson.packets.types.redisson;

@FunctionalInterface
public interface IResponse {
    void invoke(RedissonResponsePacket responsePacket);
}
