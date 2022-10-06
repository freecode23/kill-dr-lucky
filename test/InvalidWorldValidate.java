

/**
 * A mock model class whose validate game turn method throws exception.
 */
public class InvalidWorldValidate extends MockWorld {

  /**
   * Constructs a mock model that records the input string and output a string of code.
   * @param gameLog the log that the method will write things to (to check for input)
   * @param uuid the id of this mock model
   */
  public InvalidWorldValidate(StringBuilder gameLog, String uuid) {
    super(gameLog, uuid);
  }
  
  @Override
  public void validateGameTurn(boolean isCpuCall) {
    gameLog.append(this.uuid)
    .append(" validateGameTurn() called")
        .append(".\n");
    throw new IllegalStateException(" validategGameTurn() exception");
  } 

}
