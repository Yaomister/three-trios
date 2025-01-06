package cs3500.threetrios.controller;

import java.util.List;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Color;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.players.Player;
import cs3500.threetrios.view.ThreeTriosFrame;

/**
 * This class represents the controller of the Three Trios game, interacting with the model to play
 * moves, and the view to display graphics.
 */
public class ThreeTriosGameController implements ViewFeatures, ModelFeatures {

  private final ThreeTriosModel model;
  private final ThreeTriosFrame view;
  private final Color color;
  private final Player player;
  private int currentSelectedCardIndex = -1;

  /**
   * Constructs a ThreeTriosGameController with the given model, view, and player.
   *
   * @param model  the model to play on
   * @param player the player to play
   * @param view   the view
   * @param color  the color of the player
   */
  public ThreeTriosGameController(ThreeTriosModel model, Player player,
                                  ThreeTriosFrame view, Color color) {
    if (color == null) {
      throw new IllegalArgumentException("Color can't be null");
    }
    if (model == null) {
      throw new IllegalArgumentException("Model can't be null");
    }
    if (view == null) {
      throw new IllegalArgumentException("View can't be null");
    }
    if (player == null) {
      throw new IllegalArgumentException("Player can't be null");
    }
    this.model = model;
    this.view = view;
    this.color = color;
    this.player = player;
    this.model.addFeatures(new ModelFeaturesAdapter(this));
    this.view.addFeatures(new ViewFeaturesAdapter(this));
    this.player.addFeatures(new ViewFeaturesAdapter(this));
  }

  // Selects a card from the player's hand.
  @Override
  public void selectCard(int cardIndex, Color cardColor) {
    if (cardColor == null) {
      this.view.showMessage("Unrecognizable card color!");
      return;
    }
    if (this.model.getCurrentPlayerColor() != this.color) {
      this.view.showMessage("Player " + color + ": Please wait until your turn");
      return;
    }
    if (this.color != cardColor) {
      this.view.showMessage("Player " + color + ": Cannot select opponents cards");
      return;
    }
    List<Card> hand = this.model.getPlayerHand(this.color);
    if (cardIndex == currentSelectedCardIndex) {
      view.deselectCard();
      this.currentSelectedCardIndex = -1;
    } else {
      if (cardIndex >= 0 && cardIndex < hand.size()) {
        view.deselectCard();
        view.highlightCard(cardIndex, this.color);
        this.currentSelectedCardIndex = cardIndex;
      }
    }
  }

  // Selects a cell on the grid.
  @Override
  public void selectCell(int row, int col) {
    if (this.model.getCurrentPlayerColor() != this.color) {
      this.view.showMessage("Player " + color + ": Please wait until your turn");
      return;
    }
    if (this.currentSelectedCardIndex >= 0) {
      try {
        this.model.playCard(row, col, currentSelectedCardIndex);
        this.view.deselectCard();
        this.currentSelectedCardIndex = -1;
        model.battle();
        if (!model.isGameOver()) {
          model.nextTurn();
        }
      } catch (IllegalArgumentException err) {
        this.view.showMessage("Player " + color + ": " + err.getMessage());
      }
    } else {
      this.view.showMessage("Player " + color + ": Please select a card first");
    }
  }

  // Assigns player turn move.
  @Override
  public void playTurn(Color playerColor) {
    if (playerColor == null) {
      this.view.showMessage("Unable to recognize player");
      return;
    }
    if (playerColor == this.color) {
      this.view.showTitle("Player " + color + " [Currently Playing]");
      player.makeMove();
    } else {
      this.view.showTitle("Player " + color + " [Waiting To Play]");
    }
  }

  // Updates the model.
  @Override
  public void update() {
    this.view.refreshGameState();
    if (model.isGameOver()) {
      if (model.getWinner() != null) {
        String winningMessage = "Player " + model.getWinner() + " won! The winning score is "
                + model.getPlayerScore(model.getWinner());
        this.view.showMessage(winningMessage);
      } else {
        this.view.showMessage("There is a tie!");
      }
    }
  }


}
