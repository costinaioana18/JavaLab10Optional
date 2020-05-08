package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * An instance of this class holds the game's board, represented by a 15*15 matrix
 * It makes sure to announce when there is a winner
 * It creates the html representation at the end of the game
 */
public class Board {
    private int[][] board = new int[15][15];
    private int winner;
    private int submitsCount;
    private String winningType;
    private Player player1 = new Player(1, "Player1", this, "black");
    private Player player2 = new Player(2, "Player2", this, "white");

    public Board() {
        for (int i = 0; i <= 14; i++)
            for (int j = 0; j <= 14; j++)
                board[i][j] = 0;
        submitsCount = 0;
        winner = 0;
    }


    /**
     * Before inserting a piece it checks if the selected place is empty. If it is, it inserts it by changing the value of the corespondent matrix element, and it increases the submits number
     * It prints the board and it checks if there is a winner. If there is one, it means the game is over, it creates the html game representation.
     *
     * @param row    the row where the client wants to insert the piece
     * @param column the column where the player wants to insert the piece
     * @param id     the id of the player
     * @return an appropriate response, letting the client know if his piece was successfully inserted or if he has to choose another space
     */
    public String insertPiece(int row, int column, int id) {
        String response;

        if (board[row][column] == 0) {
            if (id == 1) player1.increaseSubmits();
            else player2.increaseSubmits();
            submitsCount++;
            board[row][column] = id;
            response = "Your piece was inserted at row no " + row + " and column no " + column;
        } else response = "You must choose an empty place! Try again!";

        printBoard();
        checkWinner();

        if (winner != 0) {
            System.out.println("Game over. The winner is: Player" + winner);
            response = "Game over. The winner is: Player" + winner;
            uploadHtml();
        }
        return response;
    }

    /**
     * it prints the board
     */
    public void printBoard() {
        System.out.println("The board looks like this: ");
        for (int[] row : board)
            System.out.println(Arrays.toString(row));
    }

    /**
     * it checks if there is a winner, by looking for 5 identical pieces in a row in a column, diagonal or row.
     * if it finds a winner, it makes sure to change the winner variable, which holds the winner's id, and the winningType variable, which holds the way a player won ( by having 5 consecutive pieces in a 'row', 'column' or 'diagonal')
     */
    public void checkWinner() {
        for (int i = 0; i <= 11; i++) {
            int count = 1;
            for (int j = 0; j <= 11; j++) {
                if (board[i][j] != 0 && board[i][j] == board[i][j + 1]) count++;
                else count = 1;
                if (count == 5) {
                    winner = board[i][j];
                    winningType = "row";
                }
            }
        }

        for (int j = 0; j <= 11; j++) {
            int count = 1;
            for (int i = 0; i <= 11; i++) {
                if (board[i][j] != 0 && board[i][j] == board[i + 1][j]) count++;
                else count = 1;
                if (count == 5) {
                    winner = board[i][j];
                    winningType = "column";
                }
            }
        }

        for (int m = 0; m <= 12; m++)
            for (int n = 0; n <= 12; n++) {
                int i = m;
                int j = n;
                int count = 1;
                while (j <= 12 && i <= 12) {
                    if (board[i][j] != 0 && board[i][j] == board[i + 1][j + 1]) count++;
                    else count = 1;
                    if (count == 5) {
                        winner = board[i][j];
                        winningType = "diagonal";
                    }

                    j++;
                    i++;

                }

            }

        for (int m = 3; m <= 12; m++)
            for (int n = 3; n <= 12; n++) {
                int i = m;
                int j = n;
                int count = 1;
                while (j < 14 && i < 14 && i >= 1) {
                    if (board[i][j] != 0 && board[i][j] == board[i - 1][j + 1]) count++;
                    else count = 1;
                    if (count == 5) {
                        winner = board[i][j];
                        winningType = "diagonal";
                    }

                    j++;
                    i--;

                }

            }

    }

    /**
     * It creates the html representation file, by writing the winner, the winning type, how many submits were made & other details
     */
    public void uploadHtml() {
        try {
            FileWriter fileWriter = new FileWriter("gameRepresentation.html");
            fileWriter.write("  <!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>My Game</title>\n" +
                    "</head>\n" +
                    "<body bgcolor=\"grey\"> \n" + "<h2>The game's report:</h2> \n ");
            fileWriter.write("<p> The winner is: Player" + winner + "</p>");
            fileWriter.write("<p> The player won by having 5 consecutive pieces in a " + winningType + "</p>");
            fileWriter.write("<p> The total number of submits: " + submitsCount + "</p>");
            int emptySpaces = 15 * 15 - submitsCount;
            fileWriter.write("<p> There are " + player1.getSubmitsNo() + " " + player1.getColor() + " pieces on the board</p>");
            fileWriter.write("<p> There are " + player2.getSubmitsNo() + " " + player2.getColor() + " pieces on the board</p>");
            fileWriter.write("<p> The number of empty spaces left on the board: " + emptySpaces + "</p>");
            fileWriter.write("</body>\n" + "</html>");
            fileWriter.close();
        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }

}
