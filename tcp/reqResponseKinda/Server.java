package networking.tcp.reqResponseKinda;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class Server {
    private static ServerSocket serverSocket;
    private static HashMap<String, String> dictionary = new HashMap<>();

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(4321);
            System.out.println("Server is listening on port 4321...");
        } catch (IOException e) {
            System.err.println("Creating a server socket on port 4321 failed");
            System.exit(1);
        }

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted a connection from " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            } catch (IOException e) {
                System.err.println("Error accepting client connection");
            }
        }
    }

    static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private PrintWriter output;
        private BufferedReader input;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            try {
                output = new PrintWriter(clientSocket.getOutputStream(), true);
                input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                System.err.println("Error setting up input/output streams for client");
            }
        }

        @Override
        public void run() {
            String inputString;

            try {
                while ((inputString = input.readLine()) != null) {
                    System.out.println("Received from " + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + ": " + inputString);

                    // Simple echo protocol: Send back the received message to the client
                    output.println("Server Response: " + inputString);
                }
            } catch (IOException e) {
                System.err.println("Input/output error occurred for client " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
            } finally {
                try {
                    clientSocket.close();
                    System.out.println("Connection closed for " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
                } catch (IOException e) {
                    System.err.println("Error closing client socket");
                }
            }
        }
    }
}
