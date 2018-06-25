package org.art.java_core.oop.heroes_game.characters;

public interface ICharacter {

    void showCharacterInfo();

    void showHealth();

    void attack(ICharacter victim);

    void takeDamage(int myDamage);

    boolean isAlive();
}
