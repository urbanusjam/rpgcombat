package org.urbanusjam.rpgcombat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.format;

public abstract class Character {

    private static final Logger logger = LoggerFactory.getLogger(Character.class);
    private static final int MAX_HEALTH = 1000;
    private static final int MAX_LEVEL_DIFF = 5;

    /**
     * The status of the character in combat in health points (HP).
     * The starting and maximum value is 1000.
     */
    protected int health;

    /**
     * The overall power or ability of the character. The starting level is 1.
     */
    protected int level;

    /**
     * The maximum distance (in meters) at which a character can perform an attack and apply damage to a target.
     */
    protected int attackRange;

    /**
     * The current distance from a target.
     */
    protected int currentDistance;

    /**
     * If false, the character is dead.
     */
    protected boolean alive;

    public Character() {
        this.health = 1000;
        this.level = 1;
        this.alive = true;
        this.currentDistance = 1;
    }

    protected int getHealth() {
        return health;
    }

    protected void setHealth(int health) {
        this.health = health;
    }

    protected int getLevel() {
        return level;
    }

    protected void setLevel(int level) {
        this.level = level;
    }

    protected boolean isAlive() {
        return alive;
    }

    protected void setAlive(boolean alive) {
        this.alive = alive;
    }

    protected int getAttackRange() {
        return attackRange;
    }

    public final void setAttackRange(int attackRange) {
        throw new UnsupportedOperationException("The character's attack range cannot be modified");
    }

    protected int getCurrentDistance() {
        return currentDistance;
    }

    protected void setCurrentDistance(int currentDistance) {
        this.currentDistance = currentDistance;
    }

    protected void applyDamage(Character target, int damageQty) {
        int realDamage = damageQty;
        int levelDiff = getLevelDiff(target.getLevel());

        if(isCurrentCharacter(target) || isOutsideRange(target.getCurrentDistance())) {
            throw new UnsupportedOperationException();
        }
        if(damageQty > target.getHealth()) {
            target.setHealth(0);
            target.setAlive(false);
            logger.debug("You defeated the enemy!");
        }
        else {
            if(levelDiff >= MAX_LEVEL_DIFF) {
                realDamage = damageQty / 2;
                logger.warn("The damage has been reduced from {} to {} because the enemy if more powerful than you!", damageQty, realDamage);
            }
            else if(levelDiff <= -MAX_LEVEL_DIFF) {
                realDamage = (int) (damageQty * 1.5);
                logger.warn("The damage has been increased from {} to {} because you are more powerful than the enemy!", damageQty, realDamage);
            }
            target.setHealth(target.getHealth() - realDamage);
            logger.debug("Your enemy's health has dropped to {}", target.getHealth());
        }
    }

    protected void heal(Character target, int healQty) {
        if(isCurrentCharacter(target) && target.isAlive()) {
            target.setHealth(target.getHealth() + healQty > MAX_HEALTH ?
                    MAX_HEALTH : target.getHealth() + healQty);
        }
        else {
            throw new UnsupportedOperationException();
        }
    }

    private boolean isOutsideRange(int targetDistance) {
        return targetDistance > this.attackRange;
    }

    private boolean isCurrentCharacter(Character target) {
        return target == this;
    }

    private int getLevelDiff(int targetLevel) {
        return targetLevel - this.level;
    }

    @Override
    public String toString() {
        return format("[health=%s/1000, level=%s, attackRange=%smt, status=%s]", health, level, attackRange, alive ? "alive" : "dead");
    }
}
