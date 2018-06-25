package org.art.java_core.oop.heroes_game.characters;

public class Knight extends CharacterBase {

    public Knight() {
        super(CharacterType.WARRIOR);
    }

    public Knight(int health, int damage, int armor, int fortune, int acceptedDamage) {
        super(health, damage, armor, fortune, acceptedDamage, CharacterType.WARRIOR);
    }

    @Override
    public void attack(ICharacter victim) {
        //Knight's attack (can be extended or overridden)
        if (victim != null) {
            int currentDamage = getDamage() * getFortune() / 100;
            victim.takeDamage(currentDamage);
        }
    }

    @Override
    public void takeDamage(int myDamage) {
        //Knight's defence (can be extended or overridden)
        setAcceptedDamage(myDamage * getArmor() / 100);
        setHealth(getHealth() - getAcceptedDamage());
    }
}
