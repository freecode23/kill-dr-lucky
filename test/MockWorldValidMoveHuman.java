
/**
 * A mock model world where none of the method are throwing exceptions.
 */
public final class MockWorldValidMoveHuman extends MockWorld {

  /**
   * Constructs a mock model that records the input string and output a string of code.
   * @param gameLog the log that the method will write things to (to check for input)
   * @param uuid the id of this mock model.
   */
  public MockWorldValidMoveHuman(StringBuilder gameLog, String uuid) {
    super(gameLog, uuid);
  }

}
