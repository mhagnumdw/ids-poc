package io.github.mhagnumdw.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.f4b6a3.ulid.Ulid;
import io.quarkus.jackson.ObjectMapperCustomizer;
import jakarta.inject.Singleton;

/**
 * Personaliza a configuração do Jackson.
 *
 * @see <a href="https://github.com/FasterXML/jackson">Jackson GitHub Repository</a>
 */
@Singleton
public class DefaultObjectMapperCustomizer implements ObjectMapperCustomizer {

    @Override
    public void customize(ObjectMapper mapper) {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        mapper.registerModule(new SimpleModule()
                .addSerializer(Ulid.class, UlidSerializer.INSTANCE)
                .addDeserializer(Ulid.class, UlidDeserializer.INSTANCE));
    }
}
