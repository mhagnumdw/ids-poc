package io.github.mhagnumdw.uuid.file;

import io.github.mhagnumdw.core.IdFileRunnable;
import io.github.mhagnumdw.uuid.UUIDv7NoLib;

/**
 * Gera os UUIDv7 para um arquivo.
 * <p/>
 * A contabilização para verificar se gerou algum duplicado, precisa ser feita
 * depois, ver o JavaDoc da classe {@link IdFileRunnable} para instruções.
 */
public class UUIDv7NoLibFileRunnable extends IdFileRunnable {

    public UUIDv7NoLibFileRunnable(int totalIds, int fileIndex) {
        super(totalIds, fileIndex);
    }

    @Override
    protected Object generateId() {
        return UUIDv7NoLib.randomUUID().toString();
    }

    @Override
    protected String getIdName() {
        return "uuidv7-nolib";
    }
}
