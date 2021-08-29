package io.github.seanlego23.customcompetitions.recipients.user;

import io.github.seanlego23.customcompetitions.recipients.Recipient;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;

import java.util.UUID;

public class User implements Recipient {
    private final Player player;
    private final String name;

    public User(Player player) {
        this.player = player;
        name = player.getName();
    }

    public String getName() {
        return name;
    }

    public boolean hasPermission(String perm) {
        return player.hasPermission(perm);
    }

    public void sendMessage(BaseComponent component) {

    }

    public void sendMessage(BaseComponent... components) {

    }

    public void sendMessage(UUID sender, BaseComponent component) {

    }

    public void sendMessage(UUID sender, BaseComponent... components) {

    }

    public void sendMessage(ChatMessageType type, BaseComponent component) {

    }

    public void sendMessage(ChatMessageType type, BaseComponent... components) {

    }

    public void sendMessage(ChatMessageType type, UUID sender, BaseComponent component) {

    }

    public void sendMessage(ChatMessageType type, UUID sender, BaseComponent... components) {

    }

}
