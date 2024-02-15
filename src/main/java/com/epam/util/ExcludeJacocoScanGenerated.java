package com.epam.util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The ExcludeJacocoScanGenerated annotation is used to mark types, methods, and constructors that should be excluded from Jacoco unit testing scan.
 * Used for static and infinite methods that cannot be tested via JUnit.
 */
@Documented
@Retention(RUNTIME)
@Target({TYPE, METHOD, CONSTRUCTOR})
public @interface ExcludeJacocoScanGenerated {
}