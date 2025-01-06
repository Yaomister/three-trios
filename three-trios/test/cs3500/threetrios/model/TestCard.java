package cs3500.threetrios.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the Card class.
 * These tests make sure that the Card class is working properly.
 */
public class TestCard {

  private Card card1;
  private Card card2;
  private Card card3;
  private Card card4;

  @Before
  public void setUp() {
    card1 = new GameCard("SurgeTheSodaMachine",
            AttackValue.SEVEN, AttackValue.FOUR, AttackValue.THREE, AttackValue.A);
    card2 = new GameCard("RtTheCopRobot",
            AttackValue.EIGHT, AttackValue.A, AttackValue.TWO, AttackValue.FIVE);
    card3 = new GameCard("JanetTheFlyingStar",
            AttackValue.FOUR, AttackValue.FOUR, AttackValue.FOUR, AttackValue.FOUR);
    card4 = new GameCard("SurgeTheSodaMachine",
            AttackValue.SEVEN, AttackValue.FOUR, AttackValue.THREE, AttackValue.A);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullName() {
    new GameCard(null, AttackValue.TWO, AttackValue.THREE, AttackValue.FOUR, AttackValue.FIVE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullAttackValueNorth() {
    new GameCard("InvalidCard", null, AttackValue.THREE,
            AttackValue.FOUR, AttackValue.FIVE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullAttackValueSouth() {
    new GameCard("InvalidCard", AttackValue.THREE,
            null, AttackValue.FOUR, AttackValue.FIVE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullAttackValueEast() {
    new GameCard("InvalidCard", AttackValue.THREE,
            AttackValue.FOUR, null, AttackValue.FIVE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullAttackValueWest() {
    new GameCard("InvalidCard", AttackValue.THREE,
            AttackValue.FOUR, AttackValue.FIVE, null);
  }

  @Test
  public void testGetName() {
    Assert.assertEquals("SurgeTheSodaMachine", card1.getName());
  }

  @Test
  public void testGetNorth() {
    Assert.assertEquals(AttackValue.SEVEN, card1.getNorth());
  }

  @Test
  public void testGetSouth() {
    Assert.assertEquals(AttackValue.FOUR, card1.getSouth());
  }

  @Test
  public void testGetEast() {
    Assert.assertEquals(AttackValue.THREE, card1.getEast());
  }

  @Test
  public void testGetWest() {
    Assert.assertEquals(AttackValue.A, card1.getWest());
  }

  @Test
  public void testSetAndGetColor() {
    card1.setColor(Color.RED);
    Assert.assertEquals(Color.RED, card1.getColor());

    card1.setColor(Color.BLUE);
    Assert.assertEquals(Color.BLUE, card1.getColor());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetColorToNullThrowsIAE() {
    card1.setColor(null);
  }

  @Test
  public void testCompareAttackValueCardNorth() {
    // North - south
    int result = card2.compareAttackValue(card3, Direction.NORTH);
    Assert.assertEquals(4, result);
  }

  @Test
  public void testCompareAttackValueCardSouth() {
    // South - north
    int result = card3.compareAttackValue(card4, Direction.SOUTH);
    Assert.assertEquals(-3, result);
  }

  @Test
  public void testCompareAttackValueCardEast() {
    // East - west
    int result = card4.compareAttackValue(card3, Direction.EAST);
    Assert.assertEquals(-1, result);
  }

  @Test
  public void testCompareAttackValueCardWest() {
    // West - east
    int result = card2.compareAttackValue(card1, Direction.WEST);
    Assert.assertEquals(2, result);
  }

  @Test
  public void testEqualsIdenticalCards() {
    Assert.assertEquals(card1, card4);
  }

  @Test
  public void testEqualsDifferentCards() {
    Assert.assertNotEquals(card1, card2);
  }

  @Test
  public void testEqualsNull() {
    Assert.assertNotEquals(null, card1);
  }

  @Test
  public void testNotEqualsDifferentObjectType() {
    Assert.assertNotEquals("Wrong", card1);
  }

  @Test
  public void testEqualsReturnsFalseForDifferentCards() {
    card1.setColor(Color.RED);
    card2.setColor(Color.BLUE);

    Assert.assertNotEquals(card1, card2);
  }

  @Test
  public void testHashCode() {
    Assert.assertEquals(card1.hashCode(), card4.hashCode());
    Assert.assertNotEquals(card1.hashCode(), card2.hashCode());
  }

}
