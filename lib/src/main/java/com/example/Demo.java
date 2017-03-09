package com.example;

import com.example.udp.Rece;
import com.example.udp.Send;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Demo {
    public static void main(String[] a) throws IOException {
        // threadDemo();
        // threadDemo1();
        //IODemo();
        //inetAdressDemo();
        udpDemo();

    }

    private static void udpDemo() throws SocketException {
        DatagramSocket sendDs = new DatagramSocket();
        DatagramSocket receDs = new DatagramSocket(10225);
        new Thread(new Send(sendDs)).start();
        new Thread(new Rece(receDs)).start();
    }

    private static void inetAdressDemo() throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        System.out.println(inetAddress);
        inetAddress = InetAddress.getByName("www.baidu.com");
        System.out.println(inetAddress);
        System.out.println(inetAddress.getHostAddress());
        System.out.println(inetAddress.getHostName());
    }

    private static void threadDemo1() {
        Person p = new Person();
        new Thread(new Producer(p)).start();
        new Thread(new Consumer(p)).start();
    }

    private static void threadDemo() {
        SellDemo sellDemo = new SellDemo();
        new Thread(sellDemo, "A").start();
        new Thread(sellDemo, "B").start();
        new Thread(sellDemo, "C").start();
    }

    private static void IODemo() throws IOException {
        InputStream inputStream = new FileInputStream(new File("E:\\Recovery\\123.pdf"));
        byte[] buff = new byte[1024];
        int len;
        while ((len = inputStream.read(buff)) != -1) {
            System.out.println(new String(buff, 0, buff.length));
            System.out.println("==============================");
        }
        inputStream.close();
    }
}
