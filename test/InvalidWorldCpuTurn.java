

/**
 * A mock model class whose make cpu turn method throws exception.
 */
public class InvalidWorldCpuTurn extends MockWorld {

  /**
   * Constructs a mock model that records the input string and output a string of code.
   * @param gameLog the log that the method will write things to (to check for input)
   * @param uuid the id of this mock model
   */
  public InvalidWorldCpuTurn(StringBuilder gameLog, String uuid) {
    super(gameLog, uuid);
  }
  
  @Override
  public void makeCpuTakeTurn() {
    gameLog.append(this.uuid)
            .append(" makeCpuTakeTurn() called")
            .append(".\n");
    throw new IllegalStateException("makeCpuTakeTurn() exception");
  }

}
