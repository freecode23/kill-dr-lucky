package world.model;


/**
 * Interface for the various commands that will be executed by Cpu to control players.
 */
public interface PlayerCommand {
  /**
   * Starting point for the controller.
   * @param player the player who will execute the command
   * @return the resulting string of the status after execution.
   * @throws IllegalArgumentException if player is null
   */
  String go(Player player) throws IllegalArgumentException;
}

