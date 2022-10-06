

/**
 * A mock model class whose makeHumanLook method throws exception.
 */
public class InvalidWorldLook extends MockWorld {
  
  /**
   * Constructs a mock model that records the input string and output a string of code.
   * @param gameLog the log that the method will write things to (to check for input)
   * @param uuid the id of this mock model
   */
  public InvalidWorldLook(StringBuilder gameLog, String uuid) {
    super(gameLog, uuid);
  }
  
  @Override
  public void makeHumanLook() {
    gameLog.append(this.uuid)
            .append(" makeHumanLook() called")
            .append(".\n");

    throw new IllegalStateException("makeHumanLook() exception");
  }


}
