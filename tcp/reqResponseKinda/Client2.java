package networking.tcp.reqResponseKinda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client2 {
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        PrintWriter output = null;
        BufferedReader serverInput = null;

        try {
            socket = new Socket("localhost", 4321);
            output = new PrintWriter(socket.getOutputStream(), true);
            serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.print("Enter your request (or 'exit' to quit): ");
                String request = userInput.readLine();

                if (request.equalsIgnoreCase("exit")) {
                    // If the user enters 'exit', break out of the loop and close resources.
                    break;
                }

                output.println(request);
                System.out.println("Server Response: " + serverInput.readLine());
            }
        } catch (UnknownHostException e) {
            System.err.println("Unknown host");
        } catch (IOException e) {
            System.err.println("Cannot connect to server");
        } finally {
            // Ensure proper cleanup of resources.
            if (output != null) {
                output.close();
            }
            if (serverInput != null) {
                serverInput.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
