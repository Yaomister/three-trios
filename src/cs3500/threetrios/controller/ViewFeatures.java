package cs3500.threetrios.controller;

import cs3500.threetrios.model.Color;

/**
 * Represents the features of the controller, used to control the view of a game of Three Trios.
 */
public interface ViewFeatures {

  /**
   * Selects a card from the player's hand. The player cannot choose a card in the opponents hands.
   *
   * @param cardIndex the index of the card to select
   * @param cardColor the color of the card to select
   */
  void selectCard(int cardIndex, Color cardColor);

  /**
   * Selects a cell from the grid, and printing the selected coordinates to the console. There must
   * be a card already selected before they can play to a cell.
   *
   * @param row the row of the cell to select
   * @param col the column of the cell to select
   */
  void selectCell(int row, int col);


}
