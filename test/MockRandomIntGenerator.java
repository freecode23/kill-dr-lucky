
import java.util.ArrayList;
import java.util.List;
import world.model.RandomIntGenerator;

/**
 * A mock class that generate pseudo-random integer that
 * is actually produced from the argument given in the constructor.
 */
public class MockRandomIntGenerator implements RandomIntGenerator {
  private List<Integer> randomNumbers;
  
  /**
  * Constructor that uses user's argument to generate "random" list of numbers.
  * @param someArgs the list of numbers that will be provided in test cases
  */    
  public MockRandomIntGenerator(int ... someArgs) {
    this.randomNumbers = new ArrayList<Integer>();
    for (int i : someArgs) {
      randomNumbers.add(i);
    }
  }

  @Override
  public int getNextInt(int min, int max) throws IllegalArgumentException {
    if (min < 0 || max < 0) {
      throw new IllegalArgumentException("min and max cannot be negative");
    }
    int theNumber = randomNumbers.remove(0);
    return theNumber;
  }
  
}
