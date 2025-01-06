package cs3500.threetrios.strategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Color;
import cs3500.threetrios.model.Direction;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

/**
 * This strategy will determine the best move by choosing the position and card where the
 * opponent has the least chance of flipping it.
 */
public class MinimizeChanceOfBeingFlippedStrategy extends BaseStrategy {

  /**
   * Constructs a strategy with a callback strategy to fall on
   * (the next strategy in line to be applied).
   *
   * @param nextStrategy the callback strategy
   */
  public MinimizeChanceOfBeingFlippedStrategy(ThreeTriosStrategy nextStrategy) {
    super(nextStrategy);
  }

  // Method to apply strategy.
  @Override
  public List<Move> applyStrategy(ReadOnlyThreeTriosModel model, Color playerColor,
                                  List<Move> previousMoves) {
    List<Card> opponentHand = model.getPlayerHand(playerColor == Color.RED
            ? Color.BLUE : Color.RED);

    Map<Move, Integer> allPossibleMoves = new HashMap<Move, Integer>();
    for (Move move : previousMoves) {
      allPossibleMoves.put(move, amountOfOpponentCardsCanFlip(model, opponentHand, move));
    }

    List<Move> bestMoves = new ArrayList<>();
    for (Map.Entry<Move, Integer> entry : allPossibleMoves.entrySet()) {
      if (bestMoves.isEmpty()) {
        bestMoves.add(entry.getKey());
        continue;
      }
      int lowestAmountFlipped = allPossibleMoves.get(bestMoves.get(0));
      if (entry.getValue() < lowestAmountFlipped) {
        bestMoves.clear();
        bestMoves.add(entry.getKey());
      } else if (bestMoves.isEmpty() || entry.getValue() == lowestAmountFlipped) {
        bestMoves.add(entry.getKey());
      }
    }

    if (nextStrategy != null) {
      return nextStrategy.chooseMoves(model, playerColor, bestMoves);
    }
    return bestMoves;
  }


  /**
   * Find the amount of opponent's card flip that can flip the players card by placing it at the
   * position in the move passed in.
   */
  protected int amountOfOpponentCardsCanFlip(ReadOnlyThreeTriosModel model, List<Card> opponentHand,
                                             Move move) {
    int amount = 0;
    Card card = move.card;
    for (Direction dir : Direction.values()) {
      int row = move.row;
      int col = move.col;
      switch (dir) {
        case NORTH:
          row = row < model.getGridHeight() - 1 ? row + 1 : row;
          break;
        case SOUTH:
          row = row < 0 ? row - 1 : row;
          break;
        case EAST:
          col = col < model.getGridWidth() - 1 ? col + 1 : col;
          break;
        case WEST:
          col = col < 0 ? col - 1 : col;
          break;
        default:
          // No other case possible.
      }
      if (model.isPlayLegal(row, col)) {
        for (Card opponentCard : opponentHand) {
          if (card.compareAttackValue(opponentCard, dir) > 0) {
            amount++;
          }
        }
      }
    }
    return amount;
  }

}
