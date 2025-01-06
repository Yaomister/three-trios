package cs3500.threetrios.controller;

import cs3500.threetrios.model.Color;

/**
 * Represents the features of the controller to control the model of a game of Three Trios.
 */
public interface ModelFeatures {

  /**
   * Plays the next turn. This method is called when the player's turn is over.
   *
   * @param playerColor the color of the player whose turn it is
   */
  void playTurn(Color playerColor);

  /**
   * Prompts the features listener to update the corresponding view(s) with the most up to date
   * game status represented in the model.
   */
  void update();
}
