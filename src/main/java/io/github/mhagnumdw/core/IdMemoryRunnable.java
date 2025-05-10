package io.github.mhagnumdw.core;

import java.util.Set;

/**
 * Gera os IDs em memória e já faz a contabilização para saber se algum ID foi gerado duplicado.
 * <p/>
 * É impresso um log de WARN para cada ID gerado duplicado.
 */
public abstract class IdMemoryRunnable implements Runnable {

    private final int totalIds;
    private final Set<Object> idsStore;

    /**
     * Construtor.
     * @param totalIds total de IDs que devem ser gerados
     * @param idsStore alguma implementação thread-safe e super rápida que será utilizada para detectar
     * se algum ID foi gerado duplicado
     */
    protected IdMemoryRunnable(int totalIds, Set<Object> idsStore) {
        this.totalIds = totalIds;
        this.idsStore = idsStore;
    }

    protected int getTotalIds() {
        return totalIds;
    }

    @Override
    public final void run() {
        generate();
    }

    protected final void generate() {
        Log.info("Gerando " + getClass().getName() + " ...");
        for (int i = 0; i < getTotalIds(); i++) {
            Object id = generateId();
            // Aqui poderia lançar exceção se a intenção for apenas saber se algum ID foi gerado duplicado
            if (!idsStore.add(id)) {
                Log.warn("ID duplicado: " + id);
            }
        }
        Log.info("...gerou.");
    }

    /**
     * Sobrescrever para definir o local de onde os IDs são gerados.
     * @return o ID gerado
     */
    protected abstract Object generateId();

}
