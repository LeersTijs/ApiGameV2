package com.apigamev2.Games.mineSweeper;

public abstract class Tiles {

    private boolean flag = false;
    private boolean reveald = false;

    public Tiles(){
    }

    public void flag() { flag = true; }
    public void unflag() { flag = false; }
    public boolean isFlag() { return flag; }

    public void reveal() { reveald = true; }

    public boolean isRevealed() { return reveald; }

    @Override
    public abstract String toString();
}
