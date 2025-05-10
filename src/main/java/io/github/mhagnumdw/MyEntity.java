package io.github.mhagnumdw;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.UuidGenerator;

import com.github.f4b6a3.ulid.Ulid;

import io.github.mhagnumdw.hibernate.TsidAutoCreate;
import io.github.mhagnumdw.hibernate.UUIDv7ValueGenerator;
import io.github.mhagnumdw.hibernate.UlidAutoCreate;
import io.github.mhagnumdw.hibernate.UlidBinaryConverter;
import io.github.mhagnumdw.hibernate.UlidBinaryUserType;
import io.github.mhagnumdw.hibernate.UlidStringUserType;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MY_ENTITY")
public class MyEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "EXTERNAL_ID_UUID_V4", /* nullable = false, */ unique = true, updatable = false)
    @UuidGenerator // style=default=AUTO que é igual a RANDOM (UUID v4)
    private UUID externalIdUuidV4;

    @Column(name = "EXTERNAL_ID_UUID_V4_STR", /* nullable = false, */ unique = true, updatable = false, length = 36)
    @UuidGenerator // style=default=AUTO que é igual a RANDOM (UUID v4)
    private String externalIdUuidV4Str; // o Hibernate não seta o length automaticamente para

    @Column(name = "EXTERNAL_ID_UUID_V1", /* nullable = false, */ unique = true, updatable = false)
    @UuidGenerator(style = UuidGenerator.Style.TIME) // TIME = UUID v1
    private UUID externalIdUuidV1;

    @Column(name = "EXTERNAL_ID_UUID_V7", /* nullable = false, */ unique = true, updatable = false)
    @UuidGenerator(algorithm = UUIDv7ValueGenerator.class)
    private UUID externalIdUuidV7;

    @TsidAutoCreate
    @Column(name = "EXTERNAL_ID_TSID", /* nullable = false, */ unique = true, updatable = false)
    private Long externalIdTsid;

    @UlidAutoCreate
    // @Convert(converter = UlidStringConverter.class)
    @Type(UlidStringUserType.class)
    @Column(name = "EXTERNAL_ID_ULID", /* nullable = false, */ unique = true, updatable = false)
    private Ulid externalIdUlid; // Apenas com Converter foi criado no Oracle como RAW(255)

    @UlidAutoCreate
    // @Convert(converter = UlidStringConverter.class)
    @Type(UlidBinaryUserType.class)
    @Column(name = "EXTERNAL_ID_ULID_BYTE", /* nullable = false, */ unique = true, updatable = false)
    private Ulid externalIdUlidByte;

    @UlidAutoCreate
    @Convert(converter = UlidBinaryConverter.class) // Com Convert precisa especificar o length
    @Column(name = "EXTERNAL_ID_ULID_BYTE2", /* nullable = false, */ unique = true, updatable = false, length = 16)
    private Ulid externalIdUlidByte2;

    public Long getId() {
        return id;
    }

    public UUID getExternalIdUuidV4() {
        return externalIdUuidV4;
    }

    public String getExternalIdUuidV4Str() {
        return externalIdUuidV4Str;
    }

    public UUID getExternalIdUuidV1() {
        return externalIdUuidV1;
    }

    public UUID getExternalIdUuidV7() {
        return externalIdUuidV7;
    }

    public Long getExternalIdTsid() {
        return externalIdTsid;
    }

    public Ulid getExternalIdUlid() {
        return externalIdUlid;
    }

    public Ulid getExternalIdUlidByte() {
        return externalIdUlidByte;
    }

    public Ulid getExternalIdUlidByte2() {
        return externalIdUlidByte2;
    }

}
