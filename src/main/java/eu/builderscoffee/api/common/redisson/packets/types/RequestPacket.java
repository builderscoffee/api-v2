package eu.builderscoffee.api.common.redisson.packets.types;

import eu.builderscoffee.api.common.redisson.packets.Packet;

public abstract class RequestPacket<T extends ResponsePacket> extends Packet {

    public transient IResponse<T> onResponse;
}
