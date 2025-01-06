package cs3500.threetrios.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.controller.ConfigurationFileReader;
import cs3500.threetrios.view.ThreeTriosTextView;

/**
 * Tests for the three trios reverse model
 */
public class TestThreeTriosGameReverseModel {

  private ThreeTriosGameModel reverseModel;

  /**
   * Sets up a 2x2 grid and deck for testing the reverse model.
   */
  @Before
  public void setup() {
    List<Card> deck = new ArrayList<>();
    deck.add(new GameCard("Card1", AttackValue.SIX, AttackValue.NINE, AttackValue.TWO,
            AttackValue.THREE));
    deck.add(new GameCard("Card2", AttackValue.FOUR, AttackValue.TWO, AttackValue.FIVE,
            AttackValue.THREE));
    deck.add(new GameCard("Card3", AttackValue.FOUR, AttackValue.THREE, AttackValue.EIGHT,
            AttackValue.TWO));
    deck.add(new GameCard("Card4", AttackValue.SIX, AttackValue.FOUR, AttackValue.FIVE,
            AttackValue.THREE));
    reverseModel = new ThreeTriosGameModel(ConfigurationFileReader.createGridFromFile(
            "gridTwoByTwoHoleBottomRight.txt"), deck, new ThreeTriosGameReverseModel(null));
    reverseModel.startGame();
  }

  @Test
  public void testBattleReversesFlipForLowerAttackValue() {
    reverseModel.playCard(0, 0, 0);
    reverseModel.battle();

    reverseModel.nextTurn();
    reverseModel.playCard(1, 0, 0);

    reverseModel.battle();

    Assert.assertEquals("Red card should flip to Blue", Color.BLUE,
            reverseModel.getCellAt(0, 0).getCard().getColor());
    Assert.assertEquals("Blue card remains unchanged", Color.BLUE,
            reverseModel.getCellAt(1, 0).getCard().getColor());
  }
  @Test
  public void testBattleDoesNotFlipForHigherAttackValue() {

    reverseModel.playCard(0, 0, 0);
    // card 1 red
    reverseModel.battle();
    reverseModel.nextTurn();
    reverseModel.playCard(1, 0, 0);
    // card 1 blue
    reverseModel.battle();

    Assert.assertEquals("Red Card should not flip",
            Color.BLUE, reverseModel.getCellAt(0, 0).getCard().getColor());
    Assert.assertEquals("Blue card remains unchanged",
            Color.BLUE, reverseModel.getCellAt(1, 0).getCard().getColor());
  }

  @Test
  public void testBattleDoesNotFlipForEqualAttackValue() {
    List<Card> deck = new ArrayList<>();
    deck.add( new GameCard("C1", AttackValue.THREE,
            AttackValue.THREE, AttackValue.THREE, AttackValue.THREE));
    deck.add(new GameCard("C2", AttackValue.THREE,
            AttackValue.THREE, AttackValue.THREE, AttackValue.THREE));
    deck.add(new GameCard("C3", AttackValue.THREE,
            AttackValue.THREE, AttackValue.THREE, AttackValue.THREE));
    deck.add( new GameCard("C4", AttackValue.THREE,
            AttackValue.THREE, AttackValue.THREE, AttackValue.THREE));
    reverseModel = new ThreeTriosGameModel(
            ConfigurationFileReader.createGridFromFile("gridTwoByTwoHoleBottomRight.txt"),
            deck,
            new ThreeTriosGameReverseModel(null)
    );
    reverseModel.startGame();

    reverseModel.playCard(0, 0, 0);
    reverseModel.battle();

    reverseModel.nextTurn();
    reverseModel.playCard(1, 0, 0);
    reverseModel.battle();
    Assert.assertEquals(Color.RED, reverseModel.getCellAt(0, 0).getCard().getColor());
    Assert.assertEquals(Color.BLUE, reverseModel.getCellAt(1, 0).getCard().getColor());
  }

  @Test
  public void testBattleMultipleCardsFlipping() {
    reverseModel.playCard(0, 0, 0);
    reverseModel.battle();
    reverseModel.nextTurn();
    reverseModel.playCard(1, 0, 0);
    reverseModel.battle();
    reverseModel.nextTurn();
    reverseModel.playCard(0, 1, 0);
    reverseModel.battle();

    Assert.assertEquals("Red card should flip to Blue", Color.BLUE,
            reverseModel.getCellAt(0, 0).getCard().getColor());
    Assert.assertEquals("Blue card remains unchanged", Color.BLUE,
            reverseModel.getCellAt(1, 0).getCard().getColor());
    Assert.assertEquals("Neutral card should flip to Red", Color.RED,
            reverseModel.getCellAt(0, 1).getCard().getColor());
  }

  @Test
  public void testBattleNoFlippingForEmptyCell() {
    reverseModel.playCard(0, 0, 0); // Red Card

    reverseModel.battle();

    Assert.assertEquals("Red card should remain Red", Color.RED,
            reverseModel.getCellAt(0, 0).getCard().getColor());
    assertNull("Empty cell should remain empty",
            reverseModel.getCellAt(1, 0).getCard());
  }
}
