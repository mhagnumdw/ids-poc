package io.github.mhagnumdw.uuid.file;

import java.security.SecureRandom;

import com.github.f4b6a3.uuid.alt.GUID;

import io.github.mhagnumdw.core.IdFileRunnable;

/**
 * Gera os UUIDv7 para um arquivo.
 * <p/>
 * A contabilização para verificar se gerou algum duplicado, precisa ser feita
 * depois, ver o JavaDoc da classe {@link IdFileRunnable} para instruções.
 */
public class UUIDv7FileRunnable extends IdFileRunnable {

    public UUIDv7FileRunnable(int totalIds, int fileIndex) {
        super(totalIds, fileIndex);
    }

    @Override
    protected Object generateId() {
        // return GUID.v7().toString();
        return GUID.v7(null, new SecureRandom()).toString();
        // return GUID.v7(null, ThreadLocalRandom.current()).toString();
        // return UuidCreator.getTimeOrderedEpoch().toString();
    }

    @Override
    protected String getIdName() {
        return "uuidv7";
    }
}
