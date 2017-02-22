package com.example;

/**
 * Created by CRZ on 2017/2/22 17:56.
 */

class Consumer implements Runnable {
    private Person p;

    Consumer(Person p) {
        super();
        this.p = p;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            p.get();
        }
    }
}
