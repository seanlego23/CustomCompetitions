package io.github.seanlego23.customcompetitions.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Options for CustomCompetitions configuration file.<br>
 * <a id="Format"><h2>Format</h2></a>
 * <p>"{[{pre:&lt;identifier&gt;, post:&lt;identifier&gt;[, flags:&lt;(l|c|r)heo&gt;, info:{req-info}]},] id:&lt;
 * identifier&gt;[, flags:&lt;p&gt;, info:{req-info}]}"<br>
 * *: Requires <a href="#Info"><i>info</i></a><br>
 * Identifiers:
 * <ul>
 *     <li><i>config-name</i>: name of config</li>
 *     <li><i>file-name</i>: name of file</li>
 *     <li><i>section-name</i>: name of section</li>
 *     <li><i>package-name</i>: name of package</li>
 *     <li><i>class-name</i>: name of class*</li>
 *     <li><i>section-path</i>: all parent sections and this section (ex: grandparent::parent::child)</li>
 *     <li><i>author</i>: the author's name</li>
 *     <li><i>version</i>: the version number</li>
 *     <li><i>plugin-name</i>: the name of this plugin</li>
 *     <li>&lt;characters&gt;: a pattern of characters</li>
 * </ul>
 * Flags:
 * <ul>
 *     <li>Choose one of the following <b>if</b> any:
 *     <ul>
 *         <li><b>l</b>: left aligned</li>
 *         <li><b>c</b>: center aligned</li>
 *         <li><b>r</b>: right aligned</li>
 *     </ul></li>
 *     <li><b>p</b>: repeat identifier*</li>
 *     <li><b>h</b>: amount of characters* (if <i>identifier</i> is greater than this, then pre and post are
 *     discarded)</li>
 *     <li><b>e</b>: spaces after prefix*</li>
 *     <li><b>o</b>: spaces before postfix*</li>
 * </ul>
 * <a id="Info">Info:</a><br>
 * This is a comma-separated list of info needed by the rest of the format.
 * Identifier info's must go at the beginning of the list.
 * Flag info's must go in the order that the flags were given.
 * <ul>
 *     <li><i>class-name</i>: The class name (<i>this</i> if it's this class)</li>
 *     <li><b>p</b>: The number of times to repeat this identifier</li>
 *     <li><b>h</b>: The number of characters for prefix and postfix to fill into (<i>identifier</i> is exempted from
 *     this)</li>
 *     <li><b>e</b>: The number of spaces after prefix</li>
 *     <li><b>o</b>: The number of spaces before postfix</li>
 * </ul>
 * <h2>Example</h2>
 * " !-- Plugin info --! <br>
 * This is {id:plugin-name}'s config and {id:author} wrote it.<br>
 * {{pre:#, post:#, flags:ceoh, info:{1, 1, 30}}, id:section-name}"<br>
 *
 * Could look like:<br>
 * " !-- Plugin info --! <br>
 * This is CustomCompetition's config and seanlego23 wrote it.<br>
 * ######## Competitions ########"
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ConfigOptions {

    /**
     * The name of this config.
     *
     * @return the name of this config.
     */
    String name();

    /**
     * Format string for the header of this config.
     * The version of CustomCompetitions will always
     * appear before the header with one line space between.
     *
     * @return format string for the header of this config.
     */
    String header();

    /**
     * Whether to include the author in the config. Goes before
     * the header:
     *
     * <p>#Author: {author}
     *
     * @return whether to include the author of this plugin in the config.
     */
    boolean includeAuthor() default false;

    /**
     * Whether to include the version in the config. Goes before
     * the header:
     *
     * <p>#Version: {version}
     *
     * @return whether to include the version of this plugin in the config.
     */
    boolean includeVersion() default true;

    int numLinesAfterHeader() default 1;

    /**
     * Format string for section headers.
     *
     * @return format string for section headers.
     */
    String sectionHeader() default "";

    int numLinesAfterSectionHeader() default 1;

    /**
     * Format string for section footers.
     *
     * @return format string for section footers.
     */
    String sectionFooter() default "";

    int numLinesAfterSectionFooter() default 1;

    /**
     * Format string for the footer of this config.
     *
     * @return format string for the header of this config.
     */
    String footer() default "";

    int numLinesBeforeFooter() default 1;

}
