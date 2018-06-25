package org.art.java_core.oop.heroes_game.characters;

public class Bowman extends CharacterBase {

    public Bowman() {
        super(CharacterType.ARROW);
    }

    public Bowman(int health, int damage, int armor, int fortune, int acceptedDamage) {
        super(health, damage, armor, fortune, acceptedDamage, CharacterType.ARROW);
    }

    @Override
    public void attack(ICharacter victim) {
        //Bowman's attack (can be extended or overridden)
        if (victim != null) {
            int currentDamage = getDamage() * getFortune() / 100;
            victim.takeDamage(currentDamage);
        }
    }

    @Override
    public void takeDamage(int myDamage) {
        //Bowman's defence (can be extended or overridden)
        setAcceptedDamage(myDamage * getArmor() / 100);
        setHealth(getHealth() - getAcceptedDamage());
    }
}
