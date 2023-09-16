package networking.udp.chatAppOld;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient1 {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("client1 has started");
        Scanner input = new Scanner(System.in);
        InetAddress serverAddress = InetAddress.getByName("localhost");
        DatagramSocket ds = new DatagramSocket(9875);
        byte[] dataReceived = new byte[256];
        while (true){
            byte[] dataSent = input.nextLine().getBytes();
            DatagramPacket dps = new DatagramPacket(dataSent, dataSent.length, serverAddress, 9876);
            ds.send(dps);
            DatagramPacket dpr = new DatagramPacket(dataReceived, dataReceived.length);
            ds.receive(dpr);
            if(dpr.getData()!=null){
                System.out.println(new String(dpr.getData()));
            }
        }
    }
}
