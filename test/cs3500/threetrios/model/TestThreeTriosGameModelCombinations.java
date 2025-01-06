package cs3500.threetrios.model;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.controller.ConfigurationFileReader;

/**
 * Tests for the three trios game model combinations
 */
public class TestThreeTriosGameModelCombinations {

  private ThreeTriosGameModel fallenAceThanReverseModel;
  private ThreeTriosGameModel reverseThanFallenAceModel;

  private ThreeTriosGameModel sameWithReverseModel;
  private ThreeTriosGameModel plusWithFallenAceModel;

  /**
   * Sets up a 3x3 grid and deck for testing the chained Fallen Ace and Reverse models.
   */
  @Before
  public void setup() {

    fallenAceThanReverseModel = new ThreeTriosGameModel(
            ConfigurationFileReader.createGridFromFile("gridThreeByThreeNoHoles.txt"),
            makeDeck(),
            new ThreeTriosGameFallenAceModel(new ThreeTriosGameReverseModel(null))
    );

    reverseThanFallenAceModel = new ThreeTriosGameModel(
            ConfigurationFileReader.createGridFromFile("gridThreeByThreeNoHoles.txt"),
            makeDeck(),
            new ThreeTriosGameReverseModel(new ThreeTriosGameFallenAceModel(null))
    );

    List<Card> sameValueDeck = new ArrayList<>();
    for (int index = 0; index < 10; index++) {
      sameValueDeck.add(new GameCard("Card" + index, AttackValue.A, AttackValue.A,
              AttackValue.A, AttackValue.A));
    }

    sameWithReverseModel = new ThreeTriosGameSameModel(ConfigurationFileReader
            .createGridFromFile("gridThreeByThreeNoHoles.txt"),
            sameValueDeck,
            new ThreeTriosGameReverseModel(null));


    List<Card> sameSumDeck = new ArrayList<>();
    for (int index = 0; index < 10; index++) {
      sameSumDeck.add(new GameCard("Card" + index, AttackValue.ONE, AttackValue.ONE,
              AttackValue.ONE, AttackValue.ONE));
    }
    plusWithFallenAceModel = new ThreeTriosGamePlusModel(
            ConfigurationFileReader.createGridFromFile("gridThreeByThreeNoHoles.txt"),
            sameSumDeck,
            new ThreeTriosGameFallenAceModel(null)
    );

    sameWithReverseModel.startGame();
    plusWithFallenAceModel.startGame();
    fallenAceThanReverseModel.startGame();
    reverseThanFallenAceModel.startGame();
  }

  private List<Card> makeDeck() {
    List<Card> deck = new ArrayList<>();
    deck.add(new GameCard("Card1", AttackValue.ONE, AttackValue.THREE, AttackValue.A,
            AttackValue.TWO));
    deck.add(new GameCard("Card2", AttackValue.TWO, AttackValue.THREE, AttackValue.FOUR,
            AttackValue.TWO));
    deck.add(new GameCard("Card3", AttackValue.ONE, AttackValue.SIX, AttackValue.FIVE,
            AttackValue.THREE));
    deck.add(new GameCard("Card4", AttackValue.SIX, AttackValue.SIX, AttackValue.SIX,
            AttackValue.SIX));
    deck.add(new GameCard("Card5", AttackValue.SIX, AttackValue.THREE, AttackValue.NINE,
            AttackValue.A));
    // Other Hand
    deck.add(new GameCard("Card6", AttackValue.A, AttackValue.A, AttackValue.A,
            AttackValue.NINE));
    deck.add(new GameCard("Card7", AttackValue.THREE, AttackValue.SIX, AttackValue.SIX,
            AttackValue.FOUR));
    deck.add(new GameCard("Card8", AttackValue.FIVE, AttackValue.FOUR, AttackValue.NINE,
            AttackValue.A));
    deck.add(new GameCard("Card9", AttackValue.ONE, AttackValue.NINE, AttackValue.EIGHT,
            AttackValue.SIX));
    deck.add(new GameCard("Card10", AttackValue.THREE, AttackValue.FOUR, AttackValue.ONE,
            AttackValue.A));
    return deck;
  }

  @Test
  public void testFallenAceThanReverseAceBeatsOne() {
    // Step 1: Play a Red card with Ace (A) at (0, 0)
    fallenAceThanReverseModel.playCard(0, 0, 0);
    fallenAceThanReverseModel.battle();
    fallenAceThanReverseModel.nextTurn();

    // Step 2: Play a Blue card with 1 at (1, 0)
    fallenAceThanReverseModel.playCard(1, 0, 0);
    fallenAceThanReverseModel.battle();

    // Step 3: Validate that the Blue 1 can't flip red A
    Assert.assertEquals(Color.RED,
            fallenAceThanReverseModel.getCellAt(0, 0).getCard().getColor());
    Assert.assertEquals(Color.BLUE,
            fallenAceThanReverseModel.getCellAt(1, 0).getCard().getColor());
  }

  @Test
  public void testFallenAceReverseTwoToNineBeatsAce() {
    fallenAceThanReverseModel.playCard(0, 0, 0);
    fallenAceThanReverseModel.battle();
    fallenAceThanReverseModel.nextTurn();

    fallenAceThanReverseModel.playCard(0, 1, 0);
    fallenAceThanReverseModel.battle();

    // Ace is flipped by Six
    Assert.assertEquals(Color.BLUE,
            fallenAceThanReverseModel.getCellAt(0, 0).getCard().getColor());
    Assert.assertEquals(Color.BLUE,
            fallenAceThanReverseModel.getCellAt(0, 1).getCard().getColor());
  }

  @Test
  public void testFallenAceThanReverseOneBeatsTwoToNine() {
    fallenAceThanReverseModel.playCard(0, 0, 0);
    fallenAceThanReverseModel.battle();
    fallenAceThanReverseModel.nextTurn();

    fallenAceThanReverseModel.playCard(0, 1, 3);
    fallenAceThanReverseModel.battle();

    // Three is flipped by One
    Assert.assertEquals(Color.BLUE,
            fallenAceThanReverseModel.getCellAt(0, 0).getCard().getColor());
    Assert.assertEquals(Color.BLUE,
            fallenAceThanReverseModel.getCellAt(0, 1).getCard().getColor());
  }


  @Test
  public void testReverseThanFallenAceAceCannotBeatOne() {
    reverseThanFallenAceModel.playCard(1, 0, 0);
    reverseThanFallenAceModel.battle();
    reverseThanFallenAceModel.nextTurn();

    reverseThanFallenAceModel.playCard(0, 0, 0);
    reverseThanFallenAceModel.battle();

    Assert.assertEquals(Color.BLUE,
            reverseThanFallenAceModel.getCellAt(0, 0).getCard().getColor());
    Assert.assertEquals(Color.RED,
            reverseThanFallenAceModel.getCellAt(1, 0).getCard().getColor());
  }

  @Test
  public void testReverseThanFallenAceOneBeatsAce() {
    reverseThanFallenAceModel.playCard(0, 1, 4);
    reverseThanFallenAceModel.battle();
    reverseThanFallenAceModel.nextTurn();

    reverseThanFallenAceModel.playCard(0, 0, 4);
    reverseThanFallenAceModel.battle();

    Assert.assertEquals(Color.BLUE,
            reverseThanFallenAceModel.getCellAt(0, 1).getCard().getColor());
    Assert.assertEquals(Color.BLUE,
            reverseThanFallenAceModel.getCellAt(0, 0).getCard().getColor());
  }

  @Test
  public void testReverseThanFallenAceOneBeatsTwoToNine() {
    reverseThanFallenAceModel.playCard(0, 0, 0);
    reverseThanFallenAceModel.battle();
    reverseThanFallenAceModel.nextTurn();

    reverseThanFallenAceModel.playCard(0, 1, 3);
    reverseThanFallenAceModel.battle();

    // Three is flipped by One
    Assert.assertEquals(Color.BLUE,
            reverseThanFallenAceModel.getCellAt(0, 0).getCard().getColor());
    Assert.assertEquals(Color.BLUE,
            reverseThanFallenAceModel.getCellAt(0, 1).getCard().getColor());
  }

  @Test
  public void testReverseThanFallenAceTwoToNineBeatsAce() {
    reverseThanFallenAceModel.playCard(0, 0, 0);
    reverseThanFallenAceModel.battle();
    reverseThanFallenAceModel.nextTurn();

    reverseThanFallenAceModel.playCard(0, 1, 0);
    reverseThanFallenAceModel.battle();

    // Ace is flipped by Six
    Assert.assertEquals(Color.BLUE,
            reverseThanFallenAceModel.getCellAt(0, 0).getCard().getColor());
    Assert.assertEquals(Color.BLUE,
            reverseThanFallenAceModel.getCellAt(0, 1).getCard().getColor());
  }


  @Test
  public void testFallenAceThanReverseWorksForChainedRules() {
    fallenAceThanReverseModel.playCard(0, 0, 0);
    fallenAceThanReverseModel.battle();
    fallenAceThanReverseModel.nextTurn();
    fallenAceThanReverseModel.playCard(0, 1, 0);
    fallenAceThanReverseModel.battle();
    fallenAceThanReverseModel.nextTurn();
    fallenAceThanReverseModel.playCard(0, 2, 0);
    fallenAceThanReverseModel.battle();
    fallenAceThanReverseModel.nextTurn();

    fallenAceThanReverseModel.playCard(1, 2, 2);
    fallenAceThanReverseModel.battle();
    fallenAceThanReverseModel.nextTurn();


    Assert.assertEquals(Color.BLUE,
            fallenAceThanReverseModel.getCellAt(0, 1).getCard().getColor());
    Assert.assertEquals(Color.BLUE,
            fallenAceThanReverseModel.getCellAt(0, 0).getCard().getColor());
    Assert.assertEquals(Color.BLUE,
            fallenAceThanReverseModel.getCellAt(0, 2).getCard().getColor());
    Assert.assertEquals(Color.BLUE,
            fallenAceThanReverseModel.getCellAt(1, 2).getCard().getColor());
  }

  @Test
  public void testReverseThanFallenAceWorksForChainedRules() {
    reverseThanFallenAceModel.playCard(0, 0, 0);
    reverseThanFallenAceModel.battle();
    reverseThanFallenAceModel.nextTurn();
    reverseThanFallenAceModel.playCard(1, 0, 0);
    reverseThanFallenAceModel.battle();
    reverseThanFallenAceModel.nextTurn();
    reverseThanFallenAceModel.playCard(2, 0, 0);
    reverseThanFallenAceModel.battle();
    reverseThanFallenAceModel.nextTurn();
    reverseThanFallenAceModel.playCard(2, 1, 0);
    reverseThanFallenAceModel.battle();
    reverseThanFallenAceModel.nextTurn();
    reverseThanFallenAceModel.playCard(2, 2, 0);
    reverseThanFallenAceModel.battle();
    reverseThanFallenAceModel.nextTurn();


    Assert.assertEquals(Color.RED,
            reverseThanFallenAceModel.getCellAt(1, 0).getCard().getColor());
    Assert.assertEquals(Color.RED,
            reverseThanFallenAceModel.getCellAt(0, 0).getCard().getColor());
    Assert.assertEquals(Color.RED,
            reverseThanFallenAceModel.getCellAt(2, 0).getCard().getColor());
    Assert.assertEquals(Color.RED,
            reverseThanFallenAceModel.getCellAt(2, 1).getCard().getColor());
  }

  @Test
  public void testSameWithReverseModelOnlyAppliesSameRuleBeforeComboStep() {
    sameWithReverseModel.playCard(0, 1, 0);
    sameWithReverseModel.battle();
    sameWithReverseModel.nextTurn();
    sameWithReverseModel.playCard(0, 0, 0);
    sameWithReverseModel.battle();
    sameWithReverseModel.nextTurn();
    sameWithReverseModel.playCard(2, 1, 0);
    sameWithReverseModel.battle();
    sameWithReverseModel.nextTurn();
    sameWithReverseModel.playCard(2, 2, 0);
    sameWithReverseModel.battle();
    sameWithReverseModel.nextTurn();
    sameWithReverseModel.playCard(1, 2, 0);
    sameWithReverseModel.battle();
    sameWithReverseModel.nextTurn();
    // Placing this in the middle should flip the three adjacent cards, but not the outer ones
    // because the same rule is not applied for the combo step
    sameWithReverseModel.playCard(0, 2, 0);
    sameWithReverseModel.battle();
    sameWithReverseModel.nextTurn();

    Assert.assertEquals(Color.BLUE,
            sameWithReverseModel.getCellAt(0, 0).getCard().getColor());
    Assert.assertEquals(Color.BLUE,
            sameWithReverseModel.getCellAt(0, 1).getCard().getColor());
    Assert.assertEquals(Color.BLUE,
            sameWithReverseModel.getCellAt(0, 2).getCard().getColor());
    Assert.assertEquals(Color.BLUE,
            sameWithReverseModel.getCellAt(1, 2).getCard().getColor());
    Assert.assertEquals(Color.BLUE,
            sameWithReverseModel.getCellAt(2, 2).getCard().getColor());
    // This one did not get flipped from the combo step.
    Assert.assertEquals(Color.RED,
            sameWithReverseModel.getCellAt(2, 1).getCard().getColor());
  }

  @Test
  public void testPlusWithFallenAceModelOnlyAppliesPlusRuleBeforeComboStep() {
    plusWithFallenAceModel.playCard(0, 0, 0);
    plusWithFallenAceModel.battle();
    plusWithFallenAceModel.nextTurn();
    plusWithFallenAceModel.playCard(0, 1, 0);
    plusWithFallenAceModel.battle();
    plusWithFallenAceModel.nextTurn();
    plusWithFallenAceModel.playCard(2, 1, 0);
    plusWithFallenAceModel.battle();
    plusWithFallenAceModel.nextTurn();
    plusWithFallenAceModel.playCard(2, 2, 0);
    plusWithFallenAceModel.battle();
    plusWithFallenAceModel.nextTurn();
    plusWithFallenAceModel.playCard(1, 2, 0);
    plusWithFallenAceModel.battle();
    plusWithFallenAceModel.nextTurn();

    // The two red cards on the tails should not be flipped, because the plus rule does not apply to them, nor do they meet the criteria for the fallen ace rule to flip them
    plusWithFallenAceModel.playCard(0, 2, 0);
    plusWithFallenAceModel.battle();
    plusWithFallenAceModel.nextTurn();

    Assert.assertEquals(Color.BLUE,
            plusWithFallenAceModel.getCellAt(0, 2).getCard().getColor());
    Assert.assertEquals(Color.BLUE,
            plusWithFallenAceModel.getCellAt(1, 2).getCard().getColor());
    Assert.assertEquals(Color.BLUE,
            plusWithFallenAceModel.getCellAt(2, 2).getCard().getColor());
    Assert.assertEquals(Color.RED,
            plusWithFallenAceModel.getCellAt(2, 1).getCard().getColor());
    Assert.assertEquals(Color.RED,
            plusWithFallenAceModel.getCellAt(0, 0).getCard().getColor());

  }
}

