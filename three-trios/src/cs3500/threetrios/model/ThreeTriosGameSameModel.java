package cs3500.threetrios.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a ThreeTrios game with same features.
 */
public class ThreeTriosGameSameModel extends ThreeTriosGameModel {

  /**
   * Constructs a ThreeTriosGameSameModel.
   *
   * @param grid      the grid of the game
   * @param deck      the deck of cards
   * @param nextModel the next model
   */
  public ThreeTriosGameSameModel(boolean[][] grid, List<Card> deck, ThreeTriosModel nextModel) {
    super(grid, deck, nextModel);
  }

  @Override
  protected List<Card> getNonChainedCardsFlippedByPlayingAt(Card card, Cell center) {
    List<Card> toFlip = new ArrayList<>();
    Map<Direction, Cell> adjacentCells = this.grid.getAdjacentCells(center);
    for (Direction dir : adjacentCells.keySet()) {
      Cell cellInDirection = adjacentCells.get(dir);
      Card cardInDirection = cellInDirection.getCard();
      if (!cellInDirection.isEmpty()) {
        if (card.compareAttackValue(cardInDirection, dir) == 0) {
          toFlip.add(cardInDirection);
        }
      }
    }

    if (toFlip.size() >= 2) {
      // return only the ones that are opposite colors
      return toFlip.stream().filter(filterCard -> filterCard.getColor()
              != this.getCurrentPlayerColor()).collect(Collectors.toList());
    }
    return List.of();
  }
}
