package world.controller;

import java.awt.Point;

/**
 * This class represents all the features a controller
 * should have for the view to use inorder to make a game
 * play possible.
 */
public interface Features {

  /**
   * This API is for the view to notify
   * the controller that a human player is to be added.
   * It will ask the view to show input dialogue to the user
   * asking for player's name and space they want to move to.
   * It will pass these info to the model then tell the view
   * to show the result of these changes in the map
   * 
   */
  void addHumanPlayer();

  /**
   * This API is for the view to notify
   * the controller that a cpu player is to be added.
   * it will tell the model to add a random cpu player
   * it will tell the view to update the map.
   */
  void addCpuPlayer();

  /**
   * This will ask the model if the coordinate is on a player or a neighbor 
   * of current player's space.
   * 
   * Case 1: if it's a player, it will grab player's info from the model 
   * and ask the view to display this info
   * 
   * Case 2: If it's a neighboring space, it will ask the view to show 
   * dialogue to the user for confirmation.
   * if the user pick a yes, it will ask the model to move the player to this coordinate
   * if the user pick a no, it will not do anything.
   *
   * Will do view.resetFocus() at the end to allow keypress to work again
   * @param coord the coordinate 
   */
  void moveHumanOrDisplayPlayerInfo(Point coord);

  /**
   * This API is for the view to notify
   * the controller to display player's information.
   * It will tell the model to make human look around.
   * and then pass the string to the view so it can show the look around info to the user.
   */
  void humanLookAround();

  /**
   * This API is for the view to notify
   * the controller to move the pet by a human player.
   * It will tell the view to ask user for space input
   * then tell the model to make a human move a pet
   * and tell the view to display changes.
   */
  void humanMovePet();

  /**
   * This API is for the view to notify
   * the controller to pick an item by a human player.
   * It will tell the view to ask user for item input
   * then tell the model to make a human pick an item
   * and tell the view to display changes.
   */
  void humanPickItem();

  /**
   * This API is for the view to notify
   * the controller to a kill attempt was made by a human player.
   * It will tell the view to ask user for item input
   * then tell the model to allow human to attempt a kill
   * and tell the view to display changes.
   */
  void humanKill();

  /**
   * This API is for the view to notify
   * the controller to make CPU take turn.
   * It will tell the model to take cpu's turn 
   * and update the view.
   * 
   */
  void takeCpuTurn();
  
  /**
   * This API is for the view to notify
   * the controller start the game. It will switch panel from welcome to start game.
   */
  void startGame();

  /**
   * This is called by the view when a user click on the option to load a new world specification.
   * It will tell the model to reset the game using the new world specification.
   * @param file the new world specification to load the game with
   */
  void loadNewGame(Readable file);

  /**
   * It will ask the model to quit the game and ask the view to close the window
   * It will be called by the view to notify the controller to quit the game.
   */
  void quitGame();

  /**
   * Will tell the view to switch layout from welcome to show
   * add players.
   */
  void playGameMode();

  /**
   * Will reset the current game to before adding players.
   */
  void resetCurrentGame();
}
