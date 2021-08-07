package io.github.seanlego23.customcompetitions.command;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface TabCompleter {

    List<String> complete(CommandSender sender, String[] cmdLine);

}
