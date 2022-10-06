
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import world.model.DefaultItem;
import world.model.DefaultPet;
import world.model.DefaultPlayer;
import world.model.DefaultSpace;
import world.model.Item;
import world.model.Pet;
import world.model.Player;
import world.model.RowCol;
import world.model.Space;



/**
 * Test class for the Space class.
 */

public class SpaceTest {
  private Pet cat;
  private Player sherly;
  private Space archery;
  private Space zombie;
  private Space westVillage;
  private Space cabotTesting;
  private Item arrow;
  private Item bow;
  private Item extension;
  
  
  /**
   * This method is providing short-hand way of creating instances of a new
   * Item object.
   * @param name the name of the item.
   * @param roomId the index of the room in which this item is placed.
   * @param damagePoint the amount of damage to the target character.
   * @return a new instance of an Item object
   */
  protected Item itemHelper(String name, int itemId, int damagePoint) {
    
    return new DefaultItem(name, itemId, damagePoint);
  }
  
  /**
   * This method is providing short-hand way of creating instances of a new
   * Player object.
   * @param name the name of the item.
   * @param spaceName the name of the space it chose
   */
  protected Player playerHelper(String name, Space spaceObject, int maxItem) {
    return new DefaultPlayer(name, spaceObject, maxItem);
  }
  
  /**
   * This method is providing short-hand way of creating instances of a new
   * Item object.
   * @param spaceId the index of the space.
   * @param name the name of the space.
   * @param upLeft the upper left coordinates of the space
   * @param lowRight the lower right coordinates of the space
   * @param items the list of items inside this space.
   * @return Space object
   */
  protected Space spaceHelper(int spaceId, String name, RowCol upLeft, RowCol lowRight) {
    return new DefaultSpace(spaceId, name, upLeft, lowRight);
  }

  /**
   * This method is providing short-hand way of creating instances of a new
   * pet object.
   * @param name the name of the pet
   * @param space the space the pet will occupy
   * @return the pet object
   */
  protected Pet petHelper(String name, Space currentSpace) {
    return new DefaultPet(name, currentSpace);
  }
  
  /**
   * Instantiate space and items objects so that space contain items.
   */
  @Before
  public void setUp() { 
    
    // 1. initialize space
    archery =  spaceHelper(0, "Archery Range", new RowCol(3, 1), new RowCol(5, 2));
    westVillage = spaceHelper(1, "West Village", new RowCol(0, 3), new RowCol(2, 4));
    zombie = spaceHelper(2, "Zombie VR", new RowCol(0, 0), new RowCol(2, 2));
    cabotTesting = spaceHelper(3, "Cabot Testing", new RowCol(6, 1), new RowCol(10, 8));
    
    // 2. initialize items
    arrow = itemHelper("Suction Cup Arrow", 0, 12);
    bow = itemHelper("Plastic Bow", 3, 4);
    extension = itemHelper("Deadline Extension", 1, 5);
    
    // 3. pet
    cat = petHelper("Fortune the Cat", archery);
    
    // 4. player
    sherly = playerHelper("Sherly", westVillage, 5);
    
    archery.addNeighbor(zombie);
    archery.addNeighbor(cabotTesting);   
    zombie.addNeighbor(westVillage);
    zombie.addNeighbor(cabotTesting); 
    westVillage.addNeighbor(cabotTesting);
    
  }
  
  @Test
  public void testValidSpaceNoPlayers() {   
    // 1. with items
    String expected = "Archery Range-0\n"
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
        + ">>> Zombie VR-2 <<<\n"
        + "\n"
        + ">>> Cabot Testing-3 <<<\n"
        + "\n"
        + "";
    assertEquals(expected, archery.toString());
        
    // 2. no items 
    expected = "Archery Range-1\n"
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
        + "";
    String actual = new DefaultSpace(1, "Archery Range", 
        new RowCol(10, 25), new RowCol(12, 30)).toString();
    assertEquals(expected, actual);
   
  }
  
  @Test
  public void testGetters() {
    assertEquals("Archery Range", archery.getName());
    assertEquals(0, archery.getId());
    assertEquals(2, archery.getNeighborSize());
    
    // items
    archery.addItem(arrow);
    assertEquals(1, archery.getItemSize());
    assertEquals(arrow, archery.getItemAt(0));
  }
  
  @Test
  public void testGetUpLeft() {
    String actual = spaceHelper(1, "Archery Range", new RowCol(0, 0), 
        new RowCol(12, 30)).getUpLeft().toString();  
    assertEquals("0 X 0", actual);
    
    actual = spaceHelper(1, "archery", new RowCol(1231, 231), new RowCol(4000,
        500)).getUpLeft().toString();
    assertEquals("1231 X 231", actual);
  }

  @Test
  public void testGetLowRight() {
    String actual = spaceHelper(1, "Archery Range", new RowCol(0, 0), 
        new RowCol(1, 1)).getLowRight().toString();  
    assertEquals("1 X 1", actual);
    
    actual = spaceHelper(1, "archery", new RowCol(20, 15), new RowCol(1231, 
        231)).getLowRight().toString();
    assertEquals("1231 X 231", actual);
  }
  
  @Test
  public void testAddItem() {
    // add 1 item
    archery.addItem(arrow);
    String expected = "Archery Range-0\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. Suction Cup Arrow 12\n"
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
        + "\n"
        + ">>> Cabot Testing-3 <<<\n"
        + "\n"
        + "";
    String actual = archery.toString();
    assertEquals(expected, actual);
    
    // add 2 items
    archery.addItem(bow);
    expected = "Archery Range-0\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. Suction Cup Arrow 12\n"
        + "           2. Plastic Bow 4\n"
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
        + "\n"
        + ">>> Cabot Testing-3 <<<\n"
        + "\n"
        + "";
    actual = archery.toString();
    assertEquals(expected, actual);
  }
  
  @Test
  public void testAddPet() {
    zombie.addPet(cat);
    String expected = "Archery Range-0\n"
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
        + ">>> Zombie VR-2 <<<\n"
        + "\n"
        + "           Pet:\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "\n"
        + ">>> Cabot Testing-3 <<<\n"
        + "\n"
        + "";
    String actual = archery.toString();
    assertEquals(expected, actual);
    
    // check pet from neighbor
    // should be visible
    expected = "Zombie VR-2\n"
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
        + "\n"
        + "           Pet:\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "\n"
        + ">>> West Village-1 <<<\n"
        + "\n"
        + "           Players:\n"
        + "           1. Sherly, max item: 5\n"
        + "\n"
        + "\n"
        + "";
    actual = zombie.toString();
    assertEquals(expected, actual);
   
  }
  
  @Test(expected = IllegalStateException.class)
  public void testAddPetTwice() {
    archery.addPet(cat);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testAddNullPet() {
    archery.addPet(null);
  }
  
  @Test
  public void testAddNeighbor() {
    // Already done in set up. just check correct placement 
    // 1. west village should only have zombie as neighbor
    String expected = "West Village-1\n"
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
        + "           1. Sherly, max item: 5\n"
        + "\n"
        + "\n"
        + "PET\n"
        + "---------------\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Zombie VR-2 <<<\n"
        + "\n"
        + "";
    String actual = westVillage.toString();
    assertEquals(expected, actual);
    
  }
 
  @Test(expected = IllegalArgumentException.class)
  public void testAddPlayerAlreadyExist() {
    // duplicate
    westVillage.addPlayer(sherly); 
  }
  
  @Test
  public void testRemoveItem() {
    
    // 1. add 2 items
    archery.addItem(arrow);
    archery.addItem(bow);
    
    // 2. remove arrow
    archery.removeItem(arrow);
    String expected = "Archery Range-0\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. Plastic Bow 4\n"
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
        + "\n"
        + ">>> Cabot Testing-3 <<<\n"
        + "\n"
        + "";
    String actual = archery.toString();
    assertEquals(expected, actual);
    
    // 3. remove bow
    archery.removeItem(bow);
    expected = "Archery Range-0\n"
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
        + ">>> Zombie VR-2 <<<\n"
        + "\n"
        + ">>> Cabot Testing-3 <<<\n"
        + "\n"
        + "";
    actual = archery.toString();
    assertEquals(expected, actual);
  }
  
  @Test
  public void testRemovePet() {
    zombie.addPet(cat); // will have cat in both zombie and archery
    String actual = zombie.toString();
    String expected = "Zombie VR-2\n"
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
        + "\n"
        + "           Pet:\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "\n"
        + ">>> West Village-1 <<<\n"
        + "\n"
        + "           Players:\n"
        + "           1. Sherly, max item: 5\n"
        + "\n"
        + "\n"
        + "";
    assertEquals(expected, actual);
    
    
    // remove pet from archery
    archery.removePet();
    expected = "Archery Range-0\n"
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
        + ">>> Zombie VR-2 <<<\n"
        + "\n"
        + "           Pet:\n"
        + "           Fortune the Cat\n"
        + "\n"
        + "\n"
        + ">>> Cabot Testing-3 <<<\n"
        + "\n"
        + "";
    actual = archery.toString();
    assertEquals(expected, actual);
  }
  
  @Test
  public void testRemovePlayer() {
    // remove sherly from archery
    westVillage.removePlayer(sherly);
    String expected = "West Village-1\n"
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
        + ">>> Zombie VR-2 <<<\n"
        + "\n"
        + "";
    String actual = westVillage.toString();
    assertEquals(expected, actual);
    
  }
 
  // boolean group
  @Test
  public void testIsItemPresent() {
    assertFalse(westVillage.isItemPresent(extension));
    assertFalse(westVillage.isItemPresent(arrow));
    
    westVillage.addItem(extension);
    assertTrue(westVillage.isItemPresent(extension));
  }
  
  @Test
  public void testIsNeighbour() {
    // zombie and cabot should be archery's neighbor
    assertTrue(archery.isNeighborByCalc(cabotTesting));
    assertTrue(cabotTesting.isNeighborByCalc(archery));
    
    assertTrue(archery.isNeighborByCalc(zombie));
    assertTrue(zombie.isNeighborByCalc(archery));
        
    // west village is not cabot's and archery's neighbor
    assertFalse(westVillage.isNeighborByCalc(cabotTesting));
    assertFalse(cabotTesting.isNeighborByCalc(westVillage));
    assertFalse(westVillage.isNeighborByCalc(archery));
    assertFalse(archery.isNeighborByCalc(westVillage));
    
    // west village is zombie'sneighrbor
    assertTrue(westVillage.isNeighborByCalc(zombie));
    assertTrue(zombie.isNeighborByCalc(westVillage));
    
  }
  
  @Test
  public void testIsPetHere() {
    assertFalse(zombie.isPetHere());
    assertTrue(archery.isPetHere());
 
  }
  
  @Test
  public void testDoNeighboursHavePlayers() {
    assertFalse(archery.doNeighborsHavePlayers());
    
    // player is in west village only zombie is a neighbor
    assertTrue(zombie.doNeighborsHavePlayers());
  }
  
  @Test
  public void testDoNeighboursHavePlayersPetModeNoPlayers() {
    // remove pet from Archery range
    archery.removePet();
    
    // add pet to west village
    westVillage.addPet(cat);
    
    // Player and pet is in west village
    // there should be no players in neighbors from zombie
    assertFalse(zombie.doNeighborsHavePlayersPetMode());
  }
  
  @Test
  public void testDoNeighboursHavePlayersPetModeHavePlayers() {
    // remove pet from Archery range
    archery.removePet();
    
    // add pet to zombieVr
    zombie.addPet(cat);
    
    // add player to zombie VR
    playerHelper("ai", zombie, 5);
    
    westVillage.removePlayer(sherly);
    cabotTesting.addPlayer(sherly);
    
    // There should be a player in neighbors from archery range
    // in Cabot testing
    assertTrue(archery.doNeighborsHavePlayersPetMode());
    
  }
  
  @Test
  public void testPetEffectWithPlayers() {
    // Archery is hidden
    // archery have player and cat
    playerHelper("ai", zombie, 5);
    
    
    westVillage.addItem(arrow);
    sherly.pickUpItem(arrow);
    // zombie(archery's neighbor) should show west village only
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
        + "           1. ai, max item: 5\n"
        + "\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> West Village-1 <<<\n"
        + "\n"
        + "           Players:\n"
        + "           1. Sherly, max item: 5\n"
        + "               > Suction Cup Arrow 12\n"
        + "\n"
        + "\n"
        + "";
    String actual = zombie.toStringIsLookAround(true);
    assertEquals(expected, actual);
    
    // Cabot (archery's neighbor) should show no neighbor
    expected = "LOOKING AROUND FROM YOUR ROOM: Cabot Testing-3\n"
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
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + "";
    actual = cabotTesting.toStringIsLookAround(true);
    assertEquals(expected, actual);
    
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyName() {
    spaceHelper(1, "", new RowCol(10, 25), new RowCol(12, 30));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeSpaceId() {
    spaceHelper(-1, "Archery Range", 
        new RowCol(10, 25), new RowCol(12, 30));
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testupLeftRowIsBigger() {
    spaceHelper(1, "Archery Range", 
        new RowCol(10, 25), new RowCol(9, 30));
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testUpLeftColIsBigger() {
    spaceHelper(1, "Archery Range", 
        new RowCol(10, 25), new RowCol(12, 24));
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testUpLeftRowEqual() {
    spaceHelper(1, "Archery Range", 
        new RowCol(12, 24), new RowCol(12, 24));
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testUpLeftColEqual() {
    spaceHelper(1, "Archery Range", 
        new RowCol(12, 24), new RowCol(12, 24));
  }
  
  
  @Test(expected = IllegalArgumentException.class)
  public void testnullUpleft() {
    RowCol upLeft = null;

    spaceHelper(1, "Archery Range",
        upLeft, new RowCol(12, 30));
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testnullLowright() {
    RowCol lowRight = null;

    spaceHelper(1, "Archery Range",
        new RowCol(12, 30), lowRight);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testnullItem() {
    archery.addItem(null);
  }
  
  @Test
  public void testGetName() {
    //Long name
    String actual = spaceHelper(1, "Archery Range", 
        new RowCol(10, 25), new RowCol(12, 30)).getName();
    
    assertEquals("Archery Range", actual);
    
    // Short name
    actual = spaceHelper(1, "B", new RowCol(10, 25), new RowCol(12, 30)).getName();
    assertEquals("B", actual);
  }
 
  @Test
  public void testIsItemInSpace() {
    
    // 1. Space with items
    // existing
    archery.addItem(arrow);
    assertTrue(archery.isItemPresent(arrow));
    
    // non existing
    Item banana = itemHelper("Banana", 5, 12);
    assertFalse(archery.isItemPresent(banana));
    
    // 2. Space with no items
    assertFalse(cabotTesting.isItemPresent(arrow));
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidItemInSpace() {
    Space space1 = spaceHelper(1, "Archery Range", new RowCol(0, 0), new RowCol(12, 30));
    assertTrue(space1.isItemPresent(null));
  }

  @Test
  public void testEquals() {
    // 1.Same name - test new object vs previously created object and symmetry
    assertTrue(archery.equals(new DefaultSpace(1, 
        "Archery Range", new RowCol(10, 25), new RowCol(12, 30))));
    
    assertTrue(new DefaultSpace(1, "Archery Range", 
        new RowCol(10, 25), new RowCol(12, 30)).equals(archery));
    
    // 2. different items
    assertTrue(archery.equals(new DefaultSpace(1, "Archery Range", 
        new RowCol(10, 25), new RowCol(12, 30))));
    
    assertTrue(new DefaultSpace(1, "Archery Range",
        new RowCol(10, 25), new RowCol(12, 30)).equals(archery));
    
    // 3. different name
    assertFalse(archery.equals(new DefaultSpace(1, "Behraki Health "
        + "and Science Center", new RowCol(10, 25), new RowCol(12, 30))));
  }
  
  @Test
  public void testHashCode() {
    // 1. new object vs previously created object
    assertTrue(archery.hashCode() == (new DefaultSpace(1, "Archery Range",
        new RowCol(10, 25), new RowCol(12, 30)).hashCode()));
    
    
    // 2. same item different object
    assertTrue(archery.hashCode() == (new DefaultSpace(1, "Archery Range",
        new RowCol(10, 25), new RowCol(12, 30)).hashCode()));
    
    // 3. different name
    assertFalse(archery.hashCode() == (new DefaultSpace(1, "Archery Rang",
        new RowCol(10, 25), new RowCol(12, 30)).hashCode()));
  }
  
}





















