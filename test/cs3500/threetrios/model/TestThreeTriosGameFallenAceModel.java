package cs3500.threetrios.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.controller.ConfigurationFileReader;

/**
 * Tests for the fallen ace game model
 */
public class TestThreeTriosGameFallenAceModel {

  private ThreeTriosGameModel fallenAceModel;

  /**
   * Sets up a 2x2 grid and deck for testing the Fallen Ace model.
   */
  @Before
  public void setup() {
    List<Card> deck = new ArrayList<>();
    deck.add(new GameCard("Card1", AttackValue.A, AttackValue.A, AttackValue.A,
            AttackValue.A));
    deck.add(new GameCard("Card2", AttackValue.FIVE, AttackValue.SIX, AttackValue.ONE,
            AttackValue.THREE));
    deck.add(new GameCard("Card3", AttackValue.ONE, AttackValue.TWO, AttackValue.THREE,
            AttackValue.FOUR));
    deck.add(new GameCard("Card4", AttackValue.TWO, AttackValue.TWO, AttackValue.THREE,
            AttackValue.A));

    fallenAceModel = new ThreeTriosGameModel(ConfigurationFileReader
            .createGridFromFile("gridTwoByTwoHoleBottomRight.txt"),
            deck, new ThreeTriosGameFallenAceModel(null));
    fallenAceModel.startGame();
  }

  @Test
  public void testAceFlipsWithOne() {
    // Red Card (ACE on NORTH)
    fallenAceModel.playCard(0, 0, 0);
    fallenAceModel.battle();
    fallenAceModel.nextTurn();
    // Blue Ace (ONE on NORTH)
    fallenAceModel.playCard(1, 0, 0);
    fallenAceModel.battle();

    Assert.assertEquals("Red Ace should flip to Blue", Color.BLUE,
            fallenAceModel.getCellAt(0, 0).getCard().getColor());
    Assert.assertEquals("Blue Card remains Blue", Color.BLUE,
            fallenAceModel.getCellAt(1, 0).getCard().getColor());
  }

  @Test
  public void testAceDoesNotFlipWithoutOne() {
    fallenAceModel.playCard(0, 0, 0);
    fallenAceModel.battle();
    fallenAceModel.nextTurn();
    fallenAceModel.playCard(1, 0, 1);
    fallenAceModel.battle();

    Assert.assertEquals("Red Ace should remain Red", Color.RED,
            fallenAceModel.getCellAt(0, 0).getCard().getColor());
    Assert.assertEquals("Blue Card should remain Blue", Color.BLUE,
            fallenAceModel.getCellAt(1, 0).getCard().getColor());
  }

  @Test
  public void testAceWillNotFlipAOne() {
    fallenAceModel.playCard(0, 0, 1);
    fallenAceModel.battle();
    fallenAceModel.nextTurn();
    fallenAceModel.playCard(1, 0, 1);
    fallenAceModel.battle();

    Assert.assertEquals("Red Card should remain Red", Color.RED,
            fallenAceModel.getCellAt(0, 0).getCard().getColor());
    Assert.assertEquals("Blue Card should remain Blue", Color.BLUE,
            fallenAceModel.getCellAt(1, 0).getCard().getColor());
  }
}