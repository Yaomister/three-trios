package cs3500.threetrios.model;

import cs3500.threetrios.controller.ModelFeatures;

/**
 * Behaviours for a game of Three Trios.
 * The game consists of three structures:
 * <ul>
 *   <li>A grid representing the coordinate system which the cells reside on</li>
 *   <li>A red player and a blue player</li>
 *   <li>Cells to play cards to, but could also be an unplayable hole</li>
 * </ul>
 * The goal of the game is for each player to take turns playing cards on to the grid until it
 * is full, while calculating the "damage" done by each card to its adjacent neighbours, and
 * flipping its colors.
 * The indexes of the grid are 0-based. Rows represent the Y axis, and columns represent the X axis.
 */
public interface ThreeTriosModel extends ReadOnlyThreeTriosModel {

  /**
   * Play the chosen card from the hand to the cell chosen in the grid. The card is removed from the
   * current player's hand and placed on the cell.
   *
   * @param cellIndexCol    the columns (X) index of the cell
   * @param cellIndexRow    the rows (Y) index of the cell
   * @param cardIndexInHand the index of the card in the hand
   * @throws IllegalStateException    if the game has not started or the game is over
   * @throws IllegalStateException    if the game is not in the placing phase
   * @throws IllegalArgumentException if trying to play to a hole or a cell occupied by another
   *                                  card
   * @throws IllegalArgumentException if the X or Y index is less than 0 or greater than the
   *                                  bounds of the grid
   */
  void playCard(int cellIndexRow, int cellIndexCol, int cardIndexInHand);

  /**
   * The startGame method is responsible for initializing and starting a game.
   * It performs checks to ensure the game can start and notifies any registered listeners
   * to handle the initial turn, indicating a game is active and going over the
   * features listeners list.
   *
   * @throws IllegalStateException if the game has already started or the game finished
   */
  void startGame();

  /**
   * Switch the game's current player to the next player. Doing so also puts the game in the placing
   * phase of the next player.
   *
   * @throws IllegalStateException if the game has not started or the game is over
   * @throws IllegalStateException if the player did not battle this turn
   */
  void nextTurn();

  /**
   * Deal damage to all cards in adjacent cells to the card placed in the most recent placing
   * phase (the center card). If the attack value of the center card is strictly greater than the
   * attack value of the adjacent card, than the adjacent card's color is set to match center card.
   * Applies the combo damage, that is: repeatedly damage the cards in the adjacent cells of the
   * cards that had their colors flipped in this phase, until a point is reached where no further
   * cards could be flipped.
   *
   * @throws IllegalStateException if the game has not started or the game is over
   * @throws IllegalStateException if the current phase is not the battle phase
   * @throws IllegalStateException if the player has already battled this turn
   */
  void battle();

  /**
   * Adds features to the game.
   *
   * @param features the features to add
   */
  void addFeatures(ModelFeatures features);

  /**
   * Checks if the flip condition is met for the given cards and direction.
   *
   * @param card1 first card
   * @param card2 second card
   * @param dir   direction
   * @param prev  previous card
   * @return true if the flip condition is met
   */
  boolean flipCondition(Card card1, Card card2, Direction dir, boolean prev);


}
