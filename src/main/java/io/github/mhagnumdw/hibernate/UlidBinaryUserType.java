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
 * <p>Mapeia o {@code Ulid} para um binário de {@value Ulid#ULID_BYTES} bytes.
 */
public class UlidBinaryUserType extends UlidUserTypeAbstract {

    @Override
    public int getSqlType() {
        return Types.BINARY;
    }

    @Override
    public long getDefaultSqlLength(Dialect dialect, JdbcType jdbcType) {
        return Ulid.ULID_BYTES;
    }

    @Override
    public Ulid nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner)
            throws SQLException {
        byte[] bytes = rs.getBytes(position);
        return bytes != null ? Ulid.from(bytes) : null;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Ulid value, int index, SharedSessionContractImplementor session)
            throws SQLException {
        if (value == null) {
            st.setNull(index, Types.BINARY);
        } else {
            st.setBytes(index, value.toBytes());
        }
    }

    @Override
    public Serializable disassemble(Ulid value) {
        return value != null ? value.toBytes() : null;
    }

    @Override
    public Ulid assemble(Serializable cached, Object owner) {
        return cached != null ? Ulid.from((byte[]) cached) : null;
    }
}
