package world.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;
 
/**
 * ImageGenerator class create a graphical image of the world.
 * It displays players and target in the spaces
 * The target will move at every turn of the game
 */
public class DefaultImageGenerator implements ImageGenerator {

  private final BufferedImage theMap; 
  private final Graphics2D g2;
  private final int adj;
  private final int theMapWidth;
  private final int theMapHeight;
  private final List<Space> spaces;
  private final List<MapData> spacesData;
  private final int maxPlayers;
  private final int targetCircleSize;
  private final int playerCircleSize;
  private final Color targetColor;
  private final Color playersColor;
  private final Color mapBackgroundCol;
  private final Color gridColor;
  
  /**
   * Construct a theMap from the world's spaces by drawing on the theMap.
   * and save it as a png file.
   * @param spaces the list of spaces
   * @param adj the scaling adjustment to convert from the actual world size to image size. 
   * @param theMapWidth the width of the theMap.
   * @param theMapHeight the height of the theMap
   * @param adj the adjustment needed to size the pixel.
   * @throws IllegalArgumentException if spaces is null or map width
   *      and map height is less than or equals to 0.
   */
  public DefaultImageGenerator(List<Space> spaces, int theMapWidth, int theMapHeight, 
      int adj) throws IllegalArgumentException {
    if (spaces == null || theMapWidth <= 0
            || theMapHeight <= 0 || adj <= 0) {
      throw new IllegalArgumentException(
              new StringBuilder("spaces cannot be null ")
                      .append("or map width and height cannot be 0")
                      .toString());
    }
    
    this.adj = adj;
    this.spaces = spaces;
    this.theMapWidth = theMapWidth * adj + 1;
    this.theMapHeight = theMapHeight * adj + 1;
    this.theMap = new BufferedImage(this.theMapWidth, 
        this.theMapHeight, BufferedImage.TYPE_INT_RGB);
    this.g2 = (Graphics2D) theMap.getGraphics();
    this.spacesData = new ArrayList<>();
    this.maxPlayers = 10;
    
    // set colors
    this.mapBackgroundCol = new Color(164, 149, 137);
    this.targetColor = Color.RED;
    this.playersColor = Color.GREEN;
    this.gridColor = new Color(180, 180, 180);
    
    // set circle size
    this.targetCircleSize = 17;
    this.playerCircleSize = 17;
    
  }
  
  @Override
  public void drawWorld() throws IllegalArgumentException {
    
    // 1. draw background
    g2.setColor(mapBackgroundCol);
    g2.fillRect(0, 0, this.theMapWidth, this.theMapHeight);

    int upLeftRow; // y top
    int upLeftCol; // x
    int lowRightRow; // y bottom
    int lowRightCol; // x
    int rectWidth;
    int rectHeight;
    int x;
    int y;

    int roomIdx = 0;
    
    
    // 2. Draw the space lines
    for (Space space : spaces) {
      
      g2.setColor(Color.WHITE);
      upLeftRow = space.getUpLeft().getRow();
      upLeftCol = space.getUpLeft().getCol();
      lowRightRow = space.getLowRight().getRow();
      lowRightCol = space.getLowRight().getCol();

      // get width height and the starting x,y point
      rectWidth = (lowRightCol * this.adj - upLeftCol * this.adj) + this.adj; 
      rectHeight = (lowRightRow * this.adj - upLeftRow * this.adj) + this.adj;
      x = upLeftCol *  this.adj;
      y = upLeftRow *  this.adj;
      
      g2.setStroke(new BasicStroke(2));
      g2.drawRect(x, y, rectWidth, rectHeight); 
      
      // 3. Write the name of the room
      StringBuilder spaceNameAndIdx = new StringBuilder();
      g2.setColor(Color.BLACK);
      spaceNameAndIdx.append(roomIdx).append(" ").append(space.getName());
      final int xTextGap = 5;
      final int yTextGap = adj / 2;
      g2.drawString(spaceNameAndIdx.toString(), x + xTextGap, y + yTextGap - 5);

      // 4. Set the boxes in each space
      // set up box sizes
      final int roughTextHeight = 2;
      int totalBoxesSpaceWidth = rectWidth;
      int totalBoxesSpaceHeight = rectHeight - yTextGap;
      
      int eachBoxWidth = (totalBoxesSpaceWidth / 4) - 1;
      int eachBoxHeight = (totalBoxesSpaceHeight / 3) - 1;
      
      // create a new spaceData
      MapData spaceData = new SpaceMapData(new Point(x, y), 
          rectWidth, rectHeight, eachBoxWidth, eachBoxHeight);
 
      g2.setColor(gridColor);
      int xbox;
      int ybox;
      
      // create grid
      final int totalRow = 3;
      final int totalCol = 4;
      for (int i = 0; i < totalCol; i++) {
        for (int j = 0; j < totalRow; j++) {
          
          // store the x and y top left coord of each box
          xbox = x + 1 + (i * eachBoxWidth);
          ybox = y + yTextGap + roughTextHeight + (j * eachBoxHeight);
          
          spaceData.addBoxCoord(new Point(xbox, ybox));
          g2.setStroke(new BasicStroke(1));
          // draw grid
          g2.drawRect(xbox, ybox, eachBoxWidth, eachBoxHeight); 
        }
      }
      roomIdx++;
      // store all of the space data 
      spacesData.add(spaceData);

    }
  }
  
  @Override
  public BufferedImage getImage() {
    return this.theMap;
  }
  
  @Override
  public void saveWorldImage() throws IllegalStateException {
    try {
      // save image
      File outputfile = new File("worldtheMap.png");
      ImageIO.write(theMap, "png", outputfile);
    } catch (IOException e) {
      throw new IllegalStateException("this file path does not exist");
    }
  }
  
  @Override
  public Space getSpaceAtPoint(Point point) throws IllegalArgumentException {
    if (point == null) {
      throw new IllegalArgumentException("Cannot get space at null point");
    }
    int i = 0;
    for (MapData spaceData : spacesData) {

      int xspace = spaceData.getSpaceCoord().x;
      int yspace = spaceData.getSpaceCoord().y;
      int width = spaceData.getSpaceWidth();
      int height = spaceData.getSpaceHeight();
      
      if (isPointWithinRect(point, xspace, yspace, width, height)) {
        return spaces.get(i);
      }
      i += 1;
      
    }
    return null;
  }
  
  @Override
  public Player getPlayerAtPoint(Point coord) throws IllegalArgumentException  {
    if (coord == null || coord.x < 0 || coord.y < 0) {
      throw new IllegalArgumentException("Coordinates cannot be negative or null.\n");
    }
    // 1. get the space at this point
    Space space = getSpaceAtPoint(coord);
    
    if (space == null) {
      throw new IllegalArgumentException("Cannot move here.\n");
    }
    
    // 2. set up
    MapData spaceData = spacesData.get(space.getId());
    int playerBoxIndex = -1;
    
    // 3. get the box index of the clicked Point
    // loop through each box that has players
    for (int i = 0; i < space.getPlayersSize(); i++) {
      
      int xplayer = spaceData.getPlayerCoordAtBox(i).x;
      int yplayer = spaceData.getPlayerCoordAtBox(i).y;
      
      // check if this point belong to this box,
      if (isPointWithinRect(coord, xplayer, yplayer, playerCircleSize, playerCircleSize)) {
        playerBoxIndex = i;
        // get the player at this box index
        return space.getPlayerByOrder(playerBoxIndex);
      }
    }
    return null;
  }

  @Override
  public void redrawSpacesPlayers(Space oldSpace, Space newSpace) throws IllegalArgumentException {
    if (oldSpace == null || newSpace == null) {
      throw new IllegalArgumentException("Spaces cannot be null.\n");
    }
    erasePlayers(oldSpace);
    drawPlayers(oldSpace);
    
    erasePlayers(newSpace);
    drawPlayers(newSpace);
  }
  
  @Override
  public void drawTarget(TargetCharacter target) {
    if (target == null) {
      throw new IllegalArgumentException("Target cannot be null.\n");
    }
    // get the spaceData
    MapData spaceData = spacesData.get(target.getCurrentSpaceId());
    g2.setColor(targetColor);
    
    // draw circle
    int xtarget = spaceData.getPlayerCoordAtBox(11).x;
    int ytarget = spaceData.getPlayerCoordAtBox(11).y;
    g2.fillOval(xtarget, ytarget, targetCircleSize, targetCircleSize);

    // draw name (initials)
    List<String> splitName = Arrays.asList(target.getName().split("\\s+"));
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < splitName.size(); i++) {
      sb.append(splitName.get(i).substring(0, 1)).append(".");
    }
    sb.append("-").append(target.getHealth());
    drawNameWithinBox(sb.toString(), spaceData, xtarget, ytarget);
  }
  
  @Override
  public void drawPlayers(Space space) throws IllegalArgumentException {
    if (space == null) {
      throw new IllegalArgumentException("Spaces cannot be null.\n");
    }
    
    // get the map data of this space
    MapData spaceData = spacesData.get(space.getId());

    
    // for all players draw it on the box
    for (int i = 0; i < space.getPlayersSize(); i++) {
      g2.setColor(playersColor);
      
      // draw circle
      int xplayer = spaceData.getPlayerCoordAtBox(i).x;
      int yplayer = spaceData.getPlayerCoordAtBox(i).y;
      g2.fillOval(xplayer, yplayer, playerCircleSize, playerCircleSize);
      
      // draw name
      String name = space.getPlayerByOrder(i).getName(); 
      drawNameWithinBox(name, spaceData, xplayer, yplayer);
    }
  }

  @Override
  public void erasePlayers(Space space) throws IllegalArgumentException {
    if (space == null) {
      throw new IllegalArgumentException("Spaces cannot be null.\n");
    }
    
    // get the map data of this space
    MapData spaceData = spacesData.get(space.getId());
    
    for (int i = 0; i < maxPlayers; i++) {
      
      // get coordinates
      int x = spaceData.getCoordAtBox(i).x;
      int y = spaceData.getCoordAtBox(i).y;
      
      eraseArea(x, y, spaceData.getboxWidth(), spaceData.getboxHeight());
    }
  }

  @Override
  public void eraseTarget(TargetCharacter target) throws IllegalArgumentException {
    if (target == null) {
      throw new IllegalArgumentException("TargetCharacter cannot be null.\n");
    }
    
    // get the spaceData of where the target is at
    MapData spaceData = spacesData.get(target.getCurrentSpaceId());
    
    // get the coordinate of the target
    int x = spaceData.getCoordAtBox(11).x;
    int y = spaceData.getCoordAtBox(11).y;
    
    // erase
    eraseArea(x, y, spaceData.getboxWidth(), spaceData.getboxHeight());
  }

  /**
   * This private method helps erase an area.
   * @param x starting coordinate of the area to be erased.
   * @param y starting coordinate of the area to be erased
   * @param width of the area to be erased.
   * @param height of the area to be erased.
   */
  private void eraseArea(int x, int y, int width, int height) {
    if (x <= -1 || y <= -1 || height <= -1) {
      throw new IllegalArgumentException("Coordinates for erasing cannot be null.\n");
    }
    // clear means set to black
    g2.clearRect(x, y, width, height);
    
    // color it to our background
    g2.setColor(mapBackgroundCol);
    g2.fillRect(x, y, width, height);

    // redraw the grid
    g2.setStroke(new BasicStroke(1));
    g2.setColor(gridColor);
    g2.drawRect(x, y, width, height); 
  }

  /**
   * Util method to check if a point is within a rectangle.
   * @param point the point to be checked
   * @param xrect the topleft x coordinate
   * @param yrect the topLeft y coordinate
   * @param width the width of the rectangle
   * @param height the height of the rectangle
   * @return true if this point is within the rectangle
   */
  private boolean isPointWithinRect(Point point, int xrect,
                                    int yrect, int width, int height) {
    if (point == null) {
      throw new IllegalArgumentException("Point cannot be null.\n");
    }
    if (xrect <= -1 || yrect <= -1 || height <= -1 || width <= -1) {
      throw new IllegalArgumentException("x,y, hieght or width cannot be null.\n");
    }
    int xmin = xrect;
    int ymin = yrect;
    int xmax = xrect + width;
    int ymax = yrect + height;

    if ((point.getX() >= xmin && point.getX() <= xmax)
            && (point.getY() >= ymin && point.getY() <= ymax)) {
      return true;
    }

    return false;
  }

  /**
   * Helper method to draw names of entities within the space box.
   * @param name of the entity.
   * @param spaceData of the space to be used.
   * @param x of coordinates of entity.
   * @param y of coordinates of entity.
   */
  private void drawNameWithinBox(String name, MapData spaceData, int x, int y) {
    if (name == null || "".equals(name)) {
      throw new IllegalArgumentException("Name cannot be null.\n");
    }
    if (x <= -1 || y <= -1) {
      throw new IllegalArgumentException("x,y cannot be null.\n");
    }
    if (spaceData == null) {
      throw new IllegalArgumentException("SpaceData cannot be null.\n");
    }

    int xcircleTextGap = adj / 3;
    int ycircleTextGap = adj / 2;
    int ycircleBorderGap = spaceData.getboxHeight() / 3;

    int nameHeight = ycircleBorderGap + ycircleTextGap + 5;
    if (nameHeight > spaceData.getboxHeight()) {
      // draw name to the side of the circle
      g2.drawString(name, x + xcircleTextGap, y + adj / 4);

    }  else {

      // default: draw name at the bottom of circle
      g2.drawString(name, x, y + ycircleTextGap);
    }
  }

}
