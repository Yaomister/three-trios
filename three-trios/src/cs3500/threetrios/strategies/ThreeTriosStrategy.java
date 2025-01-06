package cs3500.threetrios.strategies;

import java.util.List;

import cs3500.threetrios.model.Color;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

/**
 * Represents the behaviours of a strategy used to determine the best move for a player to play.
 * The way the best move is determined depends on the specific implementation.
 */
public interface ThreeTriosStrategy {


  /**
   * Chooses the best move for the given player to play. The method the best move is determined
   * depends on the specific implementation. If there are multiple best moves, the method will break
   * ties by choosing the top-most, left-most, and card index closest to 0 move (in that order).
   * A strategy is infailable, meaning it will throw an error if it cannot produce a solution. If a
   * strategy could prodice an answer, this method will return the top-most, left-most, and card
   * index at 0 in the hand out of the pool of possible moves. If null is passed in for
   * possibleMoves, the strategies will instead search all possible moves for the player to play.
   *
   * @param model         the model the player is playing on
   * @param playerColor   the player's color
   * @param possibleMoves the pool of moves for the strategy to look through.
   * @return a list of moves containing a single move that is considered the best move.
   * @throws IllegalArgumentException if model or playerColor is null.
   * @throws IllegalStateException    if the game is already over, or when it is unable to find a
   *                                  single cell to play at.
   */
  List<Move> chooseMoves(ReadOnlyThreeTriosModel model,
                         Color playerColor, List<Move> possibleMoves);


}
