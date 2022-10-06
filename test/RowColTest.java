

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import world.model.DefaultSpace;
import world.model.RowCol;
import world.model.Space;

/**
 * Test class for the RowCol class.
 */

public class RowColTest {
  private RowCol rowCol1025;
  /**
   * This method is providing short-hand way of creating instances of a new
   * RowCol object.
   * @param row the row index
   * @param col the col index
   * @return a new instance of an RowCol object
   */
  
  protected RowCol rowColHelper(int row, int col) {
    return new RowCol(row, col);
  }
  
  /**
   * Instantiate rowCol object.
   */
  @Before
  public void setUp() {
    rowCol1025 = rowColHelper(10, 25);
  }

  @Test
  public void testValidRowCol() {
    assertEquals(rowCol1025.getRow(), 10);
    assertEquals(rowCol1025.getCol(), 25);
    assertEquals(rowCol1025.toString(), "10 X 25");
    
    // test zero
    assertEquals(rowColHelper(0, 0).getRow(), 0);
    assertEquals(rowColHelper(0, 0).getCol(), 0);
    assertEquals(rowColHelper(0, 0).toString(), "0 X 0");
    
    // test large number
    assertEquals(rowColHelper(752, 255).getRow(), 752);
    assertEquals(rowColHelper(752, 255).getCol(), 255);
    assertEquals(rowColHelper(752, 255).toString(), "752 X 255");
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRow() {
    rowColHelper(-1, 123);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCol() {
    rowColHelper(121, -123);
  }
  
  @Test
  public void testEqualsObject() {
    // test same object and symmetry
    assertTrue(rowColHelper(10, 25).equals(rowCol1025));
    assertTrue(rowCol1025.equals(rowColHelper(10, 25)));
    
    Space space = new DefaultSpace(1, "Behrakisr", new RowCol(10, 25), new RowCol(12, 30));
    Space space2 = new DefaultSpace(1, "Behr", new RowCol(10, 25), new RowCol(12, 30));
    
    // different object
    assertTrue(space.getUpLeft().equals(space2.getUpLeft()));
    assertTrue(space.getLowRight().equals(space2.getLowRight()));
    
    // newly created vs get from space
    RowCol spaceUpLeftRowCol = new RowCol(10, 25);
    assertTrue(spaceUpLeftRowCol.equals(space2.getUpLeft()));
    
    // different row
    assertFalse(rowColHelper(11, 25).equals(rowCol1025));
    
    // different column
    assertFalse(rowColHelper(10, 24).equals(rowCol1025));
  }

  @Test
  public void testHashCode() {
    // test new object vs previously created same object
    assertTrue(rowColHelper(10, 25).hashCode() == rowCol1025.hashCode());
    
    // test different row
    assertFalse(rowColHelper(9, 25).hashCode() == rowCol1025.hashCode());
    
    // test different column
    assertFalse(rowColHelper(10, 24).hashCode() == rowCol1025.hashCode());
  }  

}
