package Client;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MultiClient {
    public static void main(String[] args) {
        String name = "empty";
        String reply = "empty";
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your name (Please enter your name to join the chat): ");
        reply = sc.nextLine();
        name = reply;

        try (Socket socket = new Socket("localhost", 5000)) {
            PrintWriter cout = new PrintWriter(socket.getOutputStream(), true);

            ClientThread threadClient = new ClientThread(socket);
            new Thread(threadClient).start(); // start thread to receive message

            cout.println(reply + ": has joined chat-room.");
            do {
                String message = (name + " : ");
                reply = sc.nextLine();
                if (reply.equals("logout")) {
                    cout.println("logout");
                    break;
                }
                cout.println(message + reply);
            } while (!reply.equals("logout"));
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }
}