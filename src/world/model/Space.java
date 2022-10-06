package world.model;

import java.util.List;

/**
 * Spaces is defined in terms of its upper left point, and lower right point.
 * There maybe some items inside a space.
 * Two space are the same if they have the same name and rowCol
 * ensured that item names are unique.
 * Space stores its neighbors object as list. 
 * The neighbors variable is protected for WorldImpl to set the neighbors for each space.
 * A space class does not know all the other spaces in the world 
 * hence it cannot set its own neighbors.
 */
public interface Space {

  /**
   * Getters for the space Id.
   * @return the Id of the space in the world.
   */
  int getId();
  
  /**
   * Getters for the name of the space.
   * @return the name of the space
   */
  String getName();
  
  /**
   * Getters for the top left coordinate of the space.
   * @return the top left coordinate as RowCol object
   */
  RowCol getUpLeft();
  
  /**
   * Getters for the bottom right coordinate of the space.
   * @return the bottom right coordinate as RowCol object
   */
  RowCol getLowRight();
  
  /**
   * Getters for the number of neighbors.
   * @return the number of neighbor in the space
   */  
  int getNeighborSize();
  
  /**
   * Get the size of the player.
   * @return the number of players in the space
   */  
  int getPlayersSize();
  
  /**
   * Getters for the number of items.
   * @return the number of items in the space
   */
  int getItemSize();
  
  /**
   * Getters for names and index of the items.
   * @return the items in the space
   */
  String getItemsAsString();

  /**
   * Getters for a neighbor.
   * @param id the Id of neighbor in the set
   * @return a neighbor space
   * @throws IllegalArgumentException if Id is less than 0
   */  
  Space getNeighborAt(int id) throws IllegalArgumentException;
  
  
  /**
   * Getters for item.
   * @param id the Id of item in the set
   * @return the item.
   * @throws IllegalArgumentException if Id is less than 0
   */  
  Item getItemAt(int id) throws IllegalArgumentException;
  
  
  /**
   * Add a an item to this space.
   * @param item the item to be added
   * @throws IllegalArgumentException if item is null
   */  
  void addItem(Item item) throws IllegalArgumentException;
  
  /**
   * Add a neighbor to the set of neighbors for this space. 
   * @param neighbor the space that's a neighbor of this space.
   * @throws IllegalArgumentException if neighbor is a null pointer.
   */  
  void addNeighbor(Space neighbor) throws IllegalArgumentException;
  
  /**
   * Add a player to the set of players for this space. 
   * Player object can add itself to a space
   * when it's moving to neighbor.
   * @param player the player that will be added inside this space
   * @throws IllegalArgumentException if player is null
   */
  void addPlayer(Player player) throws IllegalArgumentException;
  
  /**
   * Add pet to the current space.
   * @param pet the pet added
   * @throws IllegalArgumentException pet is null
   * @throws IllegalStateException if there is already a pet in this space.
   */
  void addPet(Pet pet) throws IllegalArgumentException, IllegalStateException;
  
  /**
   * Remove pet from the space by set pet to null.
   */
  void removePet();
  
  /**
   * Remove player from this space.
   * Player object will use this to remove itself from a space
   * when it's moving to neighbor.
   * @param player the player that will be removed inside this space
   * @throws IllegalArgumentException if player is null or player to be removed does not exist
   */
  void removePlayer(Player player) throws IllegalArgumentException;
  
  
  /**
   * Remove item from this space.
   * Player will call this when it picks up an item from a space.
   * @param item the item that will be removed inside this space
   * @throws IllegalArgumentException if item is null
   * @throws IllegalStateException if the item to be removed does not exist
   */
  void removeItem(Item item) throws IllegalArgumentException;
  
  
  /**
   * Check if a space is a neighbor of this by calculation.
   * @param other the other space to check if neighbor
   * @return true if other is neighbor
   * @throws IllegalArgumentException if space is null
   */
  boolean isNeighborByCalc(Space other) throws IllegalArgumentException;
  
  /**
   * Check if a space is a neighbor of this by checking the list.
   * @param other the other space to check if neighbor
   * @return true if other is neighbor
   * @throws IllegalArgumentException if space is null
   */
  boolean isNeighbor(Space other) throws IllegalArgumentException;
  
  /**
   * Check if a item in this space.
   * @param other the item to check
   * @return true if item is present
   * @throws IllegalArgumentException if item is null
   */
  boolean isItemPresent(Item other) throws IllegalArgumentException;

  
  /**
   * Get the pet presence.
   * @return true if pet is here false otherwise
   */
  boolean isPetHere();
  
  /**
   * Check whether all neighbors, with or without pet have players.
   * @return true if player exist in any one of the neighbors
   */
  boolean doNeighborsHavePlayers();

  
  /**
   * Check whether neighbors without pet have players.
   * @return true if player exist in any one of the neighbors
   */
  boolean doNeighborsHavePlayersPetMode();
  
  /**
   * List items inside this space as string.
   * This is made public so that we can use it to display neighbors items
   * @return the string that contains item name, damage point, and its room Id
   */
  String itemsAsString(); 
  
  /**
   * List players inside this space as string.
   * This is set as public so neighbors can access it. 
   * @param nameOnly will be set to true if only name is returned
   * @return the string that contains the player name 
   */
  String playersAsString(boolean nameOnly);
  
  
  /**
   * get the name of the pet in this space.
   * @return the pet name
   */
  String petAsString();
  
  /**
   * Get the info of this space in two mode.
   * Mode 1:
   * if is look around is true it will:
   * List the items, players, and neighbors info.
   * The neighbors listed don't include pet.
   * Neighbors with pet will be hidden here.
   * 
   * 
   * Mode 2:
   * if look around is false it will:
   * Show the limited info about a space without details.
   * These includes spacename, items info, players name
   * neigbors name, pet present
   * Neighbors with pet will be hidden here.
   * 
   * @param isLookAround set the mode of the string return
   * @return the description of the space. 
   */
  String toStringIsLookAround(boolean isLookAround);

  
  /**
   * Get the neighbors indices of the current space.
   * @return the list of indices of neighbors
   */
  List<Integer> getNeighborsIndices();

  /**
   * Get the player by index order not the id of the player.
   * @param orderIndex the order of the index of the list.
   * @return the the player at this order index.
   */
  Player getPlayerByOrder(int orderIndex);



  /**
   * Remove all players in the game.
   */
  public void removeAllPlayers();



}
