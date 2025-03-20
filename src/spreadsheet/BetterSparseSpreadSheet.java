package spreadsheet;

/**
 * This class extends SparseSpreadSheet to implement the BetterSpreadSheet interface.
 * It adds support for bulk operations on rectangular regions.
 */
public class BetterSparseSpreadSheet extends SparseSpreadSheet implements BetterSpreadSheet {

  /**
   * Constructs a new BetterSparseSpreadSheet instance.
   */
  public BetterSparseSpreadSheet() {
    super();
  }

  @Override
  public void bulkSet(int startRow, int startCol, int endRow, int endCol, double value)
          throws IllegalArgumentException {
    if (startRow < 0 || startCol < 0 || endRow < 0 || endCol < 0) {
      throw new IllegalArgumentException("Row or column cannot be negative");
    }

    if (endRow < startRow || endCol < startCol) {
      throw new IllegalArgumentException("End coordinates must be greater than or equal "
              + "to start coordinates");
    }

    // Set all cells in the specified region to the given value
    for (int row = startRow; row <= endRow; row++) {
      for (int col = startCol; col <= endCol; col++) {
        set(row, col, value);
      }
    }
  }
}