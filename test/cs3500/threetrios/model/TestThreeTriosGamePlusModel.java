package cs3500.threetrios.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.controller.ConfigurationFileReader;

/**
 *  Tests for the Three Trios game plus model
 */
public class TestThreeTriosGamePlusModel {

  private ThreeTriosGameModel plusModel;

  /**
   * Sets up a 3x3 grid and deck for testing the same model.
   */
  @Before
  public void setup() {
    List<Card> deck = new ArrayList<>();
    // Red 1
    deck.add(new GameCard("Card1", AttackValue.SIX, AttackValue.SIX, AttackValue.TWO,
            AttackValue.SEVEN));
    // Red 2
    deck.add(new GameCard("Card2", AttackValue.SIX, AttackValue.FOUR, AttackValue.FIVE,
            AttackValue.THREE));
    // Middle red card
    deck.add(new GameCard("Card3", AttackValue.FIVE, AttackValue.FOUR, AttackValue.THREE,
            AttackValue.TWO));
    deck.add(new GameCard("Card4", AttackValue.ONE, AttackValue.ONE, AttackValue.ONE,
            AttackValue.ONE));
    deck.add(new GameCard("Card5", AttackValue.TWO, AttackValue.SIX, AttackValue.EIGHT,
            AttackValue.FIVE));

    // Blue 1
    deck.add(new GameCard("Card6", AttackValue.ONE, AttackValue.FIVE, AttackValue.A,
            AttackValue.TWO));
    // Blue 2
    deck.add(new GameCard("Card7", AttackValue.TWO, AttackValue.NINE, AttackValue.ONE,
            AttackValue.SEVEN));
    deck.add(new GameCard("Card8", AttackValue.ONE, AttackValue.ONE, AttackValue.NINE,
            AttackValue.EIGHT));
    deck.add(new GameCard("Card9", AttackValue.TWO, AttackValue.NINE, AttackValue.EIGHT,
            AttackValue.ONE));
    deck.add(new GameCard("Card10", AttackValue.SIX, AttackValue.A, AttackValue.EIGHT,
            AttackValue.A));

    plusModel = new ThreeTriosGameSameModel(ConfigurationFileReader.createGridFromFile(
            "gridThreeByThreeNoHoles.txt"), deck, null);

    plusModel.startGame();
  }

  @Test
  public void multiplePlusPairsNormalFlip(){
    plusModel.playCard(2, 1, 0);
    plusModel.battle();
    plusModel.nextTurn();
    Assert.assertEquals(Color.RED, plusModel.getCellAt(2, 1).getCard().getColor());

    plusModel.playCard(0, 1, 0);
    plusModel.battle();
    plusModel.nextTurn();
    Assert.assertEquals(Color.BLUE, plusModel.getCellAt(0, 1).getCard().getColor());

    plusModel.playCard(1, 2, 0);
    plusModel.battle();
    plusModel.nextTurn();
    Assert.assertEquals(Color.RED, plusModel.getCellAt(1, 2).getCard().getColor());

    plusModel.playCard(1, 0, 0);
    plusModel.battle();
    plusModel.nextTurn();
    Assert.assertEquals(Color.BLUE, plusModel.getCellAt(1, 0).getCard().getColor());

    plusModel.playCard(1, 1, 0);
    plusModel.battle();

    Assert.assertEquals("Middle is red", Color.RED,
            plusModel.getCellAt(1, 1).getCard().getColor());

    Assert.assertEquals("Middle left is now red under normal rule", Color.RED,
            plusModel.getCellAt(1, 0).getCard().getColor());

    Assert.assertEquals("Bottom middle stays as red", Color.RED,
            plusModel.getCellAt(2, 1).getCard().getColor());

    Assert.assertEquals("Middle right stays as red", Color.RED,
            plusModel.getCellAt(1, 2).getCard().getColor());

    Assert.assertEquals("Top middle flips to red", Color.RED,
            plusModel.getCellAt(0, 1).getCard().getColor());
  }

  @Test
  public void multiplePlusPairsNoNormalFlip(){
    plusModel.playCard(2, 1, 0);
    plusModel.battle();
    plusModel.nextTurn();
    Assert.assertEquals(Color.RED, plusModel.getCellAt(2, 1).getCard().getColor());

    plusModel.playCard(0, 1, 0);
    plusModel.battle();
    plusModel.nextTurn();
    Assert.assertEquals(Color.BLUE, plusModel.getCellAt(0, 1).getCard().getColor());

    plusModel.playCard(1, 2, 0);
    plusModel.battle();
    plusModel.nextTurn();
    Assert.assertEquals(Color.RED, plusModel.getCellAt(1, 2).getCard().getColor());

    plusModel.playCard(1, 0, 1);
    plusModel.battle();
    plusModel.nextTurn();
    Assert.assertEquals(Color.BLUE, plusModel.getCellAt(1, 0).getCard().getColor());

    plusModel.playCard(1, 1, 0);
    plusModel.battle();

    Assert.assertEquals("Middle is red", Color.RED,
            plusModel.getCellAt(1, 1).getCard().getColor());

    Assert.assertEquals("Middle left stays as blue", Color.BLUE,
            plusModel.getCellAt(1, 0).getCard().getColor());

    Assert.assertEquals("Bottom middle stays as red", Color.RED,
            plusModel.getCellAt(2, 1).getCard().getColor());

    Assert.assertEquals("Middle right stays as red", Color.RED,
            plusModel.getCellAt(1, 2).getCard().getColor());

    Assert.assertEquals("Top middle flips to red", Color.RED,
            plusModel.getCellAt(0, 1).getCard().getColor());
  }

  @Test
  public void testComboStepApplies(){

  }
}
