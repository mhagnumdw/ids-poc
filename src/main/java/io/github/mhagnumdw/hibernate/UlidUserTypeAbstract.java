package io.github.mhagnumdw.hibernate;

import com.github.f4b6a3.ulid.Ulid;
import java.util.Objects;
import org.hibernate.usertype.UserType;

/** Classe base que possibilita o Hibernate entender o tipo {@link Ulid} como atributo nas entidades. */
abstract class UlidUserTypeAbstract implements UserType<Ulid> {

    @Override
    public final Class<Ulid> returnedClass() {
        return Ulid.class;
    }

    @Override
    public final boolean equals(Ulid x, Ulid y) {
        return Objects.equals(x, y);
    }

    @Override
    public final int hashCode(Ulid x) {
        return Objects.hashCode(x);
    }

    @Override
    public final Ulid deepCopy(Ulid value) {
        return value; // Ulid é imutável
    }

    @Override
    public final boolean isMutable() {
        return false;
    }
}
