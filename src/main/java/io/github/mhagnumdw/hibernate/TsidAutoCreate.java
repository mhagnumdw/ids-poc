package io.github.mhagnumdw.hibernate;

import io.hypersistence.tsid.TSID;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.hibernate.annotations.ValueGenerationType;

/**
 * Especifica que um atributo de uma entidade deve ser automaticamente gerado como um <a
 * href="https://github.com/vladmihalcea/hypersistence-tsid">TSID</a>.
 *
 * <p>O tipo do atributo na entidade pode ser uma {@link String} (que tem 13 caracteres e é codificada em Crockford's
 * Base32) ou {@link Long} de 64 bits ou {@link TSID}.
 */
@ValueGenerationType(generatedBy = TsidGenerator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Inherited
public @interface TsidAutoCreate {}
