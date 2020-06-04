package cz.upol.zp4jv.reflect;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotace urcujici serializovatelnost tridy nebo metody
 */

@Retention(RetentionPolicy.RUNTIME)

public @interface Serialize {
    String as();
}
