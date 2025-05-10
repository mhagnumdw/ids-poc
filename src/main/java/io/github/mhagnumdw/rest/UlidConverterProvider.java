package io.github.mhagnumdw.rest;

import com.github.f4b6a3.ulid.Ulid;
import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import jakarta.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/** Provedor para instruir a engine do JAX-RS qual o converter apropriado para o tipo {@link Ulid}. */
@Provider
public class UlidConverterProvider implements ParamConverterProvider {

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (rawType == Ulid.class) {
            return (ParamConverter<T>) UlidConverter.INSTANCE;
        }
        return null;
    }
}
