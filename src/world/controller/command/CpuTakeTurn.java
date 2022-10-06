package world.controller.command;

import world.model.World;
import world.view.GameView;

/**
 * This class lets the controller execute taking turn by the CPU player
 * world model.
 */
public class CpuTakeTurn implements WorldCommand {
  private GameView view;
  
  /**
   * Contructor to create AddCpuPlayer using view.
   * @param view the view object to display the messages
   * @throws IllegalArgumentException if view is null
   */
  public CpuTakeTurn(GameView view) throws IllegalArgumentException {
    if (view == null) {
      throw new IllegalArgumentException("Cannot make cpu take turn error\n");
    }
    this.view = view;
  }
  
  @Override
  public void go(World world) throws IllegalArgumentException {
    if (world == null) {
      throw new IllegalArgumentException("Cannot make cpu take turn error\n");
    }
    
    try {
      
      world.validateGameTurn(true);
      world.makeCpuTakeTurn();
      view.updateMapStatusAndActionResultPanel(world.getResult());
      
    } catch (IllegalStateException | IllegalArgumentException e) {
      view.showErrorDialogue(e.getMessage());
    }
  }
}
