package cs3500.threetrios.view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import cs3500.threetrios.controller.ViewFeatures;
import cs3500.threetrios.model.Color;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

/**
 * Represents the frame implementation of the Three Trios game view.
 * This class extends JFrame and implements the ThreeTriosFrame interface.
 */
public class ThreeTriosFrameGUI extends JFrame implements ThreeTriosFrame {

  private final ThreeTriosHandPanelGUI redPlayerHand;
  private final ThreeTriosHandPanelGUI bluePlayerHand;
  private final ThreeTriosGridPanelGUI grid;

  private final ReadOnlyThreeTriosModel model;


  /**
   * Constructs a ThreeTriosFrameGUI. Builds the design layout for the gui to be created.
   *
   * @param model the model of the game
   */
  public ThreeTriosFrameGUI(ReadOnlyThreeTriosModel model) {
    super("Three Trios");
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());


    this.model = model;

    // Create the panels
    this.grid = new ThreeTriosGridPanelGUI(model);
    this.redPlayerHand = new ThreeTriosHandPanelGUI(model, cs3500.threetrios.model.Color.RED);
    this.bluePlayerHand = new ThreeTriosHandPanelGUI(model, cs3500.threetrios.model.Color.BLUE);

    this.add(grid, BorderLayout.CENTER);
    this.add(redPlayerHand, BorderLayout.WEST);
    this.add(bluePlayerHand, BorderLayout.EAST);

    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        redPlayerHand.revalidate();
        bluePlayerHand.revalidate();
        repaint();
      }
    });

  }

  /**
   * Make the frame and all of its components use the features passed in.
   *
   * @param features the features used by the frame and it's components.
   */
  public void addFeatures(ViewFeatures features) {
    this.grid.addFeatures(features);
    this.redPlayerHand.addFeatures(features);
    this.bluePlayerHand.addFeatures(features);
  }

  @Override
  public void showMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Message", JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void refreshGameState() {
    this.repaint();
  }

  @Override
  public void showTitle(String title) {
    this.setTitle(title);
  }

  // Highlights the card of the specified player.
  @Override
  public void highlightCard(int cardIndex, Color playerColor) {
    if (playerColor == cs3500.threetrios.model.Color.RED) {
      redPlayerHand.highlightCard(cardIndex);
    } else {
      bluePlayerHand.highlightCard(cardIndex);
    }
    this.grid.updateSelectedCard(this.model.getPlayerHand(playerColor).get(cardIndex));
    this.grid.repaint();
  }

  // Deselects the card of the specified player.
  @Override
  public void deselectCard() {
    redPlayerHand.deselectCard();
    bluePlayerHand.deselectCard();
    this.grid.updateSelectedCard(null);
    this.grid.repaint();

  }
}