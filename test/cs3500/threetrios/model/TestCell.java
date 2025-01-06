package cs3500.threetrios.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for the Cell class.
 * These tests make sure that the Cell class is working properly.
 */
public class TestCell {

  private GameCard redCard;
  private GameCard blueCard;
  private GameCell emptyCell;
  private GameCell redCell;
  private GameCell blueCell;

  @Before
  public void setUp() {
    redCard = new GameCard("Red Card", AttackValue.ONE, AttackValue.A,
            AttackValue.TWO, AttackValue.FOUR);
    redCard.setColor(Color.RED);

    blueCard = new GameCard("Blue Card", AttackValue.TWO, AttackValue.ONE,
            AttackValue.FIVE, AttackValue.EIGHT);
    blueCard.setColor(Color.BLUE);


    emptyCell = new GameCell(null);
    redCell = new GameCell(redCard);
    blueCell = new GameCell(blueCard);
  }

  @Test
  public void testGetCard() {
    Assert.assertNull("Empty no card", emptyCell.getCard());
    Assert.assertEquals("Red return redCard", redCard, redCell.getCard());
    Assert.assertEquals("Blue return blueCard", blueCard, blueCell.getCard());
  }

  // Cell tests
  @Test
  public void testCellInitialization() {
    Assert.assertTrue(emptyCell.isEmpty());
    Assert.assertFalse(redCell.isEmpty());
    Assert.assertFalse(blueCell.isEmpty());
    Assert.assertFalse(emptyCell.isHole());
  }

  @Test
  public void testSetHole() {
    emptyCell.setHole(true);
    Assert.assertTrue(emptyCell.isHole());
    emptyCell.setHole(false);
    Assert.assertFalse(emptyCell.isHole());
  }

  @Test
  public void testIsEmpty() {
    Assert.assertTrue(emptyCell.isEmpty());
    Assert.assertFalse(redCell.isEmpty());
  }

  @Test
  public void testToString() {
    emptyCell.setHole(true);
    Assert.assertEquals(" ", emptyCell.toString());

    emptyCell.setHole(false);
    Assert.assertEquals("_", emptyCell.toString());

    Assert.assertEquals("R", redCell.toString());
    Assert.assertEquals("B", blueCell.toString());
  }

  @Test
  public void testEqualsWithDifferentTypes() {
    Assert.assertNotEquals("Invalid", redCell);
  }

  @Test
  public void testEqualsWithNull() {
    Assert.assertNotEquals(null, redCell);
  }

  @Test
  public void testEqualsWithDifferentCellAttributes() {
    // Different card
    GameCell differentCardCell = new GameCell(blueCard);
    Assert.assertNotEquals(redCell, differentCardCell);

    // Different hole status
    GameCell holeCell = new GameCell(redCard);
    holeCell.setHole(true);
    Assert.assertNotEquals(redCell, holeCell);
  }
}
