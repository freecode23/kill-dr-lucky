package world.controller.command;

import java.util.List;
import world.model.World;
import world.view.GameView;

/**
 * This class lets the controller execute human picking an item
 * in the world.
 */
public class HumanPickItem implements WorldCommand {
  private GameView view;

  /**
   * Contructor to create HumanPickItem object.
   * @param view the view object to display message to user
   * @throws IllegalArgumentException if view is null
   */
  public HumanPickItem(GameView view) throws IllegalArgumentException {
    if (view == null) {
      throw new IllegalArgumentException("Cannot pick item error\n");
    }
    this.view = view;
  }

  @Override
  public void go(World world) throws IllegalArgumentException {
    if (world == null) {
      throw new IllegalArgumentException("Cannot pick item error\n");
    }
    try {
      
      world.validateGameTurn(false);
      world.currentSpaceShouldHaveItems();
        
      List<String> itemNames = world.getResult().getCurentPlayerSpaceItemNames();
      

      // 1. ask view to choose item
      String s = view.askUserChooseItem(
          "Pick item from space:", itemNames);

      // if user click close window
      if (s == null || "".equals(s)) {
        return;
      }

      // 2. get the index of the item
      String[] words = s.split("\\.");
      int itemIndex = Integer.valueOf(words[0]);

      // 3. make human pick item
      world.makeHumanPickItem(itemIndex);

      // 4. Update view
      view.updateMapStatusAndActionResultPanel(world.getResult());

    } catch (IllegalArgumentException | IllegalStateException e) {
      view.showErrorDialogue(e.getMessage());
    }
  }
}
