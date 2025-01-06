package cs3500.threetrios.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a grid in the game.
 * A grid is a 2D array of cells. A grid is valid if it has an odd number of playable cells.
 * The indexes are 0-based. Rows represent the Y axis, and columns represent the X axis.
 * The top left cell is (0, 0) and the bottom right cell is (numRows - 1, numCols - 1).
 */
public class GameGrid implements Grid {

  // Class invariant: grid has a odd number of playable cells.
  private final Cell[][] grid;
  private final int numRows;
  private final int numCols;

  /**
   * Constructs a grid.
   *
   * @param holes the holes in the grid
   * @throws IllegalArgumentException if the grid 2D array is empty
   * @throws IllegalArgumentException if the grid 2D array has an even amount of playable cells.
   */
  public GameGrid(boolean[][] holes) {

    if (holes.length == 0 || holes[0].length == 0) {
      throw new IllegalArgumentException("Invalid board size");
    }
    this.numRows = holes.length;
    this.numCols = holes[0].length;

    this.grid = new GameCell[this.numRows][this.numCols];

    int amountHoles = 0;
    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid[row].length; col++) {
        GameCell cell = new GameCell(null);
        if (holes[row][col]) {
          cell.setHole(true);
          amountHoles++;
        }
        this.grid[row][col] = cell;
      }
    }

    int amountPlayableCells = (this.numCols * this.numRows) - amountHoles;

    if (amountPlayableCells % 2 == 0) {
      throw new IllegalArgumentException("Cannot have an even amount of playable cells.");
    }

  }


  @Override
  public int getNumCols() {
    return this.numCols;
  }

  @Override
  public int getNumRows() {
    return this.numRows;
  }

  @Override
  public Cell getCell(int row, int col) {
    if (row >= this.numRows || row < 0 || col >= this.numCols || col < 0) {
      throw new IllegalArgumentException("Row or column index out of bounds.");
    }
    return this.grid[row][col];
  }

  @Override
  public List<Cell> getAllPlayableCells() {
    return this.getAllCells().stream().filter(cell -> !cell.isHole()).collect(Collectors.toList());
  }

  /**
   * Returns a list of all cells in the grid.
   *
   * @return a list of all cells in the grid.
   */
  public List<Cell> getAllCells() {
    List<Cell> cells = new ArrayList<>();
    for (Cell[] row : grid) {
      cells.addAll(Arrays.asList(row));
    }
    return cells;
  }

  @Override
  public void placeCard(int row, int col, Card card) {
    if (row < 0 || row > this.numRows - 1) {
      throw new IllegalArgumentException("Rows index out of bounds.");
    }
    if (col < 0 || col > this.numCols - 1) {
      throw new IllegalArgumentException("Column index out of bounds.");
    }
    if (grid[row][col].isHole()) {
      throw new IllegalArgumentException("Cannot place card in a hole.");
    }
    if (grid[row][col].getCard() != null) {
      throw new IllegalArgumentException("Cell is already occupied.");
    }
    this.grid[row][col] = new GameCell(card);
  }

  @Override
  public Map<Direction, Cell> getAdjacentCells(Cell center) {
    if (center == null) {
      throw new IllegalArgumentException("Cell is null.");
    }
    if (center.isHole()) {
      throw new IllegalArgumentException("Cell is a hole.");
    }
    int[] indexes = getIndexOfCell(center);
    int row = indexes[0];
    int col = indexes[1];
    Map<Direction, Cell> adjacentCells = new HashMap<>();
    if (row > 0 && !grid[row - 1][col].isHole()) {
      adjacentCells.put(Direction.NORTH, this.grid[row - 1][col]);
    }
    if (row < this.numRows - 1 && !grid[row + 1][col].isHole()) {
      adjacentCells.put(Direction.SOUTH, this.grid[row + 1][col]);
    }
    if (col > 0 && !grid[row][col - 1].isHole()) {
      adjacentCells.put(Direction.WEST, this.grid[row][col - 1]);
    }
    if (col < this.numCols - 1 && !grid[row][col + 1].isHole()) {
      adjacentCells.put(Direction.EAST, this.grid[row][col + 1]);
    }
    return adjacentCells;
  }

  @Override
  public int[] getIndexOfCell(Cell cell) {
    if (cell == null) {
      throw new IllegalArgumentException("Cell is null");
    }

    for (int tempRow = 0; tempRow < grid.length; tempRow++) {
      for (int tempCol = 0; tempCol < grid[tempRow].length; tempCol++) {
        if (grid[tempRow][tempCol].equals(cell)) {
          return new int[]{tempRow, tempCol};
        }
      }
    }
    throw new IllegalArgumentException("Cell not in grid");

  }

  @Override
  public int getNumCardsOfColor(Color color) {
    if (color == null) {
      throw new IllegalArgumentException("Color is null");
    }
    List<Cell> allCells = this.getAllPlayableCells();
    List<Cell> filteredList = allCells.stream().filter(cell -> {

      if (cell.getCard() != null) {
        return cell.getCard().getColor() == color;
      }
      return false;
    }).collect(Collectors.toList());
    return filteredList.size();
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof GameGrid) {
      GameGrid gridOther = (GameGrid) other;
      return Arrays.deepEquals(gridOther.grid, this.grid);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(Arrays.deepHashCode(this.grid), this.numCols, this.numRows);
  }
}
