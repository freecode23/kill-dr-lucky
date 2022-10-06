package world.controller.command;

import java.util.List;
import world.model.World;
import world.view.GameView;

/**
 * This class triggers creation of a human player.
 */
public class AddHumanPlayer implements WorldCommand {
  private GameView view;

  /**
   * Contructor to create AddHumanPlayer object.
   * @param view the view object to display the messages
   * @throws IllegalArgumentException if view is null
   */
  public AddHumanPlayer(GameView view) throws IllegalArgumentException {
    if (view == null) {
      throw new IllegalArgumentException("Cannot add human player error\n");
    }
    this.view = view;
  }

  @Override
  public void go(World world) throws IllegalArgumentException {
    if (world == null) {
      throw new IllegalArgumentException("Cannot add human player error\n");
    }

    try {
      List<String> temp = world.getResult().getSpaceNames();

      // 1. Send view the list of space names for combo box.
      List<String> result = view.showAddPlayerInputDialog(temp);
      
      // 2. get the player name and space index
      
      if (result.size() > 0) {

        String name = result.get(0);
        int spaceIndex = Integer.valueOf(result.get(1));
        
        // 3. add the player
        world.addHumanPlayer(name, spaceIndex);
        
        // 4. show to view
        view.updateMapStatusAndActionResultPanel(world.getResult());
      }
      
      
    } catch (IllegalStateException | IllegalArgumentException e) {
      // will throw exception if game has started or player name exists
      // or max player exceeded
      view.showErrorDialogue(e.getMessage());
    }

  }
}
