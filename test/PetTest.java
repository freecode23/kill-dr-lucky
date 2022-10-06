import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import world.model.DefaultItem;
import world.model.DefaultPet;
import world.model.DefaultSpace;
import world.model.Item;
import world.model.Pet;
import world.model.RowCol;
import world.model.Space;

/**
 * Test class for the Pet.
 */
public class PetTest {
  private Pet cat;
  private Space archery;
  private Space zombie;
  private Item arrow;
  private Item vrHeadset;


  /**
   * This method is providing short-hand way of creating instances of a new
   * pet object.
   * @param name the name of the item.
   * @param roomIndex the index of the room in which this item is placed.
   * @param damagePoint the amount of damage to the target character.
   * @return a new instance of an Item object
   */
  protected Pet petHelper(String name, Space space) {
    return new DefaultPet(name, space);
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
   * Instantiate space, items and players.
   * So that players can occupy a space with items
   */
  @Before
  public void setUp() { 
    
    // 1. initialize space
    archery =  spaceHelper(0, "Archery Range", new RowCol(3, 1), new RowCol(5, 2));
    zombie = spaceHelper(2, "Zombie VR", new RowCol(0, 0), new RowCol(2, 2));

    // 2. item
    arrow = itemHelper("Suction Cup Arrow", 0, 12);
    vrHeadset = itemHelper("VR Headset", 2, 2);

    // 3. add neighbor and items
    archery.addNeighbor(zombie);
    archery.addItem(arrow);
    zombie.addItem(vrHeadset);

    // cat start from archery
    cat = petHelper("Fortune The Cat", archery);

  } 


  @Test
  public void testValidConstructor() {
    String expected = "Fortune The Cat is in Archery Range";
    String actual = cat.toString();
    assertEquals(expected, actual);

    actual = archery.toString();
    expected = "Archery Range-0\n"
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
        + "           Fortune The Cat\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Zombie VR-2 <<<\n"
        + "           Item:\n"
        + "           1. VR Headset 2\n"
        + "\n"
        + "";
    assertEquals(expected, actual);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyName() {
    cat = petHelper("", archery);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullSpace() {
    cat = petHelper("Fortune The Cat", null);
  }


  @Test
  public void testMoveToNeighbor() {
    
    // cat start from archery range
    cat.move(zombie);

    // 1. show the cat from current space
    String expected = "Zombie VR-2\n"
        + "\n"
        + "=================\n"
        + "ROOM INFORMATION\n"
        + "=================\n"
        + "\n"
        + "ITEM\n"
        + "---------------\n"
        + "           1. VR Headset 2\n"
        + "\n"
        + "PLAYERS\n"
        + "---------------\n"
        + "\n"
        + "PET\n"
        + "---------------\n"
        + "           Fortune The Cat\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Archery Range-0 <<<\n"
        + "           Item:\n"
        + "           1. Suction Cup Arrow 12\n"
        + "\n"
        + "";


    String actual = zombie.toString();
    assertEquals(expected, actual);

    // 2. show the cat from neighbor
    // not in archery range anymore
    expected = "Archery Range-0\n"
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
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + ">>> Zombie VR-2 <<<\n"
        + "           Item:\n"
        + "           1. VR Headset 2\n"
        + "\n"
        + "           Pet:\n"
        + "           Fortune The Cat\n"
        + "\n"
        + "\n"
        + "";
    actual = archery.toString();
    assertEquals(expected, actual);
  }


  @Test
  public void testMoveToNotNeighbor() {
    Space outerSpace = spaceHelper(3, "Outer Space", new RowCol(10, 10),
        new RowCol(20, 20));

    cat.move(outerSpace);

    // 1. show the cat from current space
    String expected = "Outer Space-3\n"
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
        + "           Fortune The Cat\n"
        + "\n"
        + "NEIGHBORS\n"
        + "---------------\n"
        + "\n"
        + "";

    String actual = outerSpace.toString();
    assertEquals(expected, actual);

  }
}
