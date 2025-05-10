package io.github.mhagnumdw.uuid.file;

import java.util.UUID;

import io.github.mhagnumdw.core.IdFileRunnable;

/**
 * Gera os UUIDv4 para um arquivo.
 * <p/>
 * A contabilização para verificar se gerou algum duplicado, precisa ser feita
 * depois, ver o JavaDoc da classe {@link IdFileRunnable} para instruções.
 */
public class UUIDv4FileRunnable extends IdFileRunnable {

    public UUIDv4FileRunnable(int totalIds, int fileIndex) {
        super(totalIds, fileIndex);
    }

    @Override
    protected Object generateId() {
        return UUID.randomUUID().toString();
    }

    @Override
    protected String getIdName() {
        return "uuidv4";
    }

}
