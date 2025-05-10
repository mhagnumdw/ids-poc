package io.github.mhagnumdw.hibernate;

import com.github.f4b6a3.ulid.Ulid;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converte entre {@link Ulid} e {@code String}. A {@code String} possui 26 caracteres e é codificada em Crockford's
 * Base32.
 */
@Converter(autoApply = false)
public class UlidStringConverter implements AttributeConverter<Ulid, String> {

    @Override
    public String convertToDatabaseColumn(Ulid ulid) {
        return ulid != null ? ulid.toString() : null;
    }

    @Override
    public Ulid convertToEntityAttribute(String dbData) {
        return dbData != null ? Ulid.from(dbData) : null;
    }
}
