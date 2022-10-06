package world.controller.command;

import world.model.World;
import world.view.GameView;

/**
 * This class lets the controller execute human's kill attempt on the
 * target.
 */
public class HumanKillAttempt implements WorldCommand {
  private GameView view;

  /**
   * Constructor to create HumanKillAttempt object.
   * @param view the view object to display message to user
   * @throws IllegalArgumentException if view is null
   */
  public HumanKillAttempt(GameView view) throws IllegalArgumentException {
    if (view == null) {
      throw new IllegalArgumentException("Cannot attempt a kill error\n");
    }
    this.view = view;
  }

  @Override
  public void go(World world) throws IllegalArgumentException {
    if (world == null) {
      throw new IllegalArgumentException("Cannot attempt a kill error\n");
    }
    
    try {
      
      world.validateGameTurn(false);

      // 1. get view to ask user choose item,
      String s = view.askUserChooseItem(
          "Pick item to kill:", world.getResult().getCurentPlayerItemNames());

      if (s != null) {
        
        // 2. get the index of the item
        String[] words = s.split("\\.");
        int itemIndex = Integer.valueOf(words[0]);
        
        // 3. make kill attempt
        world.makeHumanKill(itemIndex);
        
        // 4. Update view
        view.updateMapStatusAndActionResultPanel(world.getResult());
      }


      
    } catch (IllegalArgumentException | IllegalStateException e) {
      // will throw illegal state exception if its not a human turn
      view.showErrorDialogue(e.getMessage());
    }
  }
}

