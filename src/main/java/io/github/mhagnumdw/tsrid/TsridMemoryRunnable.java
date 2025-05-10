package io.github.mhagnumdw.tsrid;

import java.util.Set;

import io.github.mhagnumdw.core.IdMemoryRunnable;

/**
 * Gera os TSRID em memória e já faz a contabilização para verificar se gerou algum duplicado.
 */
public class TsridMemoryRunnable extends IdMemoryRunnable {

    public TsridMemoryRunnable(int totalIds, Set<Object> idsStore) {
        super(totalIds, idsStore);
    }

    @Override
    protected Object generateId() {
        return TsridGenerator.generate();
    }

}
