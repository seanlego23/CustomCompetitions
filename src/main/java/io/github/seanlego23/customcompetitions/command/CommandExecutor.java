package io.github.seanlego23.customcompetitions.command;

import org.bukkit.command.CommandSender;

public interface CommandExecutor {

    enum Response {
        SUCCESS,
        ERROR,
        INVALID,
        PERM
    }

    Response execute(CommandSender sender, String[] cmdLine);

}
