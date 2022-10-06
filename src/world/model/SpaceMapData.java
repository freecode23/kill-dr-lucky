package world.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * The implementation of a map data of space in the world.
 * the space has maximum of 12 boxes.
 */
public final class SpaceMapData implements MapData {

  private Point spaceCoord;
  private int spaceWidth;
  private int spaceHeight;
  private int boxWidth;
  private int boxHeight;
  private List<Point> boxesPoint;

  /**
   * Constructor for the map data.
   * @param coord the top left coordinate of the sapce
   * @param spaceWidth the width of the space
   * @param spaceHeight the height of the space
   * @param boxWidth the width of each of the 12 boxes
   * @param boxHeight the height of each of the 12 boxes
   * @throws IllegalArgumentException for negative width or height
   */
  public SpaceMapData(Point coord, int spaceWidth, int spaceHeight, int boxWidth, int boxHeight) {  
    
    if (spaceWidth <= 0 || spaceHeight <= 0 || boxWidth <= 0 || boxHeight <= 0) {
      throw new IllegalArgumentException("width and height cannot be less than or equal to zero");
    }
    
    if (coord == null || coord.x < 0 || coord.y < 0) {
      throw new IllegalArgumentException("Coordinates cannot be negative or null.\n");
    }
    
    this.spaceCoord = coord;
    this.spaceWidth = spaceWidth;
    this.spaceHeight = spaceHeight;
    this.boxWidth = boxWidth;
    this.boxHeight = boxHeight;
    this.boxesPoint = new ArrayList<Point>();
  }
  

  @Override
  public Point getSpaceCoord() {
    return this.spaceCoord;
  }
  
  @Override
  public int getSpaceWidth() {
    return this.spaceWidth;
  }

  @Override
  public int getSpaceHeight() {
    return this.spaceHeight;
  }

  @Override
  public int getboxWidth() {
    return this.boxWidth;
  }

  @Override
  public int getboxHeight() {
    return this.boxHeight;
  }

  @Override
  public void addBoxCoord(Point coord) {
    if (coord == null || coord.x < 0 || coord.y < 0) {
      throw new IllegalArgumentException("Coordinates cannot be negative or null.\n");
    }
    boxesPoint.add(coord);
  }

  @Override
  public Point getCoordAtBox(int index) {
    if (index < 0) {
      throw new IllegalArgumentException("Index cannot be negative");
    }
    return boxesPoint.get(index);
  }



  @Override
  public Point getPlayerCoordAtBox(int index) {
    if (index < 0) {
      throw new IllegalArgumentException("Index cannot be negative");
    }
    int xcircleBorderGap = this.getboxWidth() / 6;
    int ycircleBorderGap = this.getboxHeight() / 6;
    int x = this.getCoordAtBox(index).x + xcircleBorderGap;
    int y = this.getCoordAtBox(index).y + ycircleBorderGap;
    return new Point(x, y);
  }

}
