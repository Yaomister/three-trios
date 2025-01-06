package cs3500.threetrios.controller;

import cs3500.threetrios.model.Color;

/**
 * A class that adapts the ModelFeatures interface to the ModelFeatures interface.
 */
public class ModelFeaturesAdapter implements ModelFeatures {

  private final ModelFeatures delegate;

  /**
   * Constructs a ModelFeaturesAdapter with the given adaptee.
   *
   * @param features the features to adapt
   */
  public ModelFeaturesAdapter(ModelFeatures features) {
    this.delegate = features;
  }

  /**
   * Plays the next turn. This method is called when the player's turn is over.
   *
   * @param playerColor the color of the player whose turn it is
   */
  @Override
  public void playTurn(Color playerColor) {
    this.delegate.playTurn(playerColor);
  }

  /**
   * Prompts the features listener to update the corresponding view(s) with the most up to date
   * game status represented in the model.
   */
  @Override
  public void update() {
    this.delegate.update();
  }
}
