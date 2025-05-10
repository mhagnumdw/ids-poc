package io.github.mhagnumdw.hibernate;

import com.github.f4b6a3.ulid.Ulid;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.hibernate.annotations.ValueGenerationType;

/**
 * Especifica que um atributo de uma entidade deve ser automaticamente gerado como um <a
 * href="https://github.com/ulid/spec">ULID</a>.
 *
 * <p>O tipo do atributo na entidade pode ser uma {@link String} (que tem 26 caracteres e é codificada em Crockford's
 * Base32) ou {@code byte[]} de 128 bits ou {@link Ulid}.
 */
@ValueGenerationType(generatedBy = UlidGenerator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Inherited
public @interface UlidAutoCreate {}
