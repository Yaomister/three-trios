package cs3500.threetrios.view;

import cs3500.threetrios.controller.ViewFeatures;
import cs3500.threetrios.model.Color;

/**
 * Interface for the view of the game.
 */
public interface ThreeTriosFrame {
  /**
   * Highlights a card.
   *
   * @param cardIndex the index of the card
   * @param player    the player
   */
  void highlightCard(int cardIndex, Color player);

  /**
   * Unselects a card.
   */
  void deselectCard();

  /**
   * Adds features to the view.
   *
   * @param features the features.
   */
  void addFeatures(ViewFeatures features);

  /**
   * Shows a message.
   *
   * @param message the message.
   */
  void showMessage(String message);

  /**
   * Updates the view.
   */
  void refreshGameState();

  void showTitle(String s);
}