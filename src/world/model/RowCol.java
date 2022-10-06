package world.model;

import java.util.Objects;

/**
 * A class to represent row and column as a tuple-like type.
 * @author Sherly Hartono
 */

public final class RowCol {
  private final int row;
  private final int col;
  
  /**
   * Construct row and column object from integer.
   * @param row the row index 
   * @param col the column index
   * @throws IllegalArgumentException if row and columns are negative.
   */
  public RowCol(int row, int col) throws IllegalArgumentException {
    if (row < 0 || col < 0) {
      throw new IllegalArgumentException("Row and column cannot be negative");
    } 
    
    this.row = row;
    this.col = col;
  }
  
  /**
   * Getters for the row.
   * @return the row
   */
  public int getRow() {
    return this.row;
  }

  /**
   * Getters for the column.
   * @return the column field
   */
  public int getCol() {
    return this.col;
  }
  
  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(row).append(" X ").append(col);
    return s.toString();
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(col, row);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof RowCol)) {
      return false;
    }

    RowCol that = (RowCol) o;
    return col == that.getCol() && row == that.getRow();
  }

}
