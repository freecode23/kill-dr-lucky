
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import world.model.Cpu;
import world.model.DefaultCpu;
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
 * Test class for the CPU class.
 */
public class CpuTest {
  private Cpu cpu;
  private RandomIntGenerator ranGen;
  private Pet cat;
  private Space archery;
  private Space zombie;
  private Space westVillage;
  private Space cabotTesting;
  private Item arrow;
  private Item bow;
  private Item headset;
  private Item zombieSaliva;
  private TargetCharacter target;

  /**
   * This method is providing short-hand way of creating instances of a new
   * CPU object.
   * @return the CPU object
   */
  protected Cpu cpuHelper(RandomIntGenerator ranGen, Space space)  {
    return new DefaultCpu(ranGen, space);
  }
  
  /**
   * This method is providing short-hand way of creating instances of a new
   * Space object.
   * @param spaceIndex the index of the space.
   * @param name the name of the space.
   * @param upLeft the upper left coordinates of the space
   * @param lowRight the lower right coordinates of the space
   * @return Space object
   */
  protected Space spaceHelper(int spaceIndex, String name, RowCol upLeft, 
      RowCol lowRight) {
    return new DefaultSpace(spaceIndex, name, upLeft, lowRight);
  }
  
  /**
   * This method is providing short-hand way of creating instances of a new
   * Item object.
   * @param name the name of the item.
   * @param roomIndex the index of the room in which this item is placed.
   * @param damagePoint the amount of damage to the target character.
   * @return a new instance of an Item object
   */
  protected Item itemHelper(String name, int itemIndex, int damagePoint) {
    
    return new DefaultItem(name, itemIndex, damagePoint);
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
   * This method is providing short-hand way of creating instances of a new
   * Player object.
   * @param name the name of the item.
   * @param spaceName the name of the space it chose
   * @return the Player object
   */
  protected Player playerHelper(String name, Space space, int maxItem) {
    return new DefaultPlayer(name, space, maxItem);
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
   * mock RandomIntGen object.
   * @return the RandomIntGen object
   */
  protected RandomIntGenerator randomIntGenHelper(int ... varargs) {
    return new MockRandomIntGenerator(varargs);
  }
  
  /**
   * Instantiate variables to generate players and random number generator.
   */
  @Before
  public void setUp() { 
    
    // 1. initialize space
    archery =  spaceHelper(0, "Archery Range", new RowCol(3, 1), new RowCol(5, 2));
    westVillage = spaceHelper(1, "West Village", new RowCol(0, 3), new RowCol(2, 4));
    zombie = spaceHelper(2, "Zombie VR", new RowCol(0, 0), new RowCol(2, 2));
    cabotTesting = spaceHelper(3, "Cabot Testing", new RowCol(6, 1), new RowCol(10, 8));
    
    // 2. initialize items
    arrow = itemHelper("Suction Cup Arrow", 0, 12); // in archery
    bow = itemHelper("Plastic Bow", 3, 4); // in archery
    zombieSaliva = itemHelper("zombie saliva", 1,  12); // in zombie
    headset = itemHelper("vr headset", 2, 11); // in zombie
    
    // 3. pet
    cat = petHelper("Fortune the Cat", archery);
    
    // 3. set neighbours
    archery.addNeighbor(zombie);
    archery.addNeighbor(cabotTesting);   
    zombie.addNeighbor(westVillage);
    zombie.addNeighbor(cabotTesting); 
    westVillage.addNeighbor(cabotTesting);
    
    // add items
    archery.addItem(arrow);
    archery.addItem(bow);
    zombie.addItem(headset);
    zombie.addItem(zombieSaliva);
    
    // 4. initialize player
    ranGen = randomIntGenHelper(
        7, // maxItem
        0, 1, 2, 3, 4,  // name is abcde
        0, // Action1: move to neighbor 
        1); // Argument1: neighbor set index (second neighbor in the set
    
    // 5. itinitalize target
    target = targetHelper("Dr Lucky", 3, 4);
  }
  
  @Test
  public void testConstructor() {
    // initialize controller, player starts in archery
    cpu = cpuHelper(ranGen, archery); 

    // 1. valid controller
    StringBuilder s = new StringBuilder();
    s.append("This controller is controlling: "
        + "CPU_abcde, max item: 7\n");
    String expected = s.toString();
    assertEquals(expected, cpu.toString());
  }
  
  // 0 move: command version
  @Test
  public void testMoveFirstNeighborCommand() {
    ranGen = randomIntGenHelper(
        7, // maxItem
        0, 1, 2, 3, 4,  // name is abcde
        0, // Action1: move to neighbor 
        0); // Argument1: neighbor set index ( neighbor in the set
    cpu = cpuHelper(ranGen, archery);
    
    playerHelper("sherly", archery, 2); // add other player so it won't kill
    String actual = cpu.takeCpuTurnCommand(target, cat, westVillage); // move to first neighbour
    String expected = "CPU_abcde have moved to Zombie VR";
    assertEquals(expected, actual);
  }
  
  @Test
  public void testMoveSecondNeighborCommand() {
    cpu = cpuHelper(ranGen, archery); 
    playerHelper("sherly", archery, 2);  // add other player so it won't kill
    String actual = cpu.takeCpuTurnCommand(target, cat, westVillage); // move to second neighbour
    
    String expected = "CPU_abcde have moved to Cabot Testing";
    assertEquals(expected, actual);
  }
  
  // 1 look: command version
  @Test
  public void cpuLookedAroundCommand() {
    ranGen = randomIntGenHelper(
        1, // maxItem
        0, 1, 2, 3, 4,  // name is abcde
        1 // Action1: LookAround
        ); 
    cpu = cpuHelper(ranGen, archery);
    playerHelper("sherly", archery, 2); // add other player so it won't kill
    String actual = cpu.takeCpuTurnCommand(target, cat, westVillage); //pickup second item
    String expected = "CPU_abcde have looked around.";
    assertEquals(expected, actual);
  }
  
  // 2 movepet: command version
  @Test
  public void testMovePetCommand() {
    
    // 1. add player so it wont kill
    playerHelper("Sherly", cabotTesting, 4);
    
    // 2. make cou pick item and move to archery.
    ranGen = randomIntGenHelper(
        2, // maxItem
        0, 1, 2, 3, 4,  // name is abcde
        2 // Action1: Move pet
        ); 
    cpu = cpuHelper(ranGen, zombie);
    
    // 3. cpu move pet
    String actual = cpu.takeCpuTurnCommand(target, cat, westVillage);
    String expected = "CPU_abcde have succesfuly "
        + "moved Fortune the Cat from Archery Range to West Village";
    assertEquals(expected, actual);
  }
  
  // 3 pick: command version
  @Test
  public void testPickItemCommand() {
    ranGen = randomIntGenHelper(
        7,  // maxItem
        0, 1, 2, 3, 4,  // name is abcde
        3,  // Action1: Pick item
        0,  // Argument1: pick first item in the set
        3,  // Action2: Pick item
        0); // Argument2: pick first item in the set
    cpu = cpuHelper(ranGen, archery);
    playerHelper("sherly", archery, 2); // add other player so it won't kill
    cpu.takeCpuTurnCommand(target, cat, westVillage); //pickup first item
    String actual = cpu.toString();
    String expected = "This controller is controlling: CPU_abcde, max item: 7\n"
        + "               > Suction Cup Arrow 12\n"
        + "";
    assertEquals(expected, actual);
    
    
    cpu.takeCpuTurnCommand(target, cat, westVillage); //pickup second
    actual = cpu.toString();
    expected = "This controller is controlling: CPU_abcde, max item: 7\n"
        + "               > Plastic Bow 4\n"
        + "               > Suction Cup Arrow 12\n"
        + "";
    assertEquals(expected, actual);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testPickItemFailNoItemCommand() {
    ranGen = randomIntGenHelper(
        7, // maxItem
        0, 1, 2, 3, 4,  // name is abcde
        3, // Action1: Pick item
        0); // Argument1: pick first item in the set
    cpu = cpuHelper(ranGen, cabotTesting);
    playerHelper("sherly", archery, 2); // add other player so it won't kill
    cpu.takeCpuTurnCommand(target, cat, westVillage); //pickup first item
  }
  
  @Test
  public void testPickItemFailMaxCommand() {
    ranGen = randomIntGenHelper(
        1, // maxItem
        0, 1, 2, 3, 4,  // name is abcde
        3, // Action1: Pick item
        0, // Argument1: pick first item in the set
        3, // Action2: Pick item
        0); // Argument2: pick first item in the set
    cpu = cpuHelper(ranGen, archery);
    playerHelper("sherly", archery, 2); // add other player so it won't kill
    cpu.takeCpuTurnCommand(target, cat, westVillage);
    String actual = cpu.takeCpuTurnCommand(target, cat, westVillage); //pickup second item
    String expected = "CPU_abcde have failed to pick up item"
        + " because it has reached its max or item does not exist.";
    assertEquals(expected, actual);
  }
  
  // 4 kill: command version
  @Test
  public void killSuccessEyePokeCommand() {
    // dont put any player, dont pick up item
    cpu = cpuHelper(ranGen, archery);
    String actual = cpu.takeCpuTurnCommand(target, cat, westVillage);
    String expected = "CPU_abcde have successfuly hurt Dr Lucky with eye poke. "
        + "Dr Lucky's health is now 2\n"
        + "";
    assertEquals(expected, actual);
  }
  
  @Test
  public void killSuccessWeapon() {
    ranGen = randomIntGenHelper(
        2, // maxItem
        0, 1, 2, 3, 4,  // name is abcde
        3, // Action1: Pick item
        0, // Argument1: pick first item in the set
        3, // Action2: Pick item
        0,  // Argument2: pick first item in the set
        0, 0);  // Action3: move to first neighbor
    // dont put any player
    cpu = cpuHelper(ranGen, zombie);
    
    target = targetHelper("Dr Lucky", 20, 4); // target's health
    cpu.takeCpuTurnCommand(target, cat, westVillage); // pick up item
    cpu.takeCpuTurnCommand(target, cat, westVillage); // pick up item
    cpu.takeCpuTurnCommand(target, cat, westVillage); // move to first neighbor
    String actual = cpu.takeCpuTurnCommand(target, cat, westVillage);
    String expected = "CPU_abcde have successfuly hurt Dr Lucky "
        + "with zombie saliva. Dr Lucky's health is now 8\n"
        + "";
    assertEquals(expected, actual);
  }
  
  @Test
  public void testKillFailed() {
    
    // 1. add player and pet in cabot (it will witness the murder)
    Player sherly = playerHelper("Sherly", cabotTesting, 4);
    sherly.movePet(cat, cabotTesting);
    
    // 2. make cou pick item and move to archery.
    ranGen = randomIntGenHelper(
        2, // maxItem
        0, 1, 2, 3, 4,  // name is abcde
        3, // Action1: Pick item
        0, // Argument1: pick first item in the set
        3, // Action2: Pick item
        0,  // Argument2: pick first item in the set
        0, 0);  // Action3: move to first neighbor
    cpu = cpuHelper(ranGen, zombie);
    target = targetHelper("Dr Lucky", 20, 4); // target's health
    cpu.takeCpuTurnCommand(target, cat, westVillage); // pick up item
    cpu.takeCpuTurnCommand(target, cat, westVillage); // pick up item
    cpu.takeCpuTurnCommand(target, cat, westVillage); // move to first neighbor
    
    // 3. cpu attempt murder
    String actual = cpu.takeCpuTurnCommand(target, cat, westVillage);
    String expected = "CPU_abcde have attempted to kill "
        + "Dr Lucky but failed because it can be seen\n"
        + ". Target's health is still 20\n";
    assertEquals(expected, actual);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testNullSpace() {
    cpuHelper(randomIntGenHelper(1, 2), null);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testNullRandomGenerator() {
    cpuHelper(null, archery);
  }

  @Test
  public void testEquals() {
    cpu = cpuHelper(ranGen, archery); 
    // 1.Same name different space symmetry
    assertTrue(cpu.equals(cpuHelper(randomIntGenHelper(7, 0, 1, 2, 3, 4), zombie)));
    assertTrue(cpuHelper(randomIntGenHelper(7, 0, 1, 2, 3, 4), cabotTesting).equals(cpu));
    
    // 2. different name
    assertFalse(cpu.equals(cpuHelper(randomIntGenHelper(7, 0, 1, 2, 3, 7), archery)));
  }
  
  @Test
  public void testHashCode() {
    cpu = cpuHelper(ranGen, archery); 
    // 1.Same player - test new object vs previously created object 
    // different space. cannot add to the same space
    assertTrue(cpu.hashCode() 
        == (cpuHelper(randomIntGenHelper(7, 0, 1, 2, 3, 4), zombie).hashCode()));
    
    // 2. different name
    assertFalse(cpu.hashCode() 
        == cpuHelper(randomIntGenHelper(7, 0, 1, 2, 3, 7), archery).hashCode());
  }

}
