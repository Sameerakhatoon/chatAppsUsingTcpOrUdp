package networking.tcp.chatApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(9876, 10, InetAddress.getByName("127.0.0.1"));
        System.out.println("server started");
        while(true){
            Socket s = ss.accept();

            PrintWriter outputWriter = new PrintWriter(s.getOutputStream(), true); //to send msg
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(s.getInputStream())); // to read msg


            //sending msg
            String str = inputReader.readLine();
            if(str.equals("quit")){
                s.close();
                break;
            }
            outputWriter.write(str);//.println(str);
            //print msg
            String serverMsg = inputReader.readLine();
            System.out.println(serverMsg);
        }
    }
}
