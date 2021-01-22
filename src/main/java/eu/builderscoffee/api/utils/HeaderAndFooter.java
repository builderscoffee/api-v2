package eu.builderscoffee.api.utils;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

import java.lang.reflect.Field;

public class HeaderAndFooter {
    /**
     * Used to toggle debug messages. Disabled by default.
     *
     * @deprecated No longer in use.
     */
    @Deprecated
    public static boolean DEBUG;

    private JSONObject jsonHeader;
    private JSONObject jsonFooter;

    /**
     * Constructs an {@link HeaderAndFooter} object based on plain text.
     *
     * @param header Header text.
     * @param footer Footer text.
     */
    public HeaderAndFooter(String header, String footer) {
        Preconditions.checkNotNull(header);
        Preconditions.checkNotNull(footer);
        this.jsonHeader = Title.convert(header);
        this.jsonFooter = Title.convert(footer);
    }

    /**
     * Constructs an {@link HeaderAndFooter} object based on JSON-formatted text.
     *
     * @param jsonHeader Header text to display Must be in /tellraw JSON format.
     * @param jsonFooter Footer text to display Must be in /tellraw JSON format.
     */
    public HeaderAndFooter(JSONObject jsonHeader, JSONObject jsonFooter) {
        Preconditions.checkNotNull(jsonHeader);
        Preconditions.checkNotNull(jsonFooter);
        Preconditions.checkArgument(!jsonHeader.isEmpty());
        Preconditions.checkArgument(!jsonFooter.isEmpty());
        this.jsonHeader = jsonHeader;
        this.jsonFooter = jsonFooter;
    }

    /**
     * This method has been kept just to ensure backwards compatibility with older versions of TextAPI.
     * It is not supported and will be removed in a future release.
     *
     * @param player  The player to send the message to.
     * @param message The message to send.
     * @deprecated Please create a new {@link HeaderAndFooter} instance instead.
     */
    @Deprecated
    public static void send(Player player, String message) {
        new ActionBar(message).send(player);
    }

    /**
     * This method has been kept just to ensure backwards compatibility with older versions of TextAPI.
     * It is not supported and will be removed in a future release.
     *
     * @param message The message to send.
     * @deprecated Please create a new {@link HeaderAndFooter} instance instead.
     */
    @Deprecated
    public static void sendToAll(String message) {
        new ActionBar(message).sendToAll();
    }

    /**
     * Sends an action bar message to a specific player.
     *
     * @param player The player to send the message to.
     */
    public void send(Player player) {
        Preconditions.checkNotNull(player);
        try {
            Class<?> clsIChatBaseComponent = ServerPackage.MINECRAFT.getClass("IChatBaseComponent");
            Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
            Object headerChatBaseComponent = ServerPackage.MINECRAFT.getClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class).invoke(null, jsonHeader.toString());
            Object footerChatBaseComponent = ServerPackage.MINECRAFT.getClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class).invoke(null, jsonFooter.toString());
            Object packetPlayOutPlayerListHeaderFooter = ServerPackage.MINECRAFT.getClass("PacketPlayOutPlayerListHeaderFooter").getConstructor().newInstance();

            Field headerField = packetPlayOutPlayerListHeaderFooter.getClass().getDeclaredField("a");
            headerField.setAccessible(true);
            headerField.set(packetPlayOutPlayerListHeaderFooter, headerChatBaseComponent);
            headerField.setAccessible(false);

            Field footerField = packetPlayOutPlayerListHeaderFooter.getClass().getDeclaredField("b");
            footerField.setAccessible(true);
            footerField.set(packetPlayOutPlayerListHeaderFooter, footerChatBaseComponent);
            footerField.setAccessible(false);

            playerConnection.getClass().getMethod("sendPacket", ServerPackage.MINECRAFT.getClass("Packet")).invoke(playerConnection, packetPlayOutPlayerListHeaderFooter);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sends an action bar message to all online players.
     */
    public void sendToAll() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            send(player);
        }
    }

    /**
     * Changes the text to display.
     *
     * @param header Header to display.
     * @param footer Footer to display.
     */
    public void setText(String header, String footer) {
        Preconditions.checkNotNull(header);
        Preconditions.checkNotNull(footer);
        this.jsonHeader = Title.convert(header);
        this.jsonFooter = Title.convert(footer);
    }

    /**
     * Changes the text to display.
     *
     * @param jsonHeader Header to display. Must be in /tellraw JSON format.
     * @param jsonFooter Footer to display. Must be in /tellraw JSON format.
     */
    public void setJsonText(JSONObject jsonHeader, JSONObject jsonFooter) {
        Preconditions.checkNotNull(jsonHeader);
        Preconditions.checkNotNull(jsonFooter);
        Preconditions.checkArgument(!jsonHeader.isEmpty());
        Preconditions.checkArgument(!jsonFooter.isEmpty());
        this.jsonHeader = jsonHeader;
        this.jsonFooter = jsonFooter;
    }
}
