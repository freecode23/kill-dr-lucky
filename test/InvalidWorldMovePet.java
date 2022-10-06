

/**
 * A mock model class whose makeHumanMovePet method throws exception.
 */
public class InvalidWorldMovePet extends MockWorld {
  
  /**
   * Constructs a mock model that records the input string and output a string of code.
   * @param gameLog the log that the method will write things to (to check for input)
   * @param uuid the id of this mock model
   */
  public InvalidWorldMovePet(StringBuilder gameLog, String uuid) {
    super(gameLog, uuid);
  }
  
  @Override
  public void makeHumanMovePet(int itemIndex) throws IllegalArgumentException {
    gameLog.append(this.uuid)
            .append(" makeHumanMovePet() called")
            .append(" item index is: ")
            .append(itemIndex)
            .append(".\n");
    throw new IllegalArgumentException(" makeHumanMovePet() exception");
    
  }

}
