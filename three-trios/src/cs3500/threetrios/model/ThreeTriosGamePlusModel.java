package cs3500.threetrios.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a ThreeTrios game with plus features.
 */
public class ThreeTriosGamePlusModel extends ThreeTriosGameModel {

  /**
   * Constructs a ThreeTriosGamePlusModel.
   *
   * @param grid      the grid of the game
   * @param deck      the deck of cards
   * @param nextModel the next model
   */
  public ThreeTriosGamePlusModel(boolean[][] grid, List<Card> deck, ThreeTriosModel nextModel) {
    super(grid, deck, nextModel);
  }

  @Override
  protected List<Card> getNonChainedCardsFlippedByPlayingAt(Card card, Cell center) {
    Map<Integer, List<Card>> sumCardMap = new HashMap<>();
    Map<Direction, Cell> adjacentCells = this.grid.getAdjacentCells(center);
    for (Direction dir : adjacentCells.keySet()) {
      Cell cellInDirection = adjacentCells.get(dir);
      Card cardInDirection = cellInDirection.getCard();
      if (!cellInDirection.isEmpty()) {
        int sum = 0;
        switch (dir) {
          case NORTH:
            sum = card.getNorth().getValue() + cardInDirection.getSouth().getValue();
            break;
          case SOUTH:
            sum = card.getSouth().getValue() + cardInDirection.getNorth().getValue();
            break;
          case EAST:
            sum = card.getEast().getValue() + cardInDirection.getWest().getValue();
            break;
          case WEST:
            sum = card.getWest().getValue() + cardInDirection.getEast().getValue();
            break;
          default:
            // no default case
        }

        List<Card> sumCards = sumCardMap.getOrDefault(sum, new ArrayList<>());
        sumCards.add(cardInDirection);
        sumCardMap.put(sum, sumCards);
      }
    }

    List<Card> plusList = new ArrayList<>();
    for (List<Card> sameSumCards : sumCardMap.values()) {
      if (sameSumCards.size() >= 2) {
        plusList.addAll(sameSumCards.stream().filter(filterCard -> filterCard.getColor()
                != this.getCurrentPlayerColor()).collect(Collectors.toList()));
      }
    }
    return plusList;
  }
}
