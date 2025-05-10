package io.github.mhagnumdw.hibernate;

import io.hypersistence.tsid.TSID;
import java.lang.reflect.Member;
import java.util.EnumSet;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.random.RandomGenerator;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.BeforeExecutionGenerator;
import org.hibernate.generator.EventType;
import org.hibernate.generator.EventTypeSets;
import org.hibernate.generator.GeneratorCreationContext;
import org.hibernate.internal.util.ReflectHelper;

/**
 * {@linkplain org.hibernate.generator.Generator} para produzir valores {@link TSID}.
 *
 * <p>Usa {@linkplain TsidAutoCreate} para gerar os valores.
 */
public class TsidGenerator implements BeforeExecutionGenerator {

    private static final long serialVersionUID = 7625789926495890912L;

    private final transient TSID.Factory factory;
    private final transient Function<TSID, Object> valueConverter;

    // Esse construtor é requerido pelo org.hibernate.boot.model.internal.GeneratorBinder.instantiateGenerator
    @SuppressWarnings("java:S1172")
    public TsidGenerator(TsidAutoCreate annotation, Member idMember, GeneratorCreationContext context) {
        RandomGenerator random = RandomGenerator.getDefault();
        factory = TSID.Factory.builder()
                .withNodeBits(0)
                .withRandomFunction((IntSupplier) random::nextInt)
                .build();

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
        return valueConverter.apply(factory.generate());
    }

    private Function<TSID, Object> resolveValueConverter(Class<?> propertyType) {
        if (Long.class.isAssignableFrom(propertyType)) {
            return TSID::toLong;
        } else if (String.class.isAssignableFrom(propertyType)) {
            return TSID::toString;
        } else if (TSID.class.isAssignableFrom(propertyType)) {
            return tsid -> tsid;
        } else {
            String msg = String.format(
                    "The @TsidAutoCreate annotation on [%s] can only be placed on a Long, String, "
                            + "or TSID entity attribute!",
                    propertyType);
            throw new RuntimeException(msg);
        }
    }
}
