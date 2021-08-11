package io.github.seanlego23.customcompetitions.user;

import org.bukkit.entity.Player;

public class User {
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

}
