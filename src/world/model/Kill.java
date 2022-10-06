package world.model;

/**
 * Command for player controlled to kill a target.
 */
public final class Kill implements PlayerCommand {
  private TargetCharacter target;
  
  
  /**
   * Constructor to move a player controlled by cpu.
   * @param target the target character to kill
   * @throws IllegalArgumentException if target is null
   */
  public Kill(TargetCharacter target) throws IllegalArgumentException {
    if (target == null) {
      throw new IllegalArgumentException("target cannot be null when trying to kill it");
    }
    this.target = target;
  }
  
  @Override
  public String go(Player player) throws IllegalArgumentException {
    if (player == null) {
      throw new IllegalArgumentException("player cannot be null when cpu is moving it");
    }
    
    StringBuilder message = new StringBuilder();
    // get weapon index
    int itemIdx = player.getItemIdWithMostDamage();
    String itemNameMostDamage = player.getItemNameById(itemIdx);
    if (player.attemptToKill(target, itemIdx)) { // if attempt is successful
      
      message.append("successfuly hurt ")
      .append(target.getName())
      .append(" with ")
          .append(itemNameMostDamage);
      
      if (target.getHealth() > 0) {
        message.append(". ")
                .append(target.getName())
                .append("'s health is now ")
                .append(target.getHealth())
                .append("\n");
      } else {
        message.append(". ").append(target.getName()).append(" is dead.\n");
      }


    } else { // failed attempt
      message.append("attempted to kill ")
        .append(target.getName())
          .append(" but failed because it can be seen\n")
          .append(". Target's health is still ")
          .append(target.getHealth()).append("\n");
    }
    
    return message.toString();
  }
}
