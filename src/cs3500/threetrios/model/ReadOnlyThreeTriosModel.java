package cs3500.threetrios.model;

import java.util.List;

/**
 * Represents a read-only interface for the Three Trios game model.
 */
public interface ReadOnlyThreeTriosModel {

  /**
   * Returns if the game is over as specified by the implementation.
   *
   * @return true if the game has ended and false otherwise
   * @throws IllegalStateException if the game has not started
   */
  boolean isGameOver();

  /**
   * Returns the color of the winning player. The winner is determined by counter the number of
   * cards of each player's color both on the grid and in each player's hands. The player with the
   * most owned cards win. The game is tied if both players have equal amounts of owned cards.
   *
   * @return the color of the winning player, or null if there is a tie.
   * @throws IllegalStateException if the game has not started or the game is not over
   */
  Color getWinner();

  /**
   * Returns the current phase which the game is under.
   *
   * @return the current phase
   * @throws IllegalStateException if the game has not started.
   */
  Phase getPhase();


  /**
   * Returns a read-only version of the cell located at the location passed through the arguments.
   *
   * @param row the row index of the cell
   * @param col the column index of the cell
   * @return a read-only version of the cell at position (row, col).
   * @throws IllegalStateException    if the game has not started.
   * @throws IllegalArgumentException if the row or column index is out of bounds.
   */
  Cell getCellAt(int row, int col);

  /**
   * Returns the number of cards flipped by the current player by placing the card passed in the
   * parameters at the location passed in.
   *
   * @param card the card to place
   * @param row  the row index
   * @param col  the col index
   * @return the number of cards flipped by the current player by placing the card passed in the
   *     parameters at the location passed in
   *
   * @throws IllegalStateException    if the game has not started.
   * @throws IllegalArgumentException if the row or column index is out of bounds.
   * @throws IllegalArgumentException if the row or column index is not a legal play.
   * @throws IllegalArgumentException if card is null;
   */
  int amountOfCardsFlippedByPlayingAt(Card card, int row, int col);

  /**
   * Returns a copy of the target player's hand, containing cards that are read-only. Mutating
   * this list has no effect on the player's actual hand.
   *
   * @param player the color of the target player
   * @return a list that is a copy of the player's hand
   * @throws IllegalStateException    if the game has not started.
   * @throws IllegalArgumentException if player is null
   */
  List<Card> getPlayerHand(Color player);

  /**
   * Returns the width of the grid.
   *
   * @return the width of the grid
   * @throws IllegalStateException if the game has not started.
   */
  int getGridWidth();

  /**
   * Returns the height of the grid.
   *
   * @return the width of the grid.
   * @throws IllegalStateException if the game has not started
   */
  int getGridHeight();

  /**
   * Returns the color of the player whose turn it is.
   *
   * @return the color of the player whose turn it is.
   * @throws IllegalStateException if the game has not started
   */
  Color getCurrentPlayerColor();

  /**
   * Returns whether the location passed in is playable, meaning that it is not a hole, nor is it
   * occupied.
   *
   * @param row the row index
   * @param col the column index
   * @return whether the location passed in is playable.
   * @throws IllegalStateException    if the game has not started.
   * @throws IllegalArgumentException if the row or column index is out of bounds.
   */
  boolean isPlayLegal(int row, int col);

  /**
   * Calculates the target player's score, score being the amount of cards of their color
   * on the grid, plus the amount of cards they have left in their hand.
   *
   * @param playerColor the color of the player.
   * @return the target player's score.
   * @throws IllegalStateException    if the game has not started.
   * @throws IllegalArgumentException if playerColor is null;
   */
  int getPlayerScore(Color playerColor);


  /**
   * Returns a deep copy of the grid. Mutating the 2D array returned have no effect on the grid
   * in the model.
   *
   * @throws IllegalStateException if the game has not started.
   */
  Cell[][] getGrid();


}
