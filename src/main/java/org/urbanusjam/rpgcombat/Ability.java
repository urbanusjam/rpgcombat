package org.urbanusjam.rpgcombat;

public interface Ability {

    void damage(Character target, int damageQty);

    void heal(Character target, int healQty);
}
