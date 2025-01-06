package cs3500.threetrios.players;

import java.util.List;

import cs3500.threetrios.controller.ViewFeatures;
import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Color;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.strategies.Move;
import cs3500.threetrios.strategies.ThreeTriosStrategy;

/**
 * This class represents a machine player, choosing cards and playing to cells with a specific
 * strategy.
 */
public class MachinePlayer implements Player {

  private final ThreeTriosStrategy strategy;
  private final ReadOnlyThreeTriosModel model;
  private final Color color;
  private ViewFeatures features;

  /**
   * Constructs a machine player with the given model, strategy, and color.
   *
   * @param model    the model to use
   * @param strategy the strategy to use
   * @param color    the color of the player
   * @throws IllegalArgumentException if model, strategy, or color is null;
   */
  public MachinePlayer(ReadOnlyThreeTriosModel model, ThreeTriosStrategy strategy, Color color) {
    if (model == null) {
      throw new IllegalArgumentException("Model is null");
    }
    if (strategy == null) {
      throw new IllegalArgumentException("Strategy is null");
    }
    if (color == null) {
      throw new IllegalArgumentException("Color is null");
    }
    this.model = model;
    this.strategy = strategy;
    this.color = color;
  }

  @Override
  public void makeMove() {
    Move bestMove = strategy.chooseMoves(model, color, null).get(0);
    List<Card> hand = model.getPlayerHand(color);
    int cardIndexInHand = hand.indexOf(bestMove.card);
    features.selectCard(cardIndexInHand, this.color);
    features.selectCell(bestMove.row, bestMove.col);
  }

  @Override
  public void addFeatures(ViewFeatures features) {
    this.features = features;
  }
}
