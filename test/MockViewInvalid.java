import java.util.List;
import world.controller.Features;
import world.model.Result;

/**
 * A mock view class which always throws exception
 * to test the controller is calling the
 * appropriate method.
 */
public class MockViewInvalid extends MockView {


  /**
   * Constructor to create MockViewInvalid.
   * @param gameLog to store which functions are triggered.
   * @param uuid of the mock being used.
   */
  public MockViewInvalid(StringBuilder gameLog, String uuid) {
    super(gameLog, uuid);
  }

  @Override
  public void setFeatures(Features f) throws IllegalArgumentException {
    gameLog.append(this.uuid)
            .append(" setFeatures() called")
            .append(".\n");
    throw new IllegalStateException("Cannot setFeatures.");
  }

  @Override
  public void updateMapStatusAndActionResultPanel(Result r) throws IllegalArgumentException {
    gameLog.append(this.uuid)
            .append(" updateMapStatusAndActionResultPanel() called")
            .append(".\n");
    throw new IllegalStateException("Cannot updateMapStatusAndActionResultPanel.");
  }

  @Override
  public void updateLookAround(Result r) throws IllegalArgumentException {
    gameLog.append(this.uuid)
            .append(" updateLookAround called")
            .append(".\n");
    throw new IllegalStateException("Cannot updateLookAround.");

  }

  @Override
  public void showPlayerInfo(String playerInfo) throws IllegalArgumentException {
    gameLog.append(this.uuid)
            .append(" showPlayerInfo called")
            .append(".\n");
    throw new IllegalStateException("Cannot showPlayerInfo.");
  }

  @Override
  public List<String> showAddPlayerInputDialog(List<String> spaceNames) {
    gameLog.append(this.uuid)
            .append(" showAddPlayerInputDialog called")
            .append(".\n");
    throw new IllegalStateException("Cannot showAddPlayerInputDialog.");
  }

  @Override
  public String askUserChooseItem(String msg, List<String> itemsToChooseFrom) {
    gameLog.append(this.uuid)
            .append(" askUserChooseItem called")
            .append(".\n");
    throw new IllegalStateException("Cannot askUserChooseItem.");
  }

  @Override
  public String askUserChoosePetSpace(List<String> spaceNames) {
    gameLog.append(this.uuid)
            .append(" askUserChooseItem called")
            .append(" spaceNames :: ")
            .append(spaceNames)
            .append(".\n");
    throw new IllegalStateException("Cannot askUserChoosePetSpace.");
  }

  @Override
  public int askUserMoveConfirmation(String spaceName) throws IllegalArgumentException {
    gameLog.append(this.uuid)
            .append(" askUserMoveConfirmation called")
            .append(" spaceName :: ")
            .append(spaceName)
            .append(".\n");
    throw new IllegalStateException("Cannot askUserMoveConfirmation.");
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
    throw new IllegalStateException("Cannot resetFocus.");
  }

  @Override
  public void switchToGameLayout() {
    gameLog.append(this.uuid)
            .append(" switchToGameLayout called")
            .append(".\n");
    throw new IllegalStateException("Cannot switchToGameLayout.");
  }

  @Override
  public void switchToWelcomeLayout() {
    gameLog.append(this.uuid)
            .append(" switchToWelcomeLayout called")
            .append(".\n");
    throw new IllegalStateException("Cannot switchToWelcomeLayout.");
  }

  @Override
  public void setContent(Result r, Features f) throws IllegalArgumentException {
    gameLog.append(this.uuid)
            .append(" setContent called")
            .append(".\n");
    throw new IllegalStateException("Cannot setContent.");
  }

  @Override
  public void closeWindow() {
    gameLog.append(this.uuid)
            .append(" closeWindow called")
            .append(".\n");
    throw new IllegalStateException("Cannot closeWindow.");
  }

  @Override
  public void switchLayoutToLongText() {
    gameLog.append(this.uuid)
            .append(" switchLayoutToLongText called")
            .append(".\n");
    throw new IllegalStateException("Cannot switchLayoutToLongText.");
  }

  @Override
  public void switchLayoutToAddPlayer() {
    gameLog.append(this.uuid)
            .append(" switchLayoutToAddPlayer called")
            .append(".\n");
    throw new IllegalStateException("Cannot switchLayoutToAddPlayer.");
  }
}
