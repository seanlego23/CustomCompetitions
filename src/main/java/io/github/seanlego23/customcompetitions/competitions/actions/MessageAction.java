package io.github.seanlego23.customcompetitions.competitions.actions;

import io.github.seanlego23.customcompetitions.competitions.Competition;
import io.github.seanlego23.customcompetitions.user.User;
import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MessageAction implements Action {

    public enum MessageType {
        ACTION_BAR,
        CHAT,
        TITLE
    }

    public MessageAction() {

    }

    @Override
    public ActionResult perform(@NotNull Competition competition, Object... objects) {
        Objects.requireNonNull(objects);

        ActionResult result = Action.checkObjects(objects, User.class, MessageType.class, BaseComponent[].class);
        if (result != ActionResult.SUCCESS)
            return result;

        User user = (User) objects[0];
        MessageType messageType = (MessageType) objects[1];
        BaseComponent[] components = (BaseComponent[]) objects[2];

        return ActionResult.SUCCESS;
    }
}
