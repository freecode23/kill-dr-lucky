package world.controller.command;

import world.model.World;
import world.view.GameView;

/**
 * This class lets the controller execute human moving the pet
 * in the world.
 */
public class HumanMovePet implements WorldCommand {
  private GameView view;

  /**
   * Constructor to create HumanMovePet object.
   * @param view the view object to display message to user
   * @throws IllegalArgumentException if view is null
   */
  public HumanMovePet(GameView view) throws IllegalArgumentException {
    if (view == null) {
      throw new IllegalArgumentException("Cannot move a pet error\n");
    }
    this.view = view;
  }

  @Override
  public void go(World world) throws IllegalArgumentException {
    if (world == null) {
      throw new IllegalArgumentException("Cannot move a pet error\n");
    }
    
    try {
      
      world.validateGameTurn(false);
        

      // 1. ask view to choose pet space
      int spaceIndex = -1;
      String s = view.askUserChoosePetSpace(world.getResult().getSpaceNames());


      // 2. get the index of the space
      for (int i = 0; i < world.getResult().getSpaceNames().size(); i++) {
        if (s != null && s.equals(world.getResult().getSpaceNames().get(i))) {
          spaceIndex = i;
        }
      }

      // 3. make kill attempt
      if (spaceIndex != -1) {
        
        world.makeHumanMovePet(spaceIndex);
        
        // 4. Update view
        view.updateMapStatusAndActionResultPanel(world.getResult());
      }

   
      
    } catch (IllegalArgumentException | IllegalStateException e) {
      // will throw illegal state exception if its not a human turn
      view.showErrorDialogue(e.getMessage());
    }
  }
}

