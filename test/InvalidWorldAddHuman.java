

/**
 * A mock model class whose add human method throws exception.
 */
public class InvalidWorldAddHuman extends MockWorld {

  /**
   * Constructs a mock model that records the input string and output a string of code.
   * @param gameLog the log that the method will write things to (to check for input)
   * @param uuid the id of this mock model
   */
  public InvalidWorldAddHuman(StringBuilder gameLog, String uuid) {
    super(gameLog, uuid);
  }
  
  @Override
  public void addHumanPlayer(String name, int spaceIndex) throws IllegalArgumentException {
    gameLog.append(this.uuid)
            .append(" addHumanPlayer() called. name is: ")
            .append(name)
            .append(" space index is: ")
            .append(spaceIndex)
            .append(".\n");
   
    throw new IllegalArgumentException("addHumanPlayer() exception");
    
  }

}
