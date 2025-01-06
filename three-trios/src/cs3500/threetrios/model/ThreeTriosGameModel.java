package cs3500.threetrios.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cs3500.threetrios.controller.ModelFeatures;


/**
 * Represents a ThreeTrios game.
 * Represents the model of the game, which contains the state of the game.
 * It provides methods to start the game, get the current state of the game,
 * and make moves in the game. Placing the game’s functionality in a model class,
 * ensures that the model handles game logic and state.
 */
public class ThreeTriosGameModel extends ThreeTriosGameVariantModel {

  protected final Grid grid;
  // Class Invariant: currentTurn is not null.
  private Color currentTurn;
  // Class Invariant: currentPhase is not null.
  private Phase currentPhase;
  private boolean isGameStarted;
  private boolean isGameFinished;
  private final ModelPlayer redPlayer;
  private final ModelPlayer bluePlayer;
  // Represents the cell played this turn.
  protected Cell mostRecentPlayedCell;
  // Whether battle has been called this turn.
  private boolean battledThisTurn;
  private final List<ModelFeatures> featuresListeners = new ArrayList<>();

  /**
   * Constructs a new ThreeTriosGameModel. This constructor initializes the game state and sets
   * up the initial state of the game. It sets up the grid, deck, and players.
   *
   * @param grid The grid of the game.
   * @param deck The deck of cards.
   */
  public ThreeTriosGameModel(boolean[][] grid, List<Card> deck, ThreeTriosModel nextModel) {
    super(nextModel);
    this.isGameFinished = false;
    this.isGameStarted = false;
    this.redPlayer = new GameModelPlayer("Red", Color.RED);
    this.bluePlayer = new GameModelPlayer("Blue", Color.BLUE);
    this.battledThisTurn = false;
    this.currentPhase = Phase.PLACING;
    this.currentTurn = Color.RED;

    if (grid == null) {
      throw new IllegalArgumentException("Grid is null");
    }
    if (deck == null) {
      throw new IllegalArgumentException("Deck is null");
    }
    if (deck.isEmpty()) {
      throw new IllegalArgumentException("Deck is empty");
    }
    // Read in grid from the files passed in.
    this.grid = new GameGrid(grid);

    if (deck.isEmpty()) {
      throw new IllegalArgumentException("Deck cannot be or empty.");
    }
    int handSize = (this.grid.getAllPlayableCells().size() + 1) / 2;
    if (deck.size() < (handSize * 2)) {
      throw new IllegalArgumentException("Deck does not have enough cards to fill players' hands.");
    }

    Set<Card> toAdd = new HashSet<>(deck);
    if (toAdd.size() != deck.size()) {
      throw new IllegalArgumentException("Deck contains two (or more) cards with the same name");
    }

    // Draw cards for each player.
    drawForHand(deck, redPlayer, handSize);
    drawForHand(deck, bluePlayer, handSize);

  }

  @Override
  public void startGame() {
    if (this.isGameStarted || this.isGameFinished) {
      throw new IllegalStateException("Game has already started or has already finished.");
    }
    this.isGameStarted = true;
    for (ModelFeatures listener : this.featuresListeners) {
      listener.playTurn(this.currentTurn);
    }
  }

  /*
   This is the only method that changes Invariant 2, the phase value is the nextTurn method,
   which still only sets the variable to either BLUE or RED, meaning it will never be null.
   */
  @Override
  public void nextTurn() {
    checkValidGameStateToPlay();
    if (!battledThisTurn) {
      throw new IllegalStateException("Did not battle this turn");
    }
    if (this.currentTurn == Color.RED) {
      currentTurn = Color.BLUE;
    } else {
      currentTurn = Color.RED;
    }

    // Reset the phase to the "placing" phase.
    this.currentPhase = Phase.PLACING;
    battledThisTurn = false;
    for (ModelFeatures listener : this.featuresListeners) {
      listener.playTurn(currentTurn);
    }
  }

  @Override
  public void playCard(int cellRowIndex, int cellColIndex, int cardIndexInHand) {
    checkValidGameStateToPlay();
    if (this.currentPhase != Phase.PLACING) {
      throw new IllegalStateException("Not in the placing phase.");
    }
    if (cellColIndex < 0 || cellColIndex > this.grid.getNumCols() - 1) {
      throw new IllegalArgumentException("Column index out of bounds.");
    }
    if (cellRowIndex < 0 || cellRowIndex > this.grid.getNumRows() - 1) {
      throw new IllegalArgumentException("Row index out of bounds.");
    }
    ModelPlayer currentPlayer = this.currentTurn == Color.RED ? this.redPlayer : this.bluePlayer;

    Card cardToPlay = currentPlayer.removeCardFromHand(cardIndexInHand);
    this.grid.placeCard(cellRowIndex, cellColIndex, cardToPlay);
    mostRecentPlayedCell = grid.getCell(cellRowIndex, cellColIndex);
    // Allows the player to battle after placing card.
    this.currentPhase = Phase.BATTLE;
  }

  @Override
  public boolean isGameOver() {
    if (!this.isGameStarted) {
      throw new IllegalStateException("Game has not started.");
    }
    return this.isGameFinished;
  }

  @Override
  public Color getWinner() {
    if (!isGameFinished) {
      throw new IllegalStateException("Game not finished yet.");
    }
    int redPlayerCards = getPlayerScore(Color.RED);
    int bluePlayerCards = getPlayerScore(Color.BLUE);

    if (redPlayerCards == bluePlayerCards) {
      return null;
    }
    return redPlayerCards > bluePlayerCards ? Color.RED : Color.BLUE;
  }


  @Override
  public Phase getPhase() {
    checkGameStarted();
    return this.currentPhase;
  }

  @Override
  public Cell getCellAt(int row, int col) {
    checkIndexOutOfBounds(row, col);

    Cell originalCell = grid.getCell(row, col);
    if (originalCell.getCard() == null || originalCell.isHole()) {
      GameCell copyCell = new GameCell(null);
      copyCell.setHole(originalCell.isHole());
      return copyCell;
    } else {
      Card originalCard = originalCell.getCard();

      GameCard copyCard = new GameCard(originalCard.getName(), originalCard.getNorth(),
              originalCard.getSouth(),
              originalCard.getEast(), originalCard.getWest());
      copyCard.setColor(originalCard.getColor());
      return new GameCell(copyCard);
    }


  }

  @Override
  public List<Card> getPlayerHand(Color player) {
    if (player == null) {
      throw new IllegalArgumentException("The player's color is null");
    }
    return player == Color.RED ? redPlayer.getHand() : bluePlayer.getHand();
  }

  @Override
  public int getGridWidth() {
    return this.grid.getNumCols();
  }

  @Override
  public int getGridHeight() {
    return this.grid.getNumRows();
  }

  @Override
  public Color getCurrentPlayerColor() {
    return this.currentTurn;
  }

  @Override
  public boolean isPlayLegal(int row, int col) {
    checkGameStarted();
    checkIndexOutOfBounds(row, col);
    Cell cell = this.getCellAt(row, col);
    return !cell.isHole() && cell.isEmpty();
  }

  @Override
  public int getPlayerScore(Color playerColor) {
    checkGameStarted();
    if (playerColor == null) {
      throw new IllegalArgumentException("Player color is null");
    }
    ModelPlayer player = playerColor == Color.RED ? redPlayer : bluePlayer;
    return player.getHand().size() + grid.getNumCardsOfColor(playerColor);
  }

  @Override
  public Cell[][] getGrid() {
    checkGameStarted();
    Cell[][] gridCopy = new GameCell[grid.getNumRows()][grid.getNumCols()];
    for (int row = 0; row < grid.getNumRows(); row++) {
      for (int col = 0; col < grid.getNumCols(); col++) {
        gridCopy[row][col] = getCellAt(row, col);
      }
    }
    return gridCopy;
  }

  @Override
  public void battle() {
    checkCanBattle();
    for (Card card : this.getCardsFlippedByPlayingAt(mostRecentPlayedCell.getCard(),
            mostRecentPlayedCell,
            this.getNonChainedCardsFlippedByPlayingAt(this.mostRecentPlayedCell.getCard(),
                    this.mostRecentPlayedCell))) {
      card.setColor(this.getCurrentPlayerColor());
    }
    checkIsGameOver();
    battledThisTurn = true;
    for (ModelFeatures listener : this.featuresListeners) {
      listener.update();
    }
  }

  /**
   * Helper method to check if still can battle.
   */
  protected void checkCanBattle() {
    checkValidGameStateToPlay();
    if (this.currentPhase != Phase.BATTLE) {
      throw new IllegalStateException("Not in the battling phase.");
    }
    if (battledThisTurn) {
      throw new IllegalStateException("Already battled this turn");
    }
  }

  @Override
  public void addFeatures(ModelFeatures features) {
    if (features == null) {
      throw new IllegalArgumentException("Features cannot be null");
    }
    this.featuresListeners.add(features);
  }

  @Override
  public boolean flipCondition(Card card, Card opp, Direction dir, boolean prev) {
    int damage = card.compareAttackValue(opp, dir);
    if (this.nextModel != null) {
      return this.nextModel.flipCondition(card, opp, dir, damage > 0);
    }
    return damage > 0;
  }

  @Override
  public int amountOfCardsFlippedByPlayingAt(Card card, int row, int col) {
    checkGameStarted();
    checkIndexOutOfBounds(row, col);
    if (!isPlayLegal(row, col)) {
      throw new IllegalArgumentException("Cell is either occupied or a hole");
    }
    if (card == null) {
      throw new IllegalArgumentException("Card is null");
    }
    Cell center = this.grid.getCell(row, col);
    List<Card> nonChainedFlips = getNonChainedCardsFlippedByPlayingAt(card, center);
    List<Card> chainedFlips = getCardsFlippedByPlayingAt(card, center, nonChainedFlips);
    return chainedFlips.size();
  }

  /**
   * Helper method to the non-chained cards flipped by playing at a cell.
   *
   * @param card   the card played
   * @param center the cell played at
   * @return the list of cards flipped
   */
  protected List<Card> getNonChainedCardsFlippedByPlayingAt(Card card, Cell center) {
    return new ArrayList<>();
  }

  /**
   * Applies damage to the cells adjacent to the center cell, and return those that were flipped.
   */
  protected List<Card> getCardsFlippedByPlayingAt(Card card, Cell center, List<Card> toCombo) {
    List<Cell> toBattle = new ArrayList<>();
    List<Card> toFlip = new ArrayList<>(toCombo);
    if (!toCombo.isEmpty()) {
      for (Cell cell : this.grid.getAllCells()) {
        if (toCombo.contains(cell.getCard())) {
          toBattle.add(cell);
        }
      }
    }

    while (true) {
      Map<Direction, Cell> adjacentCells = this.grid.getAdjacentCells(center);
      for (Direction dir : adjacentCells.keySet()) {
        // Get the cell in the direction
        Cell cellInDirection = adjacentCells.get(dir);
        Card cardInDirection = cellInDirection.getCard();
        if (!cellInDirection.isEmpty() && cellInDirection.getCard().getColor()
                != getCurrentPlayerColor()) {
          // the center card won, change colors
          if (flipCondition(card, cardInDirection, dir, true)
                  && !toFlip.contains(cellInDirection.getCard())) {
            toFlip.add(cardInDirection);
            toBattle.add(cellInDirection);
          }
        }
      }
      if (!toBattle.isEmpty()) {
        center = toBattle.remove(0);
        card = center.getCard();
      } else {
        break;
      }
    }
    return toFlip;
  }

  // Draw the given amount of cards to the player.
  private void drawForHand(List<Card> deck, ModelPlayer player, int amount) {
    while (amount > 0) {
      Card drawnCard = deck.remove(0);
      drawnCard.setColor(player.getColor());
      player.addCardToHand(drawnCard);
      amount--;
    }
  }

  // Checks that the row and column index is within the bounds of the grid.
  private void checkIndexOutOfBounds(int row, int col) {
    if (row < 0 || col < 0 || row >= grid.getNumRows() || col >= grid.getNumCols()) {
      throw new IllegalArgumentException("Index of row/column out of bounds");
    }

  }

  // Throws an IllegalStateException if the game has not started.
  private void checkGameStarted() {
    if (!this.isGameStarted) {
      throw new IllegalStateException("Game has not started started");
    }
  }

  // Throws an IllegalStateException if the game has not started or the game is finished.
  private void checkValidGameStateToPlay() {
    checkGameStarted();
    if (this.isGameFinished) {
      throw new IllegalStateException("Game has already ended.");
    }
  }

  // Sets isGameFinished to true if all playable cells in the grid are filled.
  private void checkIsGameOver() {
    List<Cell> allCells = grid.getAllPlayableCells();
    List<Cell> playableCells = allCells.stream().filter(Cell::isEmpty).collect(Collectors.toList());
    if (playableCells.isEmpty()) {
      this.isGameFinished = true;
    }

  }

}