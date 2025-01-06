package cs3500.threetrios.model;

/**
 * This interface represents the behaviours of a cell used in the grid in a game of Three Trios.
 */
public interface Cell {

  /**
   * Return the card played onto this cell, if any.
   *
   * @return the card played onto this cell.
   */
  Card getCard();

  /**
   * Returns whether this cell is a hole.
   *
   * @return whether this cell is a hole.
   */
  boolean isHole();

  /**
   * Returns whether this cell is empty (not occupied by a card.).
   *
   * @return whether this cell is a empty.
   */
  boolean isEmpty();

  /**
   * Sets the cell as a hole.
   *
   * @param isHole whether this cell is a hole
   */
  void setHole(boolean isHole);
}
