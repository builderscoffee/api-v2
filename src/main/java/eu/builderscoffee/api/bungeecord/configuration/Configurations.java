package eu.builderscoffee.api.bungeecord.configuration;

import eu.builderscoffee.api.bukkit.utils.Serializations;
import eu.builderscoffee.api.common.configuration.annotation.Configuration;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Slf4j
@UtilityClass
public class Configurations {

    /**
     * Créer la config avec la structure indiqué dans le dossier du plugin spécifié.
     *
     * @param plugin    Plugin
     * @param structure Structure
     */
    @SneakyThrows
    public <T> T readOrCreateConfiguration(Plugin plugin, Class<T> structure) {
        if (!structure.isAnnotationPresent(Configuration.class))
            throw new RuntimeException("Tried to load a non-annotated configuration");

        var configName = structure.getAnnotation(Configuration.class).value();
        if (configName.isEmpty())
            configName = structure.getSimpleName();
        // On veut du YML
        if(!configName.endsWith(".yml"))
            configName = configName + ".yml";

        val pluginPath = Paths.get("plugins", plugin.getDescription().getName(), configName);
        if(!Files.exists(pluginPath)) {
            Files.createDirectories(pluginPath.getParent());
            Files.createFile(pluginPath);

            val template = getResourceFileAsString(plugin.getClass(), "/" + configName);
            if(template != null) {
                Files.write(pluginPath, template.getBytes(StandardCharsets.UTF_8));
            } else {
                Files.write(
                        pluginPath,
                        Serializations.serialize(structure.newInstance()).getBytes(StandardCharsets.UTF_8)
                );
            }
        }

        return Serializations.deserialize(
                new String(Files.readAllBytes(pluginPath), StandardCharsets.UTF_8),
                structure
        );
    }

    /**
     * Ecrit la configuration avec les valeurs contenue dans l'instance spécifiée.
     *
     * @param plugin Plugin
     * @param configuration Configuration
     * @param <T> Type de la configuration
     */
    @SneakyThrows
    public <T> void writeConfiguration(Plugin plugin, T configuration) {
        Class<?> structure = configuration.getClass();
        if (!structure.isAnnotationPresent(Configuration.class))
            throw new RuntimeException("Tried to load a non-annotated configuration");

        var configName = structure.getAnnotation(Configuration.class).value();
        if (configName.isEmpty())
            configName = structure.getSimpleName();
        // On veut du YML
        if(!configName.endsWith(".yml"))
            configName = configName + ".yml";

        val pluginPath = Paths.get("plugins", plugin.getDescription().getName(), configName);
        Files.write(
                pluginPath,
                Serializations.serialize(configuration).getBytes(StandardCharsets.UTF_8)
        );
    }

    /**
     * Reads given resource file as a string.
     *
     * @param fileName path to the resource file
     * @return the file's contents
     */
    @SneakyThrows
    private String getResourceFileAsString(Class<?> classLoader, String fileName) {
        @Cleanup InputStream is = classLoader.getResourceAsStream(fileName);
        if (is == null) return null;
        @Cleanup InputStreamReader isr    = new InputStreamReader(is);
        @Cleanup BufferedReader    reader = new BufferedReader(isr);
        return reader.lines().collect(Collectors.joining(System.lineSeparator()));
    }


}