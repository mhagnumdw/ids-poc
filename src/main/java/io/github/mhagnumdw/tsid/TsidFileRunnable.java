package io.github.mhagnumdw.tsid;

import java.util.concurrent.ThreadLocalRandom;

import io.github.mhagnumdw.core.IdFileRunnable;
import io.hypersistence.tsid.TSID;

/**
 * Gera os TSID para um arquivo.
 * <p/>
 * A contabilização para verificar se gerou algum duplicado, precisa ser feita
 * depois, ver o JavaDoc da classe {@link IdFileRunnable} para instruções.
 */
public class TsidFileRunnable extends IdFileRunnable {

    private final TSID.Factory factory;

    public TsidFileRunnable(int totalIds, int fileIndex) {
        super(totalIds, fileIndex);
        factory = TSID.Factory.builder().withNodeBits(0)
            .withRandomFunction(() -> ThreadLocalRandom.current().nextInt())
            .build();
    }

    @Override
    protected Object generateId() {
        return factory.generate().toLong();
    }

    @Override
    protected String getIdName() {
        return "tsid";
    }

}
