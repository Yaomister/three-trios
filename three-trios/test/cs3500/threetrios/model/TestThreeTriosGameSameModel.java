package cs3500.threetrios.model;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.controller.ConfigurationFileReader;

/**
 * Tests for the three trios same model
 */
public class TestThreeTriosGameSameModel {

  private ThreeTriosGameModel sameModel;

  /**
   * Sets up a 3x3 grid and deck for testing the same model.
   */
  @Before
  public void setup() {
    List<Card> deck = new ArrayList<>();
    // Red 1
    deck.add(new GameCard("Card1", AttackValue.SIX, AttackValue.SIX, AttackValue.TWO,
            AttackValue.THREE));
    // Red 2
    deck.add(new GameCard("Card2", AttackValue.FOUR, AttackValue.FOUR, AttackValue.FIVE,
            AttackValue.THREE));
    // Middle red card
    deck.add(new GameCard("Card3", AttackValue.FIVE, AttackValue.FOUR, AttackValue.THREE,
            AttackValue.A));
    deck.add(new GameCard("Card4", AttackValue.ONE, AttackValue.ONE, AttackValue.ONE,
            AttackValue.ONE));
    deck.add(new GameCard("Card5", AttackValue.TWO, AttackValue.SIX, AttackValue.EIGHT,
            AttackValue.FIVE));

    // Blue 1
    deck.add(new GameCard("Card6", AttackValue.FIVE, AttackValue.FOUR, AttackValue.A,
            AttackValue.TWO));
    // Blue 2
    deck.add(new GameCard("Card7", AttackValue.TWO, AttackValue.NINE, AttackValue.EIGHT,
            AttackValue.SEVEN));
    deck.add(new GameCard("Card8", AttackValue.ONE, AttackValue.ONE, AttackValue.ONE,
            AttackValue.EIGHT));
    deck.add(new GameCard("Card9", AttackValue.TWO, AttackValue.NINE, AttackValue.EIGHT,
            AttackValue.ONE));
    deck.add(new GameCard("Card10", AttackValue.SIX, AttackValue.A, AttackValue.EIGHT,
            AttackValue.A));

    sameModel = new ThreeTriosGameSameModel(ConfigurationFileReader.createGridFromFile(
            "gridThreeByThreeNoHoles.txt"), deck, null);

    sameModel.startGame();
  }

  @Test
  public void testSameComboWithTwoCardsWorksNoFlipNormal() {
    sameModel.playCard(0, 1, 0);
    sameModel.battle();
    sameModel.nextTurn();
    Assert.assertEquals(Color.RED, sameModel.getCellAt(0, 1).getCard().getColor());

    sameModel.playCard(1, 0, 0);
    sameModel.battle();
    sameModel.nextTurn();
    Assert.assertEquals(Color.BLUE, sameModel.getCellAt(1, 0).getCard().getColor());

    sameModel.playCard(2, 1, 0);
    sameModel.battle();
    sameModel.nextTurn();
    Assert.assertEquals(Color.RED, sameModel.getCellAt(2, 1).getCard().getColor());

    sameModel.playCard(1, 2, 2);
    sameModel.battle();
    sameModel.nextTurn();
    Assert.assertEquals(Color.BLUE, sameModel.getCellAt(1, 2).getCard().getColor());

    // Middle of all four cards
    sameModel.playCard(1, 1, 0);
    sameModel.battle();
    sameModel.nextTurn();

    sameModel.playCard(0, 2, 2);


    Assert.assertEquals("Middle is red", Color.RED,
            sameModel.getCellAt(1, 1).getCard().getColor());

    Assert.assertEquals("Middle left is now red", Color.RED,
            sameModel.getCellAt(1, 0).getCard().getColor());

    Assert.assertEquals("Bottom middle stays as red", Color.RED,
            sameModel.getCellAt(2, 1).getCard().getColor());

    Assert.assertEquals("Middle right stays as blue", Color.RED,
            sameModel.getCellAt(1, 2).getCard().getColor());

    Assert.assertEquals("Top is still red", Color.RED,
            sameModel.getCellAt(0, 1).getCard().getColor());
  }

  @Test
  public void testSameComboWithTwoCardsWorksFlipsAll() {
    sameModel.playCard(0, 1, 0);
    sameModel.battle();
    sameModel.nextTurn();
    Assert.assertEquals(Color.RED, sameModel.getCellAt(0, 1).getCard().getColor());

    sameModel.playCard(1, 0, 0);
    sameModel.battle();
    sameModel.nextTurn();
    Assert.assertEquals(Color.BLUE, sameModel.getCellAt(1, 0).getCard().getColor());

    sameModel.playCard(2, 1, 0);
    sameModel.battle();
    sameModel.nextTurn();
    Assert.assertEquals(Color.RED, sameModel.getCellAt(2, 1).getCard().getColor());

    sameModel.playCard(1, 2, 2);
    sameModel.battle();
    sameModel.nextTurn();
    Assert.assertEquals(Color.BLUE, sameModel.getCellAt(1, 2).getCard().getColor());

    // Middle of all four cards
    sameModel.playCard(1, 1, 0);
    sameModel.battle();

    Assert.assertEquals("Middle is red", Color.RED,
            sameModel.getCellAt(1, 1).getCard().getColor());

    Assert.assertEquals("Middle left is now red", Color.RED,
            sameModel.getCellAt(1, 0).getCard().getColor());

    Assert.assertEquals("Bottom middle stays as red", Color.RED,
            sameModel.getCellAt(2, 1).getCard().getColor());

    Assert.assertEquals("Middle right becomes red under normal", Color.RED,
            sameModel.getCellAt(1, 2).getCard().getColor());

    Assert.assertEquals("Top is still red", Color.RED,
            sameModel.getCellAt(0, 1).getCard().getColor());
  }

  @Test
  public void testSameComboWithOnlyOneCardFails() {

    sameModel.playCard(0, 1, 0);
    sameModel.battle();
    sameModel.nextTurn();
    Assert.assertEquals(Color.RED, sameModel.getCellAt(0, 1).getCard().getColor());

    sameModel.playCard(1, 1, 4);
    sameModel.battle();
    sameModel.nextTurn();
    Assert.assertEquals(Color.BLUE, sameModel.getCellAt(1, 1).getCard().getColor());

    Assert.assertEquals("Top middle does not flip because only one matching pair",
            Color.RED, sameModel.getCellAt(0, 1).getCard().getColor());

  }

}



