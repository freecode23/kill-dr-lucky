

/**
 * A mock view where none of the method are throwing exceptions.
 */
public final class MockViewValid extends MockView {

  /**
   * The constructor that takes in log so it can print out the uuid.
   * @param gameLog the log that accumulates string
   * @param uuid the unique id of the model.
   */
  public MockViewValid(StringBuilder gameLog, String uuid) {
    super(gameLog, uuid);
  }

}
