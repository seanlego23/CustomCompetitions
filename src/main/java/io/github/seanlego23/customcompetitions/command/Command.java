package io.github.seanlego23.customcompetitions.command;

import io.github.seanlego23.customcompetitions.permissions.Permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
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
