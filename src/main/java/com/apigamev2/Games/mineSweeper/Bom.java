package com.apigamev2.Games.mineSweeper;

public class Bom extends Tiles{

    public Bom(){}

    @Override
    public String toString() {
        if (this.isFlag())
            return "F";
        if (this.isRevealed())
            return "X";
        return " ";
    }
}
