package org.art.java_core.oop.heroes_game;

import org.art.java_core.oop.heroes_game.characters.CharacterBase;

import java.util.Random;
import java.util.Scanner;

/**
 * 'Heroes vs Enemies Battle' Game context.
 * Game actors:
 * - heroes team (Bowman, Knight, Magician);
 * - enemies team (Butcher, Goblin, Warlock).
 * Each character has its own bunch of characteristics (health, damage,
 * armor and fortune).
 * The game flow consists of sequential moves (attacks) of heroes and
 * enemies teams (one after another). The survived team wins.
 */
public class GameContext implements Runnable {

    //Default allowable number of characters revives
    private static final int DEFAULT_ENEMY_REVIVES = 3;
    private static final int DEFAULT_HERO_REVIVES = 3;

    private int movesCounter = 0;
    private boolean isRunning = true;
    private boolean[] heroesDeadStatus;
    private boolean[] enemyDeadStatus;

    private int currentHeroRivives = DEFAULT_HERO_REVIVES;
    private int currentEnemyRivives = DEFAULT_ENEMY_REVIVES;

    private CharacterBase[] heroesTeam;
    private CharacterBase[] enemyTeam;

    private Scanner scanner = new Scanner(System.in);
    private Random random = new Random(System.currentTimeMillis());
    private GameStatus gameStatus = new GameStatus();

    private void generateTeams() {
        //Character teams initialization
        heroesTeam = CharacterUtils.generateHeroesTeam();
        heroesDeadStatus = new boolean[heroesTeam.length];
        enemyTeam = CharacterUtils.generateEnemiesTeam();
        enemyDeadStatus = new boolean[enemyTeam.length];

        System.out.println("HEROES team information: \n");
        for (int i = 0; i < heroesTeam.length; i++) {
            heroesTeam[i].showCharacterInfo();
        }
        System.out.println("ENEMIES team information: \n");
        for (int i = 0; i < enemyTeam.length; i++) {
            enemyTeam[i].showCharacterInfo();
        }
    }

    private void makeHeroMove(int k) {
        int opponentIndex;
        while (heroesDeadStatus[k]) {
            if (k < heroesTeam.length - 1) {
                k++;
            } else if (k == heroesTeam.length - 1) {
                k = 0;
            }
        }
        do {
            opponentIndex = random.nextInt(3);
        } while (!enemyTeam[opponentIndex].isAlive());
        heroesTeam[k].attack(enemyTeam[opponentIndex]);
        if (!enemyTeam[opponentIndex].isAlive()) {
            enemyDeadStatus[opponentIndex] = true;
        }
        boolean enemyDead = gameStatus.showMoveStatus(heroesTeam[k], enemyTeam[opponentIndex], movesCounter,
                enemyTeam[opponentIndex].isAlive());
        if (enemyDead && currentEnemyRivives > 0) {
            int indexOfDeadEnemy = CharacterUtils.defineDeadCharacter(enemyTeam, enemyDeadStatus);
            System.out.printf(" <------ %s was revived!!! ------>%n", enemyTeam[indexOfDeadEnemy].getName());
            enemyTeam[indexOfDeadEnemy] = CharacterUtils.generateCharacter(enemyTeam[indexOfDeadEnemy]);
            enemyDeadStatus[indexOfDeadEnemy] = false;
            currentEnemyRivives--;
        }
        isRunning = gameStatus.gameStatus(enemyTeam, enemyDeadStatus);
    }

    private void makeEnemyMove(int k) {
        int opponentIndex;
        while (enemyDeadStatus[k]) {
            if (k < enemyTeam.length - 1) {
                k++;
            } else if (k == enemyTeam.length - 1) {
                k = 0;
            }
        }
        do {
            opponentIndex = random.nextInt(3);
        } while (!heroesTeam[opponentIndex].isAlive());
        enemyTeam[k].attack(heroesTeam[opponentIndex]);
        if (!heroesTeam[opponentIndex].isAlive()) {
            heroesDeadStatus[opponentIndex] = true;
        }
        boolean heroDead = gameStatus.showMoveStatus(enemyTeam[k], heroesTeam[opponentIndex], movesCounter,
                heroesTeam[opponentIndex].isAlive());
        if (heroDead && currentHeroRivives > 0) {
            int indexOfDeadHero = CharacterUtils.defineDeadCharacter(heroesTeam, heroesDeadStatus);
            System.out.printf("<------ %s was revived!!! ------> %n", heroesTeam[indexOfDeadHero].getName());
            heroesTeam[indexOfDeadHero] = CharacterUtils.generateCharacter(heroesTeam[indexOfDeadHero]);
            heroesDeadStatus[indexOfDeadHero] = false;
            currentHeroRivives--;
        }
        isRunning = gameStatus.gameStatus(heroesTeam, heroesDeadStatus);
    }

    @Override
    public void run() {
        System.out.println("*** Welcome to the \"Heroes vs Enemies\" game ***");
        System.out.println("*** Initial game information ***\n");
        generateTeams();

        System.out.println("If you are ready for BLOOD enter \"1\" and start the BATTLE!");
        if (scanner.nextInt() == 1) {
            System.out.println("The battle is starting!!!\n");
            while (isRunning) {
                //Making a move
                movesCounter++;
                for (int i = 0; i < heroesTeam.length; i++) {
                    //Heroes move. Alive hero defining
                    int k = i;
                    makeHeroMove(k);
                    if (!isRunning) {
                        gameStatus.showGameStatistics(heroesTeam, enemyTeam, movesCounter);
                        break;
                    }
                    //Enemies move. Alive enemy defining
                    movesCounter++;
                    k = i;
                    makeEnemyMove(k);
                    if (!isRunning) {
                        gameStatus.showGameStatistics(heroesTeam, enemyTeam, movesCounter);
                        break;
                    }
                }
            }
        } else {
            System.out.println("*** The game has finished! ***");
        }
    }

    public static void main(String[] args) {
        //Game launching
        new Thread(new GameContext()).start();
    }
}
