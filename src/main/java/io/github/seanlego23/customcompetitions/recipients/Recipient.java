package io.github.seanlego23.customcompetitions.recipients;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;

import java.util.UUID;

public interface Recipient {

    void sendMessage(BaseComponent component);

    void sendMessage(BaseComponent... components);

    void sendMessage(UUID sender, BaseComponent component);

    void sendMessage(UUID sender, BaseComponent... components);

    void sendMessage(ChatMessageType type, BaseComponent component);

    void sendMessage(ChatMessageType type, BaseComponent... components);

    void sendMessage(ChatMessageType type, UUID sender, BaseComponent component);

    void sendMessage(ChatMessageType type, UUID sender, BaseComponent... components);

    void sendTitle(BaseComponent[] title, BaseComponent[] subTitle);

}
