package org.urbanusjam.rpgcombat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CharacterTest {

    private Character attacker;
    private Character target;

    @Before
    public void setUp() {
        attacker = new Character();
        target = new Character();
    }

    //1. All Characters, when created, have:
    //   - Health, starting at 1000
    //   - Level, starting at 1
    //   - May be Alive or Dead, starting Alive (Alive may be a true/false)
    //2. Characters can Deal Damage to Characters.
    //   - Damage is subtracted from Health
    //   - When damage received exceeds current Health, Health becomes 0 and the character dies
    //3. A Character can Heal a Character.
    //   - Dead characters cannot be healed
    //   - Healing cannot raise health above 1000

    @Test
    public void whenCharacterIsCreatedThenHealthStartsAt1000(){
        assertThat(attacker.getHealth(), is(1000));
    }

    @Test
    public void whenCharacterIsCreatedThenLevelStartsAt1(){
        assertThat(attacker.getLevel(), is(1));
    }

    @Test
    public void whenCharacterIsCreatedThenIsAlive(){
        assertThat(attacker.isAlive(), is(true));
    }

    @Test
    public void whenCharacterDealsDamageAndDamageIsLowerThanCurrentHealthThenDamageIsSubtractedFromHealth() {
        attacker.applyDamage(target, 100);
        assertThat(target.getHealth(), is(900));
    }

    @Test
    public void whenCharacterDealsDamageAndDamageExceedsCurrentHealthThenHealthIs0AndCharacterDies() {
        target.applyDamage(attacker, 2000);
        assertThat(attacker.getHealth(), is(0));
        assertThat(attacker.isAlive(), is(false));
    }

    @Test
    public void whenCharacterHealsAndIsAliveThenCurrentHealthIncreasesByTheAmountOfHealReceived() {
        target.applyDamage(attacker, 100);
        assertThat(attacker.getHealth(), is(900));

        attacker.heal(attacker, 100);
        assertThat(attacker.getHealth(), is(1000));
    }

    @Test
    public void whenCharacterHealsAndIsAliveAndNewHealthIsAbove1000ThenHealthIsSetTo1000() {
        target.applyDamage(attacker, 100);
        assertThat(attacker.getHealth(), is(900));

        attacker.heal(attacker, 900);
        assertThat(attacker.getHealth(), is(1000));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenCharacterIsHealedAndDeadThenCharacterCannotBeHealed() {
        target.applyDamage(attacker, 2000);
        attacker.heal(attacker, 100);
    }

    //1. The Character can deal damage to his enemies, but not to himself.
    //2. The Character can heal himself, but not his enemies.
    //3. The level now has an effect on the damage applied:
    //   - If the target is 5 or more levels above the attacker, Damage is reduced by 50%
    //   - If the target is 5 or more levels below the attacker, Damage is increased by 50%

    @Test(expected = UnsupportedOperationException.class)
    public void whenCharacterDamagesHimselfThenDamageCannotBeDone() {
        attacker.applyDamage(attacker, 100);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenCharacterHealsAnEnemyThenHealingCannotBeDone() {
        attacker.heal(target, 100);
    }

    @Test
    public void whenTargetIsFiveOrMoreLevelsAboveAttackerThenDamageIsReducedBy50Percent () {
        target.setLevel(6);
        attacker.applyDamage(target, 100);
        assertThat(target.getHealth(), is(950));
    }

    @Test
    public void whenTargetIsFiveOrMoreLevelsBelowAttackerThenDamageIsIncreasedBy50Percent () {
        attacker.setLevel(6);
        attacker.applyDamage(target, 100);
        assertThat(target.getHealth(), is(850));
    }

    @After
    public void tearDown() {
        attacker = null;
        target = null;
    }

}
