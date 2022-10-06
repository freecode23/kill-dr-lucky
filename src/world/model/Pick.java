package world.model;

/**
 * Command to make a cpu player pick random item in the neighbor.
 */
public final class Pick implements PlayerCommand {
  private RandomIntGenerator ranGen;
  
  /**
   * Constructor that takes initialize the random int generator.
   * @param ranGen the random integer generator to pick random item
   * @throws IllegalArgumentException RandomIntGen is null
   */
  public Pick(RandomIntGenerator ranGen) throws IllegalArgumentException {
    if (ranGen == null) {
      throw new IllegalArgumentException(
              new StringBuilder("random integer generater cannot ")
                      .append("be null when moving a cpu player").toString());
    }
    this.ranGen = ranGen;
  }
  

  @Override
  public String go(Player player) throws IllegalArgumentException {
    if (player == null) {
      throw new IllegalArgumentException("player cannot be null when cpu is moving it");
    }
    
    StringBuilder message = new StringBuilder();
    int itemSize = player.getCurrentSpace().getItemSize();
    int randomItemIdx = ranGen.getNextInt(0, itemSize - 1);
    if (itemSize == 0) {
      throw new IllegalArgumentException(
              new StringBuilder("There is no item in here,")
                      .append("CPU should not try to pick item.")
                      .toString());
    }
    Item itemToPick = player.getCurrentSpace().getItemAt(randomItemIdx);

    if (player.pickUpItem(itemToPick)) {
      message.append("picked up ").append(itemToPick.getName());
    } else {
      message.append("failed to pick up item because it has ")
          .append("reached its max or item does not exist.");
    }
    return message.toString();
  }
  
}
