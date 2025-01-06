package cs3500.threetrios.strategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.threetrios.model.Color;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

/**
 * Class for the strategy that maximizes the number of cards flipped. This strategy accounts for
 * both the immediate flips and the flips through the combo move.
 */
public class MaximizeFlipsStrategy extends BaseStrategy {

  /**
   * Constructs a strategy with a callback strategy to fall on
   * (the next strategy in line to be applied).
   *
   * @param nextStrategy the callback strategy
   */
  public MaximizeFlipsStrategy(ThreeTriosStrategy nextStrategy) {
    super(nextStrategy);
  }

  // Method to apply strategy.
  @Override
  public List<Move> applyStrategy(ReadOnlyThreeTriosModel model, Color playerColor,
                                  List<Move> previousMoves) {

    Map<Move, Integer> allPossibleMoves = new HashMap<>();

    for (Move move : previousMoves) {
      allPossibleMoves.put(move,
              model.amountOfCardsFlippedByPlayingAt(move.card, move.row, move.col));
    }

    List<Move> bestMoves = new ArrayList<>();
    for (Map.Entry<Move, Integer> entry : allPossibleMoves.entrySet()) {
      if (bestMoves.isEmpty()) {
        bestMoves.add(entry.getKey());
        continue;
      }
      int bestMoveAmountFlipped = allPossibleMoves.get(bestMoves.get(0));
      if (entry.getValue() > bestMoveAmountFlipped) {
        bestMoves = new ArrayList<>();
        bestMoves.add(entry.getKey());
      } else if (bestMoves.isEmpty() || entry.getValue() == bestMoveAmountFlipped) {
        bestMoves.add(entry.getKey());
      }
    }
    if (nextStrategy != null) {
      return nextStrategy.chooseMoves(model, playerColor, bestMoves);
    }
    return bestMoves;
  }


}
