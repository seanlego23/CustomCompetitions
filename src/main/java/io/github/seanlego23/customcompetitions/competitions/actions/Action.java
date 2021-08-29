package io.github.seanlego23.customcompetitions.competitions.actions;

import io.github.seanlego23.customcompetitions.competitions.Competition;
import org.jetbrains.annotations.NotNull;

public interface Action {

    enum ActionResult {
        NOT_ENOUGH_PARAMS,
        INVALID_PARAMS,
        SUCCESS,
    }

    ActionResult perform(@NotNull Competition competition, Object... objects);

    static ActionResult checkObjects(Object[] objects, Class<?>... clz) {
        if (clz.length == 0)
            return ActionResult.SUCCESS;
        if (objects.length < clz.length)
            return ActionResult.NOT_ENOUGH_PARAMS;
        for (int i = 0, j = 0; i < objects.length; i++) {
            if (!clz[j].isAssignableFrom(objects[i].getClass()))
                return ActionResult.INVALID_PARAMS;
            if (j < clz.length - 1)
                j++;
        }
        return ActionResult.SUCCESS;
    }

}
