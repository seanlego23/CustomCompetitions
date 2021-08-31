package io.github.seanlego23.customcompetitions.config;

import java.lang.annotation.*;

/**
 * Description of the config option. Requires that {@link Option} be present as well.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface OptionDescription {

    /**
     * Format string for the description.
     * See <a href="ConfigOptions.html#Format">ConfigOptions</a>
     * for a description on format.
     *
     * @return format string for the description.
     */
    String desc();

    /**
     * Number of blank lines before the description.
     *
     * @return number of blank lines before the description.
     */
    int numBlankLinesBefore() default 1;

}
