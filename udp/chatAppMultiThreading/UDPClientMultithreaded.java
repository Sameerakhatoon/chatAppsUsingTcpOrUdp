package networking.udp.chatAppMultiThreading;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class UDPClientMultithreaded {
    public static void main(String[] args) throws IOException {
        System.out.println("client1 has started");
        Scanner input = new Scanner(System.in);
        InetAddress serverAddress = InetAddress.getByName("localhost");
        DatagramSocket ds = new DatagramSocket(9875);

        Thread receiveThread = new Thread(() -> {
            try {
                byte[] dataReceived = new byte[256];
                while (true) {
                    DatagramPacket dpr = new DatagramPacket(dataReceived, dataReceived.length);
                    ds.receive(dpr);
                    if (dpr.getData() != null) {
                        System.out.println("Received: " + new String(dpr.getData()));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        receiveThread.start();

        while (true) {
            byte[] dataSent = input.nextLine().getBytes();
            DatagramPacket dps = new DatagramPacket(dataSent, dataSent.length, serverAddress, 9876);
            ds.send(dps);
        }
    }
}
