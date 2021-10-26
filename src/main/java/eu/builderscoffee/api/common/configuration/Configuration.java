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
     * Read or create a config file on disk from a class structure
     * @param pluginName Plugin name
     * @param structure Class of config structure
     * @return Returns the config file instance
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

    /**
     * Read or create a config file with multiple models on disk from a class structure
     * @param pluginName Plugin name
     * @param structure Class of config structure
     * @param models Enum of models
     * @return Returns a map with keys as model and value as the config file's instance
     */
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

    /**
     * Read or create a config file with a specific model on disk from a class structure
     * @param pluginName Plugin name
     * @param structure Class of config structure
     * @param model The specified model
     * @return Returns the config file instance
     */
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

    /**
     * Read or create the config file on the disk
     * @param pluginName Plugin name
     * @param structure Class of config structure
     * @param configName The name of the config file
     * @return Returns the config file instance
     */
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
     * Writes down the config file on the disk
     * @param pluginName Plugin name
     * @param configuration The config instance to write
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

    /**
     * Writes down the config files on the disk
     * @param pluginName Plugin name
     * @param map The map that contains the models as keys and config file instances as value
     */
    @SneakyThrows
    public <X extends Enum<?>, Y> void writeConfiguration(String pluginName, Map<X, Y> map) {
        map.keySet().forEach(value -> writeConfiguration(pluginName, map, value));
    }

    /**
     * Writes down the config file on the disk
     * @param pluginName Plugin name
     * @param map The map that contains the models as keys and config file instances as value
     * @param model The specific model to write
     */
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

    /**
     * Writes down the config file on the disk
     * @param pluginName Plugin name
     * @param configuration The config instance to write
     * @param configName The config file name
     */
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
