package io.github.mhagnumdw.uuid.memory;

import java.security.SecureRandom;
import java.util.Set;

import com.github.f4b6a3.uuid.alt.GUID;

import io.github.mhagnumdw.core.IdMemoryRunnable;

/**
 * Gera os UUIDv7 em memória e já faz a contabilização para verificar se gerou algum duplicado.
 */
public class UUIDv7MemoryRunnable extends IdMemoryRunnable {

    public UUIDv7MemoryRunnable(int totalIds, Set<Object> idsStore) {
        super(totalIds, idsStore);
    }

    @Override
    protected Object generateId() {
        return GUID.v7().toString();
        // return GUID.v7(null, new SecureRandom()).toString();
        // return GUID.v7(null, ThreadLocalRandom.current()).toString();
        // return UuidCreator.getTimeOrderedEpoch().toString();
    }

}
