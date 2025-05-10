package io.github.mhagnumdw.uuid.memory;

import java.util.Set;

import io.github.mhagnumdw.core.IdMemoryRunnable;
import io.github.mhagnumdw.uuid.UUIDv7NoLib;

/**
 * Gera os UUIDv7 em memória e já faz a contabilização para verificar se gerou algum duplicado.
 */
public class UUIDv7NoLibMemoryRunnable extends IdMemoryRunnable {

    public UUIDv7NoLibMemoryRunnable(int totalIds, Set<Object> idsStore) {
        super(totalIds, idsStore);
    }

    @Override
    protected Object generateId() {
        return UUIDv7NoLib.randomUUID().toString();
    }

}
