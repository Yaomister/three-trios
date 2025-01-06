package cs3500.threetrios.strategies;


import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Color;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

/**
 * Abstract class for a strategy, containing the shared functionality of all strategies.
 */
public abstract class BaseStrategy implements ThreeTriosStrategy {

  protected final ThreeTriosStrategy nextStrategy;

  /**
   * Constructs a ThreeTriosStrategy.
   *
   * @param nextStrategy the next strategy
   */
  public BaseStrategy(ThreeTriosStrategy nextStrategy) {
    this.nextStrategy = nextStrategy;
  }

  // method to choose the moves
  @Override
  public List<Move> chooseMoves(ReadOnlyThreeTriosModel model, Color playerColor,
                                List<Move> possibleMoves) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    if (model.isGameOver()) {
      throw new IllegalStateException("The game is already over");
    }
    if (playerColor == null) {
      throw new IllegalArgumentException("The player's color cannot be null");
    }

    if (possibleMoves == null) {
      possibleMoves = new ArrayList<>();
      for (Card card : model.getPlayerHand(playerColor)) {
        possibleMoves.addAll(getAllPossibleMoves(model, card));
      }
      if (possibleMoves.isEmpty()) {
        throw new IllegalStateException("Unable to play anywhere on the grid");
      }
    }

    List<Move> remainingMoves = this.applyStrategy(model, playerColor, possibleMoves);
    if (remainingMoves.size() == 1) {
      return remainingMoves;
    } else if (remainingMoves.isEmpty()) {
      return new BreakTieStrategy().applyStrategy(model, playerColor, possibleMoves);
    }
    return new BreakTieStrategy().applyStrategy(model, playerColor, remainingMoves);
  }

  /**
   * Chooses a move for the given player; The way which the  move is chosen depends on the specific
   * implementation of this method.
   *
   * @param model         the model
   * @param playerColor   the player's color
   * @param previousMoves the pool of moves that method will find a solution in.
   * @return a list of moves
   */
  protected abstract List<Move> applyStrategy(ReadOnlyThreeTriosModel model, Color playerColor,
                                              List<Move> previousMoves);

  /**
   * Get all combinations of places the card could be placed on to the grid.
   *
   * @param model the model
   * @param card  the card
   * @return a list of moves
   */
  protected List<Move> getAllPossibleMoves(ReadOnlyThreeTriosModel model, Card card) {
    List<Move> possibleMoves = new ArrayList<>();
    for (int row = 0; row < model.getGridHeight(); row++) {
      for (int col = 0; col < model.getGridWidth(); col++) {
        if (model.isPlayLegal(row, col)) {
          possibleMoves.add(new Move(row, col, card));
        }
      }
    }
    return possibleMoves;
  }


}
