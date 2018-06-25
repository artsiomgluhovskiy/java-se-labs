package org.art.java_core.oop.heroes_game.characters;

public class Goblin extends CharacterBase {

    public Goblin() {
        super(CharacterType.ARROW);
    }

    public Goblin(int health, int damage, int armor, int fortune, int acceptedDamage) {
        super(health, damage, armor, fortune, acceptedDamage, CharacterType.ARROW);
    }

    @Override
    public void attack(ICharacter victim) {
        //Goblin's attack (can be extended or overridden)
        if (victim != null) {
            int currentDamage = getDamage() * getFortune() / 100;
            victim.takeDamage(currentDamage);
        }
    }

    @Override
    public void takeDamage(int myDamage) {
        //Goblin's defence (can be extended or overridden)
        setAcceptedDamage(myDamage * getArmor() / 100);
        setHealth(getHealth() - getAcceptedDamage());
    }
}
