package com.bush.outbox.annotation;

import com.bush.outbox.config.OutboxAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that enables outbox service configuration. Without this annotation service will not work properly
 */
@Import(OutboxAutoConfiguration.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EnableOutboxService {
}
