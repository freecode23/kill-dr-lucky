package world.model;

/**
 * Player in the game the can be controlled by either human user or CPU.
 * Two players are the same if they have the same name and maxiItem
 * MaxItem varies between each players from range 1 to 5.
 */
public interface Player {
  

  /**
   * Get the name of the player.
   * @return the name of the player
   */
  String getName();
  
  /**
   * Get the space where the player is at.
   * @return the space 
   */
  Space getCurrentSpace();
  
  /**
   * Getters for maximum item that this player can hold.
   * @return the maximum item
   */
  int getMaxItem();
  
  /**
   * Get index item with the most damage point.
   * @return the index of the item if it has weapons. returns -1 if there is no item
   */
  int getItemIdWithMostDamage(); 
  
  
  /**
   * Get the name of the item this player is holding by item index.
   * @param itemId the unique index of the item
   * @return the name of the item.
   * @throws IllegalArgumentException if itemId < -2 or
   *      this player has no item of such index. 
   */
  String getItemNameById(int itemId) throws IllegalArgumentException; 
  
  /**
   * Get all of the list of items this player is holding.
   * @return this list of all the items as string
   */
  String getItemsAsString(); 
  
  
  /**
   * Check if the current player thinks it cannot be seen
   * a player thinks it cannot be seen if:
   * current space is empty AND :
   * 1. neighbors on pet mode have no players or
   * 2. current space has pet so it doesnt matter if neighbors have players.
   * @return true if it think it cannot be seen.
   */
  boolean thinksCannotBeSeen(); 
  
  /**
   * Player picks up an item and store it in its itemsHold.
   * @param other the player that this will judge if it can see or not
   * @return true if this player can see the other player
   * @throws IllegalArgumentException if player is null
   */
  boolean canSeePlayer(Player other);

  /**
   * Attempt to kill a target by using weapon. if item index is -1, it will poke eye.
   * Whether or not the attempt is sucessful, the item will be removed from the player
   * and thus the play. if poke eye, nothing is removed.
   * @param target the target it's trying to kill
   * @param itemId the item index it will use to damage the target.
   * @return true if attempt is successful false otherwise
   * @throws IllegalArgumentException if target is null or if itemId < -2 or
   *      target passed as parameter is not in the same space.
   *
   */
  boolean attemptToKill(TargetCharacter target, 
      int itemId) throws IllegalArgumentException;
  
  /**
   * Move pet to a any new space. Doesn't have to be neighbor.
   * Move pet is always sucessful as long as pet and newSpace is not null
   * @param pet the pet it's trying to move
   * @param newSpace the space it's moving to
   * @throws IllegalArgumentException if pet or newSpace is null
   *
   */
  void movePet(Pet pet, Space newSpace) throws IllegalArgumentException;
  
  /**
   * Display the neighbors of the space that the player is currently in.
   * Neighbors with pet will be hidden
   * @return the string of the names of neighbors, and the items in each space
   */
  String lookAround();
  

  /**
   * Move player to the neighbor of currentSpace.
   * @param newSpace the space it's moving to
   * @return true if moved successfully or false if neighbor does not exists.
   * @throws IllegalArgumentException if the space provided is null
   *
   */
  boolean moveToNeighbor(Space newSpace) throws IllegalArgumentException;

  
  /**
   * Player picks up an item and store it in its itemsHold.
   * @param item the item that it's adding to its inventory
   * @return true if successful
   *      return false if user cannot hold anymore item or item is not present
   * @throws IllegalArgumentException if item is null or item is not in currentSapce
   */
  boolean pickUpItem(Item item);

}
