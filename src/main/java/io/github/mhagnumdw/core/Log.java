package io.github.mhagnumdw.core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Log {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSS");

    private Log() {}

    public static void info(Object msg) {
        log("INFO", msg);
    }

    public static void warn(Object msg) {
        log("WARN", msg);
    }

    private static void log(String level, Object msg) {
        System.out.println("[" + level + "] [" + FORMATTER.format(LocalDateTime.now()) + "] [" + Thread.currentThread().getName() + "] " + msg);
    }

}
