package org.art.java_core.oop.heroes_game;


import org.art.java_core.oop.heroes_game.characters.*;

import java.util.Random;

import static java.lang.Math.round;

/**
 * Character teams generator helper class.
 */
public class CharacterUtils {

    private static final int INIT_HEALTH = 50;
    private static final int INIT_DAMAGE = 10;
    private static final int INIT_ARMOR = 50;
    private static final int INIT_FORTUNE = 50;

    private static Random random = new Random(System.currentTimeMillis());

    public static CharacterBase[] generateHeroesTeam() {
        CharacterBase[] heroesCommand = new CharacterBase[3];
        heroesCommand[0] = generateCharacter(new Knight());
        heroesCommand[1] = generateCharacter(new Bowman());
        heroesCommand[2] = generateCharacter(new Magician());
        return heroesCommand;
    }

    public static CharacterBase[] generateEnemiesTeam() {
        CharacterBase[] enemyCommand = new CharacterBase[3];
        enemyCommand[0] = generateCharacter(new Butcher());
        enemyCommand[1] = generateCharacter(new Goblin());
        enemyCommand[2] = generateCharacter(new Warlock());
        return enemyCommand;
    }

    public static CharacterBase generateCharacter(CharacterBase ch) {
        if (ch.getType() == CharacterBase.CharacterType.WARRIOR) {
            ch.setHealth(INIT_HEALTH + round((random.nextInt(80) / 10) * 10));
            ch.setDamage(INIT_DAMAGE + random.nextInt(5));
            ch.setArmor(INIT_ARMOR + round((random.nextInt(50) / 10) * 10));
            ch.setFortune(INIT_FORTUNE + round((random.nextInt(50) / 10) * 10));
        } else if (ch.getType() == CharacterBase.CharacterType.ARROW) {
            ch.setHealth(INIT_HEALTH + round((random.nextInt(30) / 10) * 10));
            ch.setDamage(INIT_DAMAGE + random.nextInt(3));
            ch.setArmor(INIT_ARMOR + round((random.nextInt(50) / 10) * 10));
            ch.setFortune(INIT_FORTUNE + round((random.nextInt(50) / 10) * 10));
        } else {
            ch.setHealth(INIT_HEALTH + round((random.nextInt(10) / 10) * 10));
            ch.setDamage(INIT_DAMAGE + random.nextInt(2));
            ch.setArmor(INIT_ARMOR + round((random.nextInt(50) / 10) * 10));
            ch.setFortune(INIT_FORTUNE + round((random.nextInt(50) / 10) * 10));
        }
        return ch;
    }

    public static int defineDeadCharacter(CharacterBase[] command, boolean[] deadStatus) {
        for (int i = 0; i < command.length; i++) {
            if (!command[i].isAlive()) {
                return i;
            }
            deadStatus[i] = false;
        }
        return -1;
    }
}

