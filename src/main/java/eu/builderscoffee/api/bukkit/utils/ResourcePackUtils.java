package eu.builderscoffee.api.bukkit.utils;

import eu.builderscoffee.api.common.utils.TextComponentUtils;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;

@UtilityClass
public class ResourcePackUtils {

    public static void sendRourcePack(Player player, String url, String message, boolean force) {
        try {
            Object craftplayer = player.getClass().getMethod("getHandle").invoke(player);
            Object entityPlayer = craftplayer.getClass().getField("b").get(craftplayer);
            Object playerConnection = entityPlayer.getClass().getField("a").get(entityPlayer);
            Class<?> clsPacket = Class.forName("net.minecraft.network.protocol.Packet");
            Class<?> packetPlayOutResourcePackSend = Class.forName("net.minecraft.network.protocol.game.PacketPlayOutResourcePackSend");
            Class<?> clsIChatBaseComponent = Class.forName("net.minecraft.network.chat.IChatBaseComponent");
            Class<?> clsCraftChatMessage = Class.forName("org.bukkit.craftbukkit.v1_18_R1.util.CraftChatMessage");
            Class<?> clsComponentSerializer = Class.forName("net.md_5.bungee.chat.ComponentSerializer");
            Object componentSerializer = clsComponentSerializer.getMethod("toString", BaseComponent.class).invoke(null, TextComponentUtils.decodeColor(message));
            Object craftChatMessage = clsCraftChatMessage.getMethod("fromJSON", String.class).invoke(null, componentSerializer);
            Object resourcePackPacket = packetPlayOutResourcePackSend
                    .getConstructor(String.class, String.class, boolean.class, clsIChatBaseComponent)
                    .newInstance(url,
                            DataUtils.sha1HashFromUrl(url),
                            force,
                            craftChatMessage);
            playerConnection.getClass().getMethod("a", clsPacket).invoke(playerConnection, resourcePackPacket);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
