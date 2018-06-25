package org.art.java_core.oop.heroes_game.characters;

public class Butcher extends CharacterBase {

    public Butcher() {
        super(CharacterType.WARRIOR);
    }

    public Butcher(int health, int damage, int armor, int fortune, int acceptedDamage) {
        super(health, damage, armor, fortune, acceptedDamage, CharacterType.WARRIOR);
    }

    @Override
    public void attack(ICharacter victim) {
        //Butcher's attack (can be extended or overridden)
        if (victim != null) {
            int currentDamage = getDamage() * getFortune() / 100;
            victim.takeDamage(currentDamage);
        }
    }

    @Override
    public void takeDamage(int myDamage) {
        //Butcher's defence (can be extended or overridden)
        setAcceptedDamage(myDamage * getArmor() / 100);
        setHealth(getHealth() - getAcceptedDamage());
    }
}
