package world.controller;

import java.awt.Point;
import world.controller.command.AddCpuPlayer;
import world.controller.command.AddHumanPlayer;
import world.controller.command.CpuTakeTurn;
import world.controller.command.DisplayPlayerInfo;
import world.controller.command.HumanKillAttempt;
import world.controller.command.HumanLook;
import world.controller.command.HumanMove;
import world.controller.command.HumanMovePet;
import world.controller.command.HumanPickItem;
import world.controller.command.LoadNewGame;
import world.controller.command.Quit;
import world.controller.command.ResetGame;
import world.controller.command.WorldCommand;
import world.model.World;
import world.view.GameView;

/**
 * The class orchestrates all user inputs to relevant actions. This is an
 * implementation of the Controller Interface. Refer it for details.
 */
public class DefaultController implements Controller, Features {
  private World world;
  private GameView view;

  /**
   * Constructor to create concrete default controller.
   * 
   * @param world facade of the game's model.
   * @param view  object to display game's states.
   * @throws IllegalArgumentException if view or world is null
   */
  public DefaultController(World world, GameView view) throws IllegalArgumentException {
    if (view == null) {
      throw new IllegalArgumentException("Cannot create default controller when view is null.\n");
    }
    if (world == null) {
      throw new IllegalArgumentException("Cannot create default controller when world is null.\n");
    }

    this.view = view;
    this.world = world;

  }

  @Override
  public void display() throws IllegalArgumentException {
    // set all listeners
    view.setFeatures(this);

    // set all contents of the view
    view.setContent(world.getResult(), this);
  }

  @Override
  public void playGameMode() {
    try {
      view.resetFocus();
      view.switchToGameLayout();
      view.resetFocus();
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.showErrorDialogue(e.getMessage());
    }

  }

  @Override
  public void startGame() {
    
    try {
      // 1. model start
      world.startTheGame();
      
      // 2. switch to long text panel
      view.switchLayoutToLongText();

      // 3. update every text
      view.updateMapStatusAndActionResultPanel(world.getResult());
      view.resetFocus();
    } catch (IllegalStateException | IllegalArgumentException e) {
      // if there is not players it will throw exception
      view.showErrorDialogue(e.getMessage());
    }


  }

  @Override
  public void loadNewGame(Readable file) {
    try {
      if (file == null) {
        throw new IllegalArgumentException("File not found\n");
      }
      
      // set world and view
      WorldCommand command = new LoadNewGame(file);
      command.go(world);
      
      // set all contents of the view
      view.setContent(world.getResult(), this);
      view.switchLayoutToAddPlayer();
      view.resetFocus();
    } catch (IllegalArgumentException  | IllegalStateException e) {
      view.showErrorDialogue(e.getMessage());
    }
  }
  
  @Override
  public void resetCurrentGame() {
    
    try {
      
      WorldCommand command = new ResetGame();
      command.go(this.world);
      
      view.setContent(world.getResult(), this);
      
      view.switchToGameLayout();
      view.switchLayoutToAddPlayer();
      view.resetFocus();
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.showErrorDialogue(e.getMessage());
    }
  }
  
  @Override
  public void quitGame() {
    
    try {
      WorldCommand quitCommand = new Quit();
      quitCommand.go(world);
      
      view.setContent(world.getResult(), this);
      view.closeWindow();
      
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.showErrorDialogue(e.getMessage());
    }
  }

  @Override
  public void addHumanPlayer() {
    
    try {
      WorldCommand command = new AddHumanPlayer(view);
      command.go(this.world);
      view.resetFocus();
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.showErrorDialogue(e.getMessage());
    }
  }

  @Override
  public void addCpuPlayer() {
    
    try {
      WorldCommand command = new AddCpuPlayer(view);
      command.go(this.world);
      view.resetFocus();
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.showErrorDialogue(e.getMessage());
    }
  }
  
  @Override
  public void humanKill() {

    try {
      WorldCommand killCommand = new HumanKillAttempt(view);
      killCommand.go(this.world);
      view.resetFocus();
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.showErrorDialogue(e.getMessage());
    }
  }

  @Override
  public void humanLookAround() {
    try {
      WorldCommand command = new HumanLook(view);
      command.go(this.world);
      view.resetFocus();
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.showErrorDialogue(e.getMessage());
    }

  }

  @Override
  public void humanMovePet() {
    
    try {
      WorldCommand command = new HumanMovePet(view);
      command.go(this.world);
      view.resetFocus();
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.showErrorDialogue(e.getMessage());
    }
  }

  @Override
  public void humanPickItem() {
    
    try {
      WorldCommand command = new HumanPickItem(view);
      command.go(this.world);
      view.resetFocus();
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.showErrorDialogue(e.getMessage());
    }
    
  }

  @Override
  public void takeCpuTurn() {
    
    try {
      WorldCommand command = new CpuTakeTurn(view);
      command.go(world);
      view.resetFocus();
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.showErrorDialogue(e.getMessage());
    }
    
  }

  @Override
  public void moveHumanOrDisplayPlayerInfo(Point coord) {
    
    try {

      // case 1: if its a player, show info
      if (world.hasGameStarted() && world.isCurrentPointHumanPlayer(coord)) {
        
        WorldCommand command = new DisplayPlayerInfo(view);
        command.go(world);
        
        // case 2: if its a neighbor, ask if they want to move
      } else if (world.hasGameStarted() 
          && world.isCurrentPointHumanPlayerNeighbor(coord)) {

        WorldCommand command = new HumanMove(view, coord);
        command.go(this.world);
      }
      view.resetFocus();
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.showErrorDialogue(e.getMessage());
    }
  }

}
