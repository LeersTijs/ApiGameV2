package com.apigamev2.Factory;

import com.apigamev2.Games.game;
import com.apigamev2.Games.mineSweeper.MineSweeper;

public class ConnectFourFactory implements Factory{
    @Override
    public game createGame(String[] args) {
        if (args.length != 3)
            return null;
        try {
            int height = Integer.parseInt(args[0]);
            int width = Integer.parseInt(args[1]);
            int amountOfBombs = Integer.parseInt(args[2]);
            return new MineSweeper(height, width, amountOfBombs);
        } catch (Exception e) {
            return null;
        }
    }
}
