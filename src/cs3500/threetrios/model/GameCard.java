package cs3500.threetrios.model;

import java.util.Objects;

/**
 * Represents a card in the game.
 * A card has a name, a color, and four attack values.
 * The attack values are represented by AttackValue enum.
 */
public class GameCard implements Card {

  private final String name;
  private Color color;
  // The directional attack values.
  private final AttackValue north;
  private final AttackValue south;
  private final AttackValue east;
  private final AttackValue west;

  /**
   * Constructs a card with the given name, north, south, east, and west attack values.
   *
   * @param name  the name of the card
   * @param north the north attack value
   * @param south the south attack value
   * @param east  the east attack value
   * @param west  the west attack value
   * @throws IllegalArgumentException if the name or any of the attack values are null
   */
  public GameCard(String name, AttackValue north,
                  AttackValue south, AttackValue east, AttackValue west) {
    if (name == null || north == null || south == null || east == null || west == null) {
      throw new IllegalArgumentException("Name and attack values cannot be null");
    }
    this.name = name;
    this.north = north;
    this.south = south;
    this.east = east;
    this.west = west;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public AttackValue getNorth() {
    return this.north;
  }

  @Override
  public AttackValue getSouth() {
    return this.south;
  }

  @Override
  public AttackValue getEast() {
    return this.east;
  }

  @Override
  public AttackValue getWest() {
    return this.west;
  }

  @Override
  public Color getColor() {
    return this.color;
  }

  @Override
  public void setColor(Color color) {
    if (color == null) {
      throw new IllegalArgumentException("Color is null");
    }
    this.color = color;
  }


  @Override
  public int compareAttackValue(Card otherCard, Direction direction) {
    if (otherCard == null) {
      throw new IllegalArgumentException("Other card is null");
    }
    if (direction == null) {
      throw new IllegalArgumentException("Direction is null");
    }
    int damage = 0;
    switch (direction) {
      case NORTH:
        damage = this.north.getValue() - otherCard.getSouth().getValue();
        break;
      case SOUTH:
        damage = this.south.getValue() - otherCard.getNorth().getValue();
        break;
      case EAST:
        damage = this.east.getValue() - otherCard.getWest().getValue();
        break;
      case WEST:
        damage = this.west.getValue() - otherCard.getEast().getValue();
        break;
      default:
        // No other cases.
    }
    return damage;
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof GameCard) {
      GameCard cardOther = (GameCard) other;
      return cardOther.name.equals(this.name) && cardOther.north == this.north && cardOther.south
              == this.south && cardOther.west == this.west && cardOther.east == this.east;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.name, this.north, this.south, this.east, this.west);
  }
}
