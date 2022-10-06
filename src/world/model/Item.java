package world.model;

/**
 * Items are weapons that are used by players in the game.
 * It reduces the target's health by a certain damage point.
 * It has initial room id at which it's placed at the start of a game.
 * Once an item is picked up by a player, its room id is not used.
 * 
 */
public interface Item {
  
  /**
   * Getters the id of this item.
   * It's a unique number in the world.
   * @return the id of the item
   */
  int getItemId();
  
  /**
   * Getters for the the name of the item.
   * @return the name of the space
   */
  public String getName();
  
  
  /**
   * Getters for the damages this item will cause to target.
   * @return the damage point.
   */
  public int getDamagePoint();

}
