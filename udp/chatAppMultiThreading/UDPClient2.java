package networking.udp.chatAppMultiThreading;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient2 {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("client2 has started");
        Scanner input = new Scanner(System.in);
        InetAddress serverAddress = InetAddress.getByName("localhost");
        DatagramSocket ds = new DatagramSocket(9874);
        ReceiveThread receiveThread = new ReceiveThread(ds);
        Thread thread = new Thread(receiveThread);
        thread.start();
        while (true){
            byte[] dataSent = input.nextLine().getBytes();
            DatagramPacket dps = new DatagramPacket(dataSent, dataSent.length, serverAddress, 9876);
            ds.send(dps);
        }
    }
}
