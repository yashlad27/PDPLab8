package spreadsheet;

/**
 * A mock implementation of BetterSpreadSheet that logs all calls to its methods.
 * Used for testing controller interactions with the model.
 */
public class MockBetterSpreadSheet extends MockSpreadSheet implements BetterSpreadSheet {

  // This will store all operations performed on this mock
  private final StringBuilder log;

  /**
   * Constructs a mock better spreadsheet that logs all calls to its methods.
   * @param log the log to record operations in
   */
  public MockBetterSpreadSheet(StringBuilder log) {
    super(log);
    this.log = log;
  }

  /**
   * Constructs a mock better spreadsheet with specified default return values.
   * @param log the log to record operations in
   * @param defaultValue default value to return from get()
   * @param defaultIsEmpty default value to return from isEmpty()
   * @param defaultWidth default value to return from getWidth()
   * @param defaultHeight default value to return from getHeight()
   */
  public MockBetterSpreadSheet(StringBuilder log, double defaultValue, boolean defaultIsEmpty,
                               int defaultWidth, int defaultHeight) {
    super(log, defaultValue, defaultIsEmpty, defaultWidth, defaultHeight);
    this.log = log;
  }

  @Override
  public void bulkSet(int startRow, int startCol, int endRow, int endCol, double value)
          throws IllegalArgumentException {
    if (startRow < 0 || startCol < 0 || endRow < 0 || endCol < 0) {
      throw new IllegalArgumentException("Row or column cannot be negative");
    }
    if (endRow < startRow || endCol < startCol) {
      throw new IllegalArgumentException("End coordinates must be greater than or equal to start coordinates");
    }

    log.append(String.format("bulkSet(%d, %d, %d, %d, %.1f)\n",
            startRow, startCol, endRow, endCol, value));
  }
}