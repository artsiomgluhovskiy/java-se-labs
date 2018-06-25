package org.art.java_core.oop.heroes_game.characters;


public class Warlock extends CharacterBase {

    public Warlock() {
        super(CharacterType.WIZARD);
    }

    public Warlock(int health, int damage, int armor, int fortune, int acceptedDamage) {
        super(health, damage, armor, fortune, acceptedDamage, CharacterType.WIZARD);
    }

    @Override
    public void attack(ICharacter victim) {
        //Warlock's attack (can be extended or overridden)
        if (victim != null) {
            int currentDamage = getDamage() * getFortune() / 100;
            victim.takeDamage(currentDamage);
        }
    }

    @Override
    public void takeDamage(int myDamage) {
        //Warlock's defence (can be extended or overridden)
        setAcceptedDamage(myDamage * getArmor() / 100);
        setHealth(getHealth() - getAcceptedDamage());
    }
}
