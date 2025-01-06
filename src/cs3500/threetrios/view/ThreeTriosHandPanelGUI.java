package cs3500.threetrios.view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import cs3500.threetrios.controller.ViewFeatures;
import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

/**
 * Represents the hand panel of the game. This class extends JPanel.
 */
public class ThreeTriosHandPanelGUI extends JPanel {

  private final ReadOnlyThreeTriosModel model;
  private final cs3500.threetrios.model.Color playerColor;

  private int selectedCardIndex = -1;

  private ViewFeatures featuresListener;
  private final List<JPanel> cardPanels = new ArrayList<>();

  /**
   * Constructs a ThreeTriosHandPanelGUI.
   *
   * @param model the model of the game
   */
  public ThreeTriosHandPanelGUI(ReadOnlyThreeTriosModel model,
                                cs3500.threetrios.model.Color playerColor) {
    this.model = model;
    this.playerColor = playerColor;
    setLayout(new GridLayout(model.getPlayerHand(playerColor).size(), 1));

    for (int i = 0; i < model.getPlayerHand(playerColor).size(); i++) {
      JPanel cardPanel = createCardPanel(i);
      cardPanels.add(cardPanel);
      add(cardPanel);
    }

    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int handSize = model.getPlayerHand(playerColor).size();
        int cardHeight = getHeight() / handSize;
        int cardIndex = e.getY() / cardHeight;
        featuresListener.selectCard(cardIndex, playerColor);
      }
    });
  }

  // Get the preferred size of the panel.
  @Override
  public Dimension getPreferredSize() {
    int parentWidth = getParent() != null ? getParent().getWidth() : 0;
    int preferredWidth = (int) (parentWidth * 0.2);
    int preferredHeight = super.getPreferredSize().height;
    return new Dimension(preferredWidth, preferredHeight);
  }

  // Method to paint the component with the graphics and g2d class.
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    int handSize = model.getPlayerHand(playerColor).size();
    int cardWidth = getWidth() - 20;
    int cardHeight = (handSize > 0) ? getHeight() / handSize : 0;

    int fontSize = Math.max(10, cardHeight / 4);
    g2d.setFont(new Font("Arial", Font.PLAIN, fontSize));

    List<Card> playerHand = model.getPlayerHand(playerColor);
    for (int i = 0; i < handSize; i++) {
      final Card card = playerHand.get(i);
      int positionY = i * cardHeight;

      ThreeTriosCardDrawingGUI cardShape = new ThreeTriosCardDrawingGUI(cardWidth, cardHeight);

      g2d.translate(10, positionY);

      g2d.setColor(playerColor == cs3500.threetrios.model.Color.RED
              ? new Color(255, 171, 173) : new Color(72, 172, 255));
      g2d.fill(cardShape);

      g2d.setColor(Color.BLACK);
      g2d.draw(cardShape);

      cardShape.drawCardNumbers(g2d, card, fontSize);
      g2d.translate(-10, -positionY);
      cardPanels.get(i).setBounds(10, positionY, cardWidth, cardHeight);
    }
  }

  private JPanel createCardPanel(int index) {
    JPanel cardPanel = new JPanel();
    cardPanel.setOpaque(false);
    cardPanel.setBorder(BorderFactory.createEmptyBorder());
    return cardPanel;
  }

  /**
   * Highlights the card at the specified index.
   *
   * @param cardIndex the index of the card to highlight
   */
  public void highlightCard(int cardIndex) {
    if (selectedCardIndex >= 0 && selectedCardIndex < cardPanels.size()) {
      cardPanels.get(selectedCardIndex).setBorder(BorderFactory.createEmptyBorder());
    }

    Border darkGrayBorder = new LineBorder(Color.DARK_GRAY, 3);
    cardPanels.get(cardIndex).setBorder(darkGrayBorder);
    selectedCardIndex = cardIndex;
    repaint();
  }

  /**
   * Deselects the currently selected card.
   */
  public void deselectCard() {
    if (selectedCardIndex >= 0 && selectedCardIndex < cardPanels.size()) {
      cardPanels.get(selectedCardIndex).setBorder(BorderFactory.createEmptyBorder());
      selectedCardIndex = -1;
    }
    repaint();
  }

  // Package private, only the frame needs access.
  // This method is to add the features.
  void addFeatures(ViewFeatures features) {
    this.featuresListener = features;
  }
}