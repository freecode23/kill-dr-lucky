import world.model.Result;

/**
 * A mock model class whose getResult method throws exception.
 */
public class InvalidWorldGetResult extends MockWorld {

  /**
   * Constructs a mock model that records the input string and output a string of code.
   * @param gameLog the log that the method will write things to (to check for input)
   * @param uuid the id of this mock model
   */
  public InvalidWorldGetResult(StringBuilder gameLog, String uuid) {
    super(gameLog, uuid);
  }
  
  @Override
  public Result getResult() {
    gameLog.append(this.uuid)
            .append(" getResult() called")
            .append(".\n");
    throw new IllegalStateException("getResult() exception");
  }

}
