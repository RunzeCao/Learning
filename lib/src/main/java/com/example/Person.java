package com.example;

/**
 * Created by CRZ on 2017/2/22 17:44.
 */

class Person {
    private String name;
    private String gender;
    private Boolean isEmpty = Boolean.TRUE;//内存为空

    private String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    private String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    void set(String name, String gender) {
        synchronized (this) {
            while (!isEmpty == Boolean.TRUE) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.name = name;
                this.gender = gender;
                isEmpty = Boolean.FALSE;
                this.notifyAll();
            }
        }
    }

    void get(){
        synchronized (this){
            while (!isEmpty == Boolean.FALSE){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("name "+getName()+",   "+"gender "+getGender());
            isEmpty = Boolean.TRUE;
            this.notifyAll();
        }
    }

}
