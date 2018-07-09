package org.art.java_core.design.patterns.memento;

/**
 * Memento pattern (from GoF) - code example.
 * Simple game saving simulation.
 */
public class Memento {

    public static void main(String[] args) {

        Game game = new Game();
        game.set("Level 3", 30000);
        System.out.println(game);

        File file = new File();
        file.setSave(game.save());
    }
}

class Game {

    private String level;

    private int ms;

    public void set(String level, int ms) {
        this.level = level;
        this.ms = ms;
    }

    public void load(Save save) {
        level = save.getLevel();
        ms = save.getMs();
    }

    public Save save() {
        return new Save(level, ms);
    }

    @Override
    public String toString() {
        return "Game{" +
                "level='" + level + '\'' +
                ", ms=" + ms +
                '}';
    }
}

//Memento
class Save {

    private final String level;

    private final int ms;

    public Save(String level, int ms) {
        this.level = level;
        this.ms = ms;
    }

    public String getLevel() {
        return level;
    }

    public int getMs() {
        return ms;
    }
}

//Caretaker
class File {

    private Save save;

    public Save getSave() {
        return save;
    }

    public void setSave(Save save) {
        this.save = save;
    }
}
