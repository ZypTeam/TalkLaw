package com.chuxin.law.ry.server.widget;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Denotes that the annotated element represents rc_ic_bubble_left packed color
 * int, {@code AARRGGBB}. If applied to an int array, every element
 * in the array represents rc_ic_bubble_left color integer.
 * <p>
 * Example:
 * <pre>{@code
 *  public abstract void setTextColor(&#64;ColorInt int color);
 * }</pre>
 */
@Retention(CLASS)
@Target({PARAMETER, METHOD, LOCAL_VARIABLE, FIELD})
public @interface ColorInt {
}
