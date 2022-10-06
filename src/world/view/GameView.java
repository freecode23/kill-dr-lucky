package world.view;

import java.util.List;
import world.controller.Features;
import world.model.Result;


/**
 * 
 * View defines how the world game is displayed in GUI.
 *
 */
public interface GameView {
  
  /**
   * A method to set the mapping of user actions and the different features
   * that will be called according to the actions.
   * @param f the feature object that view will pass control to.
   * @throws IllegalArgumentException if the feature is null
   */
  void setFeatures(Features f) throws IllegalArgumentException;
  
  /**
   * Will update the following on the GUI:
   * - image on the scroll panel
   * - the game status
   * - the action result.
   * This will be called at the end of every turn
   * when human move, move pet, attempt to kill, pick item, it will only need to call this method
   * @param r the result object that it can get the status and action result from
   * @throws IllegalArgumentException if result is null
   */
  void updateMapStatusAndActionResultPanel(Result r) throws IllegalArgumentException;
  
  /**
   * This will set the following in GUI:
   * - the long text panel to show the space that the user is at
   * - image, game status, and action result.
   * @param r the result object that it can retrieve the messages from
   * @throws IllegalArgumentException if result is null
   */
  void updateLookAround(Result r) throws IllegalArgumentException;
  

  /**
   * Will show message dialogue of the player's items and
   * max item they can hold.
   * @param playerInfo the the player info
   * @throws IllegalArgumentException if result is null
   */
  void showPlayerInfo(String playerInfo) throws IllegalArgumentException;
  
  
  
  /** Triggers the flow for showing add player to the input dialog.
   * @param spaceNames the list of names of spaces
   * @return data entered by the user for new player.
   */
  List<String> showAddPlayerInputDialog(List<String> spaceNames);
  
  
  /**
   * Will show a dialogue and ask user which item to pick.
   * @param msg the message to display to user
   * @param itemsToChooseFrom to list of items to choose from
   * @return the user choice of string of the item to pick
   */
  String askUserChooseItem(String msg, List<String> itemsToChooseFrom);
  
  /**
   * Will show a dialogue and ask user where to move the pet to.
   * @param spaceNames the list of names to choose from
   * @return the user choice of pet to move to
   */
  String askUserChoosePetSpace(List<String> spaceNames);
  
  /**
   * Will show a dialogue and ask user if they really want to move to the place chosen.
   * @param spaceName the name of the space that the user picked
   * @return the integer indicating if if the user click yes or no. 
   *      the integer can be used as an enum with JOptionPane.NO_OPTION or JOptionPane.YES_OPTION
   * @throws IllegalArgumentException if name is null
   */
  int askUserMoveConfirmation(String spaceName) throws IllegalArgumentException;
  
  
  /**
   * Will show a dialogue telling the user what have gone wrong.
   * @param msg the error message if user make invalid commands
   * @throws IllegalArgumentException if name is null
   */
  void showErrorDialogue(String msg) throws IllegalArgumentException;
  
  /**
   * Key events will not work unless this frame is in focus.
   * Everytime a user click on a button, it gets the focus
   * so we need to call resetFocus() to set the focus
   * back on frame instead of on the button
   * so keyboard events can work again
   */
  void resetFocus();


  /**
   * Switch from welcome to game layout.
   */
  void switchToGameLayout();

  /**
   * Switch from game to welcome layout.
   */
  void switchToWelcomeLayout();


  /**
   * Reset the content of the view based on the new world result.
   * It will set the name of the world, 
   * the file, the game panel image, status, and action result
   * @param r the result object of the world
   * @param f features to be used.
   * @throws IllegalArgumentException if file is null
   */
  void setContent(Result r, Features f) throws IllegalArgumentException;


  /**
   * Close the whole frame window.
   */
  void closeWindow();

  /**
   * Switch from add player layout to long text panel.
   */
  void switchLayoutToLongText();
  
  /**
   * Switch from long text panel to add player panel.
   */
  void switchLayoutToAddPlayer();


}
