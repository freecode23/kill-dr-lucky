import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.io.StringReader;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import world.model.DefaultItem;
import world.model.DefaultPlayer;
import world.model.DefaultSpace;
import world.model.DefaultTargetCharc;
import world.model.DefaultWorld;
import world.model.Item;
import world.model.Player;
import world.model.RandomIntGenerator;
import world.model.RowCol;
import world.model.Space;
import world.model.TargetCharacter;
import world.model.World;


/**
 * Test class for World Implementation class.
 * We will be using String Reader instead of reading from file for our input
 */

public class ModelTest {
  private World worldDisnu;
  private Readable in;
  private RandomIntGenerator ranGen;

  /**
   * This method is providing short-hand way of creating instances of a new
   * Item object.
   * @param name the name of the target
   * @param health the health of the target
   * @return a new instance of an target character object
   */
  protected TargetCharacter targetHelper(String name, int health, int maxSpace) {
    return new DefaultTargetCharc(name, health, maxSpace);
  }
  
  /**
   * This method is providing short-hand way of creating instances of a new
   * Item object.
   * @param name the name of the item.
   * @param damagePoint the amount of damage to the target character.
   * @return a new instance of an Item object
   */
  protected Item itemHelper(String name, int itemIndex, int damagePoint) {
    return new DefaultItem(name, itemIndex, damagePoint);
  }
  
  /**
   * This method is providing short-hand way of creating instances of a new
   * Player object.
   * @param name the name of the item.
   * @return the Player object
   */
  protected Player playerHelper(String name, Space spaceObject, int maxItem) {
    return new DefaultPlayer(name, spaceObject, maxItem);
  }
  
  /**
   * This method is providing short-hand way of creating instances of a new
   * Item object.
   * @param spaceIndex the index of the space.
   * @param name the name of the space.
   * @param upLeft the upper left coordinates of the space
   * @param lowRight the lower right coordinates of the space
   * @param items the list of items inside this space.
   * @return Space object
   */
  protected Space spaceHelper(int spaceIndex, String name, RowCol upLeft, 
      RowCol lowRight, List<Item> items) {
    return new DefaultSpace(spaceIndex, name, upLeft, lowRight);
  }
  
  /**
   * This method is providing short-hand way of creating instances of a new
   * worldImpl object.
   * @param maxTurn the maximum turn
   * @return the world object
   */
  protected World worldHelper(Readable in, RandomIntGenerator ranGen,
      int maxTurn, boolean isPetDfs) {
    return new DefaultWorld(in, ranGen, maxTurn, isPetDfs);
  }
  
  /**
   * This method is providing short-hand way of creating instances of a new
   * mock RandomIntGen object.
   * @return the RandomIntGen object
   */
  protected RandomIntGenerator randomIntGenHelper(int ... varargs) {
    return new MockRandomIntGenerator(varargs);
  }
  
  /**
   * Instantiate the world object for test.
   */
  @Before
  public void setUp() {
    in = new StringReader(
         "15 16 Northeastern X Disney Land\n"
        + "50 Prof Jump\n" 
        + "Fortune the Cat\n"
        + " 4\n"
        + " 3  1  5  2 Archery Range\n"
        + " 0  3  2  4 West Village H\n"
        + " 0  0  2  2 Zombie VR\n"
        + " 6  1 10  8 Cabot Testing Center\n"    
        + " 4\n"
        + " 0 3 Suction Cup Arrows\n"  // item 0 - archery - 0
        + " 0 10 Bow\n"                // item 1 - archery - 0
        + " 1 10 Deadline Extension\n" // item 2 - west village - 1
        + " 2 2 Motion sick VR headset\n"); // item 3 - zombie - 2
    ranGen = randomIntGenHelper(
        2, // human: max  item
        0, 2,          // player1_cpu: space, max item
        0, 1, 2, 3, 4, // name
        2, 2, 1);   
    worldDisnu = worldHelper(in, ranGen, 5, false);
  }
  
  @Test
  public void testPetMoveOnDfs() {
    in = new StringReader(
        "15 16 Northeastern X Disney Land\n"
       + "50 Prof Jump\n" 
       + "Fortune the Cat\n"
       + " 4\n"
       + " 3  1  5  2 Archery Range\n"
       + " 0  3  2  4 West Village H\n"
       + " 0  0  2  2 Zombie VR\n"
       + " 6  1 10  8 Cabot Testing Center\n"    
       + " 4\n"
       + " 0 3 Suction Cup Arrows\n"  // item 0 - archery - 0
       + " 0 10 Bow\n"                // item 1 - archery - 0
       + " 1 10 Deadline Extension\n" // item 2 - west village - 1
       + " 2 2 Motion sick VR headset\n"); // item 3 - zombie - 2
    
    ranGen = randomIntGenHelper(
        2, // human: max  item
        0, 2,          // player1_cpu: space, max item
        0, 1, 2, 3, 4, // name
        2, 2, 1);  
    
    worldDisnu = worldHelper(in, ranGen, 22, true);
    worldDisnu.addHumanPlayer("sherly", 0);
    worldDisnu.startTheGame();
    String expected = "Archery Range-0\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. Suction Cup Arrows 3\n"
        + "           2. Bow 10\n"
        + "\n"
        + "PLAYERS\n"
        + "---------------\n"
        + "           1. sherly, max item: 2\n"
        + "\n"
        + "\n"
        + "PET\n"
        + "---------------\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Zombie VR-2 <<<\n"
        + "           Item:\n"
        + "           1. Motion Sick VR Headset 2\n"
        + "\n"
        + ">>> Cabot Testing Center-3 <<<\n"
        + "\n"
        + "";
    String actual = worldDisnu.getSpaceInfo(0); 
    assertEquals(expected, actual);

    worldDisnu.makeHumanLook();
    expected = "Zombie VR-2\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. Motion Sick VR Headset 2\n"
        + "\n"
        + "PLAYERS\n"
        + "---------------\n"
        + "\n"
        + "PET\n"
        + "---------------\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Archery Range-0 <<<\n"
        + "           Item:\n"
        + "           1. Suction Cup Arrows 3\n"
        + "           2. Bow 10\n"
        + "\n"
        + "           Players:\n"
        + "           1. sherly, max item: 2\n"
        + "\n"
        + "\n"
        + ">>> West Village H-1 <<<\n"
        + "           Item:\n"
        + "           1. Deadline Extension 10\n"
        + "\n"
        + "";
    actual = worldDisnu.getSpaceInfo(2); 
    assertEquals(expected, actual);
    
    worldDisnu.makeHumanLook();
    expected = "West Village H-1\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. Deadline Extension 10\n"
        + "\n"
        + "PLAYERS\n"
        + "---------------\n"
        + "\n"
        + "PET\n"
        + "---------------\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Zombie VR-2 <<<\n"
        + "           Item:\n"
        + "           1. Motion Sick VR Headset 2\n"
        + "\n"
        + "";
    actual = worldDisnu.getSpaceInfo(1); 
    assertEquals(expected, actual);
    
    
    worldDisnu.makeHumanLook();
    expected = "Zombie VR-2\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. Motion Sick VR Headset 2\n"
        + "\n"
        + "PLAYERS\n"
        + "---------------\n"
        + "\n"
        + "PET\n"
        + "---------------\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Archery Range-0 <<<\n"
        + "           Item:\n"
        + "           1. Suction Cup Arrows 3\n"
        + "           2. Bow 10\n"
        + "\n"
        + "           Players:\n"
        + "           1. sherly, max item: 2\n"
        + "\n"
        + "\n"
        + ">>> West Village H-1 <<<\n"
        + "           Item:\n"
        + "           1. Deadline Extension 10\n"
        + "\n"
        + "";
    actual = worldDisnu.getSpaceInfo(2); 
    assertEquals(expected, actual);
    
    
    worldDisnu.makeHumanLook();
    expected = "Archery Range-0\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. Suction Cup Arrows 3\n"
        + "           2. Bow 10\n"
        + "\n"
        + "PLAYERS\n"
        + "---------------\n"
        + "           1. sherly, max item: 2\n"
        + "\n"
        + "\n"
        + "PET\n"
        + "---------------\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Zombie VR-2 <<<\n"
        + "           Item:\n"
        + "           1. Motion Sick VR Headset 2\n"
        + "\n"
        + ">>> Cabot Testing Center-3 <<<\n"
        + "\n"
        + "";
    actual = worldDisnu.getSpaceInfo(0); 
    assertEquals(expected, actual);
    
    worldDisnu.makeHumanLook();
    expected = "Cabot Testing Center-3\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "\n"
        + "PLAYERS\n"
        + "---------------\n"
        + "\n"
        + "PET\n"
        + "---------------\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Archery Range-0 <<<\n"
        + "           Item:\n"
        + "           1. Suction Cup Arrows 3\n"
        + "           2. Bow 10\n"
        + "\n"
        + "           Players:\n"
        + "           1. sherly, max item: 2\n"
        + "\n"
        + "\n"
        + "";
    actual = worldDisnu.getSpaceInfo(3); 
    assertEquals(expected, actual);
    
    
    worldDisnu.makeHumanLook();
    expected = "Archery Range-0\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. Suction Cup Arrows 3\n"
        + "           2. Bow 10\n"
        + "\n"
        + "PLAYERS\n"
        + "---------------\n"
        + "           1. sherly, max item: 2\n"
        + "\n"
        + "\n"
        + "PET\n"
        + "---------------\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Zombie VR-2 <<<\n"
        + "           Item:\n"
        + "           1. Motion Sick VR Headset 2\n"
        + "\n"
        + ">>> Cabot Testing Center-3 <<<\n"
        + "\n"
        + "";
    actual = worldDisnu.getSpaceInfo(0); 
    assertEquals(expected, actual);
  }
  
  
  // 0. test valid and invalid world
  @Test
  public void testParsing() {

    // size 15 X 16
    String expected = "Name: Northeastern X Disney Land\n"
        + "Size: 15 X 16\n"
        + "Target: Prof Jump's health is 50\n"
        + "Target's Position: 0\n"
        + "Number of Rooms: 4\n"
        + "Number of Items: 4\n";
        
    assertEquals(expected, worldDisnu.toString());
    
    
    // Size 500 X 500
    expected = ""
        + "Name: Northeastern X Disney Land\n"
        + "Size: 500 X 500\n"
        + "Target: Prof Jump's health is 50\n"
        + "Target's Position: 0\n"
        + "Number of Rooms: 4\n"
        + "Number of Items: 3\n";
    
    in = new StringReader(
        "500 500 Northeastern X Disney Land\n"
        + "50 Prof Jump\n"
        + " Fortune the Cat\n"    
        + " 4\n"
        + " 3  1  5  2 Archery Range\n"
        + " 0  3  2  4 West Village H\n"
        + " 0  0  2  2 Zombie VR\n"
        + " 6  1 10  8 Cabot Testing Center\n"
        + " 3\n"
        + " 0 3 Suction Cup Arrows\n"
        + " 1 10 Deadline Extension\n"
        + " 2 2 Motion sick VR headset\n");

    assertEquals(expected, worldHelper(in, ranGen, 5, false).toString());
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testZeroItem() {
    in = new StringReader(
        "500 500 Northeastern X Disney Land\n"
        + "50 Prof Jump\n"
        + " Fortune the Cat"
        + " 3\n"
        + " 3  1  5  2 Archery Range\n"
        + " 0  3  2  4 West Village H\n"
        + " 0  0  2  2 Zombie VR\n"
        + " 0\n"
        + " 0 3 Suction Cup Arrows\n"
        + " 1 10 Deadline Extension\n"
        + " 2 2 Motion sick VR headset\n");
    worldHelper(in, ranGen, 5, false);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testZeroSpace() {
    in = new StringReader("500 500 Northeastern X Disney Land\n"
        + "50 Prof Jump\n"
        + " Fortune the Cat"
        + " 0\n"
        + " 3  1  5  2 Archery Range\n"
        + " 0  3  2  4 West Village H\n"
        + " 0  0  2  2 Zombie VR\n"
        + " 3\n"
        + " 0 3 Suction Cup Arrows\n"
        + " 1 10 Deadline Extension\n"
        + " 2 2 Motion sick VR headset\n");
    worldHelper(in, ranGen, 5, false);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testItemRoomIndexEqualToTotalSpace() {
    in = new StringReader("500 500 Northeastern X Disney Land\n"
        + "50 Prof Jump\n"
        + " Fortune the Cat"
        + " 2\n"
        + " 3  1  5  2 Archery Range\n"
        + " 0  3  2  4 West Village H\n"
        + " 0  0  2  2 Zombie VR\n"
        + " 3\n"
        + " 0 3 Suction Cup Arrows\n"
        + " 1 10 Deadline Extension\n"
        + " 2 2 Motion sick VR headset\n");
    worldHelper(in, ranGen, 5, false);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testItemRoomIndexGreaterThanTotalSpace() {
    in = new StringReader("500 500 Northeastern X Disney Land\n"
        + "50 Prof Jump\n"
        + " Fortune the Cat"
        + " 2\n"
        + " 3  1  5  2 Archery Range\n"
        + " 0  3  2  4 West Village H\n"
        + " 0  0  2  2 Zombie VR\n"
        + " 4\n"
        + " 0 3 Suction Cup Arrows\n"
        + " 1 10 Deadline Extension\n"
        + " 4 2 Motion sick VR headset\n");
    worldHelper(in, ranGen, 5, false);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void actualAndGivenItemsNumberMismatch() {
    in = new StringReader("500 500 Northeastern X Disney Land\n"
        + "50 Prof Jump\n"
        + " Fortune the Cat"
        + " 2\n"
        + " 3  1  5  2 Archery Range\n"
        + " 0  3  2  4 West Village H\n"
        + " 0  0  2  2 Zombie VR\n"
        + " 4\n"
        + " 0 3 Suction Cup Arrows\n"
        + " 1 10 Deadline Extension\n"
        + " 2 2 Motion sick VR headset\n");
    worldHelper(in, ranGen, 5, false);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void actualAndGivenSpaceNumberMismatch() {
    in = new StringReader("500 500 Northeastern X Disney Land\n"
        + "50 Prof Jump\n"
        + " Fortune the Cat"
        + " 2\n"
        + " 3  1  5  2 Archery Range\n"
        + " 0  3  2  4 West Village H\n"
        + " 0  0  2  2 Zombie VR\n"
        + " 3\n"
        + " 0 3 Suction Cup Arrows\n"
        + " 1 10 Deadline Extension\n"
        + " 2 2 Motion sick VR headset\n");
    
    worldHelper(in, ranGen, 5, false);
  }

  @Test
  public void testCorrectNeighborPlacement() {
    String expected = "Archery Range-0\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. Suction Cup Arrows 3\n"
        + "           2. Bow 10\n"
        + "\n"
        + "PLAYERS\n"
        + "---------------\n"
        + "\n"
        + "PET\n"
        + "---------------\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Zombie VR-2 <<<\n"
        + "           Item:\n"
        + "           1. Motion Sick VR Headset 2\n"
        + "\n"
        + ">>> Cabot Testing Center-3 <<<\n"
        + "\n"
        + "";
    
    String actual = worldDisnu.getSpaceInfo(0);
    assertEquals(expected, actual);
    
    
    expected = "West Village H-1\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. Deadline Extension 10\n"
        + "\n"
        + "PLAYERS\n"
        + "---------------\n"
        + "\n"
        + "PET\n"
        + "---------------\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Zombie VR-2 <<<\n"
        + "           Item:\n"
        + "           1. Motion Sick VR Headset 2\n"
        + "\n"
        + "";

    actual = worldDisnu.getSpaceInfo(1);
    assertEquals(expected, actual);
    
    expected = "Zombie VR-2\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. Motion Sick VR Headset 2\n"
        + "\n"
        + "PLAYERS\n"
        + "---------------\n"
        + "\n"
        + "PET\n"
        + "---------------\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Archery Range-0 <<<\n"
        + "           Item:\n"
        + "           1. Suction Cup Arrows 3\n"
        + "           2. Bow 10\n"
        + "\n"
        + "           Pet:\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "\n"
        + ">>> West Village H-1 <<<\n"
        + "           Item:\n"
        + "           1. Deadline Extension 10\n"
        + "\n"
        + "";

    actual = worldDisnu.getSpaceInfo(2);
    assertEquals(expected, actual);
    
    
    expected = "Cabot Testing Center-3\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "\n"
        + "PLAYERS\n"
        + "---------------\n"
        + "\n"
        + "PET\n"
        + "---------------\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Archery Range-0 <<<\n"
        + "           Item:\n"
        + "           1. Suction Cup Arrows 3\n"
        + "           2. Bow 10\n"
        + "\n"
        + "           Pet:\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "\n"
        + "";

    actual = worldDisnu.getSpaceInfo(3);
    assertEquals(expected, actual);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testDuplicateCoordinates() {
    in = new StringReader("500 500 Northeastern X Disney Land\n"
        + "50 Prof Jump\n"
        + " 3\n"
        + " 3  1  5  2 Archery Range\n"
        + " 3  1  5  2 West Village H\n"
        + " 0  0  2  2 Zombie VR\n"
        + " 3\n"
        + " 0 3 Suction Cup Arrows\n"
        + " 1 10 Deadline Extension\n"
        + " 2 2 Motion sick VR headset\n");
    
    worldHelper(in, ranGen, 5, false);
  }
  
  // 1. Add Human Player Test
  @Test
  public void testAdd1HumanPlayerToList() {
    // 1 add 1 player
    // 1a check status
    String expected = "Sherly has been successfuly added to Archery Range";
    String actual = worldDisnu.getResult().getActionResult();
    
    // will use random number 5 for max item
    worldDisnu.addHumanPlayer("Sherly", 0); 
    worldDisnu.startTheGame();
    // 1b check if space also has this player
    expected = "Archery Range-0\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. Suction Cup Arrows 3\n"
        + "           2. Bow 10\n"
        + "\n"
        + "PLAYERS\n"
        + "---------------\n"
        + "           1. Sherly, max item: 2\n"
        + "\n"
        + "\n"
        + "PET\n"
        + "---------------\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Zombie VR-2 <<<\n"
        + "           Item:\n"
        + "           1. Motion Sick VR Headset 2\n"
        + "\n"
        + ">>> Cabot Testing Center-3 <<<\n"
        + "\n"
        + "";
    
    
    actual = worldDisnu.getSpaceInfo(0);  
    assertEquals(expected, actual);
    
    
  }
  
  @Test
  public void testAdd2HumanPlayerToList() {

    String expected = "Archery Range-0\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. Suction Cup Arrows 3\n"
        + "           2. Bow 10\n"
        + "\n"
        + "PLAYERS\n"
        + "---------------\n"
        
        + "           1. Ai, max item: 2\n"
        + "\n"
        + "           2. Sherly, max item: 0\n"
        + "\n"
        + "\n"
        + "PET\n"
        + "---------------\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Zombie VR-2 <<<\n"
        + "           Item:\n"
        + "           1. Motion Sick VR Headset 2\n"
        + "\n"
        + ">>> Cabot Testing Center-3 <<<\n"
        + "\n"
        + "";

    worldDisnu.addHumanPlayer("Ai", 0);
    worldDisnu.addHumanPlayer("Sherly", 0); 
    worldDisnu.startTheGame();
    
    String actual = worldDisnu.getSpaceInfo(0);  
    assertEquals(expected, actual);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testAddDuplicateHumanPlayer() {
    worldDisnu.addHumanPlayer("Ai", 0);
    worldDisnu.addHumanPlayer("Ai", 0);
    String expected = worldDisnu.getResult().getActionResult();
    String actual = "There is a player with duplicate name";
    assertEquals(expected, actual);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testAddHumanPlayerToNonExistingSpace() {
    worldDisnu.addHumanPlayer("Ai", 5);
    worldDisnu.startTheGame();
  }
  
  
  // 2. Human move
  @Test
  public void testHumanMoveValid() {
    
    // move from archery to zombie
    worldDisnu.addHumanPlayer("Sherly", 0);
    worldDisnu.startTheGame();
    // 1. check message
    //    worldDisnu.makeHumanMove(2);
    worldDisnu.makeHumanMovePoint(new Point(2, 3));

    String actual = worldDisnu.getResult().getActionResult();
    
    
    String expected = "Sherly has successfuly moved to Zombie VR";
    assertEquals(expected, actual);

    
    // 2. check status make sure target has moved
    expected = "Target is at West Village H   Sherly's turn   Turn:2/5";
    actual = worldDisnu.getResult().getStatus();
    assertEquals(expected, actual);
    
    // 4. check current space
    
    expected = "Zombie VR-2\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. Motion Sick VR Headset 2\n"
        + "\n"
        + "PLAYERS\n"
        + "---------------\n"
        + "           1. Sherly, max item: 2\n"
        + "\n"
        + "\n"
        + "PET\n"
        + "---------------\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Archery Range-0 <<<\n"
        + "           Item:\n"
        + "           1. Suction Cup Arrows 3\n"
        + "           2. Bow 10\n"
        + "\n"
        + "           Pet:\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "\n"
        + ">>> West Village H-1 <<<\n"
        + "           Item:\n"
        + "           1. Deadline Extension 10\n"
        + "\n"
        + "";
    actual = worldDisnu.getSpaceInfo(2);
    assertEquals(expected, actual);
    
    // 4. check previous space make sure this player is not in the list
    // and the neighbor is showin g the player
    expected = "Archery Range-0\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. Suction Cup Arrows 3\n"
        + "           2. Bow 10\n"
        + "\n"
        + "PLAYERS\n"
        + "---------------\n"
        + "\n"
        + "PET\n"
        + "---------------\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Zombie VR-2 <<<\n"
        + "           Item:\n"
        + "           1. Motion Sick VR Headset 2\n"
        + "\n"
        + "           Players:\n"
        + "           1. Sherly, max item: 2\n"
        + "\n"
        + "\n"
        + ">>> Cabot Testing Center-3 <<<\n"
        + "\n"
        + "";
    actual = worldDisnu.getSpaceInfo(0);
    assertEquals(expected, actual);
    
  }
  
  @Test(expected = IllegalStateException.class)
  public void testHumanMoveNotTurn() {
    
    worldDisnu.addCpuPlayer();
    worldDisnu.addHumanPlayer("Sherly", 0);
    worldDisnu.startTheGame();
    String expected = "There is no player in the game or max turn reached "
        + "or current player turn is a cpu\n";
    
    
    worldDisnu.makeHumanMovePoint(new Point(2, 3));

    String actual = worldDisnu.getResult().getActionResult();
    assertEquals(expected, actual);
  }
  
  @Test(expected = IllegalStateException.class)
  public void testCpuMoveNotTurn() {
    
   
    worldDisnu.addHumanPlayer("Sherly", 0);
    worldDisnu.addCpuPlayer();
    worldDisnu.startTheGame();

    worldDisnu.makeCpuTakeTurn(); // target at 1

  }
  
  @Test
  public void testHumanPlayerMaxTurnReached() {
    in = new StringReader(
        "15 16 Northeastern X Disney Land\n"
       + "50 Prof Jump\n" 
       + "Fortune the Cat\n"
       + " 4\n"
       + " 3  1  5  2 Archery Range\n"
       + " 0  3  2  4 West Village H\n"
       + " 0  0  2  2 Zombie VR\n"
       + " 6  1 10  8 Cabot Testing Center\n"    
       + " 4\n"
       + " 0 3 Suction Cup Arrows\n" // in archery range
       + " 0 10 Bow\n" // in archery 
       + " 1 10 Deadline Extension\n" // in west village
       + " 2 2 Motion sick VR headset\n"); // in zombie vr

    worldDisnu = worldHelper(in, ranGen, 3, false);
    // max 3 turns
    worldDisnu.addHumanPlayer("Sherly", 0);
    worldDisnu.startTheGame();
    // 1. status
    String expected = "Target is at Archery Range   Sherly's turn   Turn:1/3";
    String actual = worldDisnu.getResult().getStatus();
    assertEquals(expected, actual);
    
    
    // 2. space info
    expected = "Archery Range-0\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. Suction Cup Arrows 3\n"
        + "           2. Bow 10\n"
        + "\n"
        + "PLAYERS\n"
        + "---------------\n"
        + "           1. Sherly, max item: 2\n"
        + "\n"
        + "\n"
        + "PET\n"
        + "---------------\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Zombie VR-2 <<<\n"
        + "           Item:\n"
        + "           1. Motion Sick VR Headset 2\n"
        + "\n"
        + ">>> Cabot Testing Center-3 <<<\n"
        + "\n"
        + "";
    actual = worldDisnu.getSpaceInfo(0);
    assertEquals(expected, actual);
    
    
    worldDisnu.makeHumanMovePoint(new Point(314, 472));
    expected = "Target is at West Village H   Sherly's turn   Turn:2/3";
    actual = worldDisnu.getResult().getStatus();
    assertEquals(expected, actual);
    
    
    worldDisnu.makeHumanMovePoint(new Point(116, 250));
    expected = "Target is at Zombie VR   Sherly's turn   Turn:3/3";
    actual = worldDisnu.getResult().getStatus();
    assertEquals(expected, actual);

    worldDisnu.makeHumanLook(); // 3rd move (last)
    expected = "Game Over! Maximum turn reached. Prof Jump escaped and nobody wins\n"
        + "";
    actual = worldDisnu.getResult().getStatus();
    assertEquals(expected, actual);

  }
  
  @Test
  public void testHumanMoveNotNeighbor() {    
    worldDisnu.addHumanPlayer("Sherly", 0);
    worldDisnu.startTheGame();
    String expected = "Sherly has failed to move because space is not neighbor";
    worldDisnu.makeHumanMovePoint(new Point(250, 112));
    String actual = worldDisnu.getResult().getActionResult();
    assertEquals(expected, actual);
    
    // check status make sure target and turn has not changed
    expected = "Target is at Archery Range   Sherly's turn   Turn:1/5";
    actual = worldDisnu.getResult().getStatus();
    assertEquals(expected, actual);
    
  }
  
  @Test(expected = IllegalStateException.class)
  public void testHumanMoveNoPlayer() {    
    worldDisnu.makeHumanMovePoint(new Point(250, 112));
  }
  
  @Test
  public void testOneTurnPerPlayer() {
    worldDisnu.addHumanPlayer("Sherly", 3);
    worldDisnu.addHumanPlayer("Ai", 0);
    worldDisnu.startTheGame();
    
    // sherly take turn
    worldDisnu.makeHumanLook();
    String actual = worldDisnu.getResult().getActionResult();
    String expected = "Sherly has looked around";
    assertEquals(expected, actual);
    
    // next turn should be Ai 
    worldDisnu.makeHumanMovePet(1);
    actual = worldDisnu.getResult().getActionResult();
    expected = "Ai has moved Fortune the Cat to West Village H";
    assertEquals(expected, actual);
    
    // next turn should be Sh
    worldDisnu.makeHumanMovePet(2);
    actual = worldDisnu.getResult().getActionResult();
    expected = "Sherly has moved Fortune the Cat to Zombie VR";
    assertEquals(expected, actual);
  }
   
  // pet effect: can still move to space with pet
  @Test
  public void testHumanMoveToPlaceWithPet() {
    worldDisnu.addHumanPlayer("Sherly", 3);
    
    String expected = "Sherly has successfuly moved to Archery Range";
    worldDisnu.startTheGame();
    // move to archery
    worldDisnu.makeHumanMovePoint(new Point(116, 250));
    String actual = worldDisnu.getResult().getActionResult();
    assertEquals(expected, actual);
    
    expected = "Archery Range-0\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. Suction Cup Arrows 3\n"
        + "           2. Bow 10\n"
        + "\n"
        + "PLAYERS\n"
        + "---------------\n"
        + "           1. Sherly, max item: 2\n"
        + "\n"
        + "\n"
        + "PET\n"
        + "---------------\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Zombie VR-2 <<<\n"
        + "           Item:\n"
        + "           1. Motion Sick VR Headset 2\n"
        + "\n"
        + ">>> Cabot Testing Center-3 <<<\n"
        + "\n"
        + "";
    actual = worldDisnu.getSpaceInfo(0);
    assertEquals(expected, actual);
  }

  // 3. Human look
  //pet effect: can still see current space
  @Test
  public void testHumanLook() {
    
    // target is in same sapce
    worldDisnu.addHumanPlayer("Sherly", 0);
    String expected = "LOOKING AROUND FROM YOUR ROOM: Archery Range-0\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. Suction Cup Arrows 3\n"
        + "           2. Bow 10\n"
        + "\n"
        + "PLAYER\n"
        + "---------------\n"
        + "           1. Sherly, max item: 2\n"
        + "\n"
        + "\n"
        + "PET\n"
        + "---------------\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Zombie VR-2 <<<\n"
        + "           Item:\n"
        + "           1. Motion Sick VR Headset 2\n"
        + "\n"
        + ">>> Cabot Testing Center-3 <<<\n"
        + "\n"
        + "";
        
    worldDisnu.startTheGame();
    worldDisnu.makeHumanLook();
    String actual = worldDisnu.getResult().getLookAroundText();
    assertEquals(expected, actual);
  }
  
  @Test
  public void testHumanLookNoItem() {
    // 1. set up 
    in = new StringReader(
        "15 16 Northeastern X Disney Land\n"
       + "50 Prof Jump\n" 
       + " Fortune the Cat\n"
       + " 4\n"
       + " 3  1  5  2 Archery Range\n"
       + " 0  3  2  4 West Village H\n"
       + " 0  0  2  2 Zombie VR\n"
       + " 6  1 10  8 Cabot Testing Center\n"    
       + " 1\n"
       + " 1 3 Suction Cup Arrows\n"); // in zombie vr
    ranGen = randomIntGenHelper(
        2, // human: max  item
        0, 2,          // player1_cpu: space, max item
        0, 1, 2, 3, 4, // name
        2, 2, 1);   
    worldDisnu = worldHelper(in, ranGen, 5, false);
    
    worldDisnu.addHumanPlayer("shelry", 0);
    worldDisnu.startTheGame();
    worldDisnu.makeHumanLook();
    String actual  = worldDisnu.getResult().getLookAroundText();
    String expected = "LOOKING AROUND FROM YOUR ROOM: Archery Range-0\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "\n"
        + "PLAYER\n"
        + "---------------\n"
        + "           1. shelry, max item: 2\n"
        + "\n"
        + "\n"
        + "PET\n"
        + "---------------\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Zombie VR-2 <<<\n"
        + "\n"
        + ">>> Cabot Testing Center-3 <<<\n"
        + "\n"
        + "";
    assertEquals(expected, actual);
  }
   
  @Test
  public void testHumanLookSameSpaceWithNeighborPet() {
    // 1. set up 
    in = new StringReader(
        "15 16 Northeastern X Disney Land\n"
       + "50 Prof Jump\n" 
       + " Fortune the Cat\n"
       + " 4\n"
       + " 3  1  5  2 Archery Range\n"
       + " 0  3  2  4 West Village H\n"
       + " 0  0  2  2 Zombie VR\n"
       + " 6  1 10  8 Cabot Testing Center\n"    
       + " 4\n"
       + " 0 3 Suction Cup Arrows\n" // in archery range
       + " 0 10 Bow\n" // in archery 
       + " 1 10 Deadline Extension\n" // in west village
       + " 2 2 Motion sick VR headset\n"); // in zombie vr
    
    ranGen = randomIntGenHelper(
        2, // human: max item 
        0, 3,          // cpu add: space, maxItem
        0, 1, 2, 3, 4, // cpu name
        1, 1, 1);      // cpu turn: random pet space, move to room 1th neighbor on the set
    
    worldDisnu = worldHelper(in, ranGen, 5, false);
    worldDisnu.addHumanPlayer("Sherly", 2); // add to zombie
    worldDisnu.addCpuPlayer();
    worldDisnu.startTheGame();
    
    // 3. Human should not be able to see pet, player and target in space
    worldDisnu.makeHumanLook();
    String actual = worldDisnu.getResult().getLookAroundText();
    String expected = "LOOKING AROUND FROM YOUR ROOM: Zombie VR-2\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. Motion Sick VR Headset 2\n"
        + "\n"
        + "PLAYER\n"
        + "---------------\n"
        + "           1. Sherly, max item: 2\n"
        + "\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> West Village H-1 <<<\n"
        + "           Item:\n"
        + "           1. Deadline Extension 10\n"
        + "\n"
        + "";
        
    assertEquals(expected, actual);
  }
  
  // 4. Human pick item
  @Test
  public void testHumanPickValidItem() {
    worldDisnu.addHumanPlayer("Sherly", 0);
    worldDisnu.startTheGame();
    worldDisnu.makeHumanPickItem(0);
    String actual = worldDisnu.getResult().getActionResult();

    String expected = "Sherly has successfully picked Suction Cup Arrows\n";
    assertEquals(expected, actual);
    
    worldDisnu.makeHumanPickItem(1);
    actual = worldDisnu.getResult().getActionResult();

    expected = "Sherly has successfully picked Bow\n";
    assertEquals(expected, actual);
    
    worldDisnu.makeHumanLook();
    actual = worldDisnu.getResult().getLookAroundText();
    expected = "LOOKING AROUND FROM YOUR ROOM: Archery Range-0\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "\n"
        + "PLAYER\n"
        + "---------------\n"
        + "           1. Sherly, max item: 2\n"
        + "               > Bow 10\n"
        + "               > Suction Cup Arrows 3\n"
        + "\n"
        + "\n"
        + "PET\n"
        + "---------------\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Zombie VR-2 <<<\n"
        + "           Item:\n"
        + "           1. Motion Sick VR Headset 2\n"
        + "\n"
        + ">>> Cabot Testing Center-3 <<<\n"
        + "\n"
        + "";
    assertEquals(expected, actual);
    
    // check player description 
    actual = worldDisnu.getResult().getPlayerDescription();
    expected = "Sherly, max item: 2\n"
        + "               > Bow 10\n"
        + "               > Suction Cup Arrows 3\n"
        + "";
    assertEquals(expected, actual);
  }
  
  @Test
  public void testHumanPickItemNotInRoom() {
    worldDisnu.addHumanPlayer("Sherly", 0);
    worldDisnu.startTheGame();
    String expected = "Sherly has failed to pick item. "
        + "Either item is not in space, or maximum item held is reached";
    worldDisnu.makeHumanPickItem(2);
    
    String actual = worldDisnu.getResult().getActionResult();

    assertEquals(expected, actual);
    
    // check player description 
    actual = worldDisnu.getResult().getPlayerDescription();
    expected = "Sherly, max item: 2\n"
        + "";
    assertEquals(expected, actual);
    
  }
  
  @Test
  public void testHumanPickMoreThanMaxItem() {
    in = new StringReader(
        "15 16 Northeastern X Disney Land\n"
        + "50 Prof Jump\n"
        + "Fortune the Cat\n"
        + " 4\n"
        + " 3  1  5  2 Archery Range\n"
        + " 0  3  2  4 West Village H\n"
        + " 0  0  2  2 Zombie VR\n"
        + " 6  1 10  8 Cabot Testing Center\n"    
        + " 3\n"
        + " 0 3 Suction Cup Arrows\n"
        + " 0 10 Deadline Extension\n"
        + " 0 2 Motion sick VR headset"
        + " 0 5 Metal bow"
        + " \n");
    
    ranGen = randomIntGenHelper(2);
    worldDisnu = worldHelper(in, ranGen, 3, false);
    worldDisnu.addHumanPlayer("Sherly", 0);
    worldDisnu.startTheGame();
    
    worldDisnu.makeHumanPickItem(0);
    worldDisnu.makeHumanPickItem(1);
    
    String expected = "Target is at Zombie VR   Sherly's turn   Turn:3/3";
    String actual = worldDisnu.getResult().getStatus();
    assertEquals(expected, actual);
        
    worldDisnu.makeHumanPickItem(2);
    actual = worldDisnu.getResult().getActionResult();
    expected = "Sherly has failed to pick item. "
        + "Either item is not in space, or maximum item held is reached";
    assertEquals(expected, actual);

    // check player description 
    actual = worldDisnu.getResult().getPlayerDescription();
    expected = "Sherly, max item: 2\n"
        + "               > Deadline Extension 10\n"
        + "               > Suction Cup Arrows 3\n"
        + "";
    assertEquals(expected, actual);
    
  }
  
  // 5. Human move pet
  @Test
  public void humanMovePetNotNeighbor() {
    // move from archery to zombie
    worldDisnu.addHumanPlayer("Sherly", 0);
    worldDisnu.startTheGame();
    // 1. check message
    worldDisnu.makeHumanMovePet(1);
    String actual = worldDisnu.getResult().getActionResult();
    String expected = "Sherly has moved Fortune the Cat to West Village H";
    assertEquals(expected, actual);

    // 2. check status make pet has moved
    expected = "Target is at West Village H   Sherly's turn   Turn:2/5";
    actual = worldDisnu.getResult().getStatus();
    assertEquals(expected, actual);
    
    
    // check if cat is present in the new space
    expected = "West Village H-1\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. Deadline Extension 10\n"
        + "\n"
        + "PLAYERS\n"
        + "---------------\n"
        + "\n"
        + "PET\n"
        + "---------------\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Zombie VR-2 <<<\n"
        + "           Item:\n"
        + "           1. Motion Sick VR Headset 2\n"
        + "\n"
        + "";
    actual = worldDisnu.getSpaceInfo(1);
    assertEquals(expected, actual);
    
    
  }
  
  @Test
  public void humanMovePetSameSpace() {
    // move from archery to zombie
    worldDisnu.addHumanPlayer("Sherly", 0);
    worldDisnu.startTheGame();
    
    // 1. check message
    worldDisnu.makeHumanMovePet(0);
    String actual = worldDisnu.getResult().getActionResult();
    String expected = "Sherly has moved Fortune the Cat to Archery Range";
    assertEquals(expected, actual);

    // 2. check status make sure target has moved
    expected = "Target is at West Village H   Sherly's turn   Turn:2/5";
    actual = worldDisnu.getResult().getStatus();
    assertEquals(expected, actual);
    
  }
  
  @Test
  public void testHumanMovePetDfs() {
    in = new StringReader(
        "15 16 Northeastern X Disney Land\n"
       + "50 Prof Jump\n" 
       + "Fortune the Cat\n"
       + " 4\n"
       + " 3  1  5  2 Archery Range\n"
       + " 0  3  2  4 West Village H\n"
       + " 0  0  2  2 Zombie VR\n"
       + " 6  1 10  8 Cabot Testing Center\n"    
       + " 4\n"
       + " 0 3 Suction Cup Arrows\n"  // item 0 - archery - 0
       + " 0 10 Bow\n"                // item 1 - archery - 0
       + " 1 10 Deadline Extension\n" // item 2 - west village - 1
       + " 2 2 Motion sick VR headset\n"); // item 3 - zombie - 2
    
    ranGen = randomIntGenHelper(
        2, // human: max  item
        0, 2,          // player1_cpu: space, max item
        0, 1, 2, 3, 4, // name
        2, 2, 1);  
    
    worldDisnu = worldHelper(in, ranGen, 22, true);
    worldDisnu.addHumanPlayer("sherly", 0);
    worldDisnu.startTheGame();
    worldDisnu.makeHumanMovePet(2);
    // pet will move immediately with dfs to room 0
    
    String expected = "Archery Range-0\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. Suction Cup Arrows 3\n"
        + "           2. Bow 10\n"
        + "\n"
        + "PLAYERS\n"
        + "---------------\n"
        + "           1. sherly, max item: 2\n"
        + "\n"
        + "\n"
        + "PET\n"
        + "---------------\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Zombie VR-2 <<<\n"
        + "           Item:\n"
        + "           1. Motion Sick VR Headset 2\n"
        + "\n"
        + ">>> Cabot Testing Center-3 <<<\n"
        + "\n"
        + "";
    String actual = worldDisnu.getSpaceInfo(0); 
    assertEquals(expected, actual);
    worldDisnu.makeHumanLook();
    
    expected = "Cabot Testing Center-3\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "\n"
        + "PLAYERS\n"
        + "---------------\n"
        + "\n"
        + "PET\n"
        + "---------------\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Archery Range-0 <<<\n"
        + "           Item:\n"
        + "           1. Suction Cup Arrows 3\n"
        + "           2. Bow 10\n"
        + "\n"
        + "           Players:\n"
        + "           1. sherly, max item: 2\n"
        + "\n"
        + "\n"
        + "";
    actual = worldDisnu.getSpaceInfo(3); 
    assertEquals(expected, actual);
    worldDisnu.makeHumanLook();
    
    expected = "Archery Range-0\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. Suction Cup Arrows 3\n"
        + "           2. Bow 10\n"
        + "\n"
        + "PLAYERS\n"
        + "---------------\n"
        + "           1. sherly, max item: 2\n"
        + "\n"
        + "\n"
        + "PET\n"
        + "---------------\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Zombie VR-2 <<<\n"
        + "           Item:\n"
        + "           1. Motion Sick VR Headset 2\n"
        + "\n"
        + ">>> Cabot Testing Center-3 <<<\n"
        + "\n"
        + "";
    actual = worldDisnu.getSpaceInfo(0); 
    assertEquals(expected, actual);
    worldDisnu.makeHumanLook();
    
    expected = "Zombie VR-2\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. Motion Sick VR Headset 2\n"
        + "\n"
        + "PLAYERS\n"
        + "---------------\n"
        + "\n"
        + "PET\n"
        + "---------------\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Archery Range-0 <<<\n"
        + "           Item:\n"
        + "           1. Suction Cup Arrows 3\n"
        + "           2. Bow 10\n"
        + "\n"
        + "           Players:\n"
        + "           1. sherly, max item: 2\n"
        + "\n"
        + "\n"
        + ">>> West Village H-1 <<<\n"
        + "           Item:\n"
        + "           1. Deadline Extension 10\n"
        + "\n"
        + "";
    actual = worldDisnu.getSpaceInfo(2); 
    assertEquals(expected, actual);
    worldDisnu.makeHumanLook();

    
    expected = "West Village H-1\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. Deadline Extension 10\n"
        + "\n"
        + "PLAYERS\n"
        + "---------------\n"
        + "\n"
        + "PET\n"
        + "---------------\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Zombie VR-2 <<<\n"
        + "           Item:\n"
        + "           1. Motion Sick VR Headset 2\n"
        + "\n"
        + "";
    actual = worldDisnu.getSpaceInfo(1); 
    assertEquals(expected, actual);
    worldDisnu.makeHumanLook();
    
    expected = "Zombie VR-2\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. Motion Sick VR Headset 2\n"
        + "\n"
        + "PLAYERS\n"
        + "---------------\n"
        + "\n"
        + "PET\n"
        + "---------------\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Archery Range-0 <<<\n"
        + "           Item:\n"
        + "           1. Suction Cup Arrows 3\n"
        + "           2. Bow 10\n"
        + "\n"
        + "           Players:\n"
        + "           1. sherly, max item: 2\n"
        + "\n"
        + "\n"
        + ">>> West Village H-1 <<<\n"
        + "           Item:\n"
        + "           1. Deadline Extension 10\n"
        + "\n"
        + "";
    actual = worldDisnu.getSpaceInfo(2); 
    assertEquals(expected, actual);
    worldDisnu.makeHumanLook();
  }
  
  // 6. Human make Kill attempt
  @Test
  public void humanKillAttemptPokeSuccess() {
    worldDisnu.addHumanPlayer("Sherly", 0);
    worldDisnu.startTheGame();
    worldDisnu.makeHumanKill(-1);


    String actual = worldDisnu.getResult().getActionResult();

    String expected = "Sherly has successfuly attempted to kill Prof "
        + "Jump using eye poking. Prof Jump's health is now 49";
    assertEquals(expected, actual);
  }
  
  @Test(expected = IllegalStateException.class)
  public void humanKillAttemptTargetNotSameSpace() {
    worldDisnu.addHumanPlayer("Sherly", 1);
    worldDisnu.startTheGame();
    worldDisnu.makeHumanKill(-1);
    String actual = worldDisnu.getResult().getActionResult();
    String expected = "Target is not in the same space as current player. "
        + "Kill attempt is impossible";
    assertEquals(expected, actual);
  }
  
  @Test
  public void humanKillAttemptWeaponSucess() {
    worldDisnu.addHumanPlayer("Sherly", 1);
    worldDisnu.startTheGame();
    
    // pick item
    worldDisnu.makeHumanPickItem(2);
    worldDisnu.makeHumanKill(2);
    String actual = worldDisnu.getResult().getActionResult();
    String expected = "Sherly has successfuly attempted to kill Prof Jump using"
        + " Deadline Extension. Prof Jump's health is now 40";
    assertEquals(expected, actual);
  }
 
  
  /**
   * Attempt ending:
   * Human and target is at room 2
   * Cpu is at room 1 ( neighbor of room 2)
   * Pet is at room 0. 
   * Human try to kill target at room 2 by seen by cpu at room 1
   */
  
  @Test
  public void humanKillFailCanSeePlayer() {

    // 1. set up 
    in = new StringReader(
        "15 16 Northeastern X Disney Land\n"
       + "50 Prof Jump\n" 
       + " Fortune the Cat\n"
       + " 4\n"
       + " 3  1  5  2 Archery Range\n"
       + " 0  3  2  4 West Village H\n"
       + " 0  0  2  2 Zombie VR\n"
       + " 6  1 10  8 Cabot Testing Center\n"    
       + " 4\n"
       + " 0 3 Suction Cup Arrows\n" // in archery range
       + " 0 10 Bow\n" // in archery 
       + " 1 10 Deadline Extension\n" // in west village
       + " 2 2 Motion sick VR headset\n"); // in zombie vr
    
    ranGen = randomIntGenHelper(
        2, // human: max item 
        1, 3,          // cpu add: west village, maxItem
        0, 1, 2, 3, 4, // cpu name
        1, 1, 1,       // cpu turn
        1, 1, 1);      // cpu turn: random pet space, move to zombie
    
    worldDisnu = worldHelper(in, ranGen, 5, false);
    worldDisnu.addHumanPlayer("Sherly", 2); // add to zombie
    worldDisnu.addCpuPlayer();
    worldDisnu.startTheGame();
    // 2. game play:
    // check if cpu
    worldDisnu.makeHumanPickItem(3);              // target at 0
    worldDisnu.makeCpuTakeTurn(); // target at 1
    String actual = worldDisnu.getResult().getActionResult();
    String expected = "CPU_abcde have looked around.";
    assertEquals(expected, actual);
    
        
    worldDisnu.makeHumanKill(3); // target at 2
    actual = worldDisnu.getResult().getActionResult();
    expected = "Sherly has failed to kill"
        + " Prof Jump since there are players around.Target's health is still 50";
    assertEquals(expected, actual);
    
    worldDisnu.makeCpuTakeTurn(); // look around again
    
    // human look. should not be able to see archery
    // item should be gone
    worldDisnu.makeHumanLook();
    actual = worldDisnu.getResult().getLookAroundText();
    expected = "LOOKING AROUND FROM YOUR ROOM: Zombie VR-2\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "\n"
        + "PLAYER\n"
        + "---------------\n"
        + "           1. Sherly, max item: 2\n"
        + "\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> West Village H-1 <<<\n"
        + "           Item:\n"
        + "           1. Deadline Extension 10\n"
        + "\n"
        + "           Players:\n"
        + "           1. CPU_abcde, max item: 3\n"
        + "\n"
        + "\n"
        + "";
    assertEquals(expected, actual);
  }
 
  // pet effect: attempt failed
  @Test
  public void humanKillWeaponSeenCat() {
    // attempt ending:
    // pet is in room 0, cpu is at room 0 ,target us at room 2
    // room 0 and 2 are neighbors
    // player attempt at room 2 and seen by cpu at room 0
    in = new StringReader(
        "15 16 Northeastern X Disney Land\n"
       + "50 Prof Jump\n" 
       + " Fortune the Cat\n"
       + " 4\n"
       + " 3  1  5  2 Archery Range\n"
       + " 0  3  2  4 West Village H\n"
       + " 0  0  2  2 Zombie VR\n"
       + " 6  1 10  8 Cabot Testing Center\n"    
       + " 4\n"
       + " 0 3 Suction Cup Arrows\n" // in archery range
       + " 0 10 Bow\n" // in archery 
       + " 1 10 Deadline Extension\n" // in west village
       + " 2 2 Motion sick VR headset\n"); // in zombie vr
    
    ranGen = randomIntGenHelper(
        2, // human: max item 
        0, 3,          // cpu add: space, maxItem
        0, 1, 2, 3, 4, // cpu name
        1, 1, 1,       // cpu turn
        1, 1, 1);      // cpu turn: random pet space, move to room 1th neighbor on the set
    
    worldDisnu = worldHelper(in, ranGen, 5, false);
    worldDisnu.addHumanPlayer("Sherly", 2); // add to zombie
    worldDisnu.addCpuPlayer();
    worldDisnu.startTheGame();
    
    // 2. game play:
    // check if cpu
    worldDisnu.makeHumanPickItem(3); // target at 0 - pick up vr headset
    worldDisnu.makeCpuTakeTurn();    // target at 1
       
    worldDisnu.makeHumanKill(3); // target at 2 fail
    
    String expected = "Sherly has failed to kill Prof Jump since there are players "
        + "around.Target's health is still 50";
    String actual = worldDisnu.getResult().getActionResult();
    assertEquals(expected, actual);

    worldDisnu.makeCpuTakeTurn();          // cpu look again
    
    // Human look:
    // - should not be able to see Archery and Cpu player there
    // - item should be gone
    worldDisnu.makeHumanLook();
    actual = worldDisnu.getResult().getLookAroundText();
    expected = "LOOKING AROUND FROM YOUR ROOM: Zombie VR-2\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "\n"
        + "PLAYER\n"
        + "---------------\n"
        + "           1. Sherly, max item: 2\n"
        + "\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> West Village H-1 <<<\n"
        + "           Item:\n"
        + "           1. Deadline Extension 10\n"
        + "\n"
        + "";
    assertEquals(expected, actual);
  }
 
  // 7. add cpu
  @Test
  public void testAddMultiplePlayerToList() {
    
    //add 1
    in = new StringReader(
        "15 16 Northeastern X Disney Land\n"
       + "50 Prof Jump\n" 
       + " Fortune the Cat\n"
       + " 4\n"
       + " 3  1  5  2 Archery Range\n"
       + " 0  3  2  4 West Village H\n"
       + " 0  0  2  2 Zombie VR\n"
       + " 6  1 10  8 Cabot Testing Center\n"    
       + " 4\n"
       + " 0 3 Suction Cup Arrows\n" // in archery range
       + " 0 10 Bow\n" // in archery 
       + " 1 10 Deadline Extension\n" // in west village
       + " 2 2 Motion sick VR headset\n"); // in zombie vr
    
    ranGen = randomIntGenHelper(
        0, 3, // 1st cpu player initial space archery range 0, start max item 3
        0, 1, 2, 3, 4,  // name
        0, 3, // 2nd cpu player initial space archery range 0, start max item 3
        5, 6, 7, 8, 9,   // name
        2);  // human player max item 
    worldDisnu = worldHelper(in, ranGen, 3, false);
    worldDisnu.addCpuPlayer();

        
    
    // add 2 cpu players
    worldDisnu.addCpuPlayer();
    
    // add human player to the mix
    worldDisnu.addHumanPlayer("Sherly", 0);
    worldDisnu.startTheGame();
    
    // check display specific player
    String actual = worldDisnu.getResult().getPlayerDescription();
    String expected = "CPU_abcde, max item: 3\n";
    assertEquals(expected, actual);
    
    // check if the space display all 3 players
    actual = worldDisnu.getSpaceInfo(0);
    expected = "Archery Range-0\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. Suction Cup Arrows 3\n"
        + "           2. Bow 10\n"
        + "\n"
        + "PLAYERS\n"
        + "---------------\n"
        + "           1. CPU_abcde, max item: 3\n"
        + "\n"
        + "           2. CPU_fghij, max item: 3\n"
        + "\n"
        + "           3. Sherly, max item: 2\n"
        + "\n"
        + "\n"
        + "PET\n"
        + "---------------\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Zombie VR-2 <<<\n"
        + "           Item:\n"
        + "           1. Motion Sick VR Headset 2\n"
        + "\n"
        + ">>> Cabot Testing Center-3 <<<\n"
        + "\n"
        + "";
    assertEquals(expected, actual);

  }
  
  // 8. cpu command
  @Test
  public void testCpuPickItem() {
    // 1. world set up
    in = new StringReader(
        "15 16 Northeastern X Disney Land\n"
       + "50 Prof Jump\n" 
       + "Fortune the Cat\n"
       + " 4\n"
       + " 3  1  5  2 Archery Range\n"
       + " 0  3  2  4 West Village H\n"
       + " 0  0  2  2 Zombie VR\n"
       + " 6  1 10  8 Cabot Testing Center\n"    
       + " 4\n"
       + " 0 3 Suction Cup Arrows\n" // in archery range
       + " 0 10 Bow\n" // in archery 
       + " 1 10 Deadline Extension\n" // in west village
       + " 2 2 Motion sick VR headset\n"); // in zombie vr
    
    ranGen = randomIntGenHelper(
        0, 2,          // player1_cpu: space, max item
        0, 1, 2, 3, 4, // name
        4,             // player2_human: max item
        1, 3, 0);      // player1 turn: random pet space, action pick, pick item 0
    
    worldDisnu = worldHelper(in, ranGen, 3, false);
    
    // 2. add players 
    // add human player so it doesnt try to kill
    worldDisnu.addCpuPlayer();
    worldDisnu.addHumanPlayer("Sherly", 0);
    worldDisnu.startTheGame();
    
    // 3. action
    worldDisnu.makeCpuTakeTurn(); // target at 1
    String actual = worldDisnu.getResult().getActionResult();
    String expected = "CPU_abcde have picked up Suction Cup Arrows";
    assertEquals(expected, actual);
  }
  
  @Test
  public void testCpuPickMoreThanMaxItem() {
    // 1. world set up
    in = new StringReader(
        "15 16 Northeastern X Disney Land\n"
       + "50 Prof Jump\n" 
       + "Fortune the Cat\n"
       + " 4\n"
       + " 3  1  5  2 Archery Range\n"
       + " 0  3  2  4 West Village H\n"
       + " 0  0  2  2 Zombie VR\n"
       + " 6  1 10  8 Cabot Testing Center\n"    
       + " 4\n"
       + " 0 3 Suction Cup Arrows\n"  // in archery range
       + " 0 10 Bow\n"                // in archery 
       + " 1 10 Deadline Extension\n" // in west village
       + " 2 2 Motion sick VR headset\n"); // in zombie vr
    
    ranGen = randomIntGenHelper(
        0, 1,          // add player1_cpu: space, max item
        0, 1, 2, 3, 4, // name
        4,             // add player2_human: max item
        1, 3, 0,       // player1 turn: random pet space, action pick, pick item 0
        1, 3, 0);      // player1 turn: random pet space, action pick, pick item 0
    
    worldDisnu = worldHelper(in, ranGen, 3, false);
    
    // 2. add players 
    // add human player so it doesnt try to kill
    worldDisnu.addCpuPlayer();
    worldDisnu.addHumanPlayer("Sherly", 0);
    worldDisnu.startTheGame();
    
    // 3. game play
    worldDisnu.makeCpuTakeTurn();
    worldDisnu.makeHumanLook();
    worldDisnu.makeCpuTakeTurn(); // target at 1
    String actual = worldDisnu.getResult().getActionResult();
    String expected = "CPU_abcde have failed to pick up item because it has "
        + "reached its max or item does not exist.";
    assertEquals(expected, actual);
  }
  
  @Test
  public void testCpuMoveNeighbor() {  
    // 1. world set up
    in = new StringReader(
        "15 16 Northeastern X Disney Land\n"
       + "50 Prof Jump\n" 
       + "Fortune the Cat\n"
       + " 4\n"
       + " 3  1  5  2 Archery Range\n"
       + " 0  3  2  4 West Village H\n"
       + " 0  0  2  2 Zombie VR\n"
       + " 6  1 10  8 Cabot Testing Center\n"    
       + " 4\n"
       + " 0 3 Suction Cup Arrows\n"  // in archery range
       + " 0 10 Bow\n"                // in archery 
       + " 1 10 Deadline Extension\n" // in west village
       + " 2 2 Motion sick VR headset\n"); // in zombie vr
    
    ranGen = randomIntGenHelper(
        0, 1,          // add player1_cpu: space, max item
        0, 1, 2, 3, 4, // name
        4,         // add player2_human: max item
        1, 0, 1);  // player1 turn: random pet space, move to room 1th neighbor on the set
    
    worldDisnu = worldHelper(in, ranGen, 3, false);
    
    // 2. add players 
    // add human player so it doesnt try to kill
    worldDisnu.addCpuPlayer();
    worldDisnu.addHumanPlayer("Sherly", 0);
    worldDisnu.startTheGame();

    
    // 3. game play
    worldDisnu.makeCpuTakeTurn(); // target at 1
    String actual = worldDisnu.getResult().getActionResult();
    String expected = "CPU_abcde have moved to Cabot Testing Center";
    assertEquals(expected, actual);
  }
  
  @Test
  public void testCpuLookAround() {  
    // 1. world set up
    in = new StringReader(
        "15 16 Northeastern X Disney Land\n"
       + "50 Prof Jump\n" 
       + "Fortune the Cat\n"
       + " 4\n"
       + " 3  1  5  2 Archery Range\n"
       + " 0  3  2  4 West Village H\n"
       + " 0  0  2  2 Zombie VR\n"
       + " 6  1 10  8 Cabot Testing Center\n"    
       + " 4\n"
       + " 0 3 Suction Cup Arrows\n"  // in archery range
       + " 0 10 Bow\n"                // in archery 
       + " 1 10 Deadline Extension\n" // in west village
       + " 2 2 Motion sick VR headset\n"); // in zombie vr
    
    ranGen = randomIntGenHelper(
        0, 1,          // add player1_cpu: space, max item
        0, 1, 2, 3, 4, // name
        4,         // add player2_human: max item
        1, 1, 1);  // player1 turn: random pet space, move to room 1th neighbor on the set
    
    worldDisnu = worldHelper(in, ranGen, 3, false);
    
    // 2. add players 
    // add human player so it doesnt try to kill
    worldDisnu.addCpuPlayer();
    worldDisnu.addHumanPlayer("Sherly", 0);
    worldDisnu.startTheGame();
    
    // 3. game play
    worldDisnu.makeCpuTakeTurn(); // target at 1
    String actual = worldDisnu.getResult().getActionResult();
    String expected = "CPU_abcde have looked around.";
    assertEquals(expected, actual);
  }
  
  // cpu move pet
  @Test
  public void testCpuMovePet() {
    // 1. world set up
    in = new StringReader(
        "15 16 Northeastern X Disney Land\n"
       + "50 Prof Jump\n" 
       + "Fortune the Cat\n"
       + " 4\n"
       + " 3  1  5  2 Archery Range\n"
       + " 0  3  2  4 West Village H\n"
       + " 0  0  2  2 Zombie VR\n"
       + " 6  1 10  8 Cabot Testing Center\n"    
       + " 4\n"
       + " 0 3 Suction Cup Arrows\n"  // in archery range
       + " 0 10 Bow\n"                // in archery 
       + " 1 10 Deadline Extension\n" // in west village
       + " 2 2 Motion sick VR headset\n"); // in zombie vr
    
    ranGen = randomIntGenHelper(
        0, 1,          // add player1_cpu: space, max item
        0, 1, 2, 3, 4, // name
        3,             // player 2 max item
        2, 2);         // player1 turn: random pet space to move to, move pet.
    
    worldDisnu = worldHelper(in, ranGen, 3, false);
    
    // 2. add players 
    // add human player so it doesnt try to kill
    worldDisnu.addCpuPlayer();
    worldDisnu.addHumanPlayer("Sherly", 0);
    worldDisnu.startTheGame();
    
    // 3. game play
    worldDisnu.makeCpuTakeTurn(); // target at 1
    String actual = worldDisnu.getResult().getActionResult();
    String expected = "CPU_abcde have succesfuly "
        + "moved Fortune the Cat from Archery Range to Zombie VR";
    assertEquals(expected, actual);
  
  }
  
  @Test
  public void testCpuMovePetDfs() {
    in = new StringReader(
        "15 16 Northeastern X Disney Land\n"
       + "50 Prof Jump\n" 
       + "Fortune the Cat\n"
       + " 4\n"
       + " 3  1  5  2 Archery Range\n"
       + " 0  3  2  4 West Village H\n"
       + " 0  0  2  2 Zombie VR\n"
       + " 6  1 10  8 Cabot Testing Center\n"    
       + " 4\n"
       + " 0 3 Suction Cup Arrows\n"  // item 0 - archery - 0
       + " 0 10 Bow\n"                // item 1 - archery - 0
       + " 1 10 Deadline Extension\n" // item 2 - west village - 1
       + " 2 2 Motion sick VR headset\n"); // item 3 - zombie - 2
    
    // order of dfs will be 1203 (start at room 1)
    ranGen = randomIntGenHelper(
        0, 1,          // add cpu: space, max item
        0, 1, 2, 3, 4, // name
        3,             // add human: max item
        1, 2,          // cpu turn: random pet space to move to, move pet
        1, 1,          // cpu turn: look around
        1, 1);         
    
    worldDisnu = worldHelper(in, ranGen, 22, true);
    
    // add players
    worldDisnu.addCpuPlayer();
    worldDisnu.addHumanPlayer("Sherly", 0);
    worldDisnu.startTheGame();
    
    // cpu move pet
    worldDisnu.makeCpuTakeTurn();
    
    // pet will move immediately with dfs to room 2
    String expected = "Zombie VR-2\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. Motion Sick VR Headset 2\n"
        + "\n"
        + "PLAYERS\n"
        + "---------------\n"
        + "\n"
        + "PET\n"
        + "---------------\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Archery Range-0 <<<\n"
        + "           Item:\n"
        + "           1. Suction Cup Arrows 3\n"
        + "           2. Bow 10\n"
        + "\n"
        + "           Players:\n"
        + "           1. CPU_abcde, max item: 1\n"
        + "\n"
        + "           2. Sherly, max item: 3\n"
        + "\n"
        + "\n"
        + ">>> West Village H-1 <<<\n"
        + "           Item:\n"
        + "           1. Deadline Extension 10\n"
        + "\n"
        + "";
    String actual = worldDisnu.getSpaceInfo(2); 
    assertEquals(expected, actual);
    worldDisnu.makeHumanLook();
    
    expected = "Archery Range-0\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. Suction Cup Arrows 3\n"
        + "           2. Bow 10\n"
        + "\n"
        + "PLAYERS\n"
        + "---------------\n"
        + "           1. CPU_abcde, max item: 1\n"
        + "\n"
        + "           2. Sherly, max item: 3\n"
        + "\n"
        + "\n"
        + "PET\n"
        + "---------------\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Zombie VR-2 <<<\n"
        + "           Item:\n"
        + "           1. Motion Sick VR Headset 2\n"
        + "\n"
        + ">>> Cabot Testing Center-3 <<<\n"
        + "\n"
        + "";
    actual = worldDisnu.getSpaceInfo(0); 
    assertEquals(expected, actual);
    worldDisnu.makeCpuTakeTurn();
    
    expected = "Cabot Testing Center-3\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "\n"
        + "PLAYERS\n"
        + "---------------\n"
        + "\n"
        + "PET\n"
        + "---------------\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Archery Range-0 <<<\n"
        + "           Item:\n"
        + "           1. Suction Cup Arrows 3\n"
        + "           2. Bow 10\n"
        + "\n"
        + "           Players:\n"
        + "           1. CPU_abcde, max item: 1\n"
        + "\n"
        + "           2. Sherly, max item: 3\n"
        + "\n"
        + "\n"
        + "";
    actual = worldDisnu.getSpaceInfo(3); 
    assertEquals(expected, actual);
    worldDisnu.makeHumanLook();
    

  }
  
  // cpu kill success - poke eye
  @Test
  public void testCpuKillAttemptSuccessPokeEye() {
    // Pet, Target, cpu in room 0 - Archery
    // 1. world set up
    in = new StringReader(
        "15 16 Northeastern X Disney Land\n"
       + "50 Prof Jump\n" 
       + "Fortune the Cat\n"
       + " 4\n"
       + " 3  1  5  2 Archery Range\n"
       + " 0  3  2  4 West Village H\n"
       + " 0  0  2  2 Zombie VR\n"
       + " 6  1 10  8 Cabot Testing Center\n"    
       + " 4\n"
       + " 0 3 Suction Cup Arrows\n"  // in archery range
       + " 0 10 Bow\n"                // in archery 
       + " 1 10 Deadline Extension\n" // in west village
       + " 2 2 Motion sick VR headset\n"); // in zombie vr
    
    ranGen = randomIntGenHelper(
        0, 1,           // add player1_cpu: space, max item
        0, 1, 2, 3, 4,   // name
        1, 2, 3); 
    
    worldDisnu = worldHelper(in, ranGen, 3, false);
    
    // 2. add players 
    worldDisnu.addCpuPlayer();
    worldDisnu.startTheGame();
    
    // 3. game play
    worldDisnu.makeCpuTakeTurn(); // target at 1
    String actual = worldDisnu.getResult().getActionResult();
    String expected = "CPU_abcde have successfuly hurt "
        + "Prof Jump with eye poke. Prof Jump's health is now 49\n"
        + "";
    assertEquals(expected, actual);
  }
  
  // cpu kill success - weapon
  @Test
  public void testCpuKillAttemptSuccessWeapon() {
    // Pet in room 0 - Archery
    // Target and cpu in room 1 - westvillage
    
    in = new StringReader(
        "15 16 Northeastern X Disney Land\n"
       + "50 Prof Jump\n" 
       + "Fortune the Cat\n"
       + " 4\n"
       + " 3  1  5  2 Archery Range\n"
       + " 0  3  2  4 West Village H\n"
       + " 0  0  2  2 Zombie VR\n"
       + " 6  1 10  8 Cabot Testing Center\n"    
       + " 4\n"
       + " 0 3 Suction Cup Arrows\n"  // in archery range
       + " 0 10 Bow\n"                // in archery 
       + " 1 10 Deadline Extension\n" // in west village
       + " 2 2 Motion sick VR headset\n"); // in zombie vr
    
    ranGen = randomIntGenHelper(
        1, 2,           // add player1_cpu: 1-west village, max item
        0, 1, 2, 3, 4,  // name
        1, 3, 0,        // random pet space, pick up item, 0th item
        0, 0);          // random pet sapce, random action
    worldDisnu = worldHelper(in, ranGen, 3, false);
    
    // 2. add players 
    worldDisnu.addCpuPlayer();
    worldDisnu.startTheGame();
    worldDisnu.makeCpuTakeTurn();
    // 3. game play
    worldDisnu.makeCpuTakeTurn(); // target at 1
    String actual = worldDisnu.getResult().getActionResult();
    String expected = "CPU_abcde have successfuly hurt"
        + " Prof Jump with Deadline Extension. Prof Jump's health is now 40"
        + "\n";
    assertEquals(expected, actual);
  }
  
  // cpu kill fail - seen human has cat
  @Test
  public void testCpuKillFailSeenCat() {
    // Attempt ending: 
    // Human and pet in room 2-zombie
    // Target and cpu in room 1-westvillage
    // cpu attempt to kill but can be seen by human
    // They are neighbors
    
    // 1. world set up
    in = new StringReader(
        "15 16 Northeastern X Disney Land\n"
       + "50 Prof Jump\n" 
       + "Fortune the Cat\n"
       + " 4\n"
       + " 3  1  5  2 Archery Range\n"
       + " 0  3  2  4 West Village H\n"
       + " 0  0  2  2 Zombie VR\n"
       + " 6  1 10  8 Cabot Testing Center\n"    
       + " 4\n"
       + " 0 3 Suction Cup Arrows\n"  // in archery range
       + " 0 10 Bow\n"                // in archery 
       + " 1 10 Deadline Extension\n" // in west village
       + " 2 2 Motion sick VR headset\n"); // in zombie vr
    
    ranGen = randomIntGenHelper(
        4,             // player1_human: max item
        1, 2,          // player2_cpu: 1- westvillage, max item
        0, 1, 2, 3, 4, // name
        1, 3, 0);      // player2 turn: random pet space, action pick, pick item 0
    
    worldDisnu = worldHelper(in, ranGen, 3, false);
    
    // 2. add players 
    worldDisnu.addHumanPlayer("Sherly", 2); // human: 2-zombie
    worldDisnu.addCpuPlayer();
    worldDisnu.startTheGame();
    
    // 3. game play
    worldDisnu.makeHumanMovePet(2); // pet: 2-zombie
    worldDisnu.makeCpuTakeTurn(); // target at 1
    String actual = worldDisnu.getResult().getActionResult(); // target at 1-WestVillage
    String expected = "CPU_abcde have attempted "
        + "to kill Prof Jump but failed because it can be seen\n"
        + ". Target's health is still 50\n";
    assertEquals(expected, actual);
  }
  
  // 9. Game ending cases
  @Test
  public void testGameOverNoOnewins() {
  
    // 1. world set up
    in = new StringReader(
        "15 16 Northeastern X Disney Land\n"
       + "50 Prof Jump\n" 
       + "Fortune the Cat\n"
       + " 4\n"
       + " 3  1  5  2 Archery Range\n"
       + " 0  3  2  4 West Village H\n"
       + " 0  0  2  2 Zombie VR\n"
       + " 6  1 10  8 Cabot Testing Center\n"    
       + " 4\n"
       + " 0 3 Suction Cup Arrows\n"  // in archery range
       + " 0 10 Bow\n"                // in archery 
       + " 1 10 Deadline Extension\n" // in west village
       + " 2 2 Motion sick VR headset\n"); // in zombie vr
    
    ranGen = randomIntGenHelper(
        4,             // player1_human: max item
        0, 2,          // player2_cpu: 1- archery, max item
        0, 1, 2, 3, 4, // name
        1, 3, 0);      // player2 turn: random pet space, action pick, pick item 0
    
    worldDisnu = worldHelper(in, ranGen, 3, false); //max 3 turns
    
    // 2. add players 
    worldDisnu.addHumanPlayer("Sherly", 1); // human: 2-zombie
    worldDisnu.addCpuPlayer();
    worldDisnu.startTheGame();
    
    // 3. game play
    // Turn 1 
    worldDisnu.makeHumanMovePoint(new Point(114, 107));
    String actual = worldDisnu.getResult().getActionResult();
    String expected = "Sherly has successfuly moved to Zombie VR";
    assertEquals(expected, actual);
    
    
    actual = worldDisnu.getResult().getStatus();
    expected = "Target is at West Village H   CPU_abcde's turn   Turn:2/3";
    assertEquals(expected, actual);
    
    
    // Turn 2
    worldDisnu.makeCpuTakeTurn();
    actual = worldDisnu.getResult().getActionResult();
    expected = "CPU_abcde have picked up Suction Cup Arrows";
    assertEquals(expected, actual);
    
    actual = worldDisnu.getResult().getStatus();
    expected = "Target is at Zombie VR   Sherly's turn   Turn:3/3";
    assertEquals(expected, actual);
    
    // Turn 3
    worldDisnu.makeHumanMovePoint(new Point(116, 250));
    actual = worldDisnu.getResult().getActionResult();
    expected = "Sherly has successfuly moved to Archery Range";
    assertEquals(expected, actual);
    
    actual = worldDisnu.getResult().getStatus();
    expected = "Game Over! Maximum turn reached. Prof Jump escaped and nobody wins\n";
    assertEquals(expected, actual);
  }
  
  /**
   *  Attempt ending: Human player wins by waiting for target in room 3.
   *  cpu hurt target and make damage of 2.
   *  player kills target by using delta virus making damage of  10
   *  target only has 12 total health so its dead by the end of the turn
   */
  @Test
  public void testGameOverHumanwins() {
    // 1. world set up
    in = new StringReader(
        "15 16 Northeastern X Disney Land\n"
       + "12 Prof Jump\n" 
       + "Fortune the Cat\n"
       + " 4\n"
       + " 3  1  5  2 Archery Range\n"
       + " 0  3  2  4 West Village H\n"
       + " 0  0  2  2 Zombie VR\n"
       + " 6  1 10  8 Cabot Testing Center\n"    
       + " 5\n"
       + " 0 3 Suction Cup Arrows\n"  // in archery range
       + " 0 5 Bow\n"                // in archery 
       + " 1 5 Deadline Extension\n" // in west village
       + " 2 2 Motion sick VR headset\n" // in zombie vr
       + " 3 10 delta virus\n"); // in cabot
    
    ranGen = randomIntGenHelper(
        2, 2,          // cpu: zombie, max item
        0, 1, 2, 3, 4, // cpu name
        3,             // human: max item
        1, 3, 0, // cpu turn: random pet space, action pick, pick item 0
        1, 2);   // random cpu turn 
    
    worldDisnu = worldHelper(in, ranGen, 4, false); 
    
    // 2. add players 
    worldDisnu.addCpuPlayer();
    worldDisnu.addHumanPlayer("Sherly", 3); // human: 3-cabot
    worldDisnu.startTheGame();
    
    // 3. game play
    // Turn 1 
    worldDisnu.makeCpuTakeTurn(); // target at 1
    String actual = worldDisnu.getResult().getActionResult();
    String expected = "CPU_abcde have picked up Motion Sick VR Headset";
    assertEquals(expected, actual);
    
    
    actual = worldDisnu.getResult().getStatus();
    expected = "Target is at West Village H   Sherly's turn   Turn:2/4";
    assertEquals(expected, actual);
    
    // Turn 2
    worldDisnu.makeHumanPickItem(4);
    actual = worldDisnu.getResult().getActionResult();
    expected = "Sherly has successfully picked Delta Virus\n";
    assertEquals(expected, actual);
    
    
    actual = worldDisnu.getResult().getStatus();
    expected = "Target is at Zombie VR   CPU_abcde's turn   Turn:3/4";
    assertEquals(expected, actual);
    
    // Turn 3 
    worldDisnu.makeCpuTakeTurn();
    actual = worldDisnu.getResult().getActionResult();
    expected = "CPU_abcde have successfuly hurt Prof Jump with Motion "
        + "Sick VR Headset. Prof Jump's health is now 10\n";
    assertEquals(expected, actual);
    
    // Turn 4
    worldDisnu.makeHumanKill(4);
    actual = worldDisnu.getResult().getStatus();
    expected = "Game Over! Sherly wins\n";
    assertEquals(expected, actual);

  }
  
  /**
   * Cpu wins by moving a pet to its space 2-Zombie and poke eye of target.
   * human player is in neighbor (1-west village but cannot see it)
   */
  @Test
  public void testGameOverCpuwinsEyePoke() {

    // 1. world set up
    in = new StringReader(
        "15 16 Northeastern X Disney Land\n"
       + "1 Prof Jump\n" 
       + "Fortune the Cat\n"
       + " 4\n"
       + " 3  1  5  2 Archery Range\n"
       + " 0  3  2  4 West Village H\n"
       + " 0  0  2  2 Zombie VR\n"
       + " 6  1 10  8 Cabot Testing Center\n"    
       + " 5\n"
       + " 0 3 Suction Cup Arrows\n"  // in archery range
       + " 0 5 Bow\n"                // in archery 
       + " 1 5 Deadline Extension\n" // in west village
       + " 2 2 Motion sick VR headset\n" // in zombie vr
       + " 3 10 delta virus\n"); // in cabot
    
    ranGen = randomIntGenHelper(
        2, 2,          // cpu: zombie, max item
        0, 1, 2, 3, 4, // cpu name
        3,             // human: max item
        2, 2, // cpu turn: random pet space, action move pet
        1, 1, //  cpu turn: random pet space, random action
        1, 2); // random action just make cpu move
    
    worldDisnu = worldHelper(in, ranGen, 4, false); //max 4 turns
    
    // 2. add players 
    worldDisnu.addCpuPlayer();
    worldDisnu.addHumanPlayer("Sherly", 1); // human: 1-West Village
    worldDisnu.startTheGame();
    
    // 3. game play
    // Turn 1 
    worldDisnu.makeCpuTakeTurn(); // target at 1
    String actual = worldDisnu.getResult().getActionResult();
    String expected = "CPU_abcde have succesfuly moved "
        + "Fortune the Cat from Archery Range to Zombie VR";
    assertEquals(expected, actual);
    
    // Turn 2 
    worldDisnu.makeHumanPickItem(2);
    actual = worldDisnu.getResult().getActionResult();
    expected = "Sherly has successfully picked Deadline Extension\n";
    assertEquals(expected, actual);
    
    // Turn 3 
    worldDisnu.makeCpuTakeTurn();
    actual = worldDisnu.getResult().getActionResult();
    expected = "CPU_abcde have successfuly hurt Prof "
        + "Jump with eye poke. Prof Jump is dead.\n";
    assertEquals(expected, actual);
    
    actual = worldDisnu.getResult().getStatus();
    expected = "Game Over! CPU_abcde wins\n";
    assertEquals(expected, actual);
    
  }

  /**
   * Cpu wins by using item with most damage.
   * Cpu waits in room 0-Archery and picks 2 items there
   * human is in room 2-Zombie but cannot see since room 0-Archery has a pet
   */
  @Test
  public void testGameOverCpuwinsBiggestDamageWeapon() {
    // 1. world set up
    in = new StringReader(
        "15 16 Northeastern X Disney Land\n"
       + "1 Prof Jump\n" 
       + "Fortune the Cat\n"
       + " 4\n"
       + " 3  1  5  2 Archery Range\n"
       + " 0  3  2  4 West Village H\n"
       + " 0  0  2  2 Zombie VR\n"
       + " 6  1 10  8 Cabot Testing Center\n"    
       + " 5\n"
       + " 0 3 Suction Cup Arrows\n"  // in archery range
       + " 0 5 Bow\n"                // in archery 
       + " 1 5 Deadline Extension\n" // in west village
       + " 2 2 Motion sick VR headset\n" // in zombie vr
       + " 3 10 delta virus\n"); // in cabot
    
    ranGen = randomIntGenHelper(
        0, 2,          // cpu: archery, max item
        0, 1, 2, 3, 4, // cpu name
        3,             // human: max item
        1, 3, 0, // cpu turn: random pet space, pick item, 0th item
        2, 3, 0,  // cpu turn: random pet space, pick item, 0th item
        2, 3, 0);      
    
    worldDisnu = worldHelper(in, ranGen, 5, false); // max 5 turns
    
    // 2. add players 
    worldDisnu.addCpuPlayer();
    worldDisnu.addHumanPlayer("Sherly", 0); // human: 0-Archery
    worldDisnu.startTheGame();
    
    // 3. game play
    // Turn 1 
    worldDisnu.makeCpuTakeTurn(); // target at 1
    String actual = worldDisnu.getResult().getActionResult();
    String expected = "CPU_abcde have picked up Suction Cup Arrows";
    assertEquals(expected, actual);
    
    // Turn 2 
    worldDisnu.makeHumanMovePoint(new Point(114, 107));
    actual = worldDisnu.getResult().getActionResult();
    expected = "Sherly has successfuly moved to Zombie VR";
    assertEquals(expected, actual);
    
    

    // Turn 3 
    worldDisnu.makeCpuTakeTurn();
    actual = worldDisnu.getResult().getActionResult();
    expected = "CPU_abcde have picked up Bow";
    assertEquals(expected, actual);
    
    // Turn 4
    worldDisnu.makeHumanLook();
    

    // Turn 5
    worldDisnu.makeCpuTakeTurn();
    actual = worldDisnu.getResult().getActionResult();
    expected = "CPU_abcde have successfuly hurt Prof Jump with Bow. "
        + "Prof Jump is dead.\n";
    assertEquals(expected, actual);
    
    actual = worldDisnu.getResult().getStatus();
    expected = "Game Over! CPU_abcde wins\n";
    assertEquals(expected, actual);

  }


  // 10. Test game control
  @Test(expected = IllegalStateException.class)
  public void testStartTheGameNoPlayers() {
    worldDisnu.startTheGame();
        
    String actual = worldDisnu.getResult().getStatus();
    String expected = "CPU_abcde have picked up Suction Cup Arrows";
    assertEquals(expected, actual);
  }
  
  
  @Test
  public void testStartTheGameWithPlayers() {
    worldDisnu.addCpuPlayer();
    worldDisnu.addHumanPlayer("Sherly", 0); // human: 0-Archery
    worldDisnu.startTheGame();
        
    String actual = worldDisnu.getResult().getStatus();
    String expected = "Target is at Archery Range   CPU_cabcd's turn   Turn:1/5";
    assertEquals(expected, actual);
  }
  
  
  @Test
  public void testEndTheGameWithoutPlayers() {
    this.worldDisnu.endTheGame();
    String actual = worldDisnu.getResult().getStatus();
    String expected = "Game Over! Maximum turn reached. Prof Jump escaped and nobody wins\n"
        + "";
    assertEquals(expected, actual);
  }
  
  
  @Test
  public void testEndTheGameWithPlayers() {
    worldDisnu.addCpuPlayer();
    worldDisnu.addHumanPlayer("Sherly", 0); // human: 0-Archery
    worldDisnu.startTheGame();
    worldDisnu.endTheGame();
    String actual = worldDisnu.getResult().getStatus();
    String expected = "Game Over! Maximum turn reached. Prof Jump escaped and nobody wins\n"
        + "";
    assertEquals(expected, actual);
  }
  
  @Test
  public void testResetAfterStart() {
    worldDisnu.addCpuPlayer();
    worldDisnu.addHumanPlayer("Sherly", 0); // human: 0-Archery
    worldDisnu.startTheGame();
    
    // reset 
    worldDisnu.reset();
    String actual = worldDisnu.getResult().getStatus();
    String expected = "Add some players to start game"
        + "";
    assertEquals(expected, actual);
    
  }
  
  
  @Test
  public void testResetAfterEndGame() {
    worldDisnu.addCpuPlayer();
    worldDisnu.addHumanPlayer("Sherly", 0); // human: 0-Archery
    worldDisnu.startTheGame();
    worldDisnu.endTheGame();
    
    // reset 
    worldDisnu.reset();
    String actual = worldDisnu.getResult().getStatus();
    String expected = "Add some players to start game"
        + "";
    assertEquals(expected, actual);
    
  }
  
  @Test
  public void testReloadNewGame() {
    
    in = new StringReader(
        "15 16 New Reloaded Game\n"
       + "50 Prof Jump\n" 
       + "Fortune the Cat\n"
       + " 4\n"
       + " 3  1  5  2 Archery Range\n"
       + " 0  3  2  4 West Village H\n"
       + " 0  0  2  2 Zombie VR\n"
       + " 6  1 10  8 Cabot Testing Center\n"    
       + " 4\n"
       + " 0 3 Suction Cup Arrows\n"  // item 0 - archery - 0
       + " 0 10 Bow\n"                // item 1 - archery - 0
       + " 1 10 Deadline Extension\n" // item 2 - west village - 1
       + " 2 2 Motion sick VR headset\n"); // item 3 - zombie - 2
    
    worldDisnu.reloadNewGame(in);
    
    worldDisnu.reset();
    String actual = worldDisnu.toString();
    String expected = "Name: New Reloaded Game\n"
        + "Size: 15 X 16\n"
        + "Target: Prof Jump's health is 50\n"
        + "Target's Position: 0\n"
        + "Number of Rooms: 4\n"
        + "Number of Items: 4\n"
        + ""
        + "";
    assertEquals(expected, actual);
  }
  
  
  @Test
  public void testHasGameStarted() {
    worldDisnu.addCpuPlayer();
    worldDisnu.addHumanPlayer("Sherly", 0); // human: 0-Archery
    worldDisnu.startTheGame();
        
    boolean actual = worldDisnu.hasGameStarted();
    boolean expected = true;
    assertEquals(expected, actual);
    
  }
   
  // 11. Test coordinates
  
  @Test
  public void testGetSpaceNameByCoord() {
    // Use mouse click in view listener to Sytsem out the point
    // when clicking on the different space.
    
    // 0 Archery
    Point coord = new Point(116, 250);
    String actual = worldDisnu.getSpaceNameByCoord(coord);
    String expected = "Archery Range";
    assertEquals(expected, actual);
    
    // 1 West Vill
    coord = new Point(250, 112);
    actual = worldDisnu.getSpaceNameByCoord(coord);
    expected = "West Village H";
    assertEquals(expected, actual);
    
    // 2 Zombie VR
    coord = new Point(114, 107);
    actual = worldDisnu.getSpaceNameByCoord(coord);
    expected = "Zombie VR";
    assertEquals(expected, actual);
    
    // 3 Zombie VR
    coord = new Point(314, 472);
    actual = worldDisnu.getSpaceNameByCoord(coord);
    expected = "Cabot Testing Center";
    assertEquals(expected, actual);
    
  }
  
  @Test
  public void testIsPointCurrentHumanPlayer() {
    // Use mouse click in view listener to Sytsem out the point
    // when clicking on the different space.
    worldDisnu.addHumanPlayer("Sherly", 0);
    worldDisnu.addHumanPlayer("Ai", 2);
    
    // Sherly
    Point coord = new Point(76, 226);
    boolean actual = worldDisnu.isCurrentPointHumanPlayer(coord);
    boolean expected = true;
    assertEquals(expected, actual);
    
    // not Sherly
    coord = new Point(77, 250);
    actual = worldDisnu.isCurrentPointHumanPlayer(coord);
    expected = false;
    assertEquals(expected, actual);
  }
  
  @Test
  public void testIsPointCurrentHumanNeighbor() {
    // Use mouse click in view listener to Sytsem out the point
    // when clicking on the different space.
    worldDisnu.addHumanPlayer("Sherly", 0);
    worldDisnu.addHumanPlayer("Ai", 1);
    worldDisnu.startTheGame();
    worldDisnu.makeHumanLook();
    
    // neighbor(zombie vr)
    Point coord = new Point(114, 107);
    boolean actual = worldDisnu.isCurrentPointHumanPlayerNeighbor(coord);
    boolean expected = true;
    assertEquals(expected, actual);
    
    // not neighbor(archery range)
    coord = new Point(116, 250);
    actual = worldDisnu.isCurrentPointHumanPlayerNeighbor(coord);
    expected = false;
    assertEquals(expected, actual);
  }
  

  @Test
  public void testMakeHumanMovePointNeighbor() {

    worldDisnu.addHumanPlayer("Sherly", 0);
    worldDisnu.startTheGame();
    worldDisnu.makeHumanMovePoint(new Point(114, 107));
    
    // move to zombie
    String actual = worldDisnu.getResult().getActionResult();
    String expected = "Sherly has successfuly moved to Zombie VR";
    assertEquals(expected, actual);
   
  }
  
  @Test
  public void testMakeHumanMovePointNotNeighbor() {
    worldDisnu.addHumanPlayer("Sherly", 0);
    worldDisnu.startTheGame();
    worldDisnu.makeHumanMovePoint(new Point(250, 112));
    
    // move to west village
    String actual = worldDisnu.getResult().getActionResult();
    String expected = "Sherly has failed to move because space is not neighbor";
    assertEquals(expected, actual);
  }
  
  
  
  
}
