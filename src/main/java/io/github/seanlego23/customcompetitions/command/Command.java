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
     * @return the name of the command.
     */
    String name();

    /**
     * @return the aliases of the command.
     */
    String[] aliases() default { };

    /**
     * @return the description of this command.
     */
    String desc();

    /**
     * @return the usage of this command.
     */
    String use();

    /**
     * @return the command path of this command.
     */
    String path();

    /**
     * @return the permission needed to access this command.
     */
    Permission perm();

    /**
     * @return if the method this annotates is an executor.
     */
    boolean exec() default false;

    /**
     * @return if the method this annotates is a tabCompleter.
     */
    boolean tab() default false;

}
