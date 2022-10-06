package world.model;

import java.awt.Point;

/**
 * This interface defines the data that can be gathered from a 
 * space in the world.
 * All of these datas represent the actual representation on an image.
 * it contains:
 * - the space's width and height
 * - the size of each boxes in the space
 * - top left x and y coordinates of each box
 * - 
 */
public interface MapData {
  
  /**
   * Getter for the space width. 
   * @return the width of the rectangular space
   */
  int getSpaceWidth();
  
  /**
   * Getter for the space height. 
   * @return the height of the rectangular space
   */
  int getSpaceHeight();
 
  
  /**
   * Getter of the top left point coordinate of this space.
   * @return the xy coordinates of the space
   */
  Point getSpaceCoord();
  
  
  /**
   * Getter for the box width. 
   * @return the width of the rectangular box
   */
  int getboxWidth();
  
  /**
   * Getter for the box height. 
   * @return the height of the rectangular box
   */
  int getboxHeight();
  
  /**
   * Getter of the top left point coordinate of the player circle.
   * @param index the index of the box that we want to get the player's xy coordinate for
   * @return the xy coordinates of the box at the given index
   */
  Point getPlayerCoordAtBox(int index);
  
  /**
   * Append a top left xy coordinate of a box.
   * @param point the top left x and y coordinate of the box at the given index
   */
  void addBoxCoord(Point point);
  
  /**
   * Getter of the top left point coordinate of a box.
   * @param index the index of the box that we want to get the xy coordinate for
   * @return the xy coordinates of the box at the given index
   */
  Point getCoordAtBox(int index);
  
}
