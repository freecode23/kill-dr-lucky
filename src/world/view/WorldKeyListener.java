package world.view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

/**
 * 
 * A class that overrides keyTyped to capture a click on the 
 * game board and pass the coordinate to the controller.
 * keyPressed and keyReleased is not overriden
 * since the are no actions taken for these. 
 *
 */
public class WorldKeyListener implements KeyListener {

  private Map<Character, Runnable> keyTypedMap;
  
  /**
   * Constructor that will just set the map to null.
   */
  public WorldKeyListener() {
    keyTypedMap = null;
  }

  /**
   * Set the map of charactera and the corresponding runnables.
   * @param map the map that will map the character to the callback function
   */
  public void setMap(Map<Character, Runnable> map) {
    if (map == null) {
      throw new IllegalArgumentException("Keylistener map cannot be null");
    }
    this.keyTypedMap = map;
  }
  
  @Override
  public void keyTyped(KeyEvent keyEvent) {
    if (keyEvent == null) {
      throw new IllegalArgumentException("keyEvent map cannot be null");
    }
    if (keyTypedMap.containsKey(keyEvent.getKeyChar())) {
      keyTypedMap.get(keyEvent.getKeyChar()).run();
    }
  }

  @Override
  public void keyPressed(KeyEvent keyEvent) {
    if (keyEvent == null) {
      throw new IllegalArgumentException("keyEvent map cannot be null");
    }
  }

  @Override
  public void keyReleased(KeyEvent keyEvent) {
    if (keyEvent == null) {
      throw new IllegalArgumentException("keyEvent map cannot be null");
    }
  }

}
