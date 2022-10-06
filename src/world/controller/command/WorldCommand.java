package world.controller.command;

import world.model.World;

/**
 * This interface lets the controller execute specific commands
 * on the model.
 */
public interface WorldCommand {

  /**
   * This API lets a command execute uniformly by having a common
   * method.
   * @param world model to work on.
   * @throws IllegalArgumentException if any command fails.
   */
  void go(World world) throws IllegalArgumentException;
}
