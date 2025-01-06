package cs3500.threetrios.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the game.
 * Each player has a name, a color, and a hand of cards.
 */
public class GameModelPlayer implements ModelPlayer {

  private final String name;
  private final Color color;
  private final List<Card> hand;

  /**
   * Constructs a player with the given name and color.
   *
   * @param name  the name of the player
   * @param color the color of the player
   */
  public GameModelPlayer(String name, Color color) {
    this.name = name;
    this.color = color;
    this.hand = new ArrayList<>();
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public Color getColor() {
    return this.color;
  }

  @Override
  public List<Card> getHand() {
    List<Card> copy = new ArrayList<>();
    for (Card oldCard : this.hand) {
      GameCard newCard = new GameCard(oldCard.getName(),
              oldCard.getNorth(), oldCard.getSouth(), oldCard.getEast(), oldCard.getWest());
      newCard.setColor(oldCard.getColor());
      copy.add(newCard);
    }
    return copy;
  }

  @Override
  public void addCardToHand(Card card) {
    if (card == null) {
      throw new IllegalArgumentException("Card cannot be null");
    }
    hand.add(card);
  }

  @Override
  public Card removeCardFromHand(int index) {
    if (index < 0 || index >= hand.size()) {
      throw new IllegalArgumentException("Invalid card index.");
    }
    return hand.remove(index);
  }


}
