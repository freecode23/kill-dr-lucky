package world.model;


/**
 * Command to make a cpu player look.
 */
public final class Look implements PlayerCommand {
  
  
  @Override
  public String go(Player player) throws IllegalArgumentException {
    if (player == null) {
      throw new IllegalArgumentException("player cannot be null when cpu is moving it");
    }
    
    StringBuilder message = new StringBuilder();
    String spaceInfo = player.lookAround(); // return the space info
    if (!"".equals(spaceInfo) || spaceInfo != null) {
      message.append("looked around.");
    } 
  
    return message.toString();
  }
  
}
