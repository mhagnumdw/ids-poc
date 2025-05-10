package io.github.mhagnumdw.nanoid;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import com.soundicly.jnanoidenhanced.jnanoid.NanoIdUtils;

import io.github.mhagnumdw.core.IdMemoryRunnable;

/**
 * Gera os NanoId em memória e já faz a contabilização para verificar se gerou algum duplicado.
 */
public class NanoIdMemoryRunnable extends IdMemoryRunnable {

    private final Random random;

    public NanoIdMemoryRunnable(int totalIds, Set<Object> idsStore) {
        super(totalIds, idsStore);
        this.random = ThreadLocalRandom.current();
    }

    @Override
    protected Object generateId() {
        // return NanoIdUtils.randomNanoId();
        return NanoIdUtils.randomNanoId(random, NanoIdUtils.DEFAULT_ALPHABET, NanoIdUtils.DEFAULT_SIZE);
    }

}
