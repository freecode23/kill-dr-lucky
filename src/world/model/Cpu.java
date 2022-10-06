package world.model;


/**
* This class represent a cpu that controls player by generating random move.
*/
public interface Cpu {
 
  /**
   * A getter method to get the player controlled by this CPU.
   * @return the player
   */
  Player getPlayer();

  /**
   * Will control its player to attempt to kill a target using action command pattern
   * If other player can see this cpu player, it will pick other raandom move instead.
   * @param target the target it will attempt to kill. 
   * @param pet the pet it will move if it picks move pet action.
   * @param newSpace the space it will move the pet to if it decides to move pet.
   * @return the resulting status message
   * @throws IllegalArgumentException if either target, pet, or space is null
   */
  String takeCpuTurnCommand(TargetCharacter target, Pet pet, Space newSpace) 
      throws IllegalArgumentException;
}
