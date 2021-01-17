package eu.builderscoffee.api.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

@UtilityClass
public class Plugins {

    /**
     * Enregistre tout les listeners indiqués auprès de Bukkit
     *
     * @param plugin Plugin
     * @param listeners Listeners
     */
    public void registerListeners(Plugin plugin, Listener... listeners) {
        for(Listener listener: listeners) {
            plugin.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }

}
