package io.github.seanlego23.customcompetitions.command;

import io.github.seanlego23.customcompetitions.permissions.Permission;


public @interface Command {

    /**
     * @return the name of the command
     */
    String name() default "";

    /**
     * @return the aliases of the command
     */
    String[] aliases();

    /**
     * @return the permission needed to access this command
     */
    Permission permission();

    /**
     * @return the usage of this command
     */
    String usage();

    /**
     * @return the description of this command
     */
    String description();

}
