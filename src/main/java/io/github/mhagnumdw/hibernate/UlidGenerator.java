package io.github.mhagnumdw.hibernate;

import com.github.f4b6a3.ulid.Ulid;
import com.github.f4b6a3.ulid.UlidFactory;
import java.lang.reflect.Member;
import java.util.EnumSet;
import java.util.function.Function;
import java.util.function.LongSupplier;
import java.util.random.RandomGenerator;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.BeforeExecutionGenerator;
import org.hibernate.generator.EventType;
import org.hibernate.generator.EventTypeSets;
import org.hibernate.generator.GeneratorCreationContext;
import org.hibernate.internal.util.ReflectHelper;

/**
 * {@linkplain org.hibernate.generator.Generator} para produzir valores {@link Ulid}.
 *
 * <p>Usa {@linkplain UlidAutoCreate} para gerar os valores.
 */
public class UlidGenerator implements BeforeExecutionGenerator {

    private static final long serialVersionUID = -5226481508852048320L;

    private final transient UlidFactory factory;
    private final transient Function<Ulid, Object> valueConverter;

    // Esse construtor é requerido pelo org.hibernate.boot.model.internal.GeneratorBinder.instantiateGenerator
    @SuppressWarnings("java:S1172")
    public UlidGenerator(UlidAutoCreate annotation, Member idMember, GeneratorCreationContext context) {
        RandomGenerator random = RandomGenerator.getDefault();
        this.factory = UlidFactory.newInstance((LongSupplier) random::nextLong);

        Class<?> propertyType = ReflectHelper.getPropertyType(idMember);
        valueConverter = resolveValueConverter(propertyType);
    }

    @Override
    public EnumSet<EventType> getEventTypes() {
        return EventTypeSets.INSERT_ONLY;
    }

    @Override
    public Object generate(
            SharedSessionContractImplementor session, Object owner, Object currentValue, EventType eventType) {
        return valueConverter.apply(factory.create());
    }

    private Function<Ulid, Object> resolveValueConverter(Class<?> propertyType) {
        if (String.class.isAssignableFrom(propertyType)) {
            return Ulid::toString;
        } else if (Ulid.class.isAssignableFrom(propertyType)) {
            return ulid -> ulid;
        } else if (byte[].class.isAssignableFrom(propertyType)) {
            return Ulid::toBytes;
        } else {
            String msg = String.format(
                    "The @UlidAutoCreate annotation on [%s] can only be placed on a String, byte[] "
                            + "or Ulid entity attribute!",
                    propertyType);
            throw new RuntimeException(msg);
        }
    }
}
