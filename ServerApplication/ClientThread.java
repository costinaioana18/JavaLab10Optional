package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * An instance of this class communicates with a client
 */
class ClientThread extends Thread {
    private Socket socket = null;
    private GameServer game;
    private boolean run = true;
    private int id;

    public ClientThread(Socket socket, GameServer game) {
        this.socket = socket;
        this.game = game;
    }

    /**
     * the method receives commands from the players and executes them while the program is running
     * It can receive commands as 'Join', submit ( represented by 2 numbers, for example '6 7' ), and 'Exit'
     */
    public void run() {

        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            while (run) {
                String request = in.readLine();
                PrintWriter out = new PrintWriter(socket.getOutputStream());

                if (request.equals("Join")) {
                    joinGame(out, request);
                } else if (request.equals("Exit")) {
                    System.out.println("Player " + id + " left the game");
                    String raspuns = "Exit";
                    out.println(raspuns);
                    out.flush();
                    run = false;
                    game.decreasePlayersNo();
                } else {
                    insertPiece(out, request);
                }

            }
        } catch (IOException e) {
            System.err.println("Communication error... " + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    /**
     * The method allows a player to join the game
     * This method is called when a clients command is 'Join'
     */
    private void joinGame(PrintWriter out, String request) {

        if (game.getPlayersNo() == 1) {
            id = 1;
            String raspuns = "You are player 1. Your colour is black. Wait for your opponent!";
            out.println(raspuns);
            out.flush();
        }
        if (game.getPlayersNo() == 2) {
            id = 2;
            String raspuns = "You are player 2. Your colour is white.";
            out.println(raspuns);
            out.flush();
        }
    }

    /**
     * The method calls another method in order ti insert a piece in the table and sends an appropriate response to the client
     * This method is called when a client wants to submit a move
     */
    private void insertPiece(PrintWriter out, String request) {

        String raspuns = game.insertPiece(request, id);

        out.println(raspuns);
        out.flush();
    }

    /**
     * The method returns the color of the player
     */
    private String getColor(int id) {
        if (id == 1) return "black";
        else return "white";
    }
}