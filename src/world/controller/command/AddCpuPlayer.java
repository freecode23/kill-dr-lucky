package world.controller.command;

import world.model.World;
import world.view.GameView;

/**
 * This class lets the controller execute add player in the
 * world model.
 */
public class AddCpuPlayer implements WorldCommand {
  private GameView view;
  
  /**
   * Contructor to create AddCpuPlayer using view.
   * @param view the view object to display the messages
   * @throws IllegalArgumentException if view is null
   */
  public AddCpuPlayer(GameView view) throws IllegalArgumentException {
    if (view == null) {
      throw new IllegalArgumentException("Cannot add cpu player error\n");
    }
    this.view = view;
  }

  @Override
  public void go(World world) throws IllegalArgumentException {
    if (world == null) {
      throw new IllegalArgumentException("Cannot add cpu player error\n");
    }
    
    try {
      world.addCpuPlayer();
      view.updateMapStatusAndActionResultPanel(world.getResult());

    } catch (IllegalStateException e) {
      // will throw exception if game has started 
      // or max player exceeded
      view.showErrorDialogue(e.getMessage());
    }
      
  }
}

