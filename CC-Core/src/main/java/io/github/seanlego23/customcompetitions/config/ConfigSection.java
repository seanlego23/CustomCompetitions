package io.github.seanlego23.customcompetitions.config;

import java.lang.annotation.*;

/**
 * Denotes the start of a section in the config.
 *
 * <p>This annotation must be placed on a field with
 * the {@link Option} annotation. The {@link Option#section()}
 * must be equal to {@link #name()}. If they are not, than
 * this section is ignored.</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface ConfigSection {

    /**
     * The name of this section.
     *
     * @return the name of this section
     */
    String name();

    /**
     * The name of the parent section.
     *
     * @return the name of the parent section.
     */
    String parent() default "";

    /**
     * Format string for the description of this section.
     * See <a href="ConfigOptions.html#Format">ConfigOptions</a>
     * for a description on format.
     *
     * @return format string for the description of this section.
     */
    String desc() default "";

}
