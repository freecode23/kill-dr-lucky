package world.controller.command;

import world.model.World;

/**
 * This class lets the controller execute world's reset
 * to new game.
 */
public class ResetGame implements WorldCommand {

  @Override
  public void go(World world) throws IllegalArgumentException {
    if (world == null) {
      throw new IllegalArgumentException("Cannot ReSetGame when world is null.\n");
    }
    world.reset();
  }
}
