package world.view;

import java.awt.Color;

/**
 * Enum for all the colors used in the view.
 */
public enum WorldColor {
  // fields
  BLACK(new Color(32, 31, 34)),
  ORANGE(new Color(224, 149, 4)),
  RED(new Color(229, 56, 59)),
  DARKBLUE(new Color(0, 0, 51)),
  ADDBUTTONBACK(new Color(237, 240, 241)),
  BROWN(new Color(81, 59, 60)),
  WHITE(new Color(237, 240, 241));

  private Color color;

  /**
   * Constructor made private to enforce singleton.
   * @param color the color 
   */
  private WorldColor(Color color) {
    if (color == null) {
      throw new IllegalArgumentException("Color cannot be null.\n");
    }
    this.color = color;
  }

  /**
   * getter for the color.
   * @return the rgb color
   */
  public Color getColor() {
    return color;
  }


}
