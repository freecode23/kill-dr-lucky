package world.controller.command;

import world.model.World;
import world.view.GameView;

/**
 * This class lets the controller execute display player's information
 * form the world model.
 */
public class DisplayPlayerInfo implements WorldCommand {
  private GameView view;

  /**
   * Contractor to create DisplayPlayerInfo object.
   * @param view to be displayed.
   * @throws IllegalArgumentException when there is any exception on displaying
   *        player.
   */
  public DisplayPlayerInfo(GameView view) throws IllegalArgumentException {
    if (view == null) {
      throw new IllegalArgumentException("Cannot display player info error\n");
    }
    this.view = view;
  }

  @Override
  public void go(World world) throws IllegalArgumentException {
    if (world == null) {
      throw new IllegalArgumentException("Cannot display player info error\n");
    }

    try {

      world.validateGameTurn(false);
      // show the player info dialogue
      String playerInfo = world.getResult().getPlayerDescription();
      view.showPlayerInfo(playerInfo);

    } catch (IllegalStateException e) {
      view.showErrorDialogue(e.getMessage());

    }
  }
}
