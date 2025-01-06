package cs3500.threetrios.view;

import java.awt.*;
import java.awt.geom.Path2D;

import cs3500.threetrios.model.Card;

public abstract class ThreeTriosCardDrawing extends Path2D.Double{

  private final int width;
  private final int height;

  /**
   * Draws a rectangle with the given width and height.
   *
   * @param width  the width of the rectangle
   * @param height the height of the rectangle
   */
  public ThreeTriosCardDrawing(int width, int height) {
    this.width = width;
    this.height = height;
    moveTo(0, 0);
    lineTo(width, 0);
    lineTo(width, height);
    lineTo(0, height);
    closePath();
  }

  /**
   * Draws a card with the given width and height.
   *
   * @param g2d      the graphics
   * @param card     the card
   * @param fontSize the fontsize
   */
  public abstract void drawCardNumbers(Graphics2D g2d, Card card, int fontSize);

  public int getWidth(){
    return this.width;

  }

  public int getHeight(){
    return this.height;

  }
}
