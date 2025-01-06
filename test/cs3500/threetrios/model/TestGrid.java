package cs3500.threetrios.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * Test class for Grid.
 * These tests are meant to ensure that the grid is being built correctly,
 * as well as correctly handles exceptions and correct methods.
 */
public class TestGrid {

  private GameGrid gridTwoByTwo;
  private GameGrid gridThreeByThree;
  private GameCard redCard;
  private GameCard blueCard;

  @Before
  public void setUp() {

    boolean[][] gridTwoByTwoHoles = {
            {true, false},
            {false, false}};

    gridTwoByTwo = new GameGrid(gridTwoByTwoHoles);

    boolean[][] gridThreeByThreeHoles = {
            {false, false, false},
            {false, false, false},
            {false, false, false}};
    gridThreeByThree = new GameGrid(gridThreeByThreeHoles);

    redCard = new GameCard("Red Card", AttackValue.ONE, AttackValue.A,
            AttackValue.TWO, AttackValue.FOUR);
    redCard.setColor(Color.RED);

    blueCard = new GameCard("Blue Card", AttackValue.TWO, AttackValue.ONE,
            AttackValue.FIVE, AttackValue.EIGHT);
    blueCard.setColor(Color.BLUE);
  }

  @Test
  public void testGetNumRows() {
    Assert.assertEquals(2, gridTwoByTwo.getNumRows());
  }

  @Test
  public void testGetNumCols() {
    Assert.assertEquals(2, gridTwoByTwo.getNumCols());
  }

  @Test
  public void testGetCell() {
    Assert.assertNotNull(gridTwoByTwo.getCell(1, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceCardOnHole() {
    gridTwoByTwo.placeCard(0, 0, redCard);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceCardOnOccupiedCell() {
    gridTwoByTwo.placeCard(0, 0, redCard);
    gridTwoByTwo.placeCard(0, 0, blueCard);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceOutOfBounds() {
    gridTwoByTwo.placeCard(3, 3, redCard);
  }

  @Test
  public void testGetAllCells() {
    Assert.assertEquals(4, gridTwoByTwo.getAllCells().size());
  }

  @Test
  public void testGetAllPlayableCells() {
    Assert.assertEquals(3, gridTwoByTwo.getAllPlayableCells().size());
  }

  @Test
  public void testPlaceCardOnEmptyCell() {
    gridTwoByTwo.placeCard(1, 1, redCard);
    Assert.assertEquals(redCard, gridTwoByTwo.getCell(1, 1).getCard());
  }

  @Test
  public void testGetAdjacentCells() {
    gridTwoByTwo.placeCard(1, 1, redCard);
    gridTwoByTwo.placeCard(0, 1, blueCard);

    Map<Direction, Cell> adjacentCells = gridTwoByTwo.getAdjacentCells(gridTwoByTwo.getCell(1, 1));

    // Get adjacent cell with no card
    Assert.assertTrue(adjacentCells.containsKey(Direction.WEST));

    // Get adjacent cell with no card
    Assert.assertTrue(adjacentCells.containsKey(Direction.NORTH));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetAdjacentCellsInvalidCenter() {
    gridTwoByTwo.getAdjacentCells(new GameCell(null));
  }

  @Test
  public void testGetNumCardsOfColor() {
    gridTwoByTwo.placeCard(1, 0, redCard);
    gridTwoByTwo.placeCard(1, 1, blueCard);

    Assert.assertEquals(1, gridTwoByTwo.getNumCardsOfColor(Color.RED));
    Assert.assertEquals(1, gridTwoByTwo.getNumCardsOfColor(Color.BLUE));
  }


  @Test
  public void testGridBuilderCreatesGridCorrectly() {
    boolean[][] gridHoles = {
            {true, false},
            {false, false}};
    GameGrid smallGrid = new GameGrid(gridHoles);

    Assert.assertTrue(smallGrid.getCell(0, 0).isHole());
    Assert.assertFalse(smallGrid.getCell(1, 1).isHole());
  }

  @Test
  public void testAdjacentCellsWithSouthCell() {
    boolean[][] gridHoles = {
            {false, false, false},
            {false, false, false},
            {false, false, false}};
    GameGrid grid = new GameGrid(gridHoles);

    grid.placeCard(1, 1, redCard);
    grid.placeCard(2, 1, blueCard);

    Cell center = grid.getCell(1, 1);

    Map<Direction, Cell> adjacentCells = grid.getAdjacentCells(center);

    Assert.assertTrue(adjacentCells.containsKey(Direction.SOUTH));
    Assert.assertEquals("Blue Card", adjacentCells.get(Direction.SOUTH).getCard().getName());
  }

  @Test
  public void testAdjacentCellsWithEastCell() {

    gridThreeByThree.placeCard(1, 1, redCard);
    gridThreeByThree.placeCard(1, 2, blueCard);

    Cell center = gridThreeByThree.getCell(1, 1);


    Map<Direction, Cell> adjacentCells = gridThreeByThree.getAdjacentCells(center);

    Assert.assertTrue(adjacentCells.containsKey(Direction.EAST));
    Assert.assertEquals("Blue Card", adjacentCells.get(Direction.EAST).getCard().getName());
  }
}

