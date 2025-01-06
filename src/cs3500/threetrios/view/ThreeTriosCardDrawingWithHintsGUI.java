package cs3500.threetrios.view;

import java.awt.FontMetrics;
import java.awt.Graphics2D;

import cs3500.threetrios.model.Card;

/**
 * Represents the card drawing with hints.
 */
public class ThreeTriosCardDrawingWithHintsGUI extends ThreeTriosCardDrawing {


  private final String hint;

  private final ThreeTriosCardDrawing background;


  /**
   * Constructs a ThreeTriosCardDrawingWithHintsGUI.
   *
   * @param width  width
   * @param height height
   * @param hint   hint
   */
  public ThreeTriosCardDrawingWithHintsGUI(int width, int height, String hint,
                                           ThreeTriosCardDrawing background) {
    super(width, height);
    this.hint = hint;
    this.background = background;
  }


  @Override
  public void drawCardNumbers(Graphics2D g2d, Card card, int fontSize) {
    this.background.drawCardNumbers(g2d, card, fontSize);
    String hintText = hint;
    FontMetrics fm = g2d.getFontMetrics();
    int x = (this.getWidth() - fm.stringWidth(hintText));
    int y = this.getHeight() - fm.getDescent();
    g2d.drawString(hintText, x, y);
  }


}
