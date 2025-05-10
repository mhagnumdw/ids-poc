package io.github.mhagnumdw.rest;

import com.github.f4b6a3.ulid.Ulid;
import jakarta.ws.rs.ext.ParamConverter;

/** Converter para construir um {@link Ulid} a partir de uma {@code String} e vice-versa. */
public class UlidConverter implements ParamConverter<Ulid> {

    public static final UlidConverter INSTANCE = new UlidConverter();

    private UlidConverter() {}

    @Override
    public Ulid fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return Ulid.from(value);
    }

    @Override
    public String toString(Ulid ulid) {
        return ulid.toString();
    }
}
