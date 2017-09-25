package org.urbanusjam.rpgcombat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CharacterTest {

    private MeleeFighter attacker;
    private RangeFighter target;

    @Before
    public void setUp() {
        attacker = new MeleeFighter();
        target = new RangeFighter();
    }

    @Test
    public void whenCharacterIsCreatedThenHealthStartsAt1000() {
        assertThat(attacker.getHealth(), is(1000));
    }

    @Test
    public void whenCharacterIsCreatedThenLevelStartsAt1() {
        assertThat(attacker.getLevel(), is(1));
    }

    @Test
    public void whenCharacterIsCreatedThenIsAlive() {
        assertThat(attacker.isAlive(), is(true));
    }

    @Test
    public void whenCharacterAppliesDamageAndDamageIsLowerThanCurrentHealthThenDamageIsSubtractedFromHealth() {
        attacker.damage(target, 100);
        assertThat(target.getHealth(), is(900));
    }

    @Test
    public void whenCharacterAppliesDamageAndDamageExceedsCurrentHealthThenHealthIs0AndCharacterDies() {
        attacker.damage(target, 2000);
        assertThat(target.getHealth(), is(0));
        assertThat(target.isAlive(), is(false));
    }

    @Test
    public void whenCharacterHealsAndIsAliveThenCurrentHealthIncreasesByTheAmountOfHealReceived() {
        target.damage(attacker, 100);
        assertThat(attacker.getHealth(), is(900));
        attacker.heal(attacker, 100);
        assertThat(attacker.getHealth(), is(1000));
    }

    @Test
    public void whenCharacterHealsAndIsAliveAndNewHealthIsAbove1000ThenHealthIs1000() {
        target.damage(attacker, 100);
        assertThat(attacker.getHealth(), is(900));
        attacker.heal(attacker, 900);
        assertThat(attacker.getHealth(), is(1000));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenCharacterHealedAndIsDeadThenCharacterCannotBeHealed() {
        target.damage(attacker, 2000);
        attacker.heal(attacker, 100);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenCharacterDamagesHimselfThenDamageCannotBeDone() {
        attacker.damage(attacker, 100);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenCharacterHealsAnEnemyThenHealingCannotBeDone() {
        attacker.heal(target, 100);
    }

    @Test
    public void whenTargetIsExactly5LevelsAboveAttackerThenDamageIsReducedBy50Percent() {
        target.setLevel(10);
        attacker.damage(target, 100);
        assertThat(target.getHealth(), is(950));
    }

    @Test
    public void whenTargetIsMoreThan5LevelsAboveAttackerThenDamageIsReducedBy50Percent() {
        target.setLevel(6);
        attacker.damage(target, 100);
        assertThat(target.getHealth(), is(950));
    }

    @Test
    public void whenTargetIsExactlyThan5LevelsBelowAttackerThenDamageIsReducedBy50Percent() {
        attacker.setLevel(10);
        attacker.damage(target, 100);
        assertThat(target.getHealth(), is(850));
    }

    @Test
    public void whenTargetIsMoreThan5LevelsBelowAttackerThenDamageIsReducedBy50Percent() {
        attacker.setLevel(6);
        attacker.damage(target, 100);
        assertThat(target.getHealth(), is(850));
    }

    @Test
    public void whenCharacterIsMeleeFighterThenAttackRangeIs2Meters() {
        assertThat(attacker.getAttackRange(), is(2));
    }

    @Test
    public void whenCharacterIsRangeFighterThenAttackRangeIs20Meters() {
        assertThat(target.getAttackRange(), is(20));
    }

    @Test
    public void whenCharacterTriesToDealDamageInsideRangeThenDamageIsApplied() {
        target.setCurrentDistance(1);
        attacker.damage(target, 100);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenCharacterTriesToDealDamageOutsideRangeThenDamageCannotBeApplied() {
        target.setCurrentDistance(50);
        attacker.damage(target, 100);
    }

    @After
    public void tearDown() {
        attacker = null;
        target = null;
    }

}
