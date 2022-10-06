package world.controller.command;

import world.model.World;

/**
 * This class lets the controller execute quit in
 * in the game play.
 */
public class Quit implements WorldCommand {
  @Override
  public void go(World world) throws IllegalArgumentException {
    if (world == null) {
      throw new IllegalArgumentException("Cannot Quit when world is null.\n");
    }
    world.endTheGame();
  }
}

