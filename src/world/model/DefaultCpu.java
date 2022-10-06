package world.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

/**
* The default implementation of Cpu class that controls the player in a game.
* Two DefaultCpu is the same if they control the same player.
*/
public final class DefaultCpu implements Cpu {
  private final Player cpuPlayer;
  private final RandomIntGenerator ranGen;
  private final String alphabet;
  
  /**
   * Construct a CPU controller object by
   * assigning the player it will control.
   * @param ranGen2 the random number generator to generate random actions
   * @param space the space it will occupy
   * @throws IllegalArgumentException if player is null.
   *
   */
  public DefaultCpu(RandomIntGenerator ranGen2, Space space) 
      throws IllegalArgumentException {
    if (ranGen2 == null || space == null) {
      throw new IllegalArgumentException(
              new StringBuilder("Space and random number generator cannot be ")
                      .append("null when intantiating CPU controler object ")
                      .toString());
    }
    this.ranGen = ranGen2;
    this.alphabet = "abcdefghijklmnopqrstuvwxyz";
    
    // generateRandom player
    int maxItem = ranGen2.getNextInt(1, 5);
    String name = generateRandomCpuName();
    
    this.cpuPlayer = new DefaultPlayer(name, space, maxItem);
  }
  
  private String generateRandomCpuName() {
    StringBuilder s = new StringBuilder();
    s.append("CPU_");
    
    // create random string of 5 characters
    while (s.length() < 9) {
      char c = this.alphabet.charAt(ranGen.getNextInt(0, alphabet.length() - 1));
      s.append(c);
    }
    return s.toString();
  }
  
  /**
   * A method to generate random action integer using random number generator.
   * if item is present in the current player's space it will also include picking item
   * otherwise it will exclude it. 
   * @return the random action in in integer
   */
  private int generateRandomActionInt() {
    boolean isItemPresent = true;
    if (this.cpuPlayer.getCurrentSpace().getItemSize() == 0) {
      isItemPresent = false;
    }

    int excludeId = 2; // exclude the last
    if (isItemPresent) {
      excludeId = 1; // exclude nothing
    }

    // maximum 4 action including picking item
    // because 5th action is kill (a must)
    int randomActionIdx = ranGen.getNextInt(0, 4 - excludeId); 
    return randomActionIdx;
  }
  
  @Override
  public Player getPlayer() {
    return this.cpuPlayer;
  }
    
  @Override
  public String takeCpuTurnCommand(TargetCharacter target, Pet pet, Space newSpace) 
      throws IllegalArgumentException {
    if (target == null || pet == null || newSpace == null) {
      throw new IllegalArgumentException(
              new StringBuilder("Pet, target, or space cannot ")
                      .append("be null when cpu is taking turn.")
                      .toString());
    }
    
    // 1. create message
    StringBuilder message = new StringBuilder();
    message.append(cpuPlayer.getName()).append(" have ");
    
    // 2. create list of command
    Map<Integer, PlayerCommand> knownCommands = new HashMap<>();
    knownCommands.put(0, new Move(this.ranGen));
    knownCommands.put(1, new Look());
    knownCommands.put(2, new MovePet(pet, newSpace));
    knownCommands.put(3, new Pick(this.ranGen));
    knownCommands.put(4, new Kill(target));

    // 2. Always attempt kill first if it thinks it cannot be seen
    // and it's on the same space as target
    boolean sameSpaceAsTarget = target.getCurrentSpaceId()
        == this.cpuPlayer.getCurrentSpace().getId();
    
    Integer actionIdx;
    if (cpuPlayer.thinksCannotBeSeen() && sameSpaceAsTarget) {
      actionIdx = 4; // kill
    } else {
      // 3. get random integer command
      actionIdx = generateRandomActionInt();
    }
    
    // 4. match command
    PlayerCommand cmdToExecute = knownCommands.getOrDefault(actionIdx, null);
    
    // 5.add command to stack and execute
    Stack<PlayerCommand> commandStack = new Stack<>();
    commandStack.add(cmdToExecute); 
    message.append(cmdToExecute.go(this.cpuPlayer));
    return message.toString();
  }

  @Override 
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append("This controller is controlling: ").append(this.cpuPlayer.toString());
    return s.toString();
  }
  
  @Override
  public boolean equals(Object o) {
    // Fast path for pointer equality:
    if (this == o) { // backward compatibility with default equals
      return true;
    }
    // If o isn't the right class then it can't be equal:
    if (!(o instanceof Cpu)) {
      return false;
    }
    // The successful instance of check means our down casting will succeed:
    Cpu that = (Cpu) o;
    return (this.cpuPlayer.equals(that.getPlayer()));
  }
  
  @Override 
  public int hashCode() {
    return Objects.hash(this.cpuPlayer);
  }
}
