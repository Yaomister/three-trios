package cs3500.threetrios.model;

/**
 * Represents a Three Trios game model with fallen aces.
 */
public class ThreeTriosGameFallenAceModel extends ThreeTriosGameVariantModel {

  /**
   * Constructs a Three Trios game model with fallen aces.
   *
   * @param baseModel the base model to use
   */
  public ThreeTriosGameFallenAceModel(ThreeTriosModel baseModel) {
    super(baseModel);
  }

  @Override
  public boolean flipCondition(Card card, Card opp, Direction dir, boolean prev) {
    AttackValue cardAttackValue = null;
    AttackValue oppAttackValue = null;
    switch (dir) {
      case NORTH:
        cardAttackValue = card.getNorth();
        oppAttackValue = opp.getSouth();
        break;
      case SOUTH:
        cardAttackValue = card.getSouth();
        oppAttackValue = opp.getNorth();
        break;
      case EAST:
        cardAttackValue = card.getEast();
        oppAttackValue = opp.getWest();
        break;
      case WEST:
        cardAttackValue = card.getWest();
        oppAttackValue = opp.getEast();
      default:
        // there is no other case
    }

    boolean toFlip;
    if (oppAttackValue == AttackValue.A && cardAttackValue == AttackValue.ONE) {
      toFlip = true;
    } else if (oppAttackValue == AttackValue.ONE && cardAttackValue == AttackValue.A){
      toFlip = false;
    } else {
      toFlip = prev;
    }
    if (this.nextModel != null) {
      return this.nextModel.flipCondition(card, opp, dir, toFlip);
    }
    return toFlip;
  }


}
