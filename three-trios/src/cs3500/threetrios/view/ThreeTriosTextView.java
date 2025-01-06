package cs3500.threetrios.view;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.ThreeTriosModel;

/**
 * Represents the text view of the game.
 * Has a constructor and a render method that renders the game
 * state correctly as it should be shown.
 */
public class ThreeTriosTextView {
  private final ThreeTriosModel model;

  /**
   * Constructs a ThreeTriosTextView.
   * Takes in a model of the game and uses it to render the game state.
   *
   * @param model the model of the game
   */
  public ThreeTriosTextView(ThreeTriosModel model) {
    this.model = model;
  }

  /**
   * Returns a string representation of the game.
   * The string representation includes the current player's name,
   * the grid, and the current player's hand.
   * Allows user to see a visual representation of the game built
   * with a StringBuilder.
   *
   * @return the string representation of the game
   */
  public String render() {
    StringBuilder sb = new StringBuilder();

    sb.append("Player: ").append(model.getCurrentPlayerColor().toString()).append("\n");


    for (int row = 0; row < model.getGridHeight(); row++) {
      StringBuilder trimRemover = new StringBuilder();
      for (int col = 0; col < model.getGridWidth(); col++) {
        trimRemover.append(model.getCellAt(row, col)).append(" ");
      }
      sb.append(trimRemover.toString().stripTrailing()).append("\n");
    }

    sb.append("Hand:\n");
    for (Card card : model.getPlayerHand(model.getCurrentPlayerColor())) {
      sb.append(card.getName()).append(" ").append(card.getNorth()).append(" ")
              .append(card.getSouth()).append(" ").append(card.getEast())
              .append(" ").append(card.getWest()).append("\n");
    }
    return sb.toString();
  }
}