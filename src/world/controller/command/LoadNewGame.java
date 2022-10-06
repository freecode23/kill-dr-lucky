package world.controller.command;

import world.model.World;

/**
 * This class lets the controller executes loading a new game.
 */
public class LoadNewGame implements WorldCommand {
  private Readable file;

  /**
   * Constructor to create LoadNewGame object.
   * @param file to be displayed.
   * @throws IllegalArgumentException when there is any exception loading
   *        a new game.
   */
  public LoadNewGame(Readable file) throws IllegalArgumentException {
    if (file == null) {
      throw new IllegalArgumentException("Cannot Load new game when file is null.\n");
    }
    this.file = file;
  }

  @Override
  public void go(World world) {
    if (world == null) {
      throw new IllegalArgumentException("Cannot Load new game when world is null.\n");
    }
    world.reloadNewGame(this.file);
  }
}
