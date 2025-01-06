package cs3500.threetrios.players;

import cs3500.threetrios.controller.ViewFeatures;

/**
 * Represents a player action, being either a machine or a human.
 */
public interface Player {

  /**
   * Makes a move to the grid by selecting a card from the player's hand and choosing a cell on the
   * grid to play to.
   */
  void makeMove();

  /**
   * Adds features to the player.
   *
   * @param features the features.
   */
  void addFeatures(ViewFeatures features);

}
