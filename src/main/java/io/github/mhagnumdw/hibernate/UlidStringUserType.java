package io.github.mhagnumdw.hibernate;

import com.github.f4b6a3.ulid.Ulid;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.descriptor.jdbc.JdbcType;

/**
 * Possibilita o Hibernate entender o tipo {@link Ulid} como atributo nas entidades.
 *
 * <p>Mapeia o {@code Ulid} para um varchar (String) de {@value Ulid#ULID_CHARS} caracteres.
 */
public class UlidStringUserType extends UlidUserTypeAbstract {

    @Override
    public int getSqlType() {
        return Types.VARCHAR;
    }

    @Override
    public long getDefaultSqlLength(Dialect dialect, JdbcType jdbcType) {
        return Ulid.ULID_CHARS;
    }

    @Override
    public Ulid nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner)
            throws SQLException {
        String value = rs.getString(position);
        return value != null ? Ulid.from(value) : null;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Ulid value, int index, SharedSessionContractImplementor session)
            throws SQLException {
        if (value == null) {
            st.setNull(index, Types.VARCHAR);
        } else {
            st.setString(index, value.toString());
        }
    }

    @Override
    public Serializable disassemble(Ulid value) {
        return value != null ? value.toString() : null;
    }

    @Override
    public Ulid assemble(Serializable cached, Object owner) {
        return cached != null ? Ulid.from((String) cached) : null;
    }
}
