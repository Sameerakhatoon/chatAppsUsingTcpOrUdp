package networking.tcp.chatAppMultithreading;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
    private static ServerSocket serverSocket;
    private static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

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
                clientHandlers.add(clientHandler);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            } catch (IOException e) {
                System.err.println("Error accepting client connection");
            }
        }
    }

    public static void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler client : clientHandlers) {
            if (client != sender) {
                client.sendMessage("from " + sender.clientSocket.getInetAddress() + sender.clientSocket.getPort() + " " + message);
            }
        }
    }

    static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private BufferedReader input;
        private PrintWriter output;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            try {
                input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                output = new PrintWriter(clientSocket.getOutputStream(), true);
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
                    // Process client requests here based on your application's needs

                    // Broadcast the received message to all clients
                    Server.broadcastMessage(inputString, this);
                }
            } catch (IOException e) {
                System.err.println("Input error occurred for client " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
            } finally {
                try {
                    clientSocket.close();
                    clientHandlers.remove(this);
                    System.out.println("Connection closed for " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
                } catch (IOException e) {
                    System.err.println("Error closing client socket");
                }
            }
        }

        public void sendMessage(String message) {
            output.println(message);
        }
    }
}
