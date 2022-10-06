package world.model;

import java.util.Objects;

/**
 * A Class that represents the item in the game that are used
 * to give damage to the target character.
 * Two items are equal if they have the same name and damage point.
 */
public final class DefaultItem implements Item {
  private final String name;
  private final int itemId;
  private final int damagePoint;
  
  /**
   * Constructs an item.
   * @param name the name of the item.
   * @param itemId the id of the item
   * @param damagePoint the amount of damage to the target character.
   * @throws IllegalArgumentException if:
   *     <ul>
   *     <li> If damage point is less than 1.
   *     <li> If itemIdis less than 0.
   *     <li> If name is an empty string.
   *     </ul>
   */
  public DefaultItem(String name, int itemId, int damagePoint)throws IllegalArgumentException {
    if ("".equals(name) || name == null) {
      throw new IllegalArgumentException("Item name cannot be null");
    } else if (itemId < 0) {
      throw new IllegalArgumentException("Negative item id is not supported");
    } else if (damagePoint < 1) {
      throw new IllegalArgumentException("Damage point is at least 1");
    } 
    this.name = name;
    this.itemId = itemId;
    this.damagePoint = damagePoint;
  }
  
  @Override
  public int getDamagePoint() {
    return this.damagePoint;
  }
  
  @Override
  public String getName() {
    return this.name;
  }
  
  @Override
  public int getItemId() {
    return this.itemId;
  }
  
  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(this.name).append(" ").append(this.damagePoint);


    return s.toString();
  }

  @Override
  public int hashCode() {
    return Objects.hash(damagePoint, name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Item)) {
      return false;
    }
 
    Item other = (Item) o;
    return damagePoint == other.getDamagePoint() && Objects.equals(name, other.getName());
  }
}

