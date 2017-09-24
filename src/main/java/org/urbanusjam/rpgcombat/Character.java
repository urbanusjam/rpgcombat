package org.urbanusjam.rpgcombat;


public class Character {

    private static final int MAX_HEALTH = 1000;
    private int health;
    private int level;
    private boolean alive;

    public Character() {
        this.health = 1000;
        this.level = 1;
        this.alive = true;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void dealDamage(Character opponent, int damageQty) {
        if(damageQty > opponent.getHealth()) {
            opponent.setHealth(0);
            opponent.setAlive(false);
        }
        else {
            opponent.setHealth(opponent.getHealth() - damageQty);
        }
    }

    public void heal(Character opponent, int healQty) {
        if(!opponent.isAlive()) {
            throw new UnsupportedOperationException("A dead character cannot be healed");
        }
        if(opponent.getHealth() + healQty > MAX_HEALTH) {
            opponent.setHealth(MAX_HEALTH);
        }
        else {
            opponent.setHealth(opponent.getHealth() + healQty);
        }
    }
}
