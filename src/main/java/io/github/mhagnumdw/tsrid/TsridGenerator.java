package io.github.mhagnumdw.tsrid;

import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;

/**
 * WIP (Work In Progress): uma implementação minha que tem
 * N bits de timestamp e N bits totalmente aleatórios, totalizando
 * 64 bits. Com base na ideia TSID mas sem o componente de node.
 */
public class TsridGenerator {

    private static final Base64.Encoder BASE64_URL_ENCODER = Base64.getUrlEncoder().withoutPadding();

    // Caracteres Crockford (0-9, A-Z excluindo I,L,O,U)
    private static final String CROCKFORD_CHARS = "0123456789ABCDEFGHJKMNPQRSTVWXYZ";

    // Epoch: 2020-01-01 00:00:00 UTC (reduz o tamanho necessário)
    private static final long EPOCH = 1577836800000L;
    private static final long TIMESTAMP_BITS = 42;
    private static final long MAX_TIMESTAMP = (1L << TIMESTAMP_BITS) - 1;

    private TsridGenerator() { }

    public static long generate() {
        // Componente temporal (42 bits)
        long currentTime = System.currentTimeMillis() - EPOCH;
        long timestamp = currentTime & MAX_TIMESTAMP;

        // Componente aleatório (22 bits)
        int random = ThreadLocalRandom.current().nextInt(0x400000); // 2^22 = 4.194.304

        // Combinação dos componentes
        return (timestamp << 22) | random;
    }

    public static String toCrockfordBase32(long tsrid) {
        StringBuilder sb = new StringBuilder(13);
        for (int i = 0; i < 13; i++) {
            int shift = 60 - (i * 5);
            int index = (int) ((tsrid >> shift) & 0x1F);
            sb.append(CROCKFORD_CHARS.charAt(index));
        }
        return sb.toString();
    }

    public static long fromCrockfordBase32(String encoded) {
        if (encoded.length() != 13) throw new IllegalArgumentException("Invalid TSRID length");

        long result = 0;
        for (char c : encoded.toCharArray()) {
            int value = CROCKFORD_CHARS.indexOf(Character.toUpperCase(c));
            if (value == -1) throw new IllegalArgumentException("Invalid character: " + c);
            result = (result << 5) | value;
        }
        return result;
    }

    // TODO: remover?
    public static String generateAsString() {
        return Long.toUnsignedString(generate(), 36).toUpperCase(); // Base36 compacto
    }

    // TODO: remover?
    public static String toBase64(long tsrid) {
        byte[] bytes = new byte[8];
        for(int i = 7; i >= 0; i--) {
            bytes[i] = (byte) (tsrid & 0xFF);
            tsrid >>= 8;
        }
        return BASE64_URL_ENCODER.encodeToString(bytes);
    }
}
