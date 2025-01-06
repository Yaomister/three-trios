package cs3500.threetrios.model;

/**
 * Represents the attack value of a card.
 * The attack value is an integer between 1 and 10 (A), inclusive.
 */
public enum AttackValue {
  ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), A(10);

  private final int value;

  AttackValue(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  @Override
  public String toString() {
    return this == A ? "A" : Integer.toString(this.value);
  }

  /**
   * Returns the AttackValue enum corresponding to the given integer value.
   *
   * @param target the integer value of the AttackValue enum to retrieve
   * @return AttackValue enum corresponding to the given integer value, null if no enum exists
   */
  public static AttackValue enumOfValue(int target) {
    for (AttackValue attackValue : values()) {
      if (attackValue.value == target) {
        return attackValue;
      }
    }
    return null;
  }
}
