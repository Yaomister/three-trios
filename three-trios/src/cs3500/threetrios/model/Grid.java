package cs3500.threetrios.model;

import java.util.List;
import java.util.Map;


/**
 * This interface represents the behaviours of the Grid for a game of Three Trios.
 */
public interface Grid {

  /**
   * Gets the number of columns in this grid.
   *
   * @return the number of columns
   */
  int getNumCols();

  /**
   * Gets the number of rows in this grid.
   *
   * @return the number of rows
   */
  int getNumRows();

  /**
   * Returns the cell at the given row and column.
   *
   * @param row the row of the cell
   * @param col the column of the cell
   * @return the cell at the given row and column
   * @throws IllegalArgumentException if the index of row or column is out of bounds.
   */
  Cell getCell(int row, int col);


  /**
   * Get all playable cells within the grid (cells that are not holes).
   *
   * @return A list of all playable cells.
   */
  List<Cell> getAllPlayableCells();

  /**
   * Get all cells in the grid.
   *
   * @return A list of all cells.
   */
  List<Cell> getAllCells();

  /**
   * Places a card in the specified cell of the grid, assigning it to the provided owner.
   *
   * @param row  the row index where the card should be placed.
   * @param col  the column index where the card should be placed.
   * @param card the card to be placed in the grid.
   * @throws IllegalArgumentException if the specified cell is a hole or occupied by another card.
   * @throws IllegalArgumentException if the row or column index is out of bounds.
   */
  void placeCard(int row, int col, Card card);

  /**
   * Retrieves a map of the cells adjacent to the specified center cell if they are not holes.
   *
   * @param center the center cell whose adjacent cells are to be found.
   * @return a map where the keys are directions and the values are the adjacent cells.
   * @throws IllegalArgumentException if the specified center cell is not found within the grid.
   * @throws IllegalArgumentException if the specific cell is null
   */
  Map<Direction, Cell> getAdjacentCells(Cell center);

  /**
   * Returns the cell at the specified row and column.
   *
   * @param cell the cell to get the row and column of
   * @return an array of two integers, the first being the row and the second being the column
   * @throws IllegalArgumentException if cell passed in is null
   * @throws IllegalArgumentException if cell doesn't exist within the grid
   */
  int[] getIndexOfCell(Cell cell);

  /**
   * Returns the number of cards of a given color in the grid.
   *
   * @param color the color to count
   * @return the number of cards of the given color in the grid
   * @throws IllegalArgumentException if the color is null
   */
  int getNumCardsOfColor(Color color);
}
