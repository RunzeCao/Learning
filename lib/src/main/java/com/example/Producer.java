package com.example;

/**
 * Created by CRZ on 2017/2/22 17:53.
 */

class Producer implements Runnable {
    private Person p;

    Producer(Person p) {
        super();
        this.p = p;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                p.set("狒狒", "男");
            } else {
                p.set("闪电", "女");
            }
        }
    }
}
