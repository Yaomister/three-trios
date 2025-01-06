package cs3500.threetrios.model;

import java.util.List;


/**
 * This interface represents the behaviours of a player that exists in a game of Three Trios.
 */
public interface ModelPlayer {

  /**
   * Gets the name of the player.
   *
   * @return the name of the player
   */
  String getName();

  /**
   * Gets this card's color.
   *
   * @return this card's color
   */
  Color getColor();

  /**
   * Returns a copy of the hand list. Mutating the card in the list returned will have no effect
   * on the list stored in this object.
   *
   * @return copy of hand.
   */
  List<Card> getHand();

  /**
   * Adds a card to the player's hand.
   *
   * @param card the card to add
   * @throws IllegalArgumentException if the card passed in is null;
   */
  void addCardToHand(Card card);

  /**
   * Removes a card from the player's hand.
   *
   * @param index the index of the card to remove
   * @return the removed card
   * @throws IllegalArgumentException if the index of the card in hand is out of bounds.
   */
  Card removeCardFromHand(int index);
}
