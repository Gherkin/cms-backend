package com.github.gherkin.service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by niyohn on 2015-11-01.
 */
public class Incrementor extends AtomicInteger {
    public Incrementor(int initialValue) {
        super(initialValue);
    }

    public Incrementor() {
    }
}
