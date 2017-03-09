package com.example;

/**
 * Created by CRZ on 2017/2/22 17:44.
 */

class Person {
    private String name;
    private String gender;
    private Boolean isEmpty = false;//内存为空

    private String getGender() {
        return gender;
    }


    private String getName() {
        return name;
    }


    void set(String name, String gender) {
        synchronized (this) {
            //while判断标记，解决了线程获取执行权后，是否要运行  判断多次

            while (isEmpty) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }  }
                this.name = name;
                this.gender = gender;
                isEmpty = true;
                this.notifyAll();

        }
    }

    void get(){
        synchronized (this){
            while (!isEmpty){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("name "+getName()+",   "+"gender "+getGender());
            isEmpty =false;
            this.notifyAll();
        }
    }

}
