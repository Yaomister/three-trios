package cs3500.threetrios.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import cs3500.threetrios.controller.ViewFeatures;
import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Cell;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

/**
 * Represents the grid panel of the game.
 */
public class ThreeTriosGridPanelGUI extends JPanel {

  private final ReadOnlyThreeTriosModel model;
  private ViewFeatures featuresListener;
  private boolean hints = false;

  private Card selectedCard;

  /**
   * Constructs a ThreeTriosGridPanelGUI.
   *
   * @param model the model of the game
   */
  public ThreeTriosGridPanelGUI(ReadOnlyThreeTriosModel model) {
    this.model = model;

    JButton toggleHints = new JButton("Hints : OFF");
    toggleHints.setBounds(0, 0, 60, 30);
    toggleHints.setVisible(true);


    this.add(toggleHints);
    toggleHints.addActionListener((e) -> {
      this.hints = !this.hints;
      toggleHints.setText("Hints : " + (hints ? "ON" : "OFF"));
      repaint();
    });

    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int cellWidth = getWidth() / model.getGridWidth();
        int cellHeight = getHeight() / model.getGridHeight();

        int gridRow = e.getY() / cellHeight;
        int gridCol = e.getX() / cellWidth;

        featuresListener.selectCell(gridRow, gridCol);
      }
    });
  }

  // Method to paint the component with the graphics and g2d class.
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    int cellWidth = getWidth() / model.getGridWidth();
    int cellHeight = getHeight() / model.getGridHeight();

    for (int x = 0; x < model.getGridWidth(); x++) {
      for (int y = 0; y < model.getGridHeight(); y++) {
        Cell cell = model.getCellAt(y, x);

        int fontSize = Math.max(10, cellHeight / 6);
        g2d.setFont(new Font("Arial", Font.PLAIN, fontSize));

        if (!cell.isHole()) {
          g.setColor(new Color(212, 200, 0));
        } else {
          g.setColor(new Color(192, 192, 192));
        }
        if (!cell.isEmpty()) {
          g2d.setColor(cell.getCard().getColor() == cs3500.threetrios.model.Color.RED
                  ? new Color(255, 171, 173) : new Color(72, 172, 255));
        }

        int positionY = y * cellHeight;
        int positionX = x * cellWidth;

        boolean hintMode = this.hints && model.isPlayLegal(y, x) && selectedCard != null;

        ThreeTriosCardDrawing cardShape = hintMode ? new ThreeTriosCardDrawingWithHintsGUI(cellWidth, cellHeight,
                "" + model.amountOfCardsFlippedByPlayingAt(selectedCard, y, x),
                new ThreeTriosCardDrawingGUI(cellWidth, cellHeight))
                : new ThreeTriosCardDrawingGUI(cellWidth, cellHeight);

        g2d.translate(positionX, positionY);
        g2d.fill(cardShape);
        g2d.setColor(Color.BLACK);
        g2d.draw(cardShape);

        cardShape.drawCardNumbers(g2d, cell.getCard(), fontSize);
        g2d.translate(-positionX, -positionY);
      }
    }
  }

  // Package private, only the frame needs access.
  // This method is to add the features.
  void addFeatures(ViewFeatures featuresListener) {
    this.featuresListener = featuresListener;
  }

  // This method is to update the selected card.
  void updateSelectedCard(Card card) {
    this.selectedCard = card;
  }


}
