package world.controller.command;

import world.model.World;
import world.view.GameView;

/**
 * This class lets the controller execute human's looking around feature.
 */
public class HumanLook implements WorldCommand {
  private GameView view;
  
  /**
   * Constructor to create HumanLook object that uses the view.
   * @param view the view object to display message to user
   * @throws IllegalArgumentException if view is null
   */
  public HumanLook(GameView view) throws IllegalArgumentException {
    if (view == null) {
      throw new IllegalArgumentException("Cannot let player do look around error\n");
    }
    this.view = view;
  }

  @Override
  public void go(World world) throws IllegalArgumentException {
    if (world == null) {
      throw new IllegalArgumentException("Cannot let player do look around error\n");
    }
    
    try {
      world.validateGameTurn(false);
        
      // 1. take turn
      world.makeHumanLook();

      // 2. show look around dialogue
      view.updateLookAround(world.getResult());

      // Don't need to update panel, it will be handled by the listener

    
    } catch (IllegalArgumentException | IllegalStateException e) {
      // will throw illegal state exception if its not a human turn
      view.showErrorDialogue(e.getMessage());
    }
  }
}
