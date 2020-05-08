package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/**
 * An instance of this class will create a ServerSocket running at a specified port
 * it manages the game, the board and the players
 */
public class GameServer {
    public static final int PORT = 8100;
    private int playersNo = 0;
    private int gameStarted = 0;
    public Board board = new Board();

    /**
     * it creates a clientThread for each player
     * it makes sure that exactly 2 players join the game
     */
    public GameServer() throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);

            while (true) {

                if (playersNo < 2) {
                    if (playersNo == 0) System.out.println("Waiting for two players ...");

                    else System.out.println("Player 1 joined the game. Waiting for another player ...");
                    Socket socket = serverSocket.accept();
                    playersNo++;
                    new ClientThread(socket, this).start();
                    if (playersNo == 2) {
                        System.out.println("Player 2 joined the game. Game started");
                        gameStarted = 1;
                    }
                }


            }
        } catch (IOException e) {
            System.err.println("Ooops... " + e);
        } finally {
            serverSocket.close();
        }
    }

    public int getPlayersNo() {
        return playersNo;
    }

    /**
     * this method is called in case someone is exiting the game
     */
    public void decreasePlayersNo() {
        playersNo--;
    }

    /**
     * The method converts the request from the client from a string to two integers, representing the row and the column where the client wants to insert a piece
     * It calls another function to 'physically' insert the piece on the board, having the row and column numbers and the id as parameters
     *
     * @param request the raw and the column number where the clients wants to insert a piece as a string
     * @param id      the id of the player
     * @return the response for the client: either his pieces was inserted, either he has to choose another place
     */
    public String insertPiece(String request, int id) {
        String response;
        int row;
        int column;
        String[] numbers = request.split(" ");
        row = Integer.parseInt(numbers[0]);
        column = Integer.parseInt(numbers[1]);

        response = board.insertPiece(row, column, id);
        return response;
    }
}
