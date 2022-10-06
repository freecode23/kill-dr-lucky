

/**
 * A mock model class whose add cpu method throws exception.
 */
public class InvalidWorldAddCpu extends MockWorld {

  /**
   * Constructs a mock model that records the input string and output a string of code.
   * @param gameLog the log that the method will write things to (to check for input)
   * @param uuid the id of this mock model
   */
  public InvalidWorldAddCpu(StringBuilder gameLog, String uuid) {
    super(gameLog, uuid);
  }
  
  @Override
  public void addCpuPlayer() {
    gameLog.append(this.uuid)
    .append(" addCpuPlayer() called")
        .append(".\n");
    throw new IllegalStateException(" addCpuPlayer() exception");
  } 

}
