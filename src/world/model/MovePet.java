package world.model;

/**
 * Command for player controlled by a cpu to move a pet. 
 */
public final class MovePet implements PlayerCommand {
  private Pet pet;
  private Space newSpace;
  
  
  /**
   * Constructor to move a player controlled by cpu.
   * @param pet the random integer generator to pick random neighboring space.
   * @param newSpace the new space pet is moving to.
   * @throws IllegalArgumentException if pet or space is null
   */
  public MovePet(Pet pet, Space newSpace) throws IllegalArgumentException {
    if (pet == null || newSpace == null) {
      throw new IllegalArgumentException("player cannot be null when cpu is moving it");
    }
    this.pet = pet;
    this.newSpace = newSpace;
  }
  
  @Override
  public String go(Player player) throws IllegalArgumentException {
    if (player == null) {
      throw new IllegalArgumentException("player cannot be null when cpu is moving it");
    }
    
    StringBuilder message = new StringBuilder();
    String oldSpace = pet.getCurrentSpace().getName();
    player.movePet(pet, newSpace);
    message.append("succesfuly moved ")
      .append(pet.getName())
      .append(" from ")
      .append(oldSpace)
          .append(" to ").append(newSpace.getName());
 
    return message.toString();
  }
  
}
