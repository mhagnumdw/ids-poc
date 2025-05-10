package io.github.mhagnumdw.uuid;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Gerador de UUIDv7 puramente Java, sem libs.
 *
 * @see https://antonz.org/uuidv7/#java
 */
public final class UUIDv7NoLib {

    private static final Random random = new SecureRandom();
    // private static final Random random = ThreadLocalRandom.current();
    // private static final Random random = new Random();

    private UUIDv7NoLib() {}

    public static UUID randomUUID() {
        byte[] value = randomBytes();
        ByteBuffer buf = ByteBuffer.wrap(value);
        long high = buf.getLong();
        long low = buf.getLong();
        return new UUID(high, low);
    }

    public static byte[] randomBytes() {
        // random bytes
        byte[] value = new byte[16];
        random.nextBytes(value);

        // current timestamp in ms
        ByteBuffer timestamp = ByteBuffer.allocate(Long.BYTES);
        timestamp.putLong(System.currentTimeMillis());

        // timestamp
        System.arraycopy(timestamp.array(), 2, value, 0, 6);

        // version and variant
        value[6] = (byte) ((value[6] & 0x0F) | 0x70);
        value[8] = (byte) ((value[8] & 0x3F) | 0x80);

        return value;
    }

}
