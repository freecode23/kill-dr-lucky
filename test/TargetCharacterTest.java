

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import world.model.DefaultTargetCharc;
import world.model.TargetCharacter;

/**
 * Test class for the TargetCharacter class.
 */

public class TargetCharacterTest {
  private TargetCharacter unlucky100;
  private TargetCharacter li50;

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
   * Instantiate TargetCharacter object using helper methods.
   */
  @Before
  public void setUp() {
    unlucky100 = targetHelper("Prof Unlucky", 100, 5);
    li50 = targetHelper("Li", 50, 5);
  }
  
  @Test
  public void testValidTarget() {
    
    // valid health
    assertEquals("Prof Unlucky's health is 100", unlucky100.toString());
    
    // short name
    assertEquals("Li's health is 50", li50.toString());
    
    // long names
    assertEquals("Prof Takayamapemi Sparkskull Tokagepemi's health is 100", targetHelper(
        "Prof Takayamapemi Sparkskull Tokagepemi", 100, 5).toString());
    
    // small health
    assertEquals("Prof Unlucky's health is 1", targetHelper("Prof Unlucky", 1, 5).toString());
    
    // large health
    assertEquals("Prof Unlucky's health is 19999", 
        targetHelper("Prof Unlucky", 19999, 5).toString());
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void emptyName() {
    targetHelper("", 100, 5);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void zeroHealth() {
    targetHelper("Prof Unlucky", 0, 5);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void negativeHealth() {
    targetHelper("Prof Unlucky", -1, 5);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void maxSpaceZero() {
    targetHelper("Prof Unlucky", 10, 0);
  }
  
  @Test
  public void testIsDead() {
    TargetCharacter prof = targetHelper("Prof Unlucky", 5, 5);
    assertFalse(prof.isDead());
    
    prof.reduceHealth(4);   
    assertFalse(prof.isDead());   
    
    prof.reduceHealth(1);   
    assertTrue(prof.isDead());
    
  }
  
  @Test
  public void testMove() {
    // start at room 0
    unlucky100.move(); // 1 
    unlucky100.move(); // 2
    unlucky100.move(); // 3
    assertEquals(3, unlucky100.getCurrentSpaceId());
    
    unlucky100.move(); // 4 
    unlucky100.move(); // 0
    assertEquals(0, unlucky100.getCurrentSpaceId());
    
    unlucky100.move(); // to 1
    assertEquals(1, unlucky100.getCurrentSpaceId());
    
    unlucky100.move(); // to 2
    assertEquals(2, unlucky100.getCurrentSpaceId());
    
  }

  @Test
  public void testEqualsObject() {
    // 1. test new object vs previously created object symmetry
    assertTrue(targetHelper("Prof Unlucky", 100, 5).equals(unlucky100));
    assertTrue(unlucky100.equals(targetHelper("Prof Unlucky", 100, 5)));

    
    // 2. different health
    assertTrue(targetHelper("Prof Unlucky", 101, 5).equals(unlucky100));
    
    // 3. different name
    assertFalse(targetHelper("Prof Unluck", 100, 5).equals(unlucky100));
  }
  
  @Test
  public void testHashCode() {
    // 1. test new object vs previously created object
    assertTrue((targetHelper("Prof Unlucky", 100, 5).hashCode() == unlucky100.hashCode()));
    assertTrue((targetHelper("Li", 50, 5).hashCode() == li50.hashCode()));
    
    // 2. test different health
    assertTrue((targetHelper("Prof Unlucky", 101, 5).hashCode() == unlucky100.hashCode()));
    
    // 3. test different name
    assertFalse((targetHelper("Lo", 50, 5).hashCode() == li50.hashCode()));
  }

}
