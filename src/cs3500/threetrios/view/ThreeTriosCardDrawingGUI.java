package cs3500.threetrios.view;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

import cs3500.threetrios.model.Card;

/**
 * Represents the draw cards.
 */
public class ThreeTriosCardDrawingGUI extends ThreeTriosCardDrawing {

  /**
   * Draws a rectangle with the given width and height.
   *
   * @param width  the width of the rectangle
   * @param height the height of the rectangle
   */
  public ThreeTriosCardDrawingGUI(int width, int height) {
    super(width, height);
  }

  @Override
  public void drawCardNumbers(Graphics2D g2d, Card card,
                              int fontSize) {
    if (card == null){
      return;
    }
    g2d.setColor(Color.BLACK);
    FontMetrics fm = g2d.getFontMetrics();

    // North
    String northText = card.getNorth().toString();
    int northX = (this.getWidth() - fm.stringWidth(northText)) / 2;
    int northY = fm.getAscent();
    g2d.drawString(northText, northX, northY);

    // East
    String eastText = card.getEast().toString();
    int eastX = this.getWidth() - fm.stringWidth(eastText) - (this.getWidth() / 4);
    int eastY = (this.getHeight() + fm.getAscent()) / 2;
    g2d.drawString(eastText, eastX, eastY);

    // South
    String southText = card.getSouth().toString();
    int southX = (this.getWidth() - fm.stringWidth(southText)) / 2;
    int southY = this.getHeight() - fm.getDescent();
    g2d.drawString(southText, southX, southY);

    // West
    String westText = card.getWest().toString();
    int westX = (this.getWidth() / 4);
    int westY = (this.getHeight() + fm.getAscent()) / 2;
    g2d.drawString(westText, westX, westY);
  }

}
