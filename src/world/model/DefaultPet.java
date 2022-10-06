package world.model;


/**
 * Default implementation of the pet class.
 * There is only one pet in a world. 
 */
public class DefaultPet implements Pet {
  private final String name;
  private Space currentSpace;

  /**
   * Default pet constructor. 
   * @param name the name of the pet
   * @param currentSpace the space it's occupying and make it invisible to player
   * @throws IllegalArgumentException if name is empty or current space or name is null.
   */
  public DefaultPet(String name, Space currentSpace) throws IllegalArgumentException {
    if (name == null || "".equals(name)  || currentSpace == null) {
      throw new IllegalArgumentException("name and space cannot be empty when initializing a pet");
    }
    this.name = name;
    this.currentSpace = currentSpace;
    this.currentSpace.addPet(this);
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public Space getCurrentSpace() {
    return this.currentSpace;
  }

  @Override
  public void move(Space newSpace) throws IllegalArgumentException {
    if (newSpace == null) {
      throw new IllegalArgumentException("Space cannot be null when moving a pet");
    }
    this.currentSpace.removePet(); // old place has no pet
    this.currentSpace = newSpace; // move pet
    this.currentSpace.addPet(this); // new place has pet
  }

  @Override 
  public String toString() {
    StringBuilder sresult = new StringBuilder();
    sresult.append(this.name).append(" is in ").append(this.currentSpace.getName());
    return sresult.toString();
  }

}
