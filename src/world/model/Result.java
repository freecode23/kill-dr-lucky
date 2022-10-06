package world.model;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * This interface defines the different datas that are 
 * returned by the model during the game.
 * these includes Strings and BufferedImage
 */
public interface Result {
  
  /**
   * Getter for name of the world.
   * @return the name of the world
   */
  String getName();
  
  /**
   * Getter for the the status of the game including
   * target position, whose turn it is and the turn number.
   * @return the current status of the game
   */
  String getStatus();
  

  
  /**
   * Setter for the the status of the game including
   * target position, whose turn it is and the turn number.
   * @param text the current status of the game
   * @throws IllegalArgumentException if text is null
   */
  void setStatus(String text) throws IllegalArgumentException;
  
  /**
   * Getter for the resulting text after every move of 
   * both cpu and human player.
   * @return the result of an action
   */
  String getActionResult();
  
  /**
   * Setter for the resulting text after every move of 
   * both cpu and human player.
   * @param text the action result
   * @throws IllegalArgumentException if text is null
   */
  void setActionResult(String text) throws IllegalArgumentException;
  
  /**
   * Setter for the limited info of the
   * player's space at the start of every turn.
   * @param text the long description of a space
   * @throws IllegalArgumentException if text is null
   */
  void setEveryTurnSpaceInfo(String text) throws IllegalArgumentException;
  
  /**
   * Getter for the limited info of the player's space at the start of every turn.
   * @return the long description of a space
   */
  String getEveryTurnSpaceInfo();
  
  /**
   * Setter for the look around text.
   * @param text the long description of a space
   * @throws IllegalArgumentException if text is null
   */
  void setLookAroundText(String text) throws IllegalArgumentException;
  
  /**
   * Getter for the look around string after human player do look around.
   * @return the long description of a space
   */
  String getLookAroundText();
  
  /**
   * Getter for player description that includes the maximum item
   * and the list of items the player is holding.
   * @return the description of the player
   */
  String getPlayerDescription();
  
  
  /**
   * Setter for player description that includes the maximum item
   * and the list of items the player is holding.
   * @param text the desciption of the player
   * @throws IllegalArgumentException if text is null
   */
  void setPlayerDesciption(String text) throws IllegalArgumentException;

  /**
   * Setter for the graphical representation of the map.
   * @param img the image of the map
   * @throws IllegalArgumentException if img is null
   */
  void setBufferedImage(BufferedImage img) throws IllegalArgumentException;

  /**
   * Getter for the graphical representation of the map.
   * @return img the image of the map
   */
  BufferedImage getBufferedImage();

  /**
   * Returns a list of space names from each spaces.
   * object in the same order the original list is in,
   * @return list of space names
   */
  List<String> getSpaceNames();

  
  /**
   * Setter for a list of items that the current player has.
   * @param curentPlayerItemNames the list of items and the damage point 
   * @throws IllegalArgumentException if the list is null 
   */
  void setCurentPlayerItemNames(List<String> curentPlayerItemNames)
      throws IllegalArgumentException;
  
  
  /**
   * Getter for a list of items that the current player has.
   * @return the list of items and the damage point 
   */
  List<String> getCurentPlayerItemNames();
  
  /**
   * Setter for a list of items in the space that the current player is in.
   * @param curentPlayerSpaceItemNames the list of items and the damage point 
   * @throws IllegalArgumentException if the list is null
   */
  void setCurentPlayerSpaceItemNames(List<String> curentPlayerSpaceItemNames) 
      throws IllegalArgumentException;
  
  /**
   * Getter for a list of items in the space that the current player is in.
   * @return the list of items and the damage point 
   * 
   */
  List<String> getCurentPlayerSpaceItemNames();
  
}
