package org.art.java_core.oop.heroes_game;

import org.art.java_core.oop.heroes_game.characters.CharacterBase;

public class GameStatus {

    public boolean showMoveStatus(CharacterBase attacker, CharacterBase defensible, int moveNumber, boolean isAlive) {
        System.out.printf("Move number %d%n", moveNumber);
        System.out.printf("%s attacks --> %s!%n", attacker.getName(), defensible.getName());
        if (!isAlive) {
            System.out.printf("%s was KILLED !!!%n", defensible.getName());
            return true;
        } else {
            System.out.printf("%s was damaged by %s!%n", defensible.getName(), defensible.getAcceptedDamage());
            defensible.showHealth();
            return false;
        }
    }

    public boolean gameStatus(CharacterBase[] victim, boolean[] deadStatus) {
        for (int i = 0; i < victim.length; i++) {
            if (victim[i].isAlive() && !deadStatus[i]) {
                return true;
            }
        }
        return false;
    }

    private boolean showWinner(CharacterBase[] heroes, CharacterBase[] enimies) {
        for (int i = 0; i < heroes.length; i++) {
            if (heroes[i].isAlive()) {
                return true;
            }
        }
        for (int i = 0; i < enimies.length; i++) {
            if (enimies[i].isAlive()) {
                return false;
            }
        }
        return false;
    }

    private void showGameResults(CharacterBase[] heroes, CharacterBase[] enemies, int moveNumber) {
        System.out.println("The number of moves: " + moveNumber);
        System.out.println("Survivors: ");
        if (showWinner(heroes, enemies)) {
            for (int i = 0; i < heroes.length; i++) {
                if (heroes[i].isAlive()) {
                    System.out.println(heroes[i].getName());
                }
            }
        } else {
            for (int i = 0; i < enemies.length; i++) {
                if (enemies[i].isAlive()) {
                    System.out.println(enemies[i].getName());
                }
            }
        }
        System.out.println();
    }

    public void showGameStatistics(CharacterBase[] heroes, CharacterBase[] enemies, int moveNumber) {
        if (showWinner(heroes, enemies)) {
            System.out.println("*** The team of HEROES is the WINNER!!! ***\n");
            showGameResults(heroes, enemies, moveNumber);
        } else {
            System.out.println("*** The team of ENEMIES is the WINNER!!! ***\n");
            showGameResults(heroes, enemies, moveNumber);
        }
    }
}

