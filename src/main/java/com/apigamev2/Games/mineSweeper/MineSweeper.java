package com.apigamev2.Games.mineSweeper;

import com.apigamev2.Games.game;

import java.util.*;

public class MineSweeper implements game {

    Tiles[][] board;
    int height;
    int width;

    int amountOfBoms;
    int flagUsed = 0;
    int correctUsedFlags = 0;

    boolean firstPlay = true;
    boolean lose = false;
    boolean won = false;

    public MineSweeper(int height, int width, int amountOfBoms) {
        this.height = height;
        this.width = width;

        this.amountOfBoms = amountOfBoms;
    }

    public String play(int row, int colum, boolean flag) {
        if (!validCord(new int[] {row, colum}))
            return "invalid";

        if (firstPlay) {
            this.generateBoard(row, colum);
            firstPlay = false;
        }

        if (board[row][colum].isRevealed())
            return "continue";

        if (lose)
            return "Lose";

        if (won)
            return "Won";

        // Do the flag logic
        if (flag) {
            this.flag(row, colum);
            return won ? "Won" : "continue";
        }

        if (board[row][colum] instanceof Bom) {
            lose = true;
            return "Lose";
        }


        floodFil(row, colum);
        System.out.println("the cell we clicked");
        System.out.println(board[row][colum]);
        return "continue";
    }

    private void floodFil(int row, int colum) {
        Queue<int[]> fill = new LinkedList<>();
        fill.add(new int[] {row, colum});
        int[] cord = fill.poll();
        while (cord != null) {
            board[cord[0]][cord[1]].reveal();
            if (board[cord[0]][cord[1]] instanceof Num && ((Num) board[cord[0]][cord[1]]).getNumb() == 0) {
                Collection<int[]> neighbors = getNeighbors(cord);
                neighbors.removeIf(index -> board[index[0]][index[1]].isRevealed() || !(board[index[0]][index[1]] instanceof Num));
                fill.addAll(neighbors);
            }
            cord = fill.poll();
        }
    }

    private Collection<int[]> getNeighbors(int[] cord) {
        Collection<int[]> neighbors = new ArrayList<>(4);
        int row = cord[0];
        int colum = cord[1];

        for (int i = -1; i<=1; i++) {
            for (int j = -1; j<=1; j++) {
                if (i == 0 && j == 0)
                    continue;

                int[] index = new int[] {row + i, colum + j};
                if (validCord(index))
                    neighbors.add(index);
//                if (validCord(index) && !board[index[0]][index[1]].isRevealed() && board[index[0]][index[1]] instanceof Num)
            }
        }
        return neighbors;
    }

    private boolean validCord(int[] cord) {
        return cord[0] >= 0 && cord[0] < this.height && cord[1] >= 0 && cord[1] < this.width;
    }

    private void flag(int row, int colum) {

        if (board[row][colum].isFlag()) {
            board[row][colum].unflag();
            flagUsed--;
            if (board[row][colum] instanceof Bom)
                correctUsedFlags--;
        } else {

            // Can only use amountOfBoms flags
            if (flagUsed == amountOfBoms)
                return;

            board[row][colum].flag();
            flagUsed++;
            if (board[row][colum] instanceof Bom)
                correctUsedFlags++;
        }

        if (this.correctUsedFlags == this.amountOfBoms && this.flagUsed == this.correctUsedFlags)
            this.won = true;
    }

    private void generateBoard(int rowY, int columX) {
        this.board = new Tiles[height][width];
        Collection<int[]> neighbors = this.getNeighbors(new int[]{rowY, columX});

        // Create the list of where bombs are allowed to be (not around the first play)
        List<int[]> cords = new ArrayList<>();
        for (int row = 0; row < this.height; row++) {
            for (int colum = 0; colum < this.width; colum++) {
                int finalRow = row;
                int finalColum = colum;
                if (!(rowY == row && columX == colum) && neighbors.stream().noneMatch(ints -> ints[0] == finalRow && ints[1] == finalColum))
                    cords.add(new int[]{row, colum});
            }
        }

        for (int i = 0; i < this.amountOfBoms; i++) {
            int[] cord = cords.remove(generateNumber(cords.size() - 1));
            this.board[cord[0]][cord[1]] = new Bom();
        }

        cords.addAll(neighbors);
        for (int[] cord : cords) {
            this.board[cord[0]][cord[1]] = new Num(getBomsAround(cord));
        }
        this.board[rowY][columX] = new Num(getBomsAround(new int[] {rowY, columX}));
    }

    private int getBomsAround(int[] cord) {
        int row = cord[0];
        int colum = cord[1];

        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int[] index = new int[] {row + i, colum + j};
                if (validCord(index) && board[index[0]][index[1]] instanceof Bom)
                    count++;
            }
        }
        return count;
    }

    Random r = new Random();
    private int generateNumber(int max) {
        return r.nextInt(max + 1);
    }

    public void displayStatus() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");

        StringBuilder rowString = new StringBuilder();
        rowString.append("----".repeat(Math.max(0, this.width))).append("-");

        sb.append(rowString);
        sb.append("\n");
        for (Tiles[] row : board) {
            sb.append("| ");
            for (Tiles cell : row) {
                sb.append(cell.toString());
                sb.append(" | ");
            }
            sb.append("\n");
            sb.append(rowString);
            sb.append("\n");
        }
        sb.append("\n").append("flags used: ").append(flagUsed).append("\n");
        sb.append("correctly used flags: ").append(correctUsedFlags).append("\n");
        sb.append("amount of bombs: ").append(amountOfBoms);
        System.out.println(sb);
    }

    public void DisplayTheWholeBoard() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");

        StringBuilder rowString = new StringBuilder();
        rowString.append("----".repeat(Math.max(0, this.width))).append("-");

        sb.append(rowString);
        sb.append("\n");
        for (Tiles[] row : board) {
            sb.append("| ");
            for (Tiles cell : row) {
                if (cell instanceof Num)
                    sb.append(((Num) cell).getNumb());
                else
                    sb.append(cell.toString());
                sb.append(" | ");
            }
            sb.append("\n");
            sb.append(rowString);
            sb.append("\n");
        }

        System.out.println(sb);
    }

    @Override
    public String[][] getBoard() {
        String[][] copy = new String[height][width];
        for (int row = 0; row < this.height; row++) {
            for (int colum = 0; colum < this.width; colum++) {
                copy[row][colum] = this.board[row][colum].toString();
            }
        }
        return copy;
    }
}
