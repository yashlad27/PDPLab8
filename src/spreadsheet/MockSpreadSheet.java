package spreadsheet;

/**
 * A mock implementation of SpreadSheet that logs all calls to its methods.
 * Used for testing controller interactions with the model.
 */
public class MockSpreadSheet implements SpreadSheet {

  private final StringBuilder log;
  private final double defaultValue;
  private final boolean defaultIsEmpty;
  private final int defaultWidth;
  private final int defaultHeight;

  /**
   * Constructs a mock spreadsheet that logs all calls to its methods.
   *
   * @param log the log to record operations in
   */
  public MockSpreadSheet(StringBuilder log) {
    this(log, 0.0, true, 0, 0);
  }

  /**
   * Constructs a mock spreadsheet with specified default return values.
   *
   * @param log            the log to record operations in
   * @param defaultValue   default value to return from get()
   * @param defaultIsEmpty default value to return from isEmpty()
   * @param defaultWidth   default value to return from getWidth()
   * @param defaultHeight  default value to return from getHeight()
   */
  public MockSpreadSheet(StringBuilder log, double defaultValue, boolean defaultIsEmpty,
                         int defaultWidth, int defaultHeight) {
    this.log = log;
    this.defaultValue = defaultValue;
    this.defaultIsEmpty = defaultIsEmpty;
    this.defaultWidth = defaultWidth;
    this.defaultHeight = defaultHeight;
  }

  @Override
  public double get(int row, int col) throws IllegalArgumentException {
    return 0;
  }

  @Override
  public void set(int row, int col, double value) throws IllegalArgumentException {

  }

  @Override
  public boolean isEmpty(int row, int col) throws IllegalArgumentException {
    return false;
  }

  @Override
  public int getWidth() {
    return 0;
  }

  @Override
  public int getHeight() {
    return 0;
  }
}
