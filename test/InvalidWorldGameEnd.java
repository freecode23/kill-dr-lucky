

/**
 * A mock model class whose start game and reset game
 * and reset method throws exception.
 */
public class InvalidWorldGameEnd extends MockWorld {

  /**
   * Constructs a mock model that records the input string and output a string of code.
   * @param gameLog the log that the method will write things to (to check for input)
   * @param uuid the id of this mock model
   */
  public InvalidWorldGameEnd(StringBuilder gameLog, String uuid) {
    super(gameLog, uuid);
  }
  
  @Override
  public void reset() {
    gameLog.append(this.uuid)
            .append(" reset() called")
            .append(".\n");
    throw new IllegalStateException("reset() exception");
  }

  
  @Override
  public void reloadNewGame(Readable file) {
    gameLog.append(this.uuid)
            .append(" reloadNewGame() called")
            .append(".\n");
    throw new IllegalStateException("reloadNewGame() exception");
  }

  
  @Override
  public void endTheGame() {
    gameLog.append(this.uuid)
    .append(" endTheGame() called")
        .append(".\n");
    throw new IllegalStateException("endTheGame() exception");
  }
}
