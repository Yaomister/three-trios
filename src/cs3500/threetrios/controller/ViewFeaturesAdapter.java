package cs3500.threetrios.controller;

import cs3500.threetrios.model.Color;

/**
 * This class is an adapter for the ViewFeatures interface.
 */
public class ViewFeaturesAdapter implements ViewFeatures {

  private final ViewFeatures delegate;

  /**
   * Constructs a ViewFeaturesAdapter with the given adaptee.
   *
   * @param features the features to be adapted
   */
  public ViewFeaturesAdapter(ViewFeatures features) {
    this.delegate = features;
  }

  // Selects a card
  @Override
  public void selectCard(int cardIndex, Color cardColor) {
    this.delegate.selectCard(cardIndex, cardColor);

  }

  // Selects a cell
  @Override
  public void selectCell(int row, int col) {
    this.delegate.selectCell(row, col);
  }
}
