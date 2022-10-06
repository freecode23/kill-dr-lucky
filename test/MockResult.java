import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import world.model.Result;

/**
 * This class is a mock for Results object
 * used in mock world's test.
 */
public class MockResult implements Result {
  private final StringBuilder gameLog;
  private final String uuid;
  
  /**
   * Constructor that takes in gameLog to accumulate string
   * and also takes a unique id from the test cases.
   * @param gameLog to store which functions are triggered.
   * @param uuid of the mock being used.
   */
  public MockResult(StringBuilder gameLog, String uuid) {
    this.gameLog = gameLog;
    this.uuid = "RESULT-VALID";    

  }


  @Override
  public String getName() {
    return "name ABC";
  }

  @Override
  public String getStatus() {
    return "status ABC";
  }

  @Override
  public void setStatus(String text) throws IllegalArgumentException {
    gameLog.append(" setStatus() called")
          .append(".\n");
  }

  @Override
  public String getActionResult() {
    return "action result ABC";
  }

  @Override
  public void setActionResult(String text) {
    gameLog.append(" setActionResult() called")
        .append(".\n");
  }

  @Override
  public void setEveryTurnSpaceInfo(String text) {
    gameLog.append(" setEveryTurnSpaceInfo() called")
        .append(".\n");
  }

  @Override
  public String getEveryTurnSpaceInfo() {
    return "every turn space info abc";
  }

  @Override
  public void setLookAroundText(String text) {
    gameLog.append(" setLookAroundText() called")
        .append(".\n");

  }

  @Override
  public String getLookAroundText() {
    return "get look around abc";
  }

  @Override
  public String getPlayerDescription() {
    return "get player description abc";
  }

  @Override
  public void setPlayerDesciption(String text) {
    gameLog.append(text)
    .append("  setPlayerDesciptions() called")
        .append(".\n");
  }

  @Override
  public void setBufferedImage(BufferedImage img) {
    gameLog.append(" setBufferedImage() called")
        .append(".\n");
  }

  @Override
  public BufferedImage getBufferedImage() {
    return null;
  }

  @Override
  public List<String> getSpaceNames() {

    List<String> temp = new ArrayList<>();
    temp.add("some space name");
    return temp;
  }

  @Override
  public void setCurentPlayerItemNames(List<String> curentPlayerItemNames) {
    gameLog.append(" setCurrentPlayerItemName() called")
        .append(".\n");
  }
  

  @Override
  public List<String> getCurentPlayerItemNames() {
    List<String> temp = new ArrayList<>();
    temp.add("1.item");
    return temp;
  }

  @Override
  public void setCurentPlayerSpaceItemNames(List<String> curentPlayerSpaceItemNames) {
    gameLog.append(" setCurrentPlayerSpaceItemName() called")
        .append(".\n");
  }
  
  @Override
  public List<String> getCurentPlayerSpaceItemNames() {
    List<String> temp = new ArrayList<>();
    temp.add("1.item");
    return temp;
  }
  
  @Override
  public String toString() {
    return this.uuid;
  }
  

  
}