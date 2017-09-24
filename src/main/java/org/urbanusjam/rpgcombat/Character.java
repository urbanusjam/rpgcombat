package org.urbanusjam.rpgcombat;

import static java.lang.String.format;

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

    public void applyDamage(Character target, int damageQty) {
        int realDamage = damageQty;
        if(target == this) {
            throw new UnsupportedOperationException("A character cannot apply damage to himself");
        }
        if((target.getLevel() - this.level) >= 5) {
            realDamage = damageQty / 2;
        }
        else if((target.getLevel() - this.level) <= -5) {
            realDamage = damageQty + (damageQty / 2);
        }
        if(damageQty > target.getHealth()) {
            target.setHealth(0);
            target.setAlive(false);
        }
        else {
            target.setHealth(target.getHealth() - realDamage);
        }
    }

    public void heal(Character target, int healQty) {
        if(target == this) {
            if(!target.isAlive()) {
                throw new UnsupportedOperationException("A dead character cannot be healed");
            }
            if(target.getHealth() + healQty > MAX_HEALTH) {
                target.setHealth(MAX_HEALTH);
            }
            else {
                target.setHealth(target.getHealth() + healQty);
            }
        }
        else {
            throw new UnsupportedOperationException("A character cannot heal enemies");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Character))
            return false;
        Character character = (Character) o;
        return character.health == health &&
                character.level == level &&
                character.alive == alive;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + health;
        result = 31 * result + level;
        result = 31 * result + (alive ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return format("Character [health=%s, level=%s, %s]", health, level, alive ? "ALIVE" : "DEAD");
    }
}
