package eu.builderscoffee.api;

import eu.builderscoffee.api.bungeecord.BungeeChannelApi;
import eu.builderscoffee.api.gui.InventoryManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    @Getter
    private static InventoryManager invManager;

    @Override
    public void onEnable() {
        instance = this;

        invManager = new InventoryManager(this);
        invManager.init();

        BungeeChannelApi api = BungeeChannelApi.of(this);
    }

    @Override
    public void onDisable() {

    }
}
