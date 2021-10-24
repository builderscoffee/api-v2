package eu.builderscoffee.api.common.redisson.infos;

import lombok.Getter;

import java.util.*;

@Getter
public class Tournemant {

    private Map<String, Map<String, Object>> servers = new TreeMap<>();
}
