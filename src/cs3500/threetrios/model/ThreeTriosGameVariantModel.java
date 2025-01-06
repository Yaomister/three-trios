package cs3500.threetrios.model;

import java.util.List;

import cs3500.threetrios.controller.ModelFeatures;

/**
 * Represents a Three Trios game variant model.
 */
public abstract class ThreeTriosGameVariantModel implements ThreeTriosModel{
  protected final ThreeTriosModel nextModel;

  /**
   * Constructs a Three Trios game variant model.
   *
   * @param nextModel the base model to use
   */
  public ThreeTriosGameVariantModel(ThreeTriosModel nextModel) {
    this.nextModel = nextModel;;
  }

  @Override
  public boolean isGameOver() {
    return this.nextModel.isGameOver();
  }

  @Override
  public Color getWinner() {
    return this.nextModel.getWinner();
  }

  @Override
  public Phase getPhase() {
    return this.nextModel.getPhase();
  }

  @Override
  public Cell getCellAt(int row, int col) {
    return this.nextModel.getCellAt(row, col);
  }

  @Override
  public int amountOfCardsFlippedByPlayingAt(Card card, int row, int col) {
    return this.nextModel.amountOfCardsFlippedByPlayingAt(card, row, col);
  }

  @Override
  public List<Card> getPlayerHand(Color player) {
    return this.nextModel.getPlayerHand(player);
  }

  @Override
  public int getGridWidth() {
    return this.nextModel.getGridWidth();
  }

  @Override
  public int getGridHeight() {
    return this.nextModel.getGridHeight();
  }

  @Override
  public Color getCurrentPlayerColor() {
    return this.nextModel.getCurrentPlayerColor();
  }

  @Override
  public boolean isPlayLegal(int row, int col) {
    return this.nextModel.isPlayLegal(row, col);
  }

  @Override
  public int getPlayerScore(Color playerColor) {
    return this.nextModel.getPlayerScore(playerColor);
  }

  @Override
  public Cell[][] getGrid() {
    return this.nextModel.getGrid();
  }

  @Override
  public void playCard(int cellIndexRow, int cellIndexCol, int cardIndexInHand) {
    this.nextModel.playCard(cellIndexRow, cellIndexCol, cardIndexInHand);
  }

  @Override
  public void startGame() {
    this.nextModel.startGame();
  }

  @Override
  public void nextTurn() {
    this.nextModel.nextTurn();
  }

  @Override
  public void battle() {
    this.nextModel.battle();
  }

  @Override
  public void addFeatures(ModelFeatures features) {
    this.nextModel.addFeatures(features);
  }

}