package io.github.seanlego23.customcompetitions.config;

import java.lang.annotation.*;

/**
 * Used to tell CustomCompetitions what fields in a class belong to the config.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Option {

    /**
     * An alias of this option. Otherwise, the field
     * name will be used.
     *
     * @return the alias of the option.
     */
    String alias() default "";

    /**
     * The section this option is under, if any.
     *
     * @return the section the option is in.
     */
    String section() default "";

    /**
     * The path this option follows. Includes all parents.
     *
     * @return the path of this option.
     */
    String path() default "";

    /**
     * The root name of a conversion method, if needed.
     * There are technically two conversion methods, one
     * for serialization and one for deserialization.
     *
     * <p>If <i>root_name</i> is the name of the conversion
     * method, than the actual two methods should be named
     * as follows:<br>
     * private String root_name_serialize(Object);
     * private Object root_name_deserialize(String);
     *
     * @return the root name of the conversion methods.
     */
    String conversionMethod() default "";

}
