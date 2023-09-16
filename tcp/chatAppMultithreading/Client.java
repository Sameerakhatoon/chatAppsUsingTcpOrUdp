package networking.tcp.chatAppMultithreading;

import java.io.*;
import java.net.*;

public class Client {
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
                        System.out.println("Server broadcast: " + message);
                    }
                } catch (IOException e) {
                    System.err.println("Error reading server broadcast");
                }
            });
            receiveThread.start();

            String request;

            while (true) {
                System.out.print("Enter your request (or 'exit' to quit): ");
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
