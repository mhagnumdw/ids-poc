package io.github.mhagnumdw.tsid;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;

import io.github.mhagnumdw.core.IdMemoryRunnable;
import io.hypersistence.tsid.TSID;

/**
 * Gera os TSID em memória e já faz a contabilização para verificar se gerou algum duplicado.
 */
public class TsidMemoryRunnable extends IdMemoryRunnable {

    private final TSID.Factory factory;

    public TsidMemoryRunnable(int totalIds, Set<Object> idsStore) {
        super(totalIds, idsStore);

        RandomGenerator random = RandomGenerator.getDefault();

        this.factory = TSID.Factory.builder().withNodeBits(0)
            .withRandomFunction(() -> random.nextInt())
            // .withRandomFunction(() -> ThreadLocalRandom.current().nextInt()) //
            .build();
    }

    @Override
    protected Object generateId() {
        // return factory.generate().toLong();
        return factory.generate();
    }

}
