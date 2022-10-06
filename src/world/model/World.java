package world.model;

import java.awt.Point;

/**
 * World will be implemented by WorldImpl.
 * There will only be one World instance when the program runs.
 * @author Sherly Hartono
 */

public interface World {

  /**
   * Display information about a specified space in the world.
   * In addition to the name of the space, this information should include
   * what items are in the space and what spaces can be seen from the specified space.
   * @param spaceId the index of the space we want to display info
   * @return the string representation of space
   * @throws IllegalArgumentException if index is greater than space size or index is negative
   */
  String getSpaceInfo(int spaceId) throws IllegalArgumentException;

  /**  
   * Get the space name of the coordinate.
   * @param coord the point coordinate
   * @return space name
   * @throws IllegalArgumentException if coord is null
   */
  String getSpaceNameByCoord(Point coord);


  /**
   * Generate graphical image of the world and save to png file.
   * @throws IllegalStateException if cannot save the world image to a file
   */
  void generateImage() throws IllegalStateException;


  /**
   * Add players to the world. Players are not specified in a file so we need 
   * to make this public
   * 
   * @param name the name of the player
   * @param spaceId the index of the room the player chose at the start of the game
   * @throws IllegalArgumentException if names are null or an empty string or maxItem is less than 0
   *     or spaceName does not exists in the world
   */
  void addHumanPlayer(String name, int spaceId)
      throws IllegalArgumentException;


  /**
   * Add players to the world. This method will generate random names, and space 
   * index for the player.
   */
  void addCpuPlayer();


  /**
   * Make CPU controller control its players.
   * set the string message to tell users whether or not CPU has successfully made a move
   * in result.
   */
  void makeCpuTakeTurn();

  /**
   * Allow player to pick item.
   * @param itemId the index of the item it will pick up
   * @throws IllegalArgumentException if 
   *      item index is negative or its greater than or equal to items size
   */
  void makeHumanPickItem(int itemId) throws IllegalArgumentException;


  /**
   * Allow player to move to its neighbor using the point coordinate
   * of the space to move to.
   * @param coord the neighboring space's point it's moving to
   * @throws IllegalArgumentException if coordinate is null
   */
  void makeHumanMovePoint(Point coord) throws IllegalArgumentException;  

  /**
   * Allow a player to look around by displaying information about where a specific
   * player is in the world including what spaces that can been seen from where they are.
   * as well as the items inside.
   */
  void makeHumanLook();

  /**
   * Allow player to move pet to any space in the wold.
   * @param spaceId the space's index the pet is moved
   * @throws IllegalArgumentException if space index is negative
   *      or space index is greater than or equal to space size
   */
  void makeHumanMovePet(int spaceId) throws IllegalArgumentException;


  /**
   * Allow player to attempt a kill.
   * @param itemId item the player wants to use to attempt a murder
   * @throws IllegalArgumentException if item index is negative
   *      or item index is greater than or equal to space size
   */
  void makeHumanKill(int itemId) throws IllegalArgumentException;

  /**
   * Check if the game is over.
   * game is over if health of target is 0 in which case the last player
   * who hurt the target wins
   * or game is also over if currentTurn == maxTurn
   * @return true if game is over false otherwise.
   */
  boolean isGameOver();


  /**
   * 
   * Check if current turn is a cpu.
   * @return true if it's a cpu's turn
   */
  boolean isCurrentTurnCpu();

  /**
   * Check if game has started.
   * @return true if game has started
   */
  boolean hasGameStarted();

  /**
   * Getters for the result object that contains the status,
   * the action result, long space info, and player description.
   * @return the result object
   */
  Result getResult();

  /**
   * Check if the current player is in this point.
   * @param point the point from the image
   * @return true if the player is here
   * @throws IllegalArgumentException if point is null
   */
  boolean isCurrentPointHumanPlayer(Point point);

  /**
   * Check if this point is a neighbor of current player's space.
   * @param point the point from the image
   * @return if this point represent a space different from the player
   * @throws IllegalArgumentException if point is null
   */
  boolean isCurrentPointHumanPlayerNeighbor(Point point) throws IllegalArgumentException;

  /**
   * Reset the world to the current world specification.
   */
  void reset();

  /**
   * Reset to a new game with the given world specification.
   * @param file the world specification to reset the game to.
   * @throws IllegalArgumentException if file is null
   */
  void reloadNewGame(Readable file);

  /**
   * Set the start field to true.
   */
  void startTheGame();

  /**
   * End the game.
   * This will be called by Controller's quit
   */
  void endTheGame();

  /**
   * Validate game turn.
   * @param isCpuCall if cpu is taking a turn
   * @throws IllegalStateException if:
   *      - game has not started
   *      - player size is 0
   *      - game is over
   *      - human cpu turn mismatch
   */
  void validateGameTurn(boolean isCpuCall) throws IllegalStateException;

  /**
   * Make sure current space have items.
   * @throws IllegalStateException if space have no items
   */
  void currentSpaceShouldHaveItems() throws IllegalStateException;
}
