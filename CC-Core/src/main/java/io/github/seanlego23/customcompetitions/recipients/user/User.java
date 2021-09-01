package io.github.seanlego23.customcompetitions.recipients.user;

public interface User {

    public String getName();

    public boolean hasPermission(String perm);

}
