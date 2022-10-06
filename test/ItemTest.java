

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import world.model.DefaultItem;



/**
 * Test class for the Item class.
 */
public class ItemTest {
  private DefaultItem broom021;
  private DefaultItem stinkyTofu201;
  
  /**
   * This method is providing short-hand way of creating instances of a new
   * Item object.
   * @param name the name of the item.
   * @param roomIndex the index of the room in which this item is placed.
   * @param damagePoint the amount of damage to the target character.
   * @return a new instance of an Item object
   */
  protected DefaultItem itemHelper(String name, int itemIndex, int damagePoint) {
    return new DefaultItem(name, itemIndex, damagePoint);
  }
  
  /**
   * Instantiate Items object using helper methods.
   */
  @Before
  public void setUp() {
    broom021 = itemHelper("Broom", 1, 21);
    stinkyTofu201 = itemHelper("Stinky Tofu", 2,  1);
  }

  @Test
  public void testValidItem() {
    // 1. Long name
    assertEquals("Pink Bad Tight Silken Red Billiard Sharp Rat Knife 32",
        itemHelper("Pink Bad Tight Silken Red Billiard Sharp Rat Knife", 1, 32).toString());
    
    // 2. Short name
    assertEquals("Ax 32", 
        itemHelper("Ax", 1, 32).toString());
    
    // 3. room index border 
    assertEquals("Broom 21",
        broom021.toString());
    
    
    // 4. damage point border 
    assertEquals("Stinky Tofu 1",
        stinkyTofu201.toString());
  }

  
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyItemName() {
    itemHelper("", 1, 21);
  }
  

  
  @Test(expected = IllegalArgumentException.class)
  public void testDamagePointZero() {
    itemHelper("Broom", 1, 0);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testDamagePointNegative() {
    itemHelper("Broom", 1, -5);
  }
  
  @Test
  public void testHashCode() {
    // 1. test new object vs previously created object
    assertTrue((itemHelper("Broom", 1, 21).hashCode() == broom021.hashCode()));
    assertTrue((itemHelper("Stinky Tofu", 2, 1).hashCode() == stinkyTofu201.hashCode()));
    
    // 2. test different room index
    assertTrue(itemHelper("Broom", 2, 21).hashCode() == broom021.hashCode());
    assertTrue((itemHelper("Stinky Tofu", 2, 1).hashCode() == stinkyTofu201.hashCode()));
    
    // 3. test different damage point
    assertFalse((itemHelper("Broom", 1, 22).hashCode() == broom021.hashCode()));
    assertFalse((itemHelper("Stinky Tofu", 2, 2).hashCode() == stinkyTofu201.hashCode()));
  }

  @Test
  public void testEqualsObject() {
    // 1. test new object vs previously created object symmetry
    assertTrue(itemHelper("Broom", 1, 21).equals(broom021));
    assertTrue(broom021.equals(itemHelper("Broom", 1, 21)));
    
    // 2. test different room index
    assertTrue((itemHelper("Broom", 2, 21).equals(broom021)));
    
    // 3. test different damage point
    assertFalse((itemHelper("Broom", 3, 22).equals(broom021)));
  }

}
