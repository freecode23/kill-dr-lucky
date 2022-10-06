package world.model;

import java.util.Random;

/**
 * This class will use Java's random to generate a random integer.
 */
public final class DefaultRandomIntGenerator implements RandomIntGenerator {
  private final Random randomObject;
  
  /**
   * Constructor initializes Java's Random object.
   *
   */
  public DefaultRandomIntGenerator() {
    this.randomObject = new Random();

  }
  
  /**
   * Create random integer which is needed for cpu as well as to initialize player's max item.
   * @param min the minimum value inclusive
   * @param max the maximum value inclusive
   * @return random integer the random number between 1 to n inclusive
   * @throws IllegalArgumentException number is negative
   */
  public int getNextInt(int min, int max) throws IllegalArgumentException {
    if (min < -1 || max < -1) {
      throw new IllegalArgumentException("min max cannot be negative");
    }
    // if minimum = 1, max = 5 it will give 1-5 inclusive
    // if minimum = 0, max = 5 it will give 0-5 inclusive
    if (min == 0) {
      max += 1;
    }
    return this.randomObject.nextInt(max) + min; 
  }
}
