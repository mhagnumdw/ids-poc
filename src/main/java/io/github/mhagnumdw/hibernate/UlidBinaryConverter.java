package io.github.mhagnumdw.hibernate;

import com.github.f4b6a3.ulid.Ulid;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/** Converte entre {@link Ulid} e {@code byte[]}. O {@code byte[]} possui 16 bytes. */
@Converter(autoApply = false)
public class UlidBinaryConverter implements AttributeConverter<Ulid, byte[]> {

    @Override
    public byte[] convertToDatabaseColumn(Ulid ulid) {
        return ulid != null ? ulid.toBytes() : null;
    }

    @Override
    public Ulid convertToEntityAttribute(byte[] dbData) {
        return dbData != null ? Ulid.from(dbData) : null;
    }
}
