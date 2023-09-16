package networking.tcp.chatApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        InetAddress serverIpAdd = InetAddress.getByName("127.0.0.1");
        int serverPort = 9876;
        Scanner input = new Scanner(System.in);
        Socket s = new Socket(serverIpAdd, serverPort);
//        InetSocketAddress localEndpoint = new InetSocketAddress("127.0.0.1", 9875);
//        s.bind(localEndpoint);
        while(true){
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(s.getInputStream())); // to read msg
            PrintWriter outputWriter = new PrintWriter(s.getOutputStream(), true); //to send msg

            //sending msg
            String str = input.nextLine();
            if(str.equals("quit")){
                s.close();
                break;
            }
//            outputWriter.println(str);
            outputWriter.write(str);//.println(str);

            //print msg
            String serverMsg = inputReader.readLine();
            System.out.println(serverMsg);
        }

    }
}
