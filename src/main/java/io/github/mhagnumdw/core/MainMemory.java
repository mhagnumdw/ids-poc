package io.github.mhagnumdw.core;

import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import io.github.mhagnumdw.nanoid.NanoIdMemoryRunnable;
import io.github.mhagnumdw.tsid.TsidMemoryRunnable;
import io.github.mhagnumdw.tsrid.TsridMemoryRunnable;

/**
 * Gera os IDs para a memória. Imprime um WARN ao gerar um ID duplicado.
 */
public class MainMemory {

    private static final NumberFormat N_FORMATTER = NumberFormat.getInstance();

    public static void main(String[] args) throws InterruptedException {
        long maxHeapSize = Runtime.getRuntime().maxMemory();
        System.out.println("Max Heap: " + (maxHeapSize / (1024 * 1024)) + " MB");

        LocalDateTime start = LocalDateTime.now();

        Log.info("Início");

        int totalThreads = 10; // Alterar para definir o total de threads
        int totalIdsPerThread = 10_000_000; // Alterar para definir a quantidade de IDs gerados por thread

        // Set thread-safe
        Set<Object> idsStore = ConcurrentHashMap.newKeySet(totalThreads * totalIdsPerThread);

        Thread idsStoreCleaner = buildIdsStoreCleaner(idsStore);

        Thread[] threads = new Thread[totalThreads];

        // OBS: os tempos de geração dependem muito se usa
        // Random, SecureRandom, ThreadLocalRandom.current(), RandomGenerator.getDefault() etc

        for (int i = 0; i < threads.length; i++) {

            Runnable task = new TsidMemoryRunnable(totalIdsPerThread, idsStore);

            // UUIDv7
            // Runnable task = new UUIDv7NoLibV2MemoryRunnable(totalIdsPerThread, idsStore);
            // Runnable task = new UUIDv7NoLibMemoryRunnable(totalIdsPerThread, idsStore);
            // Runnable task = new UUIDv7MemoryRunnable(totalIdsPerThread, idsStore);

            // ULID
            // Runnable task = new ULIDMemoryRunnable(totalIdsPerThread, idsStore);

            // NanoId
            // Runnable task = new NanoIdMemoryRunnable(totalIdsPerThread, idsStore);

            // TSRID
            // Runnable task = new TsridMemoryRunnable(totalIdsPerThread, idsStore);

            threads[i] = new Thread(task);
        }

        for (Thread thread : threads) {
            thread.start();
        }
        idsStoreCleaner.start();

        for (Thread thread : threads) {
            thread.join();
        }
        // idsStoreCleaner.join();

        LocalDateTime end = LocalDateTime.now();

        Duration duration = Duration.between(start, end);
        long minutes = duration.toMinutes();
        long seconds = duration.minusMinutes(minutes).getSeconds();
        long micros = duration.minusMinutes(minutes).minusSeconds(seconds).toNanos() / 1_000;

        String formatted = String.format("%d min, %d seg e %d microssegundos", minutes, seconds, micros);

        Log.info("Fim: " + formatted);
    }

    private static Thread buildIdsStoreCleaner(Set<Object> idsStore) {
        return new Thread(() -> {
            int totalGerado = 0;
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(10000); // a cada 10 segundos executa a limpeza
                    if (idsStore.isEmpty()) { // não está mais gerando IDs
                        Log.info("Encerrando thread de limpeza pois novos IDs não foram adicionados desde a última limpeza. Total de IDs gerados: " + N_FORMATTER.format(totalGerado));
                        break;
                    }
                    totalGerado += idsStore.size();
                    Log.info("Executando limpeza. Total de IDs na store: " + N_FORMATTER.format(idsStore.size()) + ". Total gerado até agora: " + N_FORMATTER.format(totalGerado));
                    idsStore.clear();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // preserva o status de interrupção
                    break;
                }
            }
        }, "Cleaner");
    }

}
