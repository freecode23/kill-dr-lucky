package world.model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 
 * Default implementation of Result object. This class returns the result of
 * various methods from Default World class
 */
public final class DefaultResult implements Result {
  private String name;
  private String status;
  private String actionResult;
  private String spaceText;
  private String lookAroundText;
  private String playerDescription;
  private BufferedImage img;
  private List<String> spaceNames;
  private List<String> currentPlayerItemNames;
  private List<String> currentPlayerSpaceItemNames;

  /**
   * Constructor that sets all of the result string to empty string. The one who
   * sets these to the correct values should be the corresponding method in the
   * World class class.
   * @param name the name of the world
   * @param img the image of the world
   * @param spaceNames the list of name of all spaces for this world
   * @throws IllegalArgumentException if img or file is null
   */
  public DefaultResult(String name, BufferedImage img,
      List<String> spaceNames) throws IllegalArgumentException {
    
    validateString(name);
    if (img == null) {
      throw new IllegalArgumentException(
          "File or Random number generator is null when constructing the result of the world");
    }
    
    this.status = "Add some players to start game";
    this.actionResult = "No one has taken any turn";
    this.spaceText = "No space to be shown at this turn";
    this.playerDescription = "";
    this.lookAroundText = "";
    this.img = img;
    this.name = name;
    this.spaceNames = spaceNames;
    this.currentPlayerItemNames = new ArrayList<>();
    this.currentPlayerSpaceItemNames = new ArrayList<>();
  }

  
  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public String getStatus() {
    return this.status;
  }

  @Override
  public void setStatus(String text) throws IllegalArgumentException {
    validateString(text);
    this.status = text;

  }

  @Override
  public String getActionResult() {
    return this.actionResult;
  }

  @Override
  public void setActionResult(String text) throws IllegalArgumentException {
    validateString(text);
    this.actionResult = text;
  }

  @Override
  public String getEveryTurnSpaceInfo() {
    return this.spaceText;
  }
  
  @Override
  public void setEveryTurnSpaceInfo(String text) throws IllegalArgumentException {
    validateString(text);
    this.spaceText = text;
  }
  
  @Override
  public String getLookAroundText() {
    return this.lookAroundText;
  }
  
  @Override
  public void setLookAroundText(String text) throws IllegalArgumentException {
    validateString(text);
    this.lookAroundText = text;
  }

  @Override
  public String getPlayerDescription() {
    return this.playerDescription;
  }

  @Override
  public void setPlayerDesciption(String text) throws IllegalArgumentException {
    validateString(text);
    this.playerDescription = text;
  }

  @Override
  public void setBufferedImage(BufferedImage img) {
    if (img == null) {
      throw new IllegalArgumentException("image cannot be empty");
    }
  }

  @Override
  public BufferedImage getBufferedImage() {
    return this.img;
  }
  
  @Override
  public List<String> getSpaceNames() {
    
    return Collections.unmodifiableList(this.spaceNames);
  }
  
  @Override
  public List<String> getCurentPlayerItemNames() {
    
    return Collections.unmodifiableList(this.currentPlayerItemNames);
  }

  @Override
  public void setCurentPlayerItemNames(List<String> curentPlayerItemNames) 
      throws IllegalArgumentException {
    if (curentPlayerItemNames == null) {
      throw new IllegalArgumentException("Curent player item names cannot be null.");
    }
    
    this.currentPlayerItemNames = curentPlayerItemNames;
  }
  
  @Override
  public List<String> getCurentPlayerSpaceItemNames() {
    
    return Collections.unmodifiableList(this.currentPlayerSpaceItemNames);
  }
  
  @Override
  public void setCurentPlayerSpaceItemNames(List<String> curentPlayerSpaceItemNames) 
      throws IllegalArgumentException {
    
    if (curentPlayerSpaceItemNames == null) {
      throw new IllegalArgumentException("Curent player space item names cannot be null.");
    }
    
    this.currentPlayerSpaceItemNames = curentPlayerSpaceItemNames;
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append("status: ").append(status);
    s.append("\naction result: ").append(this.actionResult);
    s.append("\nspace text: ").append(this.spaceText);
    s.append("\nplayer description: ").append(this.playerDescription);
    return s.toString();
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, actionResult, spaceText, playerDescription);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DefaultResult)) {
      return false;
    }

    DefaultResult that = (DefaultResult) o;
    return this.status.equals(that.getStatus()) && this.actionResult.equals(that.actionResult)
        && this.playerDescription.equals(that.getPlayerDescription())
        && this.spaceText.equals(that.getEveryTurnSpaceInfo());
  }

  /* *********** Helper methods *******************/

  /**
   * Util for validating string argument.
   *
   * @param text the string to be validated
   * @throws IllegalArgumentException if the string is empty
   */
  private void validateString(String text) throws IllegalArgumentException {
    if (text == null || "".equals(text)) {
      throw new IllegalArgumentException("text arguments cannot be empty");
    }
  }
}
