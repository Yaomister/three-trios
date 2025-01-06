package cs3500.threetrios.strategies;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Color;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

/**
 * This strategy represents the tie breaking mechanic for other strategies. Out of the pool of
 * possible moves, it will choose the move with the top-most, left-most position, and card index
 * closest to 0 in the player hand (in that order specifically).
 */
class BreakTieStrategy extends BaseStrategy {

  /**
   * Constructs the strategy.
   */
  public BreakTieStrategy() {
    super(null);
  }

  // Method to apply strategy.
  @Override
  protected List<Move> applyStrategy(ReadOnlyThreeTriosModel model, Color playerColor,
                                     List<Move> previousMoves) {
    Move bestMove = null;
    for (Move move : previousMoves) {
      if (bestMove == null) {
        bestMove = move;
        continue;
      }
      bestMove = breakTie(move, bestMove, model.getPlayerHand(playerColor));
    }
    List<Move> singleBestMove = new ArrayList<>();
    singleBestMove.add(bestMove);
    return singleBestMove;
  }

  // Determine which move is "better"
  private Move breakTie(Move move1, Move move2, List<Card> hand) {
    int rowMove1 = move1.row;
    int rowMove2 = move2.row;
    if (rowMove1 == rowMove2) {
      int colMove1 = move1.col;
      int colMove2 = move2.col;
      if (colMove1 == colMove2) {
        return hand.indexOf(move1.card) < hand.indexOf(move2.card) ? move1 : move2;
      } else {
        return colMove1 < colMove2 ? move1 : move2;
      }
    } else {
      return rowMove1 < rowMove2 ? move1 : move2;
    }
  }
}
