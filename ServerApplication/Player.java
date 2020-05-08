package com.company;

/**
 * An instance of this class holds the name the id the color and the submits number of a player
 */
public class Player {
    private int id;
    private String name;
    private Board board;
    String color;
    int submitsNo;

    public Player(int id, String name, Board board, String color) {
        this.id = id;
        this.name = name;
        this.board = board;
        this.color = color;
        submitsNo = 0;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Board getBoard() {
        return board;
    }

    /**
     * this method is called everytime a particular player submits a move
     */
    public void increaseSubmits() {
        submitsNo++;
    }

    public String getColor() {
        return color;
    }

    public int getSubmitsNo() {
        return submitsNo;
    }
}
