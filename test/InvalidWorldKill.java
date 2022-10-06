

/**
 * A mock model class whose makeHumanKill method throws exception.
 */
public class InvalidWorldKill extends MockWorld {
  
  /**
   * Constructs a mock model that records the input string and output a string of code.
   * @param gameLog the log that the method will write things to (to check for input)
   * @param uuid the id of this mock model
   */
  public InvalidWorldKill(StringBuilder gameLog, String uuid) {
    super(gameLog, uuid);
  }
  
  @Override
  public void makeHumanKill(int itemIndex) throws IllegalArgumentException {
    gameLog.append(this.uuid)
    .append(" makeHumanKill() called")
    .append(" item index is: ")
    .append(itemIndex)
        .append(".\n");

    throw new IllegalArgumentException("makeHumanKill() exception");

  }

}
