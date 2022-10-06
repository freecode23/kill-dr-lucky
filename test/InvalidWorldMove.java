
import java.awt.Point;

/**
 * A mock model class whose makeHumanMovePoint method throws exception.
 */
public class InvalidWorldMove extends MockWorld {
  
  /**
   * Constructs a mock model that records the input string and output a string of code.
   * @param gameLog the log that the method will write things to (to check for input)
   * @param uuid the id of this mock model
   */
  public InvalidWorldMove(StringBuilder gameLog, String uuid) {
    super(gameLog, uuid);
  }
  
  @Override
  public void makeHumanMovePoint(Point coord) throws IllegalArgumentException {
    gameLog.append(this.uuid)
            .append(" makeHumanMovePoint() called")
            .append(" Point is: ")
            .append(coord)
            .append(".\n");

    throw new IllegalArgumentException(" makeHumanMovePoint() exception");
  }

}
