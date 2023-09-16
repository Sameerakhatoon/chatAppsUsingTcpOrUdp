package networking.tcp.chatAppMultithreading;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client2 {
    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 4321);
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Connected to the server.");

            // Create a thread to listen to broadcasted messages
            Thread receiveThread = new Thread(() -> {
                try {
                    String message;
                    while ((message = serverInput.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    System.err.println("Error reading server broadcast");
                }
            });
            receiveThread.start();

            String request;
            System.out.println("Enter your message (or 'exit' to quit): ");
            while (true) {
                request = userInput.readLine();

                if (request.equalsIgnoreCase("exit")) {
                    break;
                }

                output.println(request);
            }
        } catch (UnknownHostException e) {
            System.err.println("Unknown host");
        } catch (IOException e) {
            System.err.println("Cannot connect to server");
        }
    }
}
