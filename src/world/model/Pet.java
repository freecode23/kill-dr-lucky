package world.model;

/**
 * Every world will have a pet initialized at the start of the game.
 * The pet enters the game in the same space as the target character.
 * It should be included in the description of the space that it occupies.
 * Any space that is occupied by the pet cannot be seen by its neighbors
 * making it virtually invisible to the user.
 */
public interface Pet {

  /**
   * Getters for name.
   * @return the name of the pet
   */
  String getName();
  
  
  /**
   * Getters for space.
   * @return the space this pet is currently occupying
   */
  Space getCurrentSpace();
  
  
  /**
   * Move the pet to a new space. Move pet is always successful as long as newSpace
   * is not null
   * @param newSpace the space it's moving to
   */
  void move(Space newSpace);

}
