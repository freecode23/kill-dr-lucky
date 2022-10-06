import java.util.ArrayList;
import java.util.List;
import world.controller.Features;
import world.model.Result;
import world.view.GameView;

/**
 * A mock view abstract class
 * to test the controller is calling the
 * appropriate method.
 * The fields are protected so that its sub classes can use it 
 * to also make log 
 */
public abstract class MockView implements GameView {
  protected final StringBuilder gameLog;
  protected final String uuid;

  /**
   * The constructor that takes in a string builder to log function calls.
   * @param gameLog to store which functions are triggered.
   * @param uuid of the mock being used.
   */
  public MockView(StringBuilder gameLog, String uuid) {
    this.gameLog = gameLog;
    this.uuid = uuid;
  }

  @Override
  public void setFeatures(Features f) throws IllegalArgumentException {
    gameLog.append(this.uuid)
            .append(" setFeatures() called")
            .append(".\n");
          
  }

  @Override
  public void updateMapStatusAndActionResultPanel(Result r) throws IllegalArgumentException {
    gameLog.append(this.uuid)
            .append(" updateMapStatusAndActionResultPanel() called. Result is ")
            .append(r.toString())
            .append(".\n");
  }

  @Override
  public void updateLookAround(Result r) throws IllegalArgumentException {
    gameLog.append(this.uuid)
            .append(" updateLookAround called. Result is ")
            .append(r.toString())
            .append(".\n");
  }

  @Override
  public void showPlayerInfo(String playerInfo) throws IllegalArgumentException {
    gameLog.append(this.uuid)
            .append(" showPlayerInfo called")
            .append(".\n");
  }

  @Override
  public List<String> showAddPlayerInputDialog(List<String> spaceNames) {
    gameLog.append(this.uuid)
            .append(" showAddPlayerInputDialog called")
            .append(".\n");
    List<String> temp = new ArrayList<>();
    temp.add("ABC");
    temp.add("1");
    return temp;
  }

  @Override
  public String askUserChooseItem(String msg, List<String> itemsToChooseFrom) {
    gameLog.append(this.uuid)
            .append(" askUserChooseItem called")
            .append(".\n");
    return "1.item";
  }

  @Override
  public String askUserChoosePetSpace(List<String> spaceNames) {
    spaceNames = new ArrayList<>();
    spaceNames.add("some space name");
    gameLog.append(this.uuid)
            .append(" askUserChoosePetSpace called")
            .append(" spaceNames :: ")
            .append(spaceNames.get(0))
            .append(".\n");
    return "some space name";
  }

  @Override
  public int askUserMoveConfirmation(String spaceName) throws IllegalArgumentException {
    gameLog.append(this.uuid)
            .append(" askUserMoveConfirmation called")
            .append(" spaceName :: ")
            .append(spaceName)
            .append(".\n");
    return 0;
  }

  @Override
  public void showErrorDialogue(String msg) throws IllegalArgumentException {
    gameLog.append(this.uuid)
            .append(" showErrorDialogue called")
            .append(" msg :: ")
            .append(msg)
            .append(".\n");
  }

  @Override
  public void resetFocus() {
    gameLog.append(this.uuid)
            .append(" resetFocus called")
            .append(".\n");
  }

  @Override
  public void switchToGameLayout() {
    gameLog.append(this.uuid)
            .append(" switchToGameLayout called")
            .append(".\n");
  }

  @Override
  public void switchToWelcomeLayout() {
    gameLog.append(this.uuid)
            .append(" switchToWelcomeLayout called")
            .append(".\n");
  }

  @Override
  public void setContent(Result r, Features f) throws IllegalArgumentException {
    gameLog.append(this.uuid)
            .append(" setContent called. Result is ")
            .append(r.toString())
            .append(".\n");
  }

  @Override
  public void closeWindow() {
    gameLog.append(this.uuid)
            .append(" closeWindow called")
            .append(".\n");
  }

  @Override
  public void switchLayoutToLongText() {
    gameLog.append(this.uuid)
            .append(" switchLayoutToLongText called")
            .append(".\n");
  }

  @Override
  public void switchLayoutToAddPlayer() {
    gameLog.append(this.uuid)
            .append(" switchLayoutToAddPlayer called")
            .append(".\n");
  }
  

}
