package me.fallenbreath.tweakermore.util.mixin;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModRequire
{
	/**
	 * A list of mod ids
	 */
	String[] value() default {};
}
