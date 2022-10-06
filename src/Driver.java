import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import world.controller.Controller;
import world.controller.DefaultController;
import world.model.DefaultRandomIntGenerator;
import world.model.DefaultWorld;
import world.model.RandomIntGenerator;
import world.model.World;
import world.view.DefaultGameView;
import world.view.GameView;

/**
 * Driver is a placeholder class that runs the world program.
 * @author Sherly Hartono
 */
public class Driver {

  /**
   * It will take a an input file text, and generate the world specified in the file.
   * @param args will take in the input file tex
   */
  public static void main(String[] args) {

    // to run in console
    // String filePath = "./res/mansion.txt";
    // int maxTurn = 4;
    
    // to run with jar
    String filePath = args[0];
    int maxTurn = Integer.valueOf(args[1]);
    
    RandomIntGenerator randGen = new DefaultRandomIntGenerator();
    // 1. Try read file and create world
    try {
      Reader file = new FileReader(filePath);   
     
      World m = new DefaultWorld(file, randGen, maxTurn, true);

      GameView gameView = new DefaultGameView();
      
      Controller c = new DefaultController(m, gameView);
      
      c.display();
      
      file.close();
    } catch (IOException io) {
      System.out.println("Cannot find file or close file");
    }   
  }
}

