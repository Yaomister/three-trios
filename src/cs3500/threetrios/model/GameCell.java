package cs3500.threetrios.model;

/**
 * Represents a card in the game.
 * A card has a name, a color, and four attack values.
 * The attack values are represented by AttackValue enum.
 */
public class GameCell implements Cell {
  private final Card card;
  private boolean isHole;

  /**
   * Constructs a cell with the given card.
   *
   * @param card the card in the cell
   */
  public GameCell(Card card) {
    this.card = card;
    this.isHole = false;
  }

  @Override
  public Card getCard() {
    return this.card;
  }

  @Override
  public boolean isHole() {
    return this.isHole;
  }

  @Override
  public boolean isEmpty() {
    return this.card == null;
  }

  @Override
  public void setHole(boolean isHole) {
    this.isHole = isHole;
  }

  @Override
  public String toString() {
    if (this.isHole) {
      return " ";
    } else if (this.card == null) {
      return "_";
    } else if (this.card.getColor() == Color.RED) {
      return "R";
    } else if (this.card.getColor() == Color.BLUE) {
      return "B";
    } else {
      return "_";
    }
  }


}