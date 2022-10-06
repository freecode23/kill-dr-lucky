
/**
 * A mock model class whose start game method throws an exception.
 */
public class InvalidWorldStart extends MockWorld {

  /**
   * Constructs a mock model that records the input string and output a string of code.
   * @param gameLog the log that the method will write things to (to check for input)
   * @param uuid the id of this mock model
   */
  public InvalidWorldStart(StringBuilder gameLog, String uuid) {
    super(gameLog, uuid);
  }
  
  @Override
  public void startTheGame() {
    gameLog.append(this.uuid)
            .append(" getResult() called")
            .append(".\n");
    throw new IllegalStateException("getResult() exception");
  }

}
