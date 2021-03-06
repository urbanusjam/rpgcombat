package org.urbanusjam.rpgcombat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.format;

public abstract class Character implements Ability {

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

    @Override
    public void damage(Character target, int damageQty) {
        int realDamage = damageQty;
        int levelDiff = getLevelDiff(target.getLevel());

        if (isHimself(target) || isOutsideRange(target.getCurrentDistance())) {
            throw new UnsupportedOperationException();
        }
        if (damageQty > target.getHealth()) {
            target.setHealth(0);
            target.setAlive(false);
            logger.debug("You defeated the enemy!");
        } else {
            if (levelDiff >= MAX_LEVEL_DIFF) {
                realDamage = damageQty / 2;
                logger.warn("The damage has been reduced from {} to {} because the enemy if more powerful than you!", damageQty, realDamage);
            } else if (levelDiff <= -MAX_LEVEL_DIFF) {
                realDamage = (int) (damageQty * 1.5);
                logger.warn("The damage has been increased from {} to {} because you are more powerful than the enemy!", damageQty, realDamage);
            }
            target.setHealth(target.getHealth() - realDamage);
            logger.debug("Your enemy's health has dropped to {}", target.getHealth());
        }
    }

    @Override
    public void heal(Character target, int healQty) {
        if (isHimself(target) && target.isAlive()) {
            target.setHealth(target.getHealth() + healQty > MAX_HEALTH ?
                    MAX_HEALTH : target.getHealth() + healQty);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    private boolean isOutsideRange(int targetDistance) {
        return targetDistance > this.attackRange;
    }

    private boolean isHimself(Character target) {
        return target == this;
    }

    private int getLevelDiff(int targetLevel) {
        return targetLevel - this.level;
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

    public int getAttackRange() {
        return attackRange;
    }

    public final void setAttackRange(int attackRange) {
        throw new UnsupportedOperationException("The character's attack range cannot be modified");
    }

    public int getCurrentDistance() {
        return currentDistance;
    }

    public void setCurrentDistance(int currentDistance) {
        this.currentDistance = currentDistance;
    }

    @Override
    public String toString() {
        return format("[health=%s/1000, level=%s, attackRange=%smt, status=%s]", health, level, attackRange, alive ? "alive" : "dead");
    }
}
