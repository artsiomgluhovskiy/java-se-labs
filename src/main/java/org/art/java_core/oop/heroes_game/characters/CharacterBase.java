package org.art.java_core.oop.heroes_game.characters;

/**
 * Character skeleton implementation.
 */
public abstract class CharacterBase implements ICharacter {

    public enum CharacterType {
        WARRIOR,
        WIZARD,
        ARROW,
        HEALER
    }

    private int health;
    private int damage;
    private int armor;
    private int fortune;
    private int acceptedDamage;
    private String name = generateCharacterName();

    private CharacterType type;

    public CharacterBase() {
    }

    public CharacterBase(int health, int damage, int armor, int fortune,
                         int acceptedDamage, CharacterType type) {

        this.health = health;
        this.damage = damage;
        this.armor = armor;
        this.fortune = fortune;
        this.acceptedDamage = acceptedDamage;
        this.type = type;
    }

    public CharacterBase(CharacterType type) {
        this.type = type;
    }

    public abstract void attack(ICharacter victim);

    public abstract void takeDamage(int myDamage);

    @Override
    public void showCharacterInfo() {
        System.out.printf("%s's health: %d%n", name, health);
        System.out.printf("%s's damage: %d%n", name, damage);
        System.out.printf("%s's armor: %d%n", name, armor);
        System.out.printf("%s's fortune: %d%n%n", name, fortune);
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public void showHealth() {
        System.out.printf("%s's health: <%d> ", name, health);
        for (int i = 0; i <= health; i++) {
            System.out.print("|");
        }
        System.out.println();
    }

    protected String generateCharacterName() {
        //Character's name is the name of the character's class (can be overridden)
        return getClass().getSimpleName();
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public void setFortune(int fortune) {
        this.fortune = fortune;
    }

    public int getHealth() {
        return health;
    }

    public void setAcceptedDamage(int acceptedDamage) {
        this.acceptedDamage = acceptedDamage;
    }

    public String getName() {
        return name;
    }

    public CharacterType getType() {
        return this.type;
    }

    public int getDamage() {
        return damage;
    }

    public int getArmor() {
        return armor;
    }

    public int getFortune() {
        return fortune;
    }

    public int getAcceptedDamage() {
        return acceptedDamage;
    }
}
