package world.model;

/**
 * Command to move a player controlled by cpu.
 */
public final class Move implements PlayerCommand {
  private RandomIntGenerator ranGen;
  
  /**
   * Command to move a player controlled by cpu.
   * @param ranGen2 the random integer generator to pick random neighboring space.
   * @throws IllegalArgumentException RandomIntGen is null
   */
  public Move(RandomIntGenerator ranGen2) throws IllegalArgumentException {
    if (ranGen2 == null) {
      throw new IllegalArgumentException(
              new StringBuilder("random integer generater cannot ")
                      .append("be null when moving a cpu player").toString());
    }
    this.ranGen = ranGen2;
  }
  
  @Override
  public String go(Player player) throws IllegalArgumentException {
    if (player == null) {
      throw new IllegalArgumentException("player cannot be null when cpu is moving it");
    }
    
    StringBuilder message = new StringBuilder();
    int neighborSize = player.getCurrentSpace().getNeighborSize();
    int randomNeighborIdx = ranGen.getNextInt(0, neighborSize - 1);
    Space moveToSpace = player.getCurrentSpace().getNeighborAt(randomNeighborIdx);
    
    if (player.moveToNeighbor(moveToSpace)) {
      message.append("moved to ").append(moveToSpace.getName());
    } else {
      message.append("failed to move to neighbor.");
    }
  
    return message.toString();
  }
  
}

