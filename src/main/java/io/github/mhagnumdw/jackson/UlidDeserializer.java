package io.github.mhagnumdw.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.f4b6a3.ulid.Ulid;
import java.io.IOException;

/** Deserializador do Jackson para o tipo {@link Ulid}. */
public class UlidDeserializer extends StdDeserializer<Ulid> {

    private static final long serialVersionUID = 1L;

    public static final UlidDeserializer INSTANCE = new UlidDeserializer();

    private UlidDeserializer() {
        super(Ulid.class);
    }

    @Override
    public Ulid deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return Ulid.from(p.getValueAsString());
    }
}
