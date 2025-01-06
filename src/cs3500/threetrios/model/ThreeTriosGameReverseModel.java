package cs3500.threetrios.model;

/**
 * Represents a Three Trios game model with the cards flipped in reverse.
 */
public class ThreeTriosGameReverseModel extends ThreeTriosGameVariantModel {

  /**
   * Constructs a Three Trios game model with the cards flipped in reverse.
   *
   * @param nextModel the base model to use
   */
  public ThreeTriosGameReverseModel(ThreeTriosModel nextModel) {
    super(nextModel);
  }

  @Override
  public boolean flipCondition(Card card, Card opp, Direction dir, boolean prev) {

    boolean toFlip = !prev;
    if (card.compareAttackValue(opp, dir) == 0) {
      toFlip = false;
    }
    if (nextModel != null) {
      return nextModel.flipCondition(card, opp, dir, toFlip);
    }
    return toFlip;
  }
}
