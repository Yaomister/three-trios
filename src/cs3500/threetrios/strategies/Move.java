package cs3500.threetrios.strategies;

import java.util.Objects;

import cs3500.threetrios.model.Card;

/**
 * Represents a move in the game, only used as a data holder for a row, column, and a card.
 */
public class Move {

  public final int row;
  public final int col;
  public final Card card;

  /**
   * Constructs a move.
   *
   * @param row  the row
   * @param col  the column
   * @param card the card
   */
  public Move(int row, int col, Card card) {
    this.row = row;
    this.col = col;
    this.card = card;
  }

  // To string method
  @Override
  public String toString() {
    return "[" + this.row + ", " + this.col + ", " + this.card.getName() + "]";
  }

  // Equals methods
  @Override
  public boolean equals(Object other) {
    if (other instanceof Move) {
      Move moveOther = (Move) other;
      return moveOther.card.equals(this.card)
              && moveOther.row == this.row && moveOther.col == this.col;
    }
    return false;
  }

  // Hashcode method.
  @Override
  public int hashCode() {
    return Objects.hash(this.card, this.row, this.card);
  }
}
