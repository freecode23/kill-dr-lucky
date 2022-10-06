package world.model;

import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * This interface defines how the world is represented in a UI.
 *
 */
public interface ImageGenerator {

  /**
   * Draw the spaces on a theMap using their respective row and column info.
   * @return true if it draws successfully
   * @throws IllegalArgumentException if spaces is null
   */
  void drawWorld() throws IllegalArgumentException;
  
  
  /**
   * Get the buffered image.
   * @return the buffered image
   */
  BufferedImage getImage();
  
  
  /**
   * Save the world image as a png file.
   * @throws IllegalStateException if file path does not exist
   */
  void saveWorldImage() throws IllegalStateException;
  
  
  /**
   * Get the space at this coordinate point.
   * @param point the point to be converted to space index
   * @return the space at this point
   * @throws IllegalArgumentException if point is null
   */
  Space getSpaceAtPoint(Point point) throws IllegalArgumentException;
  
  
  /**
   * Get the player index at a pixel point.
   * @param coord the coordinate point to check which player is here
   * @return the player id
   * @throws IllegalArgumentException if coord is null
   */
  Player getPlayerAtPoint(Point coord) throws IllegalArgumentException;
  
  /**
   * Redraw spaces after a player have moved.
   * @param oldSpace the space that a player moved from.
   * @param newSpace the new space that a player have moved to.
   * @throws IllegalArgumentException if spaces are null
   */
  void redrawSpacesPlayers(Space oldSpace, Space newSpace) throws IllegalArgumentException;
  
  
  /**
   * Draw target at this world.
   * @param target the target to be drawn
   */
  void drawTarget(TargetCharacter target);
  
  /**
   * Draw players of a space.
   * @param space the space where we want to draw the players of
   * @throws IllegalArgumentException if space is null
   */
  void drawPlayers(Space space) throws IllegalArgumentException;
  
  
  /**
   * Erase all the players on the space.
   * @param space the space where we want to draw the players of
   * @throws IllegalArgumentException if space is null
   */
  void erasePlayers(Space space) throws IllegalArgumentException;
  
  
  /**
   * Erase the target mark from the world.
   * @param target the target to be erased.
   * @throws IllegalArgumentException if space is null
   */
  void eraseTarget(TargetCharacter target) throws IllegalArgumentException;
}
