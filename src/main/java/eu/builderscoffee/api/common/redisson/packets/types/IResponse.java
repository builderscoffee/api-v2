package eu.builderscoffee.api.common.redisson.packets.types;

@FunctionalInterface
public interface IResponse<T extends ResponsePacket> {
    void invoke(T response);
}
