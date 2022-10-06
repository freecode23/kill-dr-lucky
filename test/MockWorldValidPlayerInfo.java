import java.awt.Point;

/**
 * A mock model class whose is point current player returns true and is current point 
 * human player neighbor returns false
 * so that the model can display player info.
 */
public class MockWorldValidPlayerInfo extends MockWorld {

  /**
   * Constructs a mock model that records the input string and output a string of code.
   * @param gameLog the log that the method will write things to (to check for input)
   * @param uuid the id of this mock model
   */
  public MockWorldValidPlayerInfo(StringBuilder gameLog, String uuid) {
    super(gameLog, uuid);
  }
  
  @Override
  public boolean isCurrentPointHumanPlayer(Point point) {
    gameLog.append(this.uuid)
            .append(" isPointCurrentHumanPlayer() called")
            .append(" point is: ")
            .append(point.x).append(" ").append(point.y)
            .append(".\n");
    return true;
  }

  @Override
  public boolean isCurrentPointHumanPlayerNeighbor(Point point) {
    gameLog.append(this.uuid)
            .append(" isPointCurrentHumanPlayerNeighbor() called")
            .append(" point index is: ")
            .append(point.x).append(" ").append(point.y)
            .append(".\n");
    return false;
  }
  
}
