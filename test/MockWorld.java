import java.awt.Point;
import world.model.Result;
import world.model.World;

/**
 * A mock model class to test the controller is calling the 
 * appropriate method. 
 * The fields are protected so it's open to the child classes
 * to use the log and the uuid in their methods.
 */
public abstract class MockWorld implements World {
  protected final StringBuilder gameLog;
  protected final String uuid;

  /**
   * Constructs a mock model that records the input string and output a string of code.
   * @param gameLog the log that the method will write things to (to check for input)
   * @param uuid the id of this mock model
   */
  public MockWorld(StringBuilder gameLog, String uuid) {
    this.gameLog = gameLog;
    this.uuid = uuid;
  }

  @Override
  public String getSpaceInfo(int spaceId) {
    gameLog.append(this.uuid)
        .append(" getSpaceInfo() called.")
        .append(" space index is ")
        .append(spaceId)
        .append(".\n");
    return gameLog.toString();
  }

  @Override
  public void addHumanPlayer(String name, int spaceId) {
    gameLog.append(this.uuid)
            .append(" addHumanPlayer() called. name is: ")
            .append(name)
            .append(" space index is: ")
            .append(spaceId)
            .append(".\n");
  }
  
  @Override
  public void addCpuPlayer() {
    gameLog.append(this.uuid)
    .append(" addCpuPlayer() called")
        .append(".\n");
  }

  @Override
  public void makeHumanMovePoint(Point coord) {
    gameLog.append(this.uuid)
            .append(" makeHumanMovePoint() called")
            .append(" Point is: ")
            .append(coord.x).append(" ").append(coord.y)
            .append(".\n");

  }

  @Override
  public void makeHumanPickItem(int itemId) {
    gameLog.append(this.uuid)
            .append(" makeHumanPickItem() called")
            .append(" itemId is: ")
            .append(itemId)
            .append(".\n");
  }

  @Override
  public void makeHumanLook() {
    gameLog.append(this.uuid)
            .append(" makeHumanLook() called")
            .append(".\n");
  }

  @Override
  public void makeCpuTakeTurn() {
    gameLog.append(this.uuid)
            .append(" makeCpuTakeTurn() called")
            .append(".\n");
  }

  @Override
  public void generateImage() {
    gameLog.append(this.uuid)
            .append(" generateImage() called")
            .append(".\n");
  }

  @Override
  public boolean isGameOver() {
    gameLog.append(this.uuid)
            .append(" isGameOver() called")
            .append(".\n");
    return false;
  }

  @Override
  public void makeHumanMovePet(int spaceId) {
    spaceId = 1;
    gameLog.append(this.uuid)
            .append(" makeHumanMovePet() called")
            .append(" space index is: ")
            .append(spaceId)
            .append(".\n");
  }

  @Override
  public void makeHumanKill(int itemId) {
    gameLog.append(this.uuid)
            .append(" makeHumanKill() called")
            .append(" item index is: ")
            .append(itemId)
            .append(".\n");
  }

  @Override
  public Result getResult() {
    gameLog.append(this.uuid)
            .append(" getResult() called")
            .append(".\n");

    Result mockResult = new MockResult(this.gameLog, this.uuid);
    return mockResult;
  }

  @Override
  public boolean isCurrentPointHumanPlayer(Point point) {
    gameLog.append(this.uuid)
            .append(" isPointCurrentHumanPlayer() called")
            .append(" point is: ")
            .append(point.x).append(" ").append(point.y)
            .append(".\n");
    return false;
  }

  @Override
  public boolean isCurrentPointHumanPlayerNeighbor(Point point) {
    gameLog.append(this.uuid)
            .append(" isPointCurrentHumanPlayerNeighbor() called")
            .append(" point index is: ")
            .append(point.x).append(" ").append(point.y)
            .append(".\n");
    return true;
  }

  @Override
  public void startTheGame() {
    gameLog.append(this.uuid)
            .append(" startTheGame() called")
            .append(".\n");
  }

  @Override
  public void reset() {
    gameLog.append(this.uuid)
            .append(" reset() called")
            .append(".\n");
  }

  @Override
  public void reloadNewGame(Readable file) {
    gameLog.append(this.uuid)
            .append(" reloadNewGame() called")
            .append(".\n");
  }

  @Override
  public void endTheGame() {
    gameLog.append(this.uuid)
            .append(" endTheGame() called")
            .append(".\n");
  }

  @Override
  public String getSpaceNameByCoord(Point coord) {
    gameLog.append(this.uuid)
            .append(" getSpaceNameByCoord() called")
            .append(" Point ::")
            .append(coord.x).append(" ").append(coord.y)
            .append(".\n");
    return gameLog.toString();
  }

  @Override
  public boolean isCurrentTurnCpu() {
    gameLog.append(this.uuid)
            .append(" isCurrentTurnCpu() called")
            .append(".\n");
    return false;
  }

  @Override
  public boolean hasGameStarted() {
    gameLog.append(this.uuid)
            .append(" hasGameStarted() called")
            .append(".\n");
    return true;
  }

  @Override
  public void validateGameTurn(boolean isCpuCall) {
    gameLog.append(this.uuid)
            .append(" validateGameTurn() called\n")
            .append("isCpuCall :: ")
            .append(isCpuCall)
            .append(".\n");
  }

  @Override
  public void currentSpaceShouldHaveItems() throws IllegalStateException {
    gameLog.append(this.uuid)
    .append(" currentSpaceShouldHaveItems() called")
        .append(".\n");
    
  }
}
