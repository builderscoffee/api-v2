package eu.builderscoffee.api.common.configuration;

import eu.builderscoffee.api.bukkit.utils.Serializations;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.val;
import lombok.var;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class Configuration {

    /**
     * Créer la config avec la structure indiqué dans le dossier du plugin spécifié.
     *
     //* @param plugin    Plugin
     //* @param structure Structure
     */
    @SneakyThrows
    public <T> T readOrCreateConfiguration(@NonNull String pluginName, @NonNull Class<T> structure) {
        if (!structure.isAnnotationPresent(eu.builderscoffee.api.common.configuration.annotation.Configuration.class))
            throw new RuntimeException("Tried to load a non-annotated configuration");

        var configName = structure.getAnnotation(eu.builderscoffee.api.common.configuration.annotation.Configuration.class).value();
        if (configName.isEmpty())
            configName = structure.getSimpleName();
        // On veut du YML
        if(!configName.endsWith(".yml"))
            configName = configName + ".yml";

        return readOrCreateConfiguration(pluginName, structure, configName);
    }

    public <X extends Enum<?>, Y> Map<X, Y> readOrCreateConfiguration(@NonNull String pluginName, @NonNull Class<Y> structure, @NonNull Class<X> models){
        Map<X, Y> map = new HashMap<>();

        if (!structure.isAnnotationPresent(eu.builderscoffee.api.common.configuration.annotation.Configuration.class))
            throw new RuntimeException("Tried to load a non-annotated configuration");

        var configName = structure.getAnnotation(eu.builderscoffee.api.common.configuration.annotation.Configuration.class).value();
        if (configName.isEmpty())
            configName = structure.getSimpleName();

        if(configName.endsWith(".yml"))
            configName = configName.replace(".yml", "");

        if(models.getEnumConstants().length == 0)
            throw new RuntimeException("Tried to load a configuration with an empty enum");

        for (X value : models.getEnumConstants()) {
            map.put(value, readOrCreateConfiguration(pluginName + "/" + configName, structure, configName + "_" + value.toString().toLowerCase() + ".yml"));
        }

        return map;
    }

    public <X extends Enum<?>, Y> Y readOrCreateConfiguration(@NonNull String pluginName, @NonNull Class<Y> structure, @NonNull X model){
        if (!structure.isAnnotationPresent(eu.builderscoffee.api.common.configuration.annotation.Configuration.class))
            throw new RuntimeException("Tried to load a non-annotated configuration");

        var configName = structure.getAnnotation(eu.builderscoffee.api.common.configuration.annotation.Configuration.class).value();
        if (configName.isEmpty())
            configName = structure.getSimpleName();

        if(configName.endsWith(".yml"))
            configName = configName.replace(".yml", "");

        return readOrCreateConfiguration(pluginName + "/" + configName, structure, configName + "_" + model.toString().toLowerCase() + ".yml");
    }

    @SneakyThrows
    private <T> T readOrCreateConfiguration(@NonNull String pluginName, @NonNull Class<T> structure, @NonNull String configName){
        val pluginPath = Paths.get("plugins", pluginName, configName);
        if(!Files.exists(pluginPath)) {
            Files.createDirectories(pluginPath.getParent());
            Files.createFile(pluginPath);
            Files.write(
                    pluginPath,
                    Serializations.serialize(structure.newInstance()).getBytes(StandardCharsets.UTF_8)
            );
        }

        return Serializations.deserialize(
                new String(Files.readAllBytes(pluginPath), StandardCharsets.UTF_8),
                structure
        );
    }

    /**
     * Ecrit la configuration avec les valeurs contenue dans l'instance spécifiée.
     *
     //* @param plugin Plugin
     * @param configuration Configuration
     * @param <T> Type de la configuration
     */
    @SneakyThrows
    public <T> void writeConfiguration(@NonNull String pluginName, @NonNull T configuration) {
        val structure = configuration.getClass();
        if (!structure.isAnnotationPresent(eu.builderscoffee.api.common.configuration.annotation.Configuration.class))
            throw new RuntimeException("Tried to load a non-annotated configuration");

        var configName = structure.getAnnotation(eu.builderscoffee.api.common.configuration.annotation.Configuration.class).value();
        if (configName.isEmpty())
            configName = structure.getSimpleName();
        // On veut du YML
        if(!configName.endsWith(".yml"))
            configName = configName + ".yml";

        writeConfiguration(pluginName, configuration, configName);
    }

    @SneakyThrows
    public <X extends Enum<?>, Y> void writeConfiguration(String pluginName, Map<X, Y> map) {
        map.keySet().forEach(value -> writeConfiguration(pluginName, map, value));
    }

    @SneakyThrows
    public <X extends Enum<?>, Y> void writeConfiguration(String pluginName, Map<X, Y> map, X model) {
        val structure = map.get(model).getClass();
        if (!structure.isAnnotationPresent(eu.builderscoffee.api.common.configuration.annotation.Configuration.class))
            throw new RuntimeException("Tried to load a non-annotated configuration");

        var configName = structure.getAnnotation(eu.builderscoffee.api.common.configuration.annotation.Configuration.class).value();
        if (configName.isEmpty())
            configName = structure.getSimpleName();
        // On veut du YML
        if(configName.endsWith(".yml"))
            configName = configName.replace(".yml", "");

        writeConfiguration(pluginName + "/" + configName, map.get(model), configName + "_" + model.toString().toLowerCase() + ".yml");
    }

    @SneakyThrows
    private <T> void writeConfiguration(String pluginName, T configuration, String configName){
        val pluginPath = Paths.get("plugins", pluginName, configName);
        if(!Files.exists(pluginPath)) {
            Files.createDirectories(pluginPath.getParent());
            Files.createFile(pluginPath);
        }
        Files.write(
                pluginPath,
                Serializations.serialize(configuration).getBytes(StandardCharsets.UTF_8)
        );
    }
}
