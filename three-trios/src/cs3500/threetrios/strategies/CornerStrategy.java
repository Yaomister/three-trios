package cs3500.threetrios.strategies;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.Color;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

/**
 * This strategy represents the corner strategy; It will return the four corners of the grid,
 * whichever is not occupied. (if they are all occupied, then the tie-breaking mechanic will kick in
 * and choose the top-most, left-most, index 0 card move will be returned).
 * This class extends the MinimizeChanceOfBeingFlippedStrategy because they are essentially the same
 * strategy, except this strategy limits the places the card could be placed to the four corners.
 * The way they determine which card is hardest to flip reuses the functionality of the superclass.
 */
public class CornerStrategy extends MinimizeChanceOfBeingFlippedStrategy {

  /**
   * Constructs a strategy with a callback strategy to fall on
   * (the next strategy in line to be applied).
   *
   * @param nextStrategy the callback strategy
   */
  public CornerStrategy(ThreeTriosStrategy nextStrategy) {
    super(nextStrategy);
  }

  // Method to apply strategy.
  @Override
  public List<Move> applyStrategy(ReadOnlyThreeTriosModel model, Color playerColor,
                                  List<Move> previousMoves) {
    List<Move> possibleMoves = new ArrayList<>();
    for (Move move : previousMoves) {
      if ((move.row == 0 && move.col == 0)
              || (move.row == 0 && move.col == model.getGridWidth() - 1)
              || (move.row == model.getGridHeight() - 1 && move.col == 0)
              || (move.row == model.getGridHeight() - 1 && move.col == model.getGridWidth() - 1)) {
        possibleMoves.add(move);
      }
    }

    return super.applyStrategy(model, playerColor, possibleMoves);
  }
}


