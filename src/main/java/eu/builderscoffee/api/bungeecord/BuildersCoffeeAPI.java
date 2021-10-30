package eu.builderscoffee.api.bungeecord;

import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

public class BuildersCoffeeAPI extends Plugin {

    @Getter
    private static BuildersCoffeeAPI instance;

    @Override
    public void onEnable() {
        instance = this;

        // Nothing to do here
    }

    @Override
    public void onDisable() {
        // Nothing to do here
    }

}
