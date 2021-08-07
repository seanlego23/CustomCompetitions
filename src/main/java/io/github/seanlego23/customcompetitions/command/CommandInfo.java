package io.github.seanlego23.customcompetitions.command;

import io.github.seanlego23.customcompetitions.permissions.Permission;

public class CommandInfo {

    private final String[] aliases;
    private final String usage;
    private final String description;
    private final Permission permission;

    public CommandInfo(String usage, String description, String[] aliases, Permission permission) {
        this.usage = usage;
        this.description = description;
        this.aliases = aliases;
        this.permission = permission;
    }

    String getUsage() {
        return usage;
    }

    String getDescription() {
        return description;
    }

    String[] getAliases() {
        return aliases;
    }

    Permission getPermission() {
        return permission;
    }

}
