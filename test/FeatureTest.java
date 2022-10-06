import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.io.StringReader;
import org.junit.Before;
import org.junit.Test;
import world.controller.DefaultController;
import world.controller.Features;
import world.model.World;
import world.view.GameView;

/**
 * Test cases for feature class.
 */
public class FeatureTest {

  private StringBuilder gameLog;
  
  // model
  private World mvalid;
  private World mvalidPlayerInfo;
  
  private World minvalidAddCpu;
  private World minvalidAddHuman;
  
  // - turns
  private World minvalidValidate;
  private World minvalidCpuTurn;
  private World minvalidKill;
  private World minvalidLook;
  private World minvalidMove;
  private World minvalidMovePet;
  private World minvalidPick;
  private World minvalidGameEnd;
  private World minvalidStart;
  
  // view
  private GameView vvalid;
  private GameView vinvalid;
  
  // features
  private Features controllerFeatures;


  @Before
  public void setUp() {
    this.gameLog = new StringBuilder();
    
    // model
    this.mvalid = new MockWorldValidMoveHuman(gameLog, "M-VALID-ALL");
    this.mvalidPlayerInfo = new MockWorldValidPlayerInfo(gameLog, "M-VALID-playerinfo");
    
    // - invalid adding
    this.minvalidAddHuman = new InvalidWorldAddHuman(gameLog, "M-INVALID-addhuman");
    this.minvalidAddCpu = new InvalidWorldAddCpu(gameLog, "M-INVALID-addCpu");
    
    // - invalid turn
    this.minvalidValidate = new InvalidWorldValidate(gameLog, "M-INVALID-validate");
    this.minvalidCpuTurn = new InvalidWorldCpuTurn(gameLog, "M-INVALID-cputurn");
    this.minvalidKill = new InvalidWorldKill(gameLog, "M-INVALID-kill");
    this.minvalidLook = new InvalidWorldLook(gameLog, "M-INVALID-look");
    this.minvalidMove = new InvalidWorldMove(gameLog, "M-INVALID-move");
    this.minvalidMovePet = new InvalidWorldMovePet(gameLog, "M-INVALID-movepet");
    this.minvalidPick = new InvalidWorldPick(gameLog, "M-INVALID-pick");
    
    // - invalid 
    this.minvalidGameEnd = new InvalidWorldGameEnd(gameLog, "M-INVALID-gameend");
    this.minvalidStart = new InvalidWorldStart(gameLog, "M-INVALID-start");
    
    // view
    this.vvalid = new MockViewValid(gameLog, "V-VALID-ALL");
    this.vinvalid = new MockViewInvalid(gameLog, "V-INVALID-ALL");
  }

  /**
   * 1. Adding human players group
   */
  @Test
  public void addHumanPlayer() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(mvalid, vvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.addHumanPlayer();
    String expected = "V-VALID-ALL resetFocus called.\n"
        + "V-VALID-ALL switchToGameLayout called.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "M-VALID-ALL getResult() called.\n"
        + "V-VALID-ALL showAddPlayerInputDialog called.\n"
        + "M-VALID-ALL addHumanPlayer() called. name is: ABC space index is: 1.\n"
        + "M-VALID-ALL getResult() called.\n"
        + "V-VALID-ALL updateMapStatusAndActionResultPanel() called. Result is RESULT-VALID.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "";
    assertEquals(expected, gameLog.toString());
  }

  @Test
  public void addHumanPlayerMethodException() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(minvalidAddHuman, vvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.addHumanPlayer();
    String expected = "V-VALID-ALL resetFocus called.\n"
        + "V-VALID-ALL switchToGameLayout called.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "M-INVALID-addhuman getResult() called.\n"
        + "V-VALID-ALL showAddPlayerInputDialog called.\n"
        + "M-INVALID-addhuman addHumanPlayer() called. name is: ABC space index is: 1.\n"
        + "V-VALID-ALL showErrorDialogue called msg :: addHumanPlayer() exception.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "";
    assertEquals(expected, gameLog.toString());
  }
  
  @Test
  public void addHumanPlayerViewException() { 
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(mvalid, vinvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.addHumanPlayer();
    String expected = "V-INVALID-ALL resetFocus called.\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: Cannot resetFocus..\n"
        + "M-VALID-ALL getResult() called.\n"
        + "V-INVALID-ALL showAddPlayerInputDialog called.\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: Cannot showAddPlayerInputDialog..\n"
        + "V-INVALID-ALL resetFocus called.\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: Cannot resetFocus..\n";
    assertEquals(expected, gameLog.toString());
  }
  
  @Test
  public void gameStateValidateException() { 
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(minvalidValidate, vinvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.addHumanPlayer();
    String expected = "V-INVALID-ALL resetFocus called.\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: Cannot resetFocus..\n"
        + "M-INVALID-validate getResult() called.\n"
        + "V-INVALID-ALL showAddPlayerInputDialog called.\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: Cannot showAddPlayerInputDialog..\n"
        + "V-INVALID-ALL resetFocus called.\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: Cannot resetFocus..\n"
        + "";
    assertEquals(expected, gameLog.toString());
  }
  
  /**
   * Adding cpu players group.
   */
  @Test
  public void addCpuPlayer() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(mvalid, vvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.addCpuPlayer();
    String expected = "V-VALID-ALL resetFocus called.\n"
        + "V-VALID-ALL switchToGameLayout called.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "M-VALID-ALL addCpuPlayer() called.\n"
        + "M-VALID-ALL getResult() called.\n"
        + "V-VALID-ALL updateMapStatusAndActionResultPanel() called. Result is RESULT-VALID.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "";
    assertEquals(expected, gameLog.toString());
  }
  
  @Test
  public void addCpuPlayerMethodInvalid() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(minvalidAddCpu, vvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.addCpuPlayer();
    String expected = "V-VALID-ALL resetFocus called.\n"
        + "V-VALID-ALL switchToGameLayout called.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "M-INVALID-addCpu addCpuPlayer() called.\n"
        + "V-VALID-ALL showErrorDialogue called msg ::  addCpuPlayer() exception.\n"
        + "V-VALID-ALL resetFocus called.\n";
    assertEquals(expected, gameLog.toString());
  }
  
  @Test
  public void addCpuPlayerViewException() { 
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(mvalid, vinvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.addCpuPlayer();
    String expected = "V-INVALID-ALL resetFocus called.\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: Cannot resetFocus..\n"
        + "M-VALID-ALL addCpuPlayer() called.\n"
        + "M-VALID-ALL getResult() called.\n"
        + "V-INVALID-ALL updateMapStatusAndActionResultPanel() called.\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: Cannot "
        + "updateMapStatusAndActionResultPanel..\n"
        + "V-INVALID-ALL resetFocus called.\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: Cannot resetFocus..\n"
        + "";
    assertEquals(expected, gameLog.toString());
  }
  
  /**
   * 2. Move Human
   */
  @Test
  public void moveHuman() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(mvalid, vvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.moveHumanOrDisplayPlayerInfo(new Point(250, 112));

    String expected = "V-VALID-ALL resetFocus called.\n"
        + "V-VALID-ALL switchToGameLayout called.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "M-VALID-ALL hasGameStarted() called.\n"
        + "M-VALID-ALL isPointCurrentHumanPlayer() called point is: 250 112.\n"
        + "M-VALID-ALL hasGameStarted() called.\n"
        + "M-VALID-ALL isPointCurrentHumanPlayerNeighbor() called point index is: 250 112.\n"
        + "M-VALID-ALL validateGameTurn() called\n"
        + "isCpuCall :: false.\n"
        + "M-VALID-ALL getSpaceNameByCoord() called Point ::250 112.\n"
        + "V-VALID-ALL askUserMoveConfirmation called spaceName :: V-VALID-ALL resetFocus called.\n"
        + "V-VALID-ALL switchToGameLayout called.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "M-VALID-ALL hasGameStarted() called.\n"
        + "M-VALID-ALL isPointCurrentHumanPlayer() called point is: 250 112.\n"
        + "M-VALID-ALL hasGameStarted() called.\n"
        + "M-VALID-ALL isPointCurrentHumanPlayerNeighbor() called point index is: 250 112.\n"
        + "M-VALID-ALL validateGameTurn() called\n"
        + "isCpuCall :: false.\n"
        + "M-VALID-ALL getSpaceNameByCoord() called Point ::250 112.\n"
        + ".\n"
        + "M-VALID-ALL makeHumanMovePoint() called Point is: 250 112.\n"
        + "M-VALID-ALL getResult() called.\n"
        + "V-VALID-ALL updateMapStatusAndActionResultPanel() called. Result is RESULT-VALID.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "";
    assertEquals(expected, gameLog.toString());
  }
  
  @Test
  public void moveHumanMethodInvalid() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(minvalidMove, vvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.moveHumanOrDisplayPlayerInfo(new Point(250, 112));

    String expected = "V-VALID-ALL resetFocus called.\n"
        + "V-VALID-ALL switchToGameLayout called.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "M-INVALID-move hasGameStarted() called.\n"
        + "M-INVALID-move isPointCurrentHumanPlayer() called point is: 250 112.\n"
        + "M-INVALID-move hasGameStarted() called.\n"
        + "M-INVALID-move isPointCurrentHumanPlayerNeighbor() called point index is: 250 112.\n"
        + "M-INVALID-move validateGameTurn() called\n"
        + "isCpuCall :: false.\n"
        + "M-INVALID-move getSpaceNameByCoord() called Point ::250 112.\n"
        + "V-VALID-ALL askUserMoveConfirmation called spaceName :: V-VALID-ALL resetFocus called.\n"
        + "V-VALID-ALL switchToGameLayout called.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "M-INVALID-move hasGameStarted() called.\n"
        + "M-INVALID-move isPointCurrentHumanPlayer() called point is: 250 112.\n"
        + "M-INVALID-move hasGameStarted() called.\n"
        + "M-INVALID-move isPointCurrentHumanPlayerNeighbor() called point index is: 250 112.\n"
        + "M-INVALID-move validateGameTurn() called\n"
        + "isCpuCall :: false.\n"
        + "M-INVALID-move getSpaceNameByCoord() called Point ::250 112.\n"
        + ".\n"
        + "M-INVALID-move makeHumanMovePoint() called Point is: java.awt.Point[x=250,y=112].\n"
        + "V-VALID-ALL showErrorDialogue called msg ::  makeHumanMovePoint() exception.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "";
    assertEquals(expected, gameLog.toString());
  }
  
  @Test
  public void moveHumanInvalidView() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(mvalid, vinvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.moveHumanOrDisplayPlayerInfo(new Point(250, 112));

    String expected = "V-INVALID-ALL resetFocus called.\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: Cannot resetFocus..\n"
        + "M-VALID-ALL hasGameStarted() called.\n"
        + "M-VALID-ALL isPointCurrentHumanPlayer() called point is: 250 112.\n"
        + "M-VALID-ALL hasGameStarted() called.\n"
        + "M-VALID-ALL isPointCurrentHumanPlayerNeighbor() called point index is: 250 112.\n"
        + "M-VALID-ALL validateGameTurn() called\n"
        + "isCpuCall :: false.\n"
        + "M-VALID-ALL getSpaceNameByCoord() called Point ::250 112.\n"
        + "V-INVALID-ALL askUserMoveConfirmation called spaceName :: "
        + "V-INVALID-ALL resetFocus called.\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: Cannot resetFocus..\n"
        + "M-VALID-ALL hasGameStarted() called.\n"
        + "M-VALID-ALL isPointCurrentHumanPlayer() called point is: 250 112.\n"
        + "M-VALID-ALL hasGameStarted() called.\n"
        + "M-VALID-ALL isPointCurrentHumanPlayerNeighbor() called point index is: 250 112.\n"
        + "M-VALID-ALL validateGameTurn() called\n"
        + "isCpuCall :: false.\n"
        + "M-VALID-ALL getSpaceNameByCoord() called Point ::250 112.\n"
        + ".\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: Cannot askUserMoveConfirmation..\n"
        + "V-INVALID-ALL resetFocus called.\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: Cannot resetFocus..\n"
        + "";
    assertEquals(expected, gameLog.toString());
  }
  
 
  /**
   * 3. Display player info.
   */
  @Test
  public void displayPlayer() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(mvalidPlayerInfo, vvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.moveHumanOrDisplayPlayerInfo(new Point(250, 112));

    String expected = "V-VALID-ALL resetFocus called.\n"
        + "V-VALID-ALL switchToGameLayout called.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "M-VALID-playerinfo hasGameStarted() called.\n"
        + "M-VALID-playerinfo isPointCurrentHumanPlayer() called point is: 250 112.\n"
        + "M-VALID-playerinfo validateGameTurn() called\n"
        + "isCpuCall :: false.\n"
        + "M-VALID-playerinfo getResult() called.\n"
        + "V-VALID-ALL showPlayerInfo called.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "";
    assertEquals(expected, gameLog.toString());
  }
  
  @Test
  public void displayPlayerInvalidView() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(mvalidPlayerInfo, vinvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.moveHumanOrDisplayPlayerInfo(new Point(250, 112));

    String expected = "V-INVALID-ALL resetFocus called.\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: Cannot resetFocus..\n"
        + "M-VALID-playerinfo hasGameStarted() called.\n"
        + "M-VALID-playerinfo isPointCurrentHumanPlayer() called point is: 250 112.\n"
        + "M-VALID-playerinfo validateGameTurn() called\n"
        + "isCpuCall :: false.\n"
        + "M-VALID-playerinfo getResult() called.\n"
        + "V-INVALID-ALL showPlayerInfo called.\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: Cannot showPlayerInfo..\n"
        + "V-INVALID-ALL resetFocus called.\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: Cannot resetFocus..\n"
        + "";
    assertEquals(expected, gameLog.toString());
  }
  
  /**
   * 4. Look around group
   */
  @Test
  public void humanLookAroundValid() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(mvalid, vvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.humanLookAround();
    String expected = "V-VALID-ALL resetFocus called.\n"
        + "V-VALID-ALL switchToGameLayout called.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "M-VALID-ALL validateGameTurn() called\n"
        + "isCpuCall :: false.\n"
        + "M-VALID-ALL makeHumanLook() called.\n"
        + "M-VALID-ALL getResult() called.\n"
        + "V-VALID-ALL updateLookAround called. Result is RESULT-VALID.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "";
    assertEquals(expected, gameLog.toString());
  }

  @Test
  public void humanLookAroundInvalidMethod() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(minvalidLook, vvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.humanLookAround();
    String expected = "V-VALID-ALL resetFocus called.\n"
        + "V-VALID-ALL switchToGameLayout called.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "M-INVALID-look validateGameTurn() called\n"
        + "isCpuCall :: false.\n"
        + "M-INVALID-look makeHumanLook() called.\n"
        + "V-VALID-ALL showErrorDialogue called msg :: makeHumanLook() exception.\n"
        + "V-VALID-ALL resetFocus called.\n";
    assertEquals(expected, gameLog.toString());
  }

  @Test
  public void humanLookAroundViewInvalid() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(mvalid, vinvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.humanLookAround();
    String expected = "V-INVALID-ALL resetFocus called.\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: Cannot resetFocus..\n"
        + "M-VALID-ALL validateGameTurn() called\n"
        + "isCpuCall :: false.\n"
        + "M-VALID-ALL makeHumanLook() called.\n"
        + "M-VALID-ALL getResult() called.\n"
        + "V-INVALID-ALL updateLookAround called.\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: Cannot updateLookAround..\n"
        + "V-INVALID-ALL resetFocus called.\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: Cannot resetFocus..\n";
    assertEquals(expected, gameLog.toString());
  }

  /**
   * 5. Move pet group.
   */
  @Test
  public void humanMovePetValid() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(mvalid, vvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.humanMovePet();  // problem here
    String expected = "V-VALID-ALL resetFocus called.\n"
        + "V-VALID-ALL switchToGameLayout called.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "M-VALID-ALL validateGameTurn() called\n"
        + "isCpuCall :: false.\n"
        + "M-VALID-ALL getResult() called.\n"
        + "V-VALID-ALL askUserChoosePetSpace called spaceNames :: some space name.\n"
        + "M-VALID-ALL getResult() called.\n"
        + "M-VALID-ALL getResult() called.\n"
        + "M-VALID-ALL getResult() called.\n"
        + "M-VALID-ALL makeHumanMovePet() called space index is: 1.\n"
        + "M-VALID-ALL getResult() called.\n"
        + "V-VALID-ALL updateMapStatusAndActionResultPanel() called. Result is RESULT-VALID.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "";
    assertEquals(expected, gameLog.toString());
  }

  @Test
  public void humanMovePeMethodInvalid() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(minvalidMovePet, vvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.humanMovePet();  // problem here
    String expected = "V-VALID-ALL resetFocus called.\n"
        + "V-VALID-ALL switchToGameLayout called.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "M-INVALID-movepet validateGameTurn() called\n"
        + "isCpuCall :: false.\n"
        + "M-INVALID-movepet getResult() called.\n"
        + "V-VALID-ALL askUserChoosePetSpace called spaceNames :: some space name.\n"
        + "M-INVALID-movepet getResult() called.\n"
        + "M-INVALID-movepet getResult() called.\n"
        + "M-INVALID-movepet getResult() called.\n"
        + "M-INVALID-movepet makeHumanMovePet() called item index is: 0.\n"
        + "V-VALID-ALL showErrorDialogue called msg ::  makeHumanMovePet() exception.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "";
    assertEquals(expected, gameLog.toString());
  }
  
  @Test
  public void humanMovePetViewInvalid() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(mvalid, vinvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.humanMovePet();  // problem here
    String expected = "V-INVALID-ALL resetFocus called.\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: Cannot resetFocus..\n"
        + "M-VALID-ALL validateGameTurn() called\n"
        + "isCpuCall :: false.\n"
        + "M-VALID-ALL getResult() called.\n"
        + "V-INVALID-ALL askUserChooseItem called spaceNames :: [some space name].\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: Cannot askUserChoosePetSpace..\n"
        + "V-INVALID-ALL resetFocus called.\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: Cannot resetFocus..\n";
    assertEquals(expected, gameLog.toString());
  }
  
  /**
   * 6. Pick item group.
   */
  @Test 
  public void humanPickItemValid() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(mvalid, vvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.humanPickItem();
    String expected = "V-VALID-ALL resetFocus called.\n"
        + "V-VALID-ALL switchToGameLayout called.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "M-VALID-ALL validateGameTurn() called\n"
        + "isCpuCall :: false.\n"
        + "M-VALID-ALL currentSpaceShouldHaveItems() called.\n"
        + "M-VALID-ALL getResult() called.\n"
        + "V-VALID-ALL askUserChooseItem called.\n"
        + "M-VALID-ALL makeHumanPickItem() called itemId is: 1.\n"
        + "M-VALID-ALL getResult() called.\n"
        + "V-VALID-ALL updateMapStatusAndActionResultPanel() called. Result is RESULT-VALID.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "";
    assertEquals(expected, gameLog.toString());
  }
  
  @Test 
  public void humanPickItemInvalidMethod() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(minvalidPick, vvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.humanPickItem();
    String expected = "V-VALID-ALL resetFocus called.\n"
        + "V-VALID-ALL switchToGameLayout called.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "M-INVALID-pick validateGameTurn() called\n"
        + "isCpuCall :: false.\n"
        + "M-INVALID-pick currentSpaceShouldHaveItems() called.\n"
        + "M-INVALID-pick getResult() called.\n"
        + "V-VALID-ALL askUserChooseItem called.\n"
        + "M-INVALID-pick makeHumanMovePoint() calleditem index is 1.\n"
        + "V-VALID-ALL showErrorDialogue called msg :: makeHumanMovePoint() exception.\n"
        + "V-VALID-ALL resetFocus called.\n";
    assertEquals(expected, gameLog.toString());
  }
  
  @Test
  public void humanPickItemViewInvalid() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(mvalid, vinvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.humanPickItem();
    String expected = "V-INVALID-ALL resetFocus called.\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: Cannot resetFocus..\n"
        + "M-VALID-ALL validateGameTurn() called\n"
        + "isCpuCall :: false.\n"
        + "M-VALID-ALL currentSpaceShouldHaveItems() called.\n"
        + "M-VALID-ALL getResult() called.\n"
        + "V-INVALID-ALL askUserChooseItem called.\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: Cannot askUserChooseItem..\n"
        + "V-INVALID-ALL resetFocus called.\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: Cannot resetFocus..\n";
    assertEquals(expected, gameLog.toString());
  }

  /**
   * 7. Kill group.
   */
  @Test 
  public void humanKillValid() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(mvalid, vvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.humanKill();
    String expected = "V-VALID-ALL resetFocus called.\n"
        + "V-VALID-ALL switchToGameLayout called.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "M-VALID-ALL validateGameTurn() called\n"
        + "isCpuCall :: false.\n"
        + "M-VALID-ALL getResult() called.\n"
        + "V-VALID-ALL askUserChooseItem called.\n"
        + "M-VALID-ALL makeHumanKill() called item index is: 1.\n"
        + "M-VALID-ALL getResult() called.\n"
        + "V-VALID-ALL updateMapStatusAndActionResultPanel() called. Result is RESULT-VALID.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "";
    assertEquals(expected, gameLog.toString());
  }

  @Test 
  public void humanKillInvalidMethod() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(minvalidKill, vvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.humanKill();
    String expected = "V-VALID-ALL resetFocus called.\n"
        + "V-VALID-ALL switchToGameLayout called.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "M-INVALID-kill validateGameTurn() called\n"
        + "isCpuCall :: false.\n"
        + "M-INVALID-kill getResult() called.\n"
        + "V-VALID-ALL askUserChooseItem called.\n"
        + "M-INVALID-kill makeHumanKill() called item index is: 1.\n"
        + "V-VALID-ALL showErrorDialogue called msg :: makeHumanKill() exception.\n"
        + "V-VALID-ALL resetFocus called.\n";
    assertEquals(expected, gameLog.toString());
  }

  @Test 
  public void humanKillInvalidView() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(mvalid, vinvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.humanKill();
    String expected = "V-INVALID-ALL resetFocus called.\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: Cannot resetFocus..\n"
        + "M-VALID-ALL validateGameTurn() called\n"
        + "isCpuCall :: false.\n"
        + "M-VALID-ALL getResult() called.\n"
        + "V-INVALID-ALL askUserChooseItem called.\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: Cannot askUserChooseItem..\n"
        + "V-INVALID-ALL resetFocus called.\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: Cannot resetFocus..\n"
        + "";
    assertEquals(expected, gameLog.toString());
  }
  
  /**
   * 8. Cpu turn
   */
  @Test
  public void takeCpuTurnValid() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(mvalid, vvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.takeCpuTurn();
    String expected = "V-VALID-ALL resetFocus called.\n"
        + "V-VALID-ALL switchToGameLayout called.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "M-VALID-ALL validateGameTurn() called\n"
        + "isCpuCall :: true.\n"
        + "M-VALID-ALL makeCpuTakeTurn() called.\n"
        + "M-VALID-ALL getResult() called.\n"
        + "V-VALID-ALL updateMapStatusAndActionResultPanel() called. Result is RESULT-VALID.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "";
    assertEquals(expected, gameLog.toString());
  }
  
  @Test
  public void takeCpuTurnInvalidMethod() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(minvalidCpuTurn, vvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.takeCpuTurn();
    String expected = "V-VALID-ALL resetFocus called.\n"
        + "V-VALID-ALL switchToGameLayout called.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "M-INVALID-cputurn validateGameTurn() called\n"
        + "isCpuCall :: true.\n"
        + "M-INVALID-cputurn makeCpuTakeTurn() called.\n"
        + "V-VALID-ALL showErrorDialogue called msg :: makeCpuTakeTurn() exception.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "";
    assertEquals(expected, gameLog.toString());
  }
  
  @Test
  public void takeCpuTurnViewInvalid() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(mvalid, vinvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.takeCpuTurn();
    String expected = "V-INVALID-ALL resetFocus called.\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: Cannot resetFocus..\n"
        + "M-VALID-ALL validateGameTurn() called\n"
        + "isCpuCall :: true.\n"
        + "M-VALID-ALL makeCpuTakeTurn() called.\n"
        + "M-VALID-ALL getResult() called.\n"
        + "V-INVALID-ALL updateMapStatusAndActionResultPanel() called.\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: "
        + "Cannot updateMapStatusAndActionResultPanel..\n"
        + "V-INVALID-ALL resetFocus called.\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: Cannot resetFocus..\n"
        + "";
    assertEquals(expected, gameLog.toString());
  }
  
  @Test
  public void takeCpuTurnInvalidMethodView() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(minvalidCpuTurn, vinvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.takeCpuTurn();
    String expected = "V-INVALID-ALL resetFocus called.\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: Cannot resetFocus..\n"
        + "M-INVALID-cputurn validateGameTurn() called\n"
        + "isCpuCall :: true.\n"
        + "M-INVALID-cputurn makeCpuTakeTurn() called.\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: makeCpuTakeTurn() exception.\n"
        + "V-INVALID-ALL resetFocus called.\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: Cannot resetFocus..\n"
        + "";
    assertEquals(expected, gameLog.toString());
  }
  
 
  /**
   * 9. Play game.
   */
  @Test
  public void playGameValid() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(mvalid, vvalid);
    controllerFeatures.playGameMode();
    String expected = "V-VALID-ALL resetFocus called.\n"
        + "V-VALID-ALL switchToGameLayout called.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "";
    assertEquals(expected, gameLog.toString());
  }
  
  /**
   * 9. Play game.
   */
  @Test
  public void playGameInvalidView() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(mvalid, vinvalid);
    controllerFeatures.playGameMode();
    String expected = "V-INVALID-ALL resetFocus called.\n"
        + "V-INVALID-ALL showErrorDialogue called msg :: Cannot resetFocus..\n"
        + "";
    assertEquals(expected, gameLog.toString());
  }
  
  /**
   * 10. Start game.
   */
  
  @Test
  public void startGame() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(mvalid, vvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.startGame();
    String expected = "V-VALID-ALL resetFocus called.\n"
        + "V-VALID-ALL switchToGameLayout called.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "M-VALID-ALL startTheGame() called.\n"
        + "V-VALID-ALL switchLayoutToLongText called.\n"
        + "M-VALID-ALL getResult() called.\n"
        + "V-VALID-ALL updateMapStatusAndActionResultPanel() called. Result is RESULT-VALID.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "";
    assertEquals(expected, gameLog.toString());
  }
  
  @Test
  public void startGameInvalid() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(minvalidStart, vvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.startGame();
    String expected = "V-VALID-ALL resetFocus called.\n"
        + "V-VALID-ALL switchToGameLayout called.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "M-INVALID-start getResult() called.\n"
        + "V-VALID-ALL showErrorDialogue called msg :: getResult() exception.\n"
        + "";
    assertEquals(expected, gameLog.toString());
  }
  
  
  /**
   * 11. Reset game 
   */
  
  @Test
  public void resetValid() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(mvalid, vvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.resetCurrentGame();
    String expected = "V-VALID-ALL resetFocus called.\n"
        + "V-VALID-ALL switchToGameLayout called.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "M-VALID-ALL reset() called.\n"
        + "M-VALID-ALL getResult() called.\n"
        + "V-VALID-ALL setContent called. Result is RESULT-VALID.\n"
        + "V-VALID-ALL switchToGameLayout called.\n"
        + "V-VALID-ALL switchLayoutToAddPlayer called.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "";
    assertEquals(expected, gameLog.toString());
  }
  
  @Test
  public void resetInvalid() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(minvalidGameEnd, vvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.startGame();
    controllerFeatures.resetCurrentGame();
    String expected = "V-VALID-ALL resetFocus called.\n"
        + "V-VALID-ALL switchToGameLayout called.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "M-INVALID-gameend startTheGame() called.\n"
        + "V-VALID-ALL switchLayoutToLongText called.\n"
        + "M-INVALID-gameend getResult() called.\n"
        + "V-VALID-ALL updateMapStatusAndActionResultPanel() called. Result is RESULT-VALID.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "M-INVALID-gameend reset() called.\n"
        + "V-VALID-ALL showErrorDialogue called msg :: reset() exception.\n"
        + "";
    assertEquals(expected, gameLog.toString());
  }
  
  
  /**
   * 12. Quit game 
   */
  
  @Test
  public void quitGameValid() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(mvalid, vvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.startGame();
    controllerFeatures.quitGame();
    String expected = "V-VALID-ALL resetFocus called.\n"
        + "V-VALID-ALL switchToGameLayout called.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "M-VALID-ALL startTheGame() called.\n"
        + "V-VALID-ALL switchLayoutToLongText called.\n"
        + "M-VALID-ALL getResult() called.\n"
        + "V-VALID-ALL updateMapStatusAndActionResultPanel() called. Result is RESULT-VALID.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "M-VALID-ALL endTheGame() called.\n"
        + "M-VALID-ALL getResult() called.\n"
        + "V-VALID-ALL setContent called. Result is RESULT-VALID.\n"
        + "V-VALID-ALL closeWindow called.\n"
        + "";
    assertEquals(expected, gameLog.toString());
  }
  
  @Test
  public void quitGameInvalid() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(minvalidGameEnd, vvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.startGame();
    controllerFeatures.quitGame();
    String expected = "V-VALID-ALL resetFocus called.\n"
        + "V-VALID-ALL switchToGameLayout called.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "M-INVALID-gameend startTheGame() called.\n"
        + "V-VALID-ALL switchLayoutToLongText called.\n"
        + "M-INVALID-gameend getResult() called.\n"
        + "V-VALID-ALL updateMapStatusAndActionResultPanel() called. Result is RESULT-VALID.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "M-INVALID-gameend endTheGame() called.\n"
        + "V-VALID-ALL showErrorDialogue called msg :: endTheGame() exception.\n"
        + "";
    assertEquals(expected, gameLog.toString());
  }
  
  
  /**
   * 13. Reload
   */
  
  @Test
  public void loadNewGameValid() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(mvalid, vvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.startGame();
    controllerFeatures.loadNewGame(new StringReader("Test"));
    String expected = "V-VALID-ALL resetFocus called.\n"
        + "V-VALID-ALL switchToGameLayout called.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "M-VALID-ALL startTheGame() called.\n"
        + "V-VALID-ALL switchLayoutToLongText called.\n"
        + "M-VALID-ALL getResult() called.\n"
        + "V-VALID-ALL updateMapStatusAndActionResultPanel() called. Result is RESULT-VALID.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "M-VALID-ALL reloadNewGame() called.\n"
        + "M-VALID-ALL getResult() called.\n"
        + "V-VALID-ALL setContent called. Result is RESULT-VALID.\n"
        + "V-VALID-ALL switchLayoutToAddPlayer called.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "";
    assertEquals(expected, gameLog.toString());
  }
  
  @Test
  public void loadNewGameInvalid() {
    gameLog.setLength(0);
    controllerFeatures = new DefaultController(minvalidGameEnd, vvalid);
    controllerFeatures.playGameMode();
    controllerFeatures.startGame();
    controllerFeatures.loadNewGame(new StringReader("Test"));
    String expected = "V-VALID-ALL resetFocus called.\n"
        + "V-VALID-ALL switchToGameLayout called.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "M-INVALID-gameend startTheGame() called.\n"
        + "V-VALID-ALL switchLayoutToLongText called.\n"
        + "M-INVALID-gameend getResult() called.\n"
        + "V-VALID-ALL updateMapStatusAndActionResultPanel() called. Result is RESULT-VALID.\n"
        + "V-VALID-ALL resetFocus called.\n"
        + "M-INVALID-gameend reloadNewGame() called.\n"
        + "V-VALID-ALL showErrorDialogue called msg :: reloadNewGame() exception.\n"
        + "";
    assertEquals(expected, gameLog.toString());
  }
}












