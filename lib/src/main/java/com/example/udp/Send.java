package com.example.udp;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Send implements Runnable {
    private DatagramSocket datagramSocket;

    public Send(DatagramSocket ds) {
        super();
        this.datagramSocket = ds;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                byte[] buf = line.getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, InetAddress.getByName("localhost"), 10225);
                datagramSocket.send(datagramPacket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
