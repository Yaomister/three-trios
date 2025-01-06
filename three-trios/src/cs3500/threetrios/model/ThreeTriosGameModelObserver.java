package cs3500.threetrios.model;

import java.util.List;

/**
 * Observer for the Three Trios Game Model.
 */
public class ThreeTriosGameModelObserver implements ReadOnlyThreeTriosModel {

  private final ReadOnlyThreeTriosModel delegate;

  /**
   * Constructs a Three Trios Game Model Observer.
   *
   * @param model the model to observe
   */
  public ThreeTriosGameModelObserver(ReadOnlyThreeTriosModel model) {
    this.delegate = model;
  }

  @Override
  public boolean isGameOver() {
    return this.delegate.isGameOver();
  }

  @Override
  public Color getWinner() {
    return this.delegate.getWinner();
  }

  @Override
  public Phase getPhase() {
    return this.delegate.getPhase();
  }

  @Override
  public Cell getCellAt(int row, int col) {
    return this.delegate.getCellAt(row, col);
  }

  @Override
  public int amountOfCardsFlippedByPlayingAt(Card card, int row, int col) {
    return this.delegate.amountOfCardsFlippedByPlayingAt(card, row, col);
  }

  @Override
  public List<Card> getPlayerHand(Color player) {
    return this.delegate.getPlayerHand(player);
  }

  @Override
  public int getGridWidth() {
    return this.delegate.getGridWidth();
  }

  @Override
  public int getGridHeight() {
    return this.delegate.getGridHeight();
  }

  @Override
  public Color getCurrentPlayerColor() {
    return this.delegate.getCurrentPlayerColor();
  }

  @Override
  public boolean isPlayLegal(int row, int col) {
    return this.delegate.isPlayLegal(row, col);
  }

  @Override
  public int getPlayerScore(Color playerColor) {
    return this.delegate.getPlayerScore(playerColor);
  }

  @Override
  public Cell[][] getGrid() {
    return this.delegate.getGrid();
  }
}
