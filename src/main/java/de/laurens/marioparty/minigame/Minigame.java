package de.laurens.marioparty.minigame;


public abstract class Minigame {

    String name;
    String author;
    String description;
    double version;

    public abstract void runMinigame();

    public abstract void stopMinigame();


}
