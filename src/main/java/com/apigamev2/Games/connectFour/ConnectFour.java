package com.apigamev2.Games.connectFour;

import com.apigamev2.Games.game;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ConnectFour implements game {

    final int AmountToWin = 4;
    String[][] board;
    int height;
    int width;

    //Players strings : "X" or "O" or "Won X" or "Won O" or "Draw"
    String currentPlayer = "X";

    Set<Integer> playableSet;

    public ConnectFour(int height, int width) {
        this.height = height;
        this.width = width;
        board = new String[height][width];
        playableSet = new HashSet<>(width);
        for (int colum = 0; colum < width; colum++) {
            playableSet.add(colum);
        }

        for (String[] strings : board) {
            Arrays.fill(strings, "");
        }
    }

    /**
     * @param colum | colum >= 0 && colum < width
     * @param player should be "O" or "X"
     * @return returns a string:
     *              invalid is the play is invalid,
     *              Won X or O or draw if the game is over
     *              continue when a next play is needed
     */
    public String play(int colum, String player) {

        if (gameIsDone()) {
            return currentPlayer;
        }

        if (!isValid(colum, player))
            return "invalid";

        // Find the row where the play should be placed.
        int row = board.length - 1;
        while (row >= 0 && !board[row][colum].equals("")) {
            row--;
        }

        //Do the play
        board[row][colum] = player;
        currentPlayer = player.equals("X") ? "O" : "X";
        if (row == 0)
            playableSet.remove(colum);

        //Check if 4 in a row
        if (this.won(row, colum, player)){
            currentPlayer = "Won " + player;
            return player;
        }
        //Check if draw
        if (playableSet.isEmpty()) {
            currentPlayer = "Draw";
            return currentPlayer;
        }

        return "continue";
    }

    private boolean gameIsDone() {
        return currentPlayer.length() > 1;
    }

    private boolean isValid(int colum, String player) {
        if (!player.equals(currentPlayer))
            return false;
        else return colum >= 0 && colum < board[0].length && playableSet.contains(colum);
    }

    private boolean won(int row, int colum, String player) {

        int count = 1;

        //check vertical, (can only be below)
        int i = row + 1;
        while (i < this.height && board[i][colum].equals(player)) {
            i++;
            count++;
        }
        System.out.println("count: vertical: " + count);
        if (count >= this.AmountToWin) {
            return true;
        }

        //check horizontally (first to the left, then right)
        count = 1;
        int j = colum - 1;
        while (j >= 0 && board[row][j].equals(player)) {
            j--;
            count++;
        }
        j = colum + 1;
        while (j < this.width && board[row][j].equals(player)) {
            j++;
            count++;
        }
        System.out.println("count: horizontally: " + count);
        if (count >= this.AmountToWin) {
            return true;
        }

        //check diagonal, left up to right bottom
        count = 1;
        j = colum - 1;
        i = row - 1;
        while (j >= 0 && i >= 0 && board[i][j].equals(player)){
            j--;
            i--;
            count++;
        }
        j = colum + 1;
        i = row + 1;
        while (i < this.height && j < this.width && board[i][j].equals(player)){
            i++;
            j++;
            count++;
        }
        System.out.println("count: diagonal (left, up) -> (right down): " + count);
        if (count >= this.AmountToWin) {
            return true;
        }

        count = 1;
        j = colum - 1;
        i = row + 1;
        while (j >= 0 && i < this.height && board[i][j].equals(player)){
            j--;
            i++;
            count++;
        }
        j = colum + 1;
        i = row -1;
        while (j < this.width && i >= 0 && board[i][j].equals(player)) {
            j++;
            i--;
            count++;
        }
        System.out.println("count: diagonal (left, down) -> (right, up): " + count);
        return count >= this.AmountToWin;
    }

    public void printStatus() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (String[] row : board) {
            sb.append("| ");
            for (String elm : row) {
                sb.append(elm.equals("") ? " " : elm);
                sb.append(" | ");
            }
            sb.append("\n");
        }
        System.out.print(sb);
        System.out.println("currentPlayer: " + currentPlayer);
    }

    public String[][] getBoard() {
        String[][] copy = new String[height][width];
        for (int row = 0; row < this.height; row++) {
            if (this.width >= 0) System.arraycopy(board[row], 0, copy[row], 0, this.width);
        }
        return copy;
    }
}
