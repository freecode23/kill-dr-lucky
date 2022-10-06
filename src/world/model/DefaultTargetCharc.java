package world.model;

import java.util.Objects;

/**
 * Class for the target character in the world. 
 * Two character must be equal if they have the same name.
 */
public final class DefaultTargetCharc implements TargetCharacter {

  private final String name;
  private final int maxSpace;
  private int health;
  private int roomIdx;
  private final int originalHealth;
  
  
  /**
   * Constructs a target character with its name and initial health.
   * @param name the name of the target character
   * @param health the initial health of the character
   * @param maxSpace the maximum room index before it goes back to 0.
   * @throws IllegalArgumentException if:
   *     <ul>
   *     <li> The health is less than 1
   *     <li> The name is an empty string
   *     <li> The maximum space is 0 or less
   *     </ul>
   */
  public DefaultTargetCharc(String name, int health, 
      int maxSpace) throws IllegalArgumentException {   
    if ("".equals(name) || name == null) {
      throw new IllegalArgumentException("Name of the target character cannot be empty");
    } else if (health <= 0) {
      throw new IllegalArgumentException(
              new StringBuilder("The initial health of the target ")
                      .append("character cannot be less than 1")
                      .toString());
    } else if (maxSpace <= 0) {
      throw new IllegalArgumentException(
              new StringBuilder("Maximum space for target")
                      .append("character cannot be 0 or less")
                      .toString());
    }
    this.name = name;
    this.health = health;
    this.originalHealth = health;
    this.roomIdx = 0;
    this.maxSpace = maxSpace;
  }
  
  @Override
  public String getName() {
    return this.name;
  }
  
  @Override
  public int getHealth() {
    return this.health;
  }
  
  @Override
  public int getCurrentSpaceId() {
    return this.roomIdx;
  }
  
  @Override
  public void move() {
    if (roomIdx < this.maxSpace - 1) {
      this.roomIdx += 1;
    } else {
      this.roomIdx = 0;
    }
  }
  
  @Override
  public void reduceHealth(int amount) throws IllegalArgumentException {
    if (amount < 0) {
      throw new IllegalArgumentException("cannot reduce negative amount");
    }
    if (health < amount) {
      health = 0;
    } else {
      this.health -= amount;
    }
  }
  
  @Override
  public boolean isDead() {
    if (health == 0) {
      return true;
    }
    return false;
  }
  
  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(name).append("'s").append(" health is ").append(health);
    return s.toString();
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TargetCharacter)) {
      return false;
    }
    
    TargetCharacter other = (TargetCharacter) o;
    return Objects.equals(name, other.getName());
  }

  @Override
  public void restoreHealthAndPosition() {
    this.health = originalHealth;
    this.roomIdx = 0;
  }
}
