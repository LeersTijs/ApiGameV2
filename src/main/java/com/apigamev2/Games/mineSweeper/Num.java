package com.apigamev2.Games.mineSweeper;

public class Num extends Tiles {

    private final int numb;

    public Num(int numb) {
        this.numb = numb;
    }

    public int getNumb() { return this.numb; }

    @Override
    public String toString() {
        if (this.isFlag())
            return "F";
        if (this.isRevealed())
            return String.valueOf(this.numb);
        return " ";
    }
}