package io.github.mhagnumdw.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.f4b6a3.ulid.Ulid;
import java.io.IOException;

/** Serializador do Jackson para o tipo {@link Ulid}. */
public class UlidSerializer extends StdSerializer<Ulid> {

    private static final long serialVersionUID = 1L;

    public static final UlidSerializer INSTANCE = new UlidSerializer();

    private UlidSerializer() {
        super(Ulid.class);
    }

    @Override
    public void serialize(Ulid ulid, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(ulid.toString());
    }
}
