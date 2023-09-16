package networking.udp.chatAppOld;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class UDPServer {
    public static void main(String[] args) throws Exception, SocketException{
        System.out.println("udpServer started");
        DatagramSocket ds = new DatagramSocket(9876);
        byte[] dataReceived = new byte[256];
        ArrayList<InetAddress> lsOfIpAdd = new ArrayList();
        ArrayList<Integer> lsOfPorts = new ArrayList();
        while(true){
            DatagramPacket dp = new DatagramPacket(dataReceived, dataReceived.length);
            ds.receive(dp);
            lsOfIpAdd.add(dp.getAddress());
            lsOfPorts.add(dp.getPort());
            String str = new String(dp.getData());
            InetAddress senderIpAdd = dp.getAddress();
            int senderPort = dp.getPort();
            String broadCastMsg = "received msg from " + senderIpAdd + " " + senderPort + " : " + str;
            System.out.println(broadCastMsg);
            for(int i = 0;i<lsOfIpAdd.size();i++){
                if(senderIpAdd!=lsOfIpAdd.get(i) &&senderPort!=lsOfPorts.get(i).intValue()){
                    ds.send(new DatagramPacket(broadCastMsg.getBytes(), broadCastMsg.length(), lsOfIpAdd.get(i), lsOfPorts.get(i).intValue()));
                }
            }
        }
    }
}
