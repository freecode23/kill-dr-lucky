import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import world.controller.Controller;
import world.controller.DefaultController;
import world.model.World;
import world.view.GameView;

/**
 * 
 * Class for testing the controller.
 *
 */
public class ControllerTest {
  
  private StringBuilder gameLog;
  
  // model
  private World mvalid;
  private World minvalidResult;


  // view
  private GameView vvalid;
  private GameView vinvalid;

  
  @Before
  public void setUp() {
    this.gameLog = new StringBuilder();
    
    // model
    this.mvalid = new MockWorldValidMoveHuman(gameLog, "M-VALID-ALL");
    this.minvalidResult = new InvalidWorldGetResult(gameLog, "M-INVALID-getresult");
    
    // view
    this.vvalid = new MockViewValid(gameLog, "V-VALID-ALL");
    this.vinvalid = new MockViewInvalid(gameLog, "V-INVALID-ALL");
  }

  @Test
  public void testDisplayValid() {

    gameLog.setLength(0);
    Controller controller = new DefaultController(mvalid, vvalid);
    controller.display();
    String expected = "V-VALID-ALL setFeatures() called.\n"
        + "M-VALID-ALL getResult() called.\n"
        + "V-VALID-ALL setContent called. Result is RESULT-VALID.\n"
        + "";
    assertEquals(expected, gameLog.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testDisplayInvalidView() {
    gameLog.setLength(0);
    Controller controller = new DefaultController(mvalid, vinvalid);
    controller.display();
  }

  @Test(expected = IllegalStateException.class)
  public void testDisplayInvalidResult() {
    gameLog.setLength(0);
    Controller controller = new DefaultController(minvalidResult, vvalid);
    controller.display();
  }
}
