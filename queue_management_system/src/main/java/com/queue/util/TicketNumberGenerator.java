package com.queue.util;

import java.util.concurrent.atomic.AtomicInteger;

public final class TicketNumberGenerator {

    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    private TicketNumberGenerator() {}

    public static String generate() {
        int v = COUNTER.updateAndGet(i -> (i >= 999) ? 1 : i + 1);
        return "A" + String.format("%03d", v);
    }
}
