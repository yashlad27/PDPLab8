import org.junit.Before;
import org.junit.Test;

import spreadsheet.BetterSparseSpreadSheet;
import spreadsheet.BetterSpreadSheet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Test class for BetterSparseSpreadSheet.
 */
public class BetterSparseSpreadSheetTest {

  private BetterSpreadSheet sheet;

  @Before
  public void setUp() {
    sheet = new BetterSparseSpreadSheet();
  }

  @Test
  public void testBulkSetSingleCell() {
    // Test bulk set on a single cell
    sheet.bulkSet(0, 0, 0, 0, 42.5);

    // Verify the cell was set correctly
    assertEquals(42.5, sheet.get(0, 0), 0.001);
  }

  @Test
  public void testBulkSetMultipleCells() {
    // Test bulk set on a 2x3 region
    sheet.bulkSet(1, 2, 2, 4, 10.0);

    // Verify all cells in the region were set correctly
    for (int row = 1; row <= 2; row++) {
      for (int col = 2; col <= 4; col++) {
        assertEquals(10.0, sheet.get(row, col), 0.001);
        assertFalse(sheet.isEmpty(row, col));
      }
    }

    // Verify cells outside the region were not affected
    assertEquals(0.0, sheet.get(0, 0), 0.001);
    assertEquals(0.0, sheet.get(3, 3), 0.001);
  }

  @Test
  public void testBulkSetUpdatesWidthAndHeight() {
    // Test that bulk set updates the spreadsheet dimensions correctly
    sheet.bulkSet(5, 7, 10, 12, 1.0);

    // Width should be 13 (columns 0-12)
    assertEquals(13, sheet.getWidth());

    // Height should be 11 (rows 0-10)
    assertEquals(11, sheet.getHeight());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBulkSetNegativeStartRow() {
    sheet.bulkSet(-1, 0, 1, 1, 1.0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBulkSetNegativeStartCol() {
    sheet.bulkSet(0, -1, 1, 1, 1.0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBulkSetNegativeEndRow() {
    sheet.bulkSet(0, 0, -1, 1, 1.0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBulkSetNegativeEndCol() {
    sheet.bulkSet(0, 0, 1, -1, 1.0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBulkSetEndRowLessThanStartRow() {
    sheet.bulkSet(5, 0, 4, 1, 1.0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBulkSetEndColLessThanStartCol() {
    sheet.bulkSet(0, 5, 1, 4, 1.0);
  }
}