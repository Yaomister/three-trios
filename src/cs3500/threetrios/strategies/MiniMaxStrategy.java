package cs3500.threetrios.strategies;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.Cell;
import cs3500.threetrios.model.Color;
import cs3500.threetrios.model.GameCell;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

/**
 * This class represents a strategy where the algorithm minimizes the maximum move the opponent
 * can make, and is known as the minimax strategy.
 */
public class MiniMaxStrategy extends BaseStrategy {

  private final ThreeTriosStrategy opponentsStrategy;

  /**
   * Constructs a ThreeTriosStrategy.
   *
   * @param nextStrategy the next strategy
   */
  public MiniMaxStrategy(ThreeTriosStrategy nextStrategy, ThreeTriosStrategy opponentsStrategy) {
    super(nextStrategy);
    this.opponentsStrategy = opponentsStrategy;
  }

  @Override
  protected List<Move> applyStrategy(ReadOnlyThreeTriosModel model, Color playerColor,
                                     List<Move> previousMoves) {
    Color opponentsColor = playerColor == Color.RED ? Color.BLUE : Color.RED;
    List<Move> opponentsBestMoves = opponentsStrategy.chooseMoves(model, opponentsColor,
            null);

    List<MoveScorePair> bestMoves = new ArrayList<>();
    for (Move move : previousMoves) {
      bestMoves.add(getMinimizingMove(model, move, opponentsBestMoves));
    }

    List<MoveScorePair> minimizedOpponentsScoreMoves = new ArrayList<>();
    for (MoveScorePair msPair : bestMoves) {
      if (minimizedOpponentsScoreMoves.isEmpty()) {
        minimizedOpponentsScoreMoves.add(msPair);
        continue;
      }
      if (msPair.score < minimizedOpponentsScoreMoves.get(0).score) {
        minimizedOpponentsScoreMoves.clear();
        minimizedOpponentsScoreMoves.add(msPair);
      } else if (msPair.score == minimizedOpponentsScoreMoves.get(0).score) {
        minimizedOpponentsScoreMoves.add(msPair);
      }

    }
    List<Move> res = new ArrayList<>();
    for (MoveScorePair pair : minimizedOpponentsScoreMoves) {
      res.add(pair.move);
    }
    return res;
  }

  // Get the move that makes the opponents's move flip the least amounts of card.
  private MoveScorePair getMinimizingMove(ReadOnlyThreeTriosModel model,
                                          Move move, List<Move> opponentsBestMoves) {
    int totalAmount = 0;
    for (Move oppMove : opponentsBestMoves) {
      Cell[][] grid = model.getGrid();
      grid[move.row][move.col] = new GameCell(move.card);
      grid[oppMove.row][oppMove.col] = new GameCell(oppMove.card);
      totalAmount += getAmountFlipped(oppMove.row, oppMove.col, grid);
    }
    return new MoveScorePair(totalAmount, move);
  }

  // Recursively find out how many cards will be flipped by placing a card at the row and column
  // index passed in.
  private int getAmountFlipped(int row, int col, Cell[][] grid) {
    int amount = 1;

    if (row < 0 || row > grid.length - 1 || col < 0 || col > grid[0].length - 1
            || grid[row][col].isEmpty()) {
      return amount;
    }
    if (row + 1 < grid.length
            && !grid[row + 1][col].isEmpty()
            && grid[row][col].getCard().getNorth().getValue()
            > grid[row + 1][col].getCard().getSouth().getValue()
            && grid[row][col].getCard().getColor() != grid[row + 1][col].getCard().getColor()) {
      amount += getAmountFlipped(row + 1, col, grid);
    }
    if (row - 1 > 0 && !grid[row - 1][col].isEmpty()
            && grid[row][col].getCard().getSouth().getValue()
            > grid[row - 1][col].getCard().getNorth().getValue()
            && grid[row][col].getCard().getColor() != grid[row - 1][col].getCard().getColor()) {
      amount += getAmountFlipped(row - 1, col, grid);
    }
    if (col + 1 < grid[0].length
            && !grid[row][col + 1].isEmpty() && grid[row][col].getCard().getEast().getValue()
            > grid[row][col + 1].getCard().getWest().getValue()
            && grid[row][col].getCard().getColor() != grid[row][col + 1].getCard().getColor()) {
      amount += getAmountFlipped(row, col + 1, grid);
    }
    if (col - 1 > 0 && !grid[row][col - 1].isEmpty()
            && grid[row][col].getCard().getWest().getValue()
            > grid[row][col - 1].getCard().getEast().getValue()
            && grid[row][col].getCard().getColor() != grid[row][col - 1].getCard().getColor()) {
      amount += getAmountFlipped(row, col + 1, grid);
    }
    return amount;
  }

  /**
   * Represents object used to store a move and the score associated with the move.
   */
  private static class MoveScorePair {
    int score;
    Move move;

    private MoveScorePair(int score, Move move) {
      this.score = score;
      this.move = move;
    }

  }

}
