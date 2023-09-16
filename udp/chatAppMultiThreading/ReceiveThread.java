package networking.udp.chatAppMultiThreading;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ReceiveThread implements Runnable{
    private DatagramSocket DS;
    private byte[] dataReceived = new byte[256];
    ReceiveThread(DatagramSocket ds){
        this.DS = ds;
    }
    @Override
    public void run() {
        DatagramPacket dpr = new DatagramPacket(dataReceived, dataReceived.length);
        try {
            DS.receive(dpr);
            if(dpr.getData()!=null){
                System.out.println(new String(dpr.getData(), 0, dpr.getLength()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
