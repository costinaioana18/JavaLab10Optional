package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * An instance of this class will read commands from the keyboard and it will send them to the server. The client stops when it reads from the keyboard the string "exit".
 */
public class GameClient {

    /**
     * We keep reading commands from the keyboard as long as the game runs.
     * We can send commands such as "Join",  "Exit", and submitting a move, by writing 2 integers. For example: "3 2"
     */
    public static void main(String[] args) throws IOException {
        String serverAddress = "127.0.0.1";
        int PORT = 8100;
        try (
                Socket socket = new Socket(serverAddress, PORT);
                PrintWriter out =
                        new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()))) {

            boolean run = true;
            while (run) {
                System.out.print("Enter a command : ");
                Scanner scanner = new Scanner(System.in);
                String request = scanner.nextLine();
                if (request.equals("Exit")) run = false;
                out.println(request);
                String response = in.readLine();
                System.out.println(response);
            }
        } catch (UnknownHostException e) {
            System.err.println("No server listening... " + e);
        }
    }
}