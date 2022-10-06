package world.model;

/**
 * Interface for target character.
 * Target character is placed on index 0 in the beginning of the game.
 * Players will make attempt to kill it and will reduce its health if
 * attempts are successful. 
 * Target character move to new room index every at every game turn.
 * 
 */
public interface TargetCharacter {
  
  /**
   * Getters for name.
   * @return the name of the character
   */
  String getName();
  
  
  /**
   * Getters the health of this character.
   * @return the current health points
   */
  int getHealth();
  
  
  /**
   * Getters for current space.
   * @return the current space index this character is at
   */
  int getCurrentSpaceId();
  
  
  /**
   * Will reduce the health by the specified points.
   * @param amount the health will be reduced by this amount
   * @throws IllegalArgumentException if amount is negative.
   * 
   */
  void reduceHealth(int amount) throws IllegalArgumentException;
  
  /**
   * Move this target character by 1 index room up.
   */
  void move();
  
  
  /**
   * Get the status of the target, whether or not is dead.
   * @return true if target's health is 0, false otherwise
   * 
   */
  boolean isDead();

  /**
   * Restore to target's health and position to before the game is played.
   * 
   */
  void restoreHealthAndPosition();
}


