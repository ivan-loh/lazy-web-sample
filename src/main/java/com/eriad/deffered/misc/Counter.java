package com.eriad.deffered.misc;

import java.util.concurrent.atomic.AtomicInteger;

public enum Counter {

    INSTANCE;

    private AtomicInteger counter = new AtomicInteger(0);

    public int incr() {
        return counter.incrementAndGet();
    }

    public int get() {
        return counter.intValue();
    }
}
