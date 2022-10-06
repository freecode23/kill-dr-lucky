import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import world.model.DefaultItem;
import world.model.DefaultPet;
import world.model.DefaultPlayer;
import world.model.DefaultSpace;
import world.model.DefaultTargetCharc;
import world.model.Item;
import world.model.Pet;
import world.model.Player;
import world.model.RandomIntGenerator;
import world.model.RowCol;
import world.model.Space;
import world.model.TargetCharacter;

/**
 * Test class for the Player class.
 */

public class PlayerTest {
  private Player sherly;
  private Pet cat;
  private Space archery;
  private Space cabotTesting;
  private Space westVillage;
  private Space zombie;
  private TargetCharacter target;
  private Item arrow;
  private Item bow;
  private Item extension;
  private Item vrHeadset;
  private Item plant;
  private int maxItem;
  private RandomIntGenerator ranGen;

  /**
   * This method is providing short-hand way of creating instances of a new
   * Player object.
   * @param name the name of the item.
   * @param space the name of the space it chose
   * @return the Player object
   */
  protected Player playerHelper(String name, Space space, int maxItem) {
    return new DefaultPlayer(name, space, maxItem);
  }
  
  /**
   * This method is providing short-hand way of creating instances of a new
   * Space object.
   * @param spaceId the index of the space.
   * @param name the name of the space.
   * @param upLeft the upper left coordinates of the space
   * @param lowRight the lower right coordinates of the space
   * @return Space object
   */
  protected Space spaceHelper(int spaceId, String name, RowCol upLeft, 
      RowCol lowRight) {
    return new DefaultSpace(spaceId, name, upLeft, lowRight);
  }
  
  /**
   * This method is providing short-hand way of creating instances of a new
   * target character object.
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
  protected Item itemHelper(String name, int itemId, int damagePoint) {
    
    return new DefaultItem(name, itemId, damagePoint);
  }
  
  /**
   * This method is providing short-hand way of creating instances of a new
   * Item object.
   * @param name the name of the pet
   * @param currentSpace the space the pet will occupy
   * @return the pet object
   */
  protected Pet petHelper(String name, Space currentSpace) {
    return new DefaultPet(name, currentSpace);
  }
  
  /**
   * Instantiate space and players.
   * So that players can occupy a space with items
   */
  @Before
  public void setUp() { 
    
    // 1. randomIntGenerator
    ranGen = new MockRandomIntGenerator(4, 2, 3, 4);
    
    // 2. initialize items make everything in archery range
    arrow = itemHelper("Suction Cup Arrow", 0, 12);
    extension = itemHelper("Deadline Extension", 1, 10);
    vrHeadset = itemHelper("VR Headset", 2, 2);
    plant = itemHelper("plant", 3, 2);
    bow = itemHelper("Plastic Bow", 4, 4);
    
    // 3. initialize space
    archery =  spaceHelper(0, "Archery Range", new RowCol(3, 1), new RowCol(5, 2));
    westVillage = spaceHelper(1, "West Village", new RowCol(0, 3), new RowCol(2, 4));
    zombie = spaceHelper(2, "Zombie VR", new RowCol(0, 0), new RowCol(2, 2));
    cabotTesting = spaceHelper(3, "Cabot Testing", new RowCol(6, 1), new RowCol(10, 8));

    // 4. neighbors
    archery.addNeighbor(zombie);
    archery.addNeighbor(cabotTesting);   
    zombie.addNeighbor(westVillage);
    zombie.addNeighbor(cabotTesting); 
    westVillage.addNeighbor(cabotTesting);
    
    // 5. items
    archery.addItem(arrow);
    archery.addItem(bow);
    archery.addItem(extension);
    archery.addItem(vrHeadset);
    
    target = targetHelper("Dr Lucky", 3, 4);
    maxItem = ranGen.getNextInt(0, 0); // will get the first number which is 5
    sherly = playerHelper("Sherly", archery, maxItem);
  } 

  @Test
  public void testValidPlayer() {  
    // 1. valid player
    String expected = "Sherly, max item: 4\n";
    assertEquals(expected, sherly.toString());
    
    // 2. player with numeric character
    expected = "Sherly1213, max item: 4\n";
    
    sherly = playerHelper("Sherly1213", archery, maxItem);
    assertEquals(expected, sherly.toString());
    
    
    // 3. get room index
    assertEquals(0, sherly.getCurrentSpace().getId());
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyName() {
    sherly = playerHelper("", archery, maxItem);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testNullName() {
    sherly = playerHelper(null, archery, maxItem);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testNullSpace() {
    sherly = playerHelper("Sherly", null, maxItem);
  }
  
  @Test
  public void testGetItemMostDamage() {
    sherly.pickUpItem(arrow);
    sherly.pickUpItem(bow);
    sherly.pickUpItem(extension);
    sherly.pickUpItem(plant);
    
    int expected = 0;
    int actual = sherly.getItemIdWithMostDamage();
    assertEquals(expected, actual);
    
    // attempt to kill target to drop weapon
    sherly.attemptToKill(target, 0);
    
    expected = 1; // deadline extension
    actual = sherly.getItemIdWithMostDamage();
    assertEquals(expected, actual);
  } 
  
  @Test
  public void testGetItemMostDamageEmpty() {
    int expected = -1;
    int actual = sherly.getItemIdWithMostDamage();
    assertEquals(expected, actual);

  }
  
  // kill success
  @Test
  public void testAttemptToKillSuccessPoke() {
    // pick no weapon
    boolean actual = sherly.attemptToKill(target, -1);
    boolean expected = true;
    assertEquals(expected, actual);
    
    // check target's health
    int expectedH = 2;
    int actualH = target.getHealth();
    assertEquals(expectedH, actualH);
    
  }
  
  @Test
  public void testAttemptToKillSuccessWeapon() {
    
    // pick arrow
    sherly.pickUpItem(arrow); // damage point 10
    boolean actual = sherly.attemptToKill(target, arrow.getItemId());
    boolean expected = true;
    assertEquals(expected, actual);
    
    // check target's health
    int expectedH = 0;
    int actualH = target.getHealth();
    assertEquals(expectedH, actualH);
    
    // check that item is removed
    String expectedS = "Sherly, max item: 4\n"
        + "";
    String actualS = sherly.toString();
    assertEquals(expectedS, actualS);
    
    // target is dead
    actual = target.isDead();
    expected = true;
    assertEquals(expected, actual);
  }
  
  @Test
  public void testAttemptToKillSuccessWeaponTargetNotDead() {
    
    target = targetHelper("Dr Lucky", 12, 4);  // health is 12
    // pick arrow
    sherly.pickUpItem(extension); // damage 10
    boolean actual = sherly.attemptToKill(target, extension.getItemId());
    boolean expected = true;
    assertEquals(expected, actual);
    
    // check target's health
    int expectedH = 2;
    int actualH = target.getHealth();
    assertEquals(expectedH, actualH);
    
    // check that item is removed
    String expectedS = "Sherly, max item: 4\n";
    String actualS = sherly.toString();
    assertEquals(expectedS, actualS);
    
    // target is dead
    actual = target.isDead();
    expected = false;
    assertEquals(expected, actual);
  }
  
  @Test
  public void testAttemptToKillSuccessPetIsHere() {
    // add player to sherly's neighbor
    playerHelper("Ai", zombie, 4);
    
    // add pet to sherly's space (archery)
    petHelper("Fortune the Cat", archery);
    
    sherly.pickUpItem(extension); // damage 10
    boolean actual = sherly.attemptToKill(target, extension.getItemId());
    boolean expected = true;
    assertEquals(expected, actual);
    
    // check target's health
    int expectedH = 0;
    int actualH = target.getHealth();
    assertEquals(expectedH, actualH);
    
    // check that item is removed
    String expectedS = "Sherly, max item: 4\n"
        + "";
    String actualS = sherly.toString();
    assertEquals(expectedS, actualS);
    
    // target is dead
    actual = target.isDead();
    expected = true;
    assertEquals(expected, actual);
  }
  
  // kill fail
  @Test
  public void testAttemptToKillFailSeenSameSpace() {
    // add player to sherly's space
    playerHelper("Ai", archery, 4);

    sherly.pickUpItem(extension); // damage 10
    boolean actual = sherly.attemptToKill(target, extension.getItemId());
    boolean expected = false;
    assertEquals(expected, actual);

    // check target's health
    int expectedH = 3;
    int actualH = target.getHealth();
    assertEquals(expectedH, actualH);

    // check that item is removed
    String expectedS = "Sherly, max item: 4\n"
        + "";
    String actualS = sherly.toString();
    assertEquals(expectedS, actualS);

    // target is dead
    actual = target.isDead();
    expected = false;
    assertEquals(expected, actual);

  }
  
  @Test
  public void testAttemptToKillFailSeenOtherSpace() {
    // add player to sherly's neighbor
    playerHelper("Ai", zombie, 4);
    
    sherly.pickUpItem(extension); // damage 10
    boolean actual = sherly.attemptToKill(target, extension.getItemId());
    boolean expected = false; // fail
    assertEquals(expected, actual);
    
    // check target's health
    int expectedH = 3;
    int actualH = target.getHealth();
    assertEquals(expectedH, actualH);
    
    // check that item is removed
    String expectedS = "Sherly, max item: 4\n";
    String actualS = sherly.toString();
    assertEquals(expectedS, actualS);
    
    // target is dead
    actual = target.isDead();
    expected = false;
    assertEquals(expected, actual);
  }
  
  @Test
  public void testAttemptToKillFailSeenNeighborHasPet() {
    // add player and cat to sherly's neighbor
    playerHelper("Ai", zombie, 4);
    petHelper("Fortune the Cat", zombie);
    
    
    sherly.pickUpItem(extension); // damage 10
    boolean actual = sherly.attemptToKill(target, extension.getItemId());
    boolean expected = false;
    assertEquals(expected, actual);
    
    // check target's health
    int expectedH = 3;
    int actualH = target.getHealth();
    assertEquals(expectedH, actualH);
    
    // check that item is removed
    String expectedS = "Sherly, max item: 4\n"
        + "";
    String actualS = sherly.toString();
    assertEquals(expectedS, actualS);
    
    // target is dead
    actual = target.isDead();
    expected = false;
    assertEquals(expected, actual);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testAttemptToKillWeaponIdDoesNotExist() {
    sherly.attemptToKill(target, extension.getItemId());
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testAttemptToKillErrorTargetNotInSpace() {
    target.move();
    sherly.attemptToKill(target, extension.getItemId());
  }
  
  // test move pet
  @Test
  public void testMovePetPlayerNotSameSpace() {
    // add pet to zombie, move to WestVillage
    cat = petHelper("cat", zombie);
    sherly.movePet(cat, westVillage);
    
    String expectedS = "cat is in West Village";
    String actualS = cat.toString();
    assertEquals(expectedS, actualS);
  }
  
  @Test
  public void testMovePetPlayerSameSpace() {
    // add pet to achery, move to zombie
    cat = petHelper("cat", archery);
    sherly.movePet(cat, zombie);  
    
    String expectedS = "cat is in Zombie VR";
    String actualS = cat.toString();
    assertEquals(expectedS, actualS);
  }
  
  @Test
  public void testMovePetToSameSpaceAsPlayer() {
    // add pet to zombie, move to archery
    cat = petHelper("cat", zombie);
    sherly.movePet(cat, archery);
    
    String expectedS = "cat is in Archery Range";
    String actualS = cat.toString();
    assertEquals(expectedS, actualS);
  }
  
  // thinks cannot be seen
  @Test
  public void testThinksCannotBeSeenSameSpace() {
    // add player same space
    playerHelper("Ai", archery, 3);
    boolean expected = false;
    boolean actual = sherly.thinksCannotBeSeen();
    assertEquals(expected, actual);
    
  }
  
  @Test
  public void testThinksCannotBeSeenOtherSpace() {
    // add player different space
    playerHelper("Ai", zombie, 3);
    boolean expected = false;
    boolean actual = sherly.thinksCannotBeSeen();
    assertEquals(expected, actual);
  }
  
  @Test
  public void testThinksCannotBeSeenNoPlayer() {
    boolean expected = true;
    boolean actual = sherly.thinksCannotBeSeen();
    assertEquals(expected, actual);
  }
  
  @Test
  public void testThinksCannotBeSeenPetMode() {
    playerHelper("Ai", zombie, 3);
    petHelper("cat", zombie);
    boolean expected = true;
    boolean actual = sherly.thinksCannotBeSeen();
    assertEquals(expected, actual);
  }
  
  // canSee player B
  @Test
  public void testCanSeePlayerbSameRoomNoPet() {
    Player ai = playerHelper("Ai", archery, 3);
    petHelper("cat", zombie);
    
    boolean expected = true;
    boolean actual = sherly.canSeePlayer(ai);
    assertEquals(expected, actual);
  }
  
  @Test
  public void testCanSeePlayerbSameRoomWithPet() {
    Player ai = playerHelper("Ai", archery, 3);
    petHelper("cat", archery);
    
    boolean expected = true;
    boolean actual = sherly.canSeePlayer(ai);
    assertEquals(expected, actual);
  }
  
  @Test
  public void testCanSeePlayerbNeighborNoCat() {
    Player ai = playerHelper("Ai", cabotTesting, 3);
    
    boolean expected = true;
    boolean actual = sherly.canSeePlayer(ai);
    assertEquals(expected, actual);
  }
  
  @Test
  public void testCannotSeePlayerbPet() {
    Player ai = playerHelper("Ai", zombie, 3);
    petHelper("cat", zombie);
    
    boolean expected = false;
    boolean actual = sherly.canSeePlayer(ai);
    assertEquals(expected, actual);
  }
  
  @Test
  public void testCannotSeePlayerbNotNeighbor() {
    Player ai = playerHelper("Ai", westVillage, 3);
    petHelper("cat", zombie);
    
    boolean expected = false;
    boolean actual = sherly.canSeePlayer(ai);
    assertEquals(expected, actual);
  }
  
  // move
  @Test
  public void testMoveToNeighbour() {
    // from archery to cabot
    boolean actual = sherly.moveToNeighbor(cabotTesting);
    boolean expected = true;
    assertEquals(expected, actual);
    
    // from archery to zombie
    sherly.moveToNeighbor(archery);
    actual = sherly.moveToNeighbor(zombie);
    expected = true;
    assertEquals(expected, actual);
    
    // from zombie to cabot
    actual = sherly.moveToNeighbor(zombie);
    expected = false;
    assertEquals(expected, actual);
  }
  
  @Test
  public void testMoveNotNeighbor() {
    // from archery to cabot
    boolean actual = sherly.moveToNeighbor(westVillage);
    boolean expected = false;
    assertEquals(expected, actual);
  }
  
  
  // look around
  @Test
  public void lookAroundNoPetWithPlayersAndItem() {
    // sherly is in archery
    // add 2 more players to zombie (neighbor) and cabot (neighbor)
    
    archery.removeItem(bow);
    archery.removeItem(extension);
    archery.removeItem(vrHeadset);
    
    zombie.addItem(vrHeadset);
    cabotTesting.addItem(extension);
    
    playerHelper("Ai", zombie, 4);
    playerHelper("Fa", cabotTesting, 4);
    
    String expected = "LOOKING AROUND FROM YOUR ROOM: Archery Range-0\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. Suction Cup Arrow 12\n"
        + "\n"
        + "PLAYER\n"
        + "---------------\n"
        + "           1. Sherly, max item: 4\n"
        + "\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Zombie VR-2 <<<\n"
        + "           Item:\n"
        + "           1. VR Headset 2\n"
        + "\n"
        + "           Players:\n"
        + "           1. Ai, max item: 4\n"
        + "\n"
        + "\n"
        + ">>> Cabot Testing-3 <<<\n"
        + "           Item:\n"
        + "           1. Deadline Extension 10\n"
        + "\n"
        + "           Players:\n"
        + "           1. Fa, max item: 4\n"
        + "\n"
        + "\n"
        + "";
    String actual = sherly.lookAround();
    assertEquals(expected, actual);
    
  }
  
  @Test
  public void lookAroundWithPetWith1Player() {
    // sherly is in archery
    // add 2 more players to zombie and cabot
    // add pet to zombie. zombie should be invisible
    playerHelper("Ai", zombie, 4);
    playerHelper("Fa", cabotTesting, 4);
    petHelper("Fortune the Cat", zombie);
    
    String expected = "LOOKING AROUND FROM YOUR ROOM: Archery Range-0\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. Suction Cup Arrow 12\n"
        + "           2. Plastic Bow 4\n"
        + "           3. Deadline Extension 10\n"
        + "           4. VR Headset 2\n"
        + "\n"
        + "PLAYER\n"
        + "---------------\n"
        + "           1. Sherly, max item: 4\n"
        + "\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Cabot Testing-3 <<<\n"
        + "\n"
        + "           Players:\n"
        + "           1. Fa, max item: 4\n"
        + "\n"
        + "\n"
        + "";
    String actual = sherly.lookAround();
    assertEquals(expected, actual);
  }
  
  @Test
  public void lookAroundWithPetWith2Player() {
    // sherly is in archery
    // add 2 more players to zombie and cabot
    // add pet to zombie. zombie should be invisible
    playerHelper("Ai", zombie, 4);
    playerHelper("Mas", zombie, 4);
    playerHelper("Fa", cabotTesting, 4);
    petHelper("Fortune the Cat", zombie);
    
    String expected = "LOOKING AROUND FROM YOUR ROOM: Archery Range-0\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. Suction Cup Arrow 12\n"
        + "           2. Plastic Bow 4\n"
        + "           3. Deadline Extension 10\n"
        + "           4. VR Headset 2\n"
        + "\n"
        + "PLAYER\n"
        + "---------------\n"
        + "           1. Sherly, max item: 4\n"
        + "\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Cabot Testing-3 <<<\n"
        + "\n"
        + "           Players:\n"
        + "           1. Fa, max item: 4\n"
        + "\n"
        + "\n"
        + "";
    String actual = sherly.lookAround();
    assertEquals(expected, actual);
  }
  
  @Test
  public void lookAroundNoItem() {
    // sherly is in archery
    // add 2 more players to zombie and cabot
    // add pet to zombie. zombie should be invisible
    Player ai = playerHelper("Ai", zombie, 4);
    petHelper("Fortune the Cat", zombie);
    
    String expected = "LOOKING AROUND FROM YOUR ROOM: Zombie VR-2\n"
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
        + "           1. Ai, max item: 4\n"
        + "\n"
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
        + "           1. Suction Cup Arrow 12\n"
        + "           2. Plastic Bow 4\n"
        + "           3. Deadline Extension 10\n"
        + "           4. VR Headset 2\n"
        + "\n"
        + "           Players:\n"
        + "           1. Sherly, max item: 4\n"
        + "\n"
        + "\n"
        + ">>> West Village-1 <<<\n"
        + "\n"
        + "";
    sherly.lookAround();
    String actual = ai.lookAround();
    assertEquals(expected, actual);
  }
  
  @Test
  public void lookAroundEmptyItemPlayers() {
    // sherly is in archery
    // add 2 more players to zombie and cabot
    // add pet to zombie. zombie should be invisible
    Player ai = playerHelper("Ai", westVillage, 4);
    petHelper("Fortune the Cat", zombie);
    
    String expected = "LOOKING AROUND FROM YOUR ROOM: West Village-1\n"
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
        + "           1. Ai, max item: 4\n"
        + "\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + "";
    sherly.lookAround();
    String actual = ai.lookAround();
    assertEquals(expected, actual);
  }
  
  @Test
  public void lookAroundWithPetPlayerInCurrentSpace() {
    
    // Sherly is in archery
    // add 2 more players to archery and cabot
    // add pet to archery
    // cabot and zombie should be visible
    playerHelper("Ai", archery, 4);
    playerHelper("Fa", cabotTesting, 4);
    petHelper("Fortune the Cat", archery);
    
    String expected = "LOOKING AROUND FROM YOUR ROOM: Archery Range-0\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. Suction Cup Arrow 12\n"
        + "           2. Plastic Bow 4\n"
        + "           3. Deadline Extension 10\n"
        + "           4. VR Headset 2\n"
        + "\n"
        + "PLAYER\n"
        + "---------------\n"
        + "           1. Sherly, max item: 4\n"
        + "\n"
        + "           2. Ai, max item: 4\n"
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
        + ">>> Cabot Testing-3 <<<\n"
        + "\n"
        + "           Players:\n"
        + "           1. Fa, max item: 4\n"
        + "\n"
        + "\n"
        + "";
    String actual = sherly.lookAround();
    assertEquals(expected, actual);

  }

  // pick up item
  @Test
  public void testPickUpItem() {
    
    // 1. pick up 1 item
    sherly.pickUpItem(arrow);
    String expected = "Sherly, max item: 4\n"
        + "               > Suction Cup Arrow 12\n"
        + "";
    
    assertEquals(expected, sherly.toString());
    
    // 2. pick 2nd item
    sherly.pickUpItem(bow);
    expected = "Sherly, max item: 4\n"
        + "               > Plastic Bow 4\n"
        + "               > Suction Cup Arrow 12\n"
        + "";
    
    assertEquals(expected, sherly.toString());
    
    // 3. pick 5 items (max)
    sherly.pickUpItem(extension);
    sherly.pickUpItem(vrHeadset);
    sherly.pickUpItem(plant);
    expected = "Sherly, max item: 4\n"
        + "               > VR Headset 2\n"
        + "               > Deadline Extension 10\n"
        + "               > Plastic Bow 4\n"
        + "               > Suction Cup Arrow 12\n"
        + "";
    
    assertEquals(expected, sherly.toString());    
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testPickUpNullItem() {
    sherly = playerHelper("Sherly", archery, maxItem);
    sherly.pickUpItem(null);
  }
  
  @Test
  public void testPickMoreThan4Items() {
    sherly.pickUpItem(arrow);
    sherly.pickUpItem(bow);
    sherly.pickUpItem(plant);
    sherly.pickUpItem(extension);
    sherly.pickUpItem(vrHeadset);
    
    assertFalse(sherly.pickUpItem(vrHeadset));
  }
  
  @Test
  public void testPickUpItemThatIsNotPresent() {
    Item banana = itemHelper("Banana", 6, 3);
    assertFalse(sherly.pickUpItem(banana));
  }
  
  @Test
  public void testPickUpItemInEmptyRoom() {
    archery =  spaceHelper(1, "Archery Range", new RowCol(10, 25),
        new RowCol(12, 30));
    sherly = playerHelper("Sherly", archery, maxItem);
    assertFalse(sherly.pickUpItem(arrow));
  }
  
  @Test
  public void testEquals() {
    // 1.Same name - test new object vs previously created object and symmetry
    assertTrue(sherly.equals(playerHelper("Sherly", zombie, maxItem)));
    assertTrue(playerHelper("Sherly", cabotTesting, maxItem).equals(sherly));
    
    // 2. Different space and symmetry
    Player sherlyMoved = playerHelper("Sherly", westVillage, maxItem);
    assertTrue(sherly.equals(sherlyMoved));
    assertTrue(sherlyMoved.equals(sherly));
    
    // 3. different name
    Player sherlyDiffName =  playerHelper("Sherl", zombie, maxItem);
    assertFalse(sherlyDiffName.equals(sherly));
  }
  
  @Test
  public void testHashCode() {
    // 1.Same name - test new object vs previously created object and symmetry
    assertTrue(sherly.hashCode() == (playerHelper("Sherly", zombie, maxItem).hashCode()));
    
    // 2. Different space 
    Player sherlyMoved = playerHelper("Sherly", cabotTesting, maxItem);
    assertTrue(sherly.hashCode() == sherlyMoved.hashCode());
    
    // 3. different name
    Player sherlyDiffName =  playerHelper("Sherl", archery, maxItem);
    assertFalse(sherlyDiffName.hashCode() == sherly.hashCode());
  }
}
