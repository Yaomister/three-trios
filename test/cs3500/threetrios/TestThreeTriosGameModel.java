package cs3500.threetrios;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.controller.ConfigurationFileReader;
import cs3500.threetrios.model.AttackValue;
import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Cell;
import cs3500.threetrios.model.GameCard;
import cs3500.threetrios.model.Color;
import cs3500.threetrios.model.Phase;
import cs3500.threetrios.model.ThreeTriosGameModel;
import cs3500.threetrios.model.ThreeTriosModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the ThreeTriosGameModel class.
 * These tests ensure all functionality of the ThreeTriosGameModel class is working as
 * intended, and that the model's methods all are accurate and correct.
 */
public class TestThreeTriosGameModel {

  private ThreeTriosModel inactiveModel;
  private ThreeTriosModel activeModel;

  private ThreeTriosModel finishedWithWinningModel;
  private ThreeTriosModel finishedWithTieModel;

  private GameCard sampleCard;

  @Before
  public void setUp() {
    inactiveModel = new ThreeTriosGameModel(ConfigurationFileReader
            .createGridFromFile("gridWithPathToAllCells.txt"),
            ConfigurationFileReader.createDeckFromFile("deckLarge.txt"), null);

    activeModel = new ThreeTriosGameModel(ConfigurationFileReader
            .createGridFromFile("gridWithPathToAllCells.txt"),
            ConfigurationFileReader.createDeckFromFile("deckLarge.txt"), null);
    activeModel.startGame();

    finishedWithTieModel = new ThreeTriosGameModel(ConfigurationFileReader
            .createGridFromFile("gridTwoByTwo.txt"),
            ConfigurationFileReader.createDeckFromFile("deckLarge.txt"), null);
    finishedWithTieModel.startGame();
    finishedWithTieModel.playCard(0, 1, 1);
    finishedWithTieModel.battle();
    finishedWithTieModel.nextTurn();
    finishedWithTieModel.playCard(1, 1, 0);
    finishedWithTieModel.battle();
    finishedWithTieModel.nextTurn();
    finishedWithTieModel.playCard(1, 0, 0);
    finishedWithTieModel.battle();

    finishedWithWinningModel = new ThreeTriosGameModel(ConfigurationFileReader
            .createGridFromFile("gridTwoByTwo.txt"),
            ConfigurationFileReader.createDeckFromFile("deckLarge.txt"), null);
    finishedWithWinningModel.startGame();
    finishedWithWinningModel.playCard(0, 1, 0);
    finishedWithWinningModel.battle();
    finishedWithWinningModel.nextTurn();
    finishedWithWinningModel.playCard(1, 1, 1);
    finishedWithWinningModel.battle();
    finishedWithWinningModel.nextTurn();
    finishedWithWinningModel.playCard(1, 0, 0);
    finishedWithWinningModel.battle();

    sampleCard = new GameCard("Joe", AttackValue.A,
            AttackValue.A, AttackValue.A, AttackValue.A);


  }


  @Test
  public void testStartGameWorks() {
    inactiveModel.startGame();
    assertFalse(inactiveModel.isGameOver());
    assertEquals(Color.RED, inactiveModel.getCurrentPlayerColor());
    assertEquals(Phase.PLACING, inactiveModel.getPhase());
    assertNotEquals(0, inactiveModel.getGridHeight());
    assertNotEquals(0, inactiveModel.getGridWidth());

  }

  @Test
  public void testCardsDealtCorrectly() {
    inactiveModel.startGame();
    assertEquals(10, inactiveModel.getPlayerHand(Color.RED).size());
    inactiveModel.playCard(0, 0, 0);
    inactiveModel.battle();
    inactiveModel.nextTurn();
    assertEquals(10, inactiveModel.getPlayerHand(Color.BLUE).size());
  }

  @Test(expected = IllegalStateException.class)
  public void testStartGameAlreadyStartedThrowsISE() {
    activeModel.startGame();
  }

  @Test(expected = IllegalStateException.class)
  public void testStartGameAlreadyEndedThrowsISE() {
    finishedWithTieModel.startGame();
  }


  @Test(expected = IllegalArgumentException.class)
  public void testConstructionWithDeckNotEnoughCards() {
    new ThreeTriosGameModel(ConfigurationFileReader
            .createGridFromFile("gridWithPathToAllCells.txt"),
            ConfigurationFileReader.createDeckFromFile("deckSmall.txt"), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructionWithDeckContainingDuplicateCards() {
    new ThreeTriosGameModel(ConfigurationFileReader
            .createGridFromFile("gridWithPathToAllCells.txt"),
            ConfigurationFileReader.createDeckFromFile("deckWithDuplicates.txt"), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructionWithGridWithEvenPlayableCells() {
    new ThreeTriosGameModel(ConfigurationFileReader
            .createGridFromFile("gridWithEvenPlayableCells.txt"),
            ConfigurationFileReader.createDeckFromFile("deckLarge.txt"), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructionWithNullGridThrowsIAE() {
    new ThreeTriosGameModel(null, ConfigurationFileReader
            .createDeckFromFile("deckLarge.txt"), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructionWithNullDeckThrowsIAE() {
    new ThreeTriosGameModel(ConfigurationFileReader
            .createGridFromFile("gridWithPathToAllCells.txt"), null, null);
  }

  @Test
  public void testPlayCardWorks() {
    assertNull(activeModel.getCellAt(0, 0).getCard());
    activeModel.playCard(0, 0, 0);
    assertNotNull(activeModel.getCellAt(0, 0).getCard());
    assertEquals(Phase.BATTLE, activeModel.getPhase());
    assertEquals(10, activeModel.getPlayerHand(Color.BLUE).size());
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayCardGameEndedThrowISE() {
    finishedWithTieModel.playCard(0, 0, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayCardGameHasNotStartedThrowISE() {
    inactiveModel.playCard(0, 0, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayCardInvalidPhaseThrowsISE() {
    activeModel.playCard(0, 0, 0);
    activeModel.playCard(1, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayCardGridIndexLessThanZeroThrowsIAE() {
    activeModel.playCard(-1, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayCardGridIndexTooLargeThrowsIAE() {
    activeModel.playCard(0, 999, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayCardCardIndexLessThanZeroThrowsIAE() {
    activeModel.playCard(0, 0, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayCardCardIndexTooLargeThrowsIAE() {
    activeModel.playCard(0, 0, 999);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayCardToCellToHoleThrowsIAE() {
    activeModel.playCard(0, 1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayCardToCellAlreadyOccupiedThrowsIAE() {
    activeModel.playCard(0, 0, 0);
    activeModel.battle();
    activeModel.nextTurn();
    activeModel.playCard(0, 0, 0);
  }

  @Test
  public void testNextTurnWorks() {
    activeModel.playCard(0, 0, 0);
    activeModel.battle();
    assertEquals(Color.RED, activeModel.getCurrentPlayerColor());
    activeModel.nextTurn();
    assertEquals(Color.BLUE, activeModel.getCurrentPlayerColor());
    assertEquals(activeModel.getPhase(), Phase.PLACING);
  }

  @Test(expected = IllegalStateException.class)
  public void testNextTurnGameEndedThrowISE() {
    finishedWithTieModel.nextTurn();
  }

  @Test(expected = IllegalStateException.class)
  public void testNextTurnGameHasNotStartedThrowISE() {
    inactiveModel.nextTurn();
  }

  @Test(expected = IllegalStateException.class)
  public void testNextTurnInvalidPhaseThrowsISE() {
    activeModel.nextTurn();
  }

  @Test(expected = IllegalStateException.class)
  public void testBattleGameEndedThrowISE() {
    finishedWithTieModel.battle();
  }

  @Test(expected = IllegalStateException.class)
  public void testBattleGameHasNotStartedThrowISE() {
    inactiveModel.battle();
  }

  @Test(expected = IllegalStateException.class)
  public void testBattleInvalidPhaseThrowsISE() {
    activeModel.battle();
  }

  @Test(expected = IllegalStateException.class)
  public void testBattleTwiceInOneTurnThrowsISE() {
    activeModel.playCard(0, 0, 0);
    activeModel.battle();
    activeModel.battle();
  }

  @Test
  public void testBattleDoesNotFlipCardColorForSameAttackValue() {
    List<Card> deck = new ArrayList<>();
    for (int index = 0; index < 20; index++) {
      deck.add(new GameCard("Test Card " + index, AttackValue.A,
              AttackValue.A, AttackValue.A, AttackValue.A));
    }
    inactiveModel = new ThreeTriosGameModel(ConfigurationFileReader
            .createGridFromFile("gridTwoByTwo.txt"),
            deck, null);
    inactiveModel.startGame();

    // The first card's south value is the same as the second card's north value
    inactiveModel.playCard(0, 1, 0);
    inactiveModel.battle();
    inactiveModel.nextTurn();
    inactiveModel.playCard(1, 1, 0);
    inactiveModel.battle();
    assertEquals(Color.RED, inactiveModel.getCellAt(0, 1).getCard().getColor());
    assertEquals(Color.BLUE, inactiveModel.getCellAt(1, 1).getCard().getColor());
  }

  @Test
  public void testBattleChainFlips() {
    activeModel.playCard(1, 1, 0);
    activeModel.battle();
    activeModel.nextTurn();
    activeModel.playCard(3, 1, 0);
    activeModel.battle();
    activeModel.nextTurn();
    activeModel.playCard(3, 0, 0);
    activeModel.battle();
    activeModel.nextTurn();
    activeModel.playCard(2, 0, 0);
    assertEquals(Color.BLUE, activeModel.getCellAt(3, 1).getCard().getColor());
    assertEquals(Color.BLUE, activeModel.getCellAt(2, 0).getCard().getColor());
    assertEquals(Color.RED, activeModel.getCellAt(1, 1).getCard().getColor());
    assertEquals(Color.RED, activeModel.getCellAt(3, 0).getCard().getColor());
    activeModel.battle();
    // Make sure card is flipped
    assertEquals(Color.BLUE, activeModel.getCellAt(3, 1).getCard().getColor());
    assertEquals(Color.BLUE, activeModel.getCellAt(2, 0).getCard().getColor());
    assertEquals(Color.BLUE, activeModel.getCellAt(3, 0).getCard().getColor());
    assertEquals(Color.RED, activeModel.getCellAt(1, 1).getCard().getColor());
    activeModel.nextTurn();
    activeModel.playCard(3, 2, 1);
    activeModel.battle();
    // Make combo works and goes beyond one layer
    assertEquals(Color.RED, activeModel.getCellAt(3, 0).getCard().getColor());
    assertEquals(Color.RED, activeModel.getCellAt(3, 1).getCard().getColor());
  }

  @Test
  public void testBattleFlipsInAllDirections() {
    activeModel.playCard(1, 1, 5);
    activeModel.battle();
    activeModel.nextTurn();
    activeModel.playCard(3, 1, 2);
    activeModel.battle();
    activeModel.nextTurn();
    activeModel.playCard(3, 0, 3);
    activeModel.battle();
    activeModel.nextTurn();
    activeModel.playCard(2, 0, 0);
    activeModel.battle();
    activeModel.nextTurn();
    activeModel.playCard(0, 0, 0);
    // Checking that cards are flipped correctly in three directions.
    assertEquals(Color.RED, activeModel.getCellAt(0, 0).getCard().getColor());
    assertEquals(Color.RED, activeModel.getCellAt(1, 1).getCard().getColor());
    assertEquals(Color.BLUE, activeModel.getCellAt(2, 0).getCard().getColor());
    activeModel.battle();
    activeModel.nextTurn();
    activeModel.playCard(1, 0, 0);
    activeModel.battle();
    assertEquals(Color.BLUE, activeModel.getCellAt(0, 0).getCard().getColor());
    assertEquals(Color.BLUE, activeModel.getCellAt(1, 1).getCard().getColor());
    assertEquals(Color.BLUE, activeModel.getCellAt(2, 0).getCard().getColor());
  }

  @Test(expected = IllegalStateException.class)
  public void testIsGameOverGameNotStartedThrowsISE() {
    inactiveModel.isGameOver();
  }

  @Test
  public void testIsGameOverWorks() {
    assertFalse(activeModel.isGameOver());
    assertTrue(finishedWithWinningModel.isGameOver());
  }

  @Test
  public void testGetWinnerWorks() {
    assertEquals(Color.BLUE, finishedWithWinningModel.getWinner());
    assertNull(finishedWithTieModel.getWinner());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetWinnerGameHasNotStartedThrowsISE() {
    inactiveModel.getWinner();
  }

  @Test(expected = IllegalStateException.class)
  public void testGetWinnerGameNotEndedThrowsISE() {
    activeModel.getWinner();
  }

  @Test(expected = IllegalStateException.class)
  public void testGetPhaseGameNotStartedThrowsISE() {
    inactiveModel.getPhase();
  }

  @Test
  public void testGetPhaseWorks() {
    assertEquals(Phase.PLACING, activeModel.getPhase());
    assertEquals(Phase.BATTLE, finishedWithWinningModel.getPhase());
  }

  @Test
  public void testGetCurrentPlayerColorWorks() {
    assertEquals(Color.RED, activeModel.getCurrentPlayerColor());
    activeModel.playCard(0, 0, 0);
    activeModel.battle();
    activeModel.nextTurn();
    assertEquals(Color.BLUE, activeModel.getCurrentPlayerColor());
  }

  @Test
  public void testGetCellAtWorks() {
    List<Card> deck = ConfigurationFileReader.createDeckFromFile("deckLarge.txt");
    Card expectedCard = deck.get(0);
    inactiveModel = new ThreeTriosGameModel(ConfigurationFileReader
            .createGridFromFile("gridTwoByTwo.txt"),
            deck, null);
    inactiveModel.startGame();
    assertTrue(inactiveModel.getCellAt(1, 1).isEmpty());
    inactiveModel.playCard(1, 1, 0);
    assertEquals(expectedCard, inactiveModel.getCellAt(1, 1).getCard());
  }

  @Test
  public void testOneByOneGrid() {
    ThreeTriosGameModel model = new ThreeTriosGameModel(ConfigurationFileReader
            .createGridFromFile("gridOneByOne.txt"),
            ConfigurationFileReader.createDeckFromFile("deckLarge.txt"), null);
    model.startGame();
    model.playCard(0, 0, 0);
    model.battle();
    assertTrue(model.isGameOver());
    assertNull(model.getWinner());
  }

  @Test(expected = IllegalStateException.class)
  public void testAmountOfCardsFlippedByPlayingAtGameNotStartedThrowsISE() {
    inactiveModel.amountOfCardsFlippedByPlayingAt(sampleCard, 1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAmountOfCardsFlippedByPlayingAtRowIndexLessThanZeroThrowsIAE() {
    activeModel.amountOfCardsFlippedByPlayingAt(sampleCard, -1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAmountOfCardsFlippedByPlayingAtRowIndexOutOfBoundsThrowsIAE() {
    activeModel.amountOfCardsFlippedByPlayingAt(sampleCard, 100, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAmountOfCardsFlippedByPlayingAtColumnIndexLessThanZeroThrowsIAE() {
    activeModel.amountOfCardsFlippedByPlayingAt(sampleCard, 0, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAmountOfCardsFlippedByPlayingAtColumnIndexOutOfBoundsThrowsIAE() {
    activeModel.amountOfCardsFlippedByPlayingAt(sampleCard, 0, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAmountOfCardsFlippedByPlayingAtForIllegalSpotThrowsIAE() {
    activeModel.amountOfCardsFlippedByPlayingAt(sampleCard, 0, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAmountOfCardsFlippedByPlayingAtForNullCardThrowsIAE() {
    activeModel.amountOfCardsFlippedByPlayingAt(null, 0, 0);
  }

  @Test
  public void testAmountOfCardsFlippedByPlayingAtWorksForSingleLayerFlips() {
    activeModel.playCard(1, 1, 5);
    activeModel.battle();
    activeModel.nextTurn();
    activeModel.playCard(3, 1, 2);
    activeModel.battle();
    activeModel.nextTurn();
    activeModel.playCard(3, 0, 3);
    activeModel.battle();
    activeModel.nextTurn();
    activeModel.playCard(2, 0, 0);
    activeModel.battle();
    activeModel.nextTurn();
    activeModel.playCard(0, 0, 0);
    activeModel.battle();
    activeModel.nextTurn();
    Card toPlay = activeModel.getPlayerHand(activeModel.getCurrentPlayerColor()).get(0);
    assertEquals(2, activeModel.amountOfCardsFlippedByPlayingAt(toPlay, 1, 0));
  }

  @Test
  public void testAmountOfCardsFlippedByPlayingAtWorksForComboFlips() {
    activeModel.playCard(1, 1, 0);
    activeModel.battle();
    activeModel.nextTurn();
    activeModel.playCard(3, 1, 0);
    activeModel.battle();
    activeModel.nextTurn();
    activeModel.playCard(3, 0, 0);
    activeModel.battle();
    activeModel.nextTurn();
    activeModel.playCard(2, 0, 0);
    activeModel.battle();
    activeModel.nextTurn();
    Card toPlay = activeModel.getPlayerHand(activeModel.getCurrentPlayerColor()).get(1);
    assertEquals(2, activeModel.amountOfCardsFlippedByPlayingAt(toPlay, 3, 2));
  }

  @Test
  public void testGetPlayerHandWorks() {
    assertEquals(10, activeModel.getPlayerHand(Color.RED).size());
    activeModel.playCard(1, 1, 0);
    activeModel.battle();
    activeModel.nextTurn();
    assertEquals(9, activeModel.getPlayerHand(Color.RED).size());
    assertEquals(10, activeModel.getPlayerHand(Color.BLUE).size());
  }

  @Test
  public void testGetPlayerHandMutationDoesNotAffectOriginal() {
    List<Card> hand = activeModel.getPlayerHand(Color.RED);
    hand.remove(0);
    assertEquals(10, activeModel.getPlayerHand(Color.RED).size());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetGridGameNotStartedThrowsISE() {
    inactiveModel.getGrid();
  }

  @Test
  public void testGetGridWorks() {
    Cell[][] grid = activeModel.getGrid();
    assertEquals(5, grid.length);
    assertEquals(5, grid[0].length);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testGetPlayerHandWithNullPlayerThrowsIAE() {
    activeModel.getPlayerHand(null);
  }


  @Test
  public void testGetGridHeightWorks() {
    assertEquals(5, activeModel.getGridHeight());
  }

  @Test
  public void testGetGridWidthWorks() {
    assertEquals(5, activeModel.getGridWidth());
  }

  @Test(expected = IllegalStateException.class)
  public void testIsPlayLegalGameNotStartedThrowsISE() {
    inactiveModel.isPlayLegal(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsPlayLegalWithRowIndexLessThanZeroThrowsIAE() {
    activeModel.isPlayLegal(-1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsPlayLegalWithRowIndexOutOfBoundsThrowsIAE() {
    activeModel.isPlayLegal(100, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsPlayLegalWithColumnIndexLessThanZeroThrowsIAE() {
    activeModel.isPlayLegal(0, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsPlayLegalWithColumnIndexOutOfBoundsThrowsIAE() {
    activeModel.isPlayLegal(0, 100);
  }

  @Test
  public void testIsPlayLegalWorks() {
    //Hole
    assertFalse(activeModel.isPlayLegal(0, 1));
    //Occupied
    assertTrue(activeModel.isPlayLegal(0, 0));
    activeModel.playCard(0, 0, 0);
    assertFalse(activeModel.isPlayLegal(0, 0));
    //Free Space
    assertFalse(activeModel.isPlayLegal(0, 3));
  }

  @Test(expected = IllegalStateException.class)
  public void testGetPlayerScoreGameNotStartedThrowsISE() {
    inactiveModel.getPlayerScore(Color.BLUE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetPlayerScoreNullPlayerThrowsIAE() {
    activeModel.getPlayerScore(null);
  }

  @Test
  public void testGetPlayerScoreWorks() {
    assertEquals(10, activeModel.getPlayerScore(Color.RED));
    assertEquals(10, activeModel.getPlayerScore(Color.BLUE));
    activeModel.playCard(1, 1, 0);
    activeModel.battle();
    activeModel.nextTurn();
    activeModel.playCard(3, 1, 1);
    activeModel.battle();
    activeModel.nextTurn();
    activeModel.playCard(3, 0, 3);
    activeModel.battle();
    activeModel.nextTurn();
    assertEquals(11, activeModel.getPlayerScore(Color.RED));
    assertEquals(9, activeModel.getPlayerScore(Color.BLUE));
  }


  @Test
  public void testFullPlayThroughTillWon() {
    ThreeTriosGameModel model = new ThreeTriosGameModel(ConfigurationFileReader
            .createGridFromFile("gridThreeByThreeNoHoles.txt"),
            ConfigurationFileReader.createDeckFromFile("deckLarge.txt"), null);
    model.startGame();
    model.playCard(0, 0, 0);
    model.battle();
    model.nextTurn();
    model.playCard(1, 0, 0);
    model.battle();
    model.nextTurn();
    model.playCard(0, 1, 0);
    model.battle();
    model.nextTurn();
    model.playCard(1, 1, 0);
    model.battle();
    model.nextTurn();
    model.playCard(1, 2, 0);
    model.battle();
    model.nextTurn();
    model.playCard(0, 2, 0);
    model.battle();
    model.nextTurn();
    model.playCard(2, 0, 0);
    model.battle();
    model.nextTurn();
    model.playCard(2, 1, 0);
    model.battle();
    model.nextTurn();
    model.playCard(2, 2, 0);
    model.battle();
    assertTrue(model.isGameOver());
    assertEquals(Color.BLUE, model.getWinner());
  }

  @Test
  public void testFullPlayThroughTillTie() {
    ThreeTriosGameModel model = new ThreeTriosGameModel(ConfigurationFileReader
            .createGridFromFile("gridThreeByThreeNoHoles.txt"),
            ConfigurationFileReader.createDeckFromFile("deckLarge.txt"), null);
    model.startGame();
    model.playCard(1, 0, 0);
    model.battle();
    model.nextTurn();
    model.playCard(0, 0, 0);
    model.battle();
    model.nextTurn();
    model.playCard(0, 1, 0);
    model.battle();
    model.nextTurn();
    model.playCard(1, 1, 0);
    model.battle();
    model.nextTurn();
    model.playCard(1, 2, 0);
    model.battle();
    model.nextTurn();
    model.playCard(0, 2, 0);
    model.battle();
    model.nextTurn();
    model.playCard(2, 1, 0);
    model.battle();
    model.nextTurn();
    model.playCard(2, 2, 0);
    model.battle();
    model.nextTurn();
    model.playCard(2, 0, 0);
    model.battle();
    assertTrue(model.isGameOver());
    assertNull(model.getWinner());
  }
}