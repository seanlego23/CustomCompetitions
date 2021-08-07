package io.github.seanlego23.customcompetitions.commands;

import io.github.seanlego23.customcompetitions.command.CommandExecutor;
import io.github.seanlego23.customcompetitions.command.CommandInfo;
import io.github.seanlego23.customcompetitions.command.TabCompleter;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CCCommand {

    private final String name;
    private final CommandInfo commandInfo;
    private CommandExecutor executor;
    private TabCompleter tabCompleter;

    public CCCommand(String name, CommandInfo commandInfo, CommandExecutor executor, TabCompleter tabCompleter) {
        this.name = name;
        this.commandInfo = commandInfo;
        this.executor = executor;
        this.tabCompleter = tabCompleter;
    }

    public String getName() {
        return name;
    }

    public CommandInfo getCommandInfo() {
        return commandInfo;
    }

    public void setExecutor(CommandExecutor executor) {
        this.executor = executor;
    }

    public void setTabCompleter(TabCompleter completer) {
        tabCompleter = completer;
    }

    /**
     * Execute this command. The command is executed using the sender and the command arguments.
     *
     * @param sender the {@link CommandSender} that wants to execute the command.
     * @param cmdLine the arguments that this command needs.
     * @return a {@link CommandExecutor.Response}.
     */
    public CommandExecutor.Response execute(CommandSender sender, String[] cmdLine) {
        return executor.execute(sender, cmdLine);
    }

    /**
     * Gives a list of options for a CommandSender to tab complete.
     *
     * @param sender the {@link CommandSender} that is writing the command.
     * @param cmdLine the arguments that this command has.
     * @return a {@link List} of options for the CommandSender to choose from.
     */
    public List<String> tabComplete(CommandSender sender, String[] cmdLine) {
        if (tabCompleter != null)
            return tabCompleter.complete(sender, cmdLine);
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        else if (!(obj instanceof CCCommand))
            return false;
        else
            return ((CCCommand)obj).name.equalsIgnoreCase(name);
    }
}
