package world.view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;


/**
 * 
 * A JPanel that draws the image of the map on it.
 *
 */
public class Canvas extends JPanel {
  /**
   * Unique serial id of the panel.
   */
  private static final long serialVersionUID;
  private BufferedImage img;
  
  static {
    serialVersionUID  = 3713234659389370379L;
  }
  

  /**
   * 
   * Constructor that just set the image.
   * @param img the image of the map.
   */
  public Canvas(BufferedImage img) {
    if (img == null) {
      throw new IllegalArgumentException("Image cannot be null");
    }
    this.img = img;
  }
  
  
  @Override
  public void paintComponent(Graphics g) {
    if (g == null) {
      throw new IllegalArgumentException("Graphics cannot be null");
    }
    g.drawImage(img, 0, 0, this);
  }
  
}
