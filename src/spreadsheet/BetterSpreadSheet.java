package spreadsheet;

/**
 * This interface represents an enhanced spreadsheet with additional operations
 * beyond the basic SpreadSheet interface.
 */
public interface BetterSpreadSheet extends SpreadSheet {

  /**
   * Sets all cells in a rectangular region to the specified value.
   *
   * @param startRow the starting row of the region (inclusive), 0-based
   * @param startCol the starting column of the region (inclusive), 0-based
   * @param endRow   the ending row of the region (inclusive), 0-based
   * @param endCol   the ending column of the region (inclusive), 0-based
   * @param value    the value to set all cells in the region to
   * @throws IllegalArgumentException if any row or column is negative or if
   *                                  the end coordinates are less than the start coordinates
   */
  void bulkSet(int startRow, int startCol, int endRow, int endCol, double value)
          throws IllegalArgumentException;
}