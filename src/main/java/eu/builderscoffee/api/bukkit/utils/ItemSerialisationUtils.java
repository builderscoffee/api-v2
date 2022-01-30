package eu.builderscoffee.api.bukkit.utils;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.val;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@UtilityClass
public class ItemSerialisationUtils {

    public static String serialize(@NonNull ItemStack item) {
        try {
            val outputStream = new ByteArrayOutputStream();
            val dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeObject(item.serialize());

            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save itemstack.", e);
        }
    }

    public static ItemStack deserialize(@NonNull String data) {
        try (val dataInput = new BukkitObjectInputStream(new ByteArrayInputStream(Base64Coder.decodeLines(data)))) {
            return ItemStack.deserialize((Map<String, Object>) dataInput.readObject());
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
