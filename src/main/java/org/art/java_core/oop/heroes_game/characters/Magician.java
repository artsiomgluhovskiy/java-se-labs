package org.art.java_core.oop.heroes_game.characters;

public class Magician extends CharacterBase {

    public Magician() {
        super(CharacterType.WIZARD);
    }

    public Magician(int health, int damage, int armor, int fortune, int acceptedDamage) {
        super(health, damage, armor, fortune, acceptedDamage, CharacterType.WIZARD);
    }

    @Override
    public void attack(ICharacter victim) {
        //Magician's attack (can be extended or overridden)
        if (victim != null) {
            int currentDamage = getDamage() * getFortune() / 100;
            victim.takeDamage(currentDamage);
        }
    }

    @Override
    public void takeDamage(int myDamage) {
        //Magician's defence (can be extended or overridden)
        setAcceptedDamage(myDamage * getArmor() / 100);
        setHealth(getHealth() - getAcceptedDamage());
    }
}
