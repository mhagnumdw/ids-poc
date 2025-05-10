package io.github.mhagnumdw.core;

import java.time.Duration;
import java.time.LocalDateTime;

import io.github.mhagnumdw.tsid.TsidFileRunnable;

/**
 * Gera os IDs para arquivos.
 */
public class MainFile {

    public static void main(String[] args) throws InterruptedException {
        long maxHeapSize = Runtime.getRuntime().maxMemory();
        System.out.println("Max Heap: " + (maxHeapSize / (1024 * 1024)) + " MB");

        LocalDateTime start = LocalDateTime.now();

        Log.info("Início");

        int totalThreads = 10; // Alterar para definir o total de threads
        int totalIdsPerThread = 10_000_000; // Alterar para definir a quantidade de IDs gerados por thread

        Thread[] threads = new Thread[totalThreads];

        // OBS: os tempos de geração dependem muito se usa
        // Random, SecureRandom, ThreadLocalRandom.current(), RandomGenerator.getDefault() etc

        for (int i = 0; i < threads.length; i++) {

            // UUIDv4
            // Runnable task = new UUIDv4Runnable(totalIdsPerThread, i);

            // UUIDv7
            // Runnable task = new UUIDv7Runnable(totalIdsPerThread, i);
            // Runnable task = new UUIDv7NoLibRunnable(totalIdsPerThread, i);
            // Runnable task = new UUIDv7NoLibV2FileRunnable(totalIdsPerThread, i);

            // TSID
            Runnable task = new TsidFileRunnable(totalIdsPerThread, i);

            threads[i] = new Thread(task);
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        LocalDateTime end = LocalDateTime.now();

        Duration duration = Duration.between(start, end);
        long minutes = duration.toMinutes();
        long seconds = duration.minusMinutes(minutes).getSeconds();
        long micros = duration.minusMinutes(minutes).minusSeconds(seconds).toNanos() / 1_000;

        String formatted = String.format("%d min, %d seg e %d microssegundos", minutes, seconds, micros);

        Log.info("Fim: " + formatted);
    }

}
