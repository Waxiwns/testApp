// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package utils.annotations;

import org.junit.jupiter.api.Disabled;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@Disabled
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
public @interface Bug {
    String value() default "";
}
