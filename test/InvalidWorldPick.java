

/**
 * A mock model class whose make human pick item method throws exception.
 */
public class InvalidWorldPick extends MockWorld {

  /**
   * Constructs a mock model that records the input string and output a string of code.
   * @param gameLog the log that the method will write things to (to check for input)
   * @param uuid the id of this mock model
   */
  public InvalidWorldPick(StringBuilder gameLog, String uuid) {
    super(gameLog, uuid);
  }
  
  @Override
  public void makeHumanPickItem(int itemIndex) throws IllegalArgumentException {
    gameLog.append(this.uuid)
    .append(" makeHumanMovePoint() called")
    .append("item index is ")
    .append(itemIndex)
        .append(".\n");

    throw new IllegalArgumentException("makeHumanMovePoint() exception");
  }



}
