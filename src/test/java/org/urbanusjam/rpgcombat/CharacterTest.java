package org.urbanusjam.rpgcombat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CharacterTest {

    private Character hero;
    private Character enemy;

    @Before
    public void setUp() {
        hero = new Character();
        enemy = new Character();
    }

    //1. All Characters, when created, have:
    //        - Health, starting at 1000
    //        - Level, starting at 1
    //        - May be Alive or Dead, starting Alive (Alive may be a true/false)
    @Test
    public void whenCharacterIsCreatedThenHealthStartsAt1000(){
        assertThat(hero.getHealth(), is(1000));
    }

    @Test
    public void whenCharacterIsCreatedThenLevelStartsAt1(){
        assertThat(hero.getLevel(), is(1));
    }

    @Test
    public void whenCharacterIsCreatedThenIsAlive(){
        assertThat(hero.isAlive(), is(true));
    }

    //2. Characters can Deal Damage to Characters.
    //   - Damage is subtracted from Health
    //   - When damage received exceeds current Health, Health becomes 0 and the character dies
    @Test
    public void whenCharacterIsDamagedAndDamageIsLowerThanCurrentHealthThenDamageIsSubtractedFromHealth() {
        hero.dealDamage(enemy, 100);
        assertThat(enemy.getHealth(), is(900));
    }

    @Test
    public void whenCharacterIsDamagedAndDamageExceedsCurrentHealthThenHealthIs0AndCharacterDies() {
        hero.dealDamage(enemy, 2000);
        assertThat(enemy.getHealth(), is(0));
        assertThat(enemy.isAlive(), is(false));
    }

    //3. A Character can Heal a Character.
    //   - Dead characters cannot be healed
    //   - Healing cannot raise health above 1000
    @Test
    public void whenCharacterIsHealedAndAliveThenCurrentHealthIsIncreasesByTheAmountOfHealReceived() {
        hero.dealDamage(enemy, 100);
        assertThat(enemy.getHealth(), is(900));
        hero.heal(enemy, 100);
        assertThat(enemy.getHealth(), is(1000));
    }

    @Test
    public void whenCharacterIsHealedAndAliveAndNewHealthIsAbove1000ThenHealthIsSetTo1000() {
        hero.dealDamage(enemy, 100);
        hero.heal(enemy, 1100);
        assertThat(enemy.getHealth(), is(1000));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenCharacterIsHealedAndDeadThenCharacterCannotBeHealed() {
        hero.dealDamage(enemy, 2000);
        hero.heal(enemy, 100);
    }

    @After
    public void tearDown() {
        hero = null;
        enemy = null;
    }

}
