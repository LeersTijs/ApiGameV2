package com.apigamev2.Factory;

import com.apigamev2.Games.connectFour.ConnectFour;
import com.apigamev2.Games.game;

public class MineSweeperFactory implements Factory{
    @Override
    public game createGame(String[] args) {
        if (args.length != 2)
            return null;
        try {
            int height = Integer.parseInt(args[0]);
            int width = Integer.parseInt(args[1]);
            return new ConnectFour(height, width);
        } catch (Exception e) {
            return null;
        }
    }
}
