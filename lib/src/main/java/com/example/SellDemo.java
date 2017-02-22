package com.example;

class SellDemo implements Runnable {
    private int num = 100;

    @Override
    public void run() {
        for (int i = 0; i < 200; i++) {
            synchronized (this) {
                if (num > 0) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "-----" + num--);
                }
            }
        }
    }
}

