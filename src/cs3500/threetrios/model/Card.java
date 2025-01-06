package cs3500.threetrios.model;

/**
 * This interface represents the behaviours of a card used in a game of Three Trios.
 */
public interface Card {

  /**
   * Gets the name of this card.
   *
   * @return this card's name
   */
  String getName();

  /**
   * Gets the attack value in the north direction.
   *
   * @return this card's attack value in the north direction
   */
  AttackValue getNorth();

  /**
   * Gets the attack value in the south direction.
   *
   * @return this card's attack value in the south direction
   */
  AttackValue getSouth();


  /**
   * Gets the attack value in the east direction.
   *
   * @return this card's attack value in the east direction
   */
  AttackValue getEast();

  /**
   * Gets the attack value in the west direction.
   *
   * @return this card's attack value in the west direction
   */
  AttackValue getWest();

  /**
   * Gets this card's color.
   *
   * @return this card's color.
   */
  Color getColor();

  /**
   * Sets this card's color to the color passed in.
   *
   * @throws IllegalArgumentException if color is null
   */
  void setColor(Color color);


  /**
   * Compares the attack value of this card to the attack value of another card in given direction.
   * The direction is relative to this card. So if NORTH was passed in, we are comparing the north
   * attack value of this card to the South attack value of the other card.
   *
   * @param otherCard the other card to compare to
   * @param direction the direction to compare in
   * @return the difference between the attack values of the two cards in the given direction
   * @throws IllegalArgumentException if otherCard or direction is null.
   */
  int compareAttackValue(Card otherCard, Direction direction);
}
