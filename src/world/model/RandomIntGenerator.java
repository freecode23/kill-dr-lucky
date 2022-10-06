package world.model;

/**
 * Generate random number using java's Random class.
 */
public interface RandomIntGenerator {
 
  
  /**
   * Create random integer which is needed for cpu as well as to initialize player's max item.
   * @param min the minimum value inclusive
   * @param max the maximum value inclusive
   * @return random integer the random number between 1 to n inclusive
   * @throws IllegalArgumentException number is negative
   */
  int getNextInt(int min, int max) throws IllegalArgumentException;
}
