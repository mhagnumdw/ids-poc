package io.github.mhagnumdw.hibernate;

import java.util.UUID;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.uuid.UuidValueGenerator;

import io.github.mhagnumdw.uuid.UUIDv7;

/**
 * {@linkplain org.hibernate.generator.Generator} para produzir valores UUID versão 7.
 *
 * <p>Exemplo:
 *
 * <pre>{@code
 * @Column(name = "EXTERNAL_ID_UUID_V7", unique = true, updatable = false)
 * @UuidGenerator(algorithm = UUIDv7ValueGenerator.class)
 * private UUID externalIdUuidV7;
 * }</pre>
 */
public class UUIDv7ValueGenerator implements UuidValueGenerator {

    @Override
    public UUID generateUuid(SharedSessionContractImplementor session) {
        return UUIDv7.randomUUID();
    }
}
