package world.controller.command;

import java.awt.Point;
import javax.swing.JOptionPane;
import world.model.World;
import world.view.GameView;

/**
 * This class lets the controller execute human moving in the
 * world.
 */
public class HumanMove implements WorldCommand {
  private GameView view;
  private Point coord;

  /**
   * Contractor to create HumanMove object.
   * @param view the view object to display message to user
   * @param coord the the coordinate of the point clicked on the map
   * @throws IllegalArgumentException if view or coordinate is null
   */
  public HumanMove(GameView view, Point coord) throws IllegalArgumentException {
    if (view == null || coord == null) {
      throw new IllegalArgumentException("Cannot move human player error\n");
    }
    this.view = view;
    this.coord = coord;
  }

  @Override
  public void go(World world) throws IllegalArgumentException {
    if (world == null) {
      throw new IllegalArgumentException("Cannot move human player error\n");
    }
    
    try {
      
      world.validateGameTurn(false);
        

      // 1. ask view to confirm
      String spaceName = world.getSpaceNameByCoord(coord);
      int response = view.askUserMoveConfirmation(spaceName);

      if (response == JOptionPane.YES_OPTION) {

        // 3. make human move
        world.makeHumanMovePoint(coord);

        // 4. Update view
        view.updateMapStatusAndActionResultPanel(world.getResult());

      }

      
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.showErrorDialogue(e.getMessage());
    }
  }
}
