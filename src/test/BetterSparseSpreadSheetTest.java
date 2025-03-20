import org.junit.Before;
import org.junit.Test;

import spreadsheet.BetterSparseSpreadSheet;
import spreadsheet.BetterSpreadSheet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

  @Test
  public void testBulkSetOverExistingValues() {
    // Set some initial values
    sheet.set(2, 2, 5.0);
    sheet.set(2, 3, 10.0);

    // Now bulk set over those values
    sheet.bulkSet(1, 1, 3, 3, 7.5);

    // Check that original values were overwritten
    assertEquals(7.5, sheet.get(2, 2), 0.001);
    assertEquals(7.5, sheet.get(2, 3), 0.001);

    // Check other cells in the region
    assertEquals(7.5, sheet.get(1, 1), 0.001);
    assertEquals(7.5, sheet.get(1, 2), 0.001);
    assertEquals(7.5, sheet.get(1, 3), 0.001);
    assertEquals(7.5, sheet.get(3, 1), 0.001);
    assertEquals(7.5, sheet.get(3, 2), 0.001);
    assertEquals(7.5, sheet.get(3, 3), 0.001);
  }

  @Test
  public void testBulkSetZeroValue() {
    // Set some initial values
    sheet.set(1, 1, 5.0);

    // Bulk set with zero value
    sheet.bulkSet(0, 0, 2, 2, 0.0);

    // Even though the value is 0, the cells are not empty
    assertFalse(sheet.isEmpty(1, 1));
    assertFalse(sheet.isEmpty(0, 0));
    assertFalse(sheet.isEmpty(2, 2));

    // But outside the region should still be empty
    assertTrue(sheet.isEmpty(3, 3));
  }

  @Test
  public void testBulkSetLargeRegion() {
    // Test with a larger region
    sheet.bulkSet(0, 0, 9, 9, 1.0);

    // Test dimensions
    assertEquals(10, sheet.getWidth());
    assertEquals(10, sheet.getHeight());

    // Check random cells in the region
    assertEquals(1.0, sheet.get(5, 5), 0.001);
    assertEquals(1.0, sheet.get(0, 9), 0.001);
    assertEquals(1.0, sheet.get(9, 0), 0.001);
  }

  @Test
  public void testBulkSetEqualStartAndEndCoordinates() {
    // Test a valid case where start and end coordinates are the same
    sheet.bulkSet(5, 5, 5, 5, 3.14);

    // Should set just one cell
    assertEquals(3.14, sheet.get(5, 5), 0.001);
    assertTrue(sheet.isEmpty(5, 4));
    assertTrue(sheet.isEmpty(5, 6));
    assertTrue(sheet.isEmpty(4, 5));
    assertTrue(sheet.isEmpty(6, 5));

    // Check dimensions
    assertEquals(6, sheet.getWidth());  // 0-5
    assertEquals(6, sheet.getHeight()); // 0-5
  }

  @Test
  public void testNonContiguousRegions() {
    // Set two separate regions
    sheet.bulkSet(0, 0, 2, 2, 1.0);
    sheet.bulkSet(5, 5, 7, 7, 2.0);

    // Check first region
    assertEquals(1.0, sheet.get(0, 0), 0.001);
    assertEquals(1.0, sheet.get(2, 2), 0.001);

    // Check second region
    assertEquals(2.0, sheet.get(5, 5), 0.001);
    assertEquals(2.0, sheet.get(7, 7), 0.001);

    // Check between regions
    assertTrue(sheet.isEmpty(3, 3));
    assertTrue(sheet.isEmpty(4, 4));

    // Check dimensions
    assertEquals(8, sheet.getWidth());  // 0-7
    assertEquals(8, sheet.getHeight()); // 0-7
  }

  @Test
  public void testBulkSetWithExistingLargerSpreadSheet() {
    // First create a larger spreadsheet
    sheet.set(20, 20, 5.0);

    // Now bulk set in a smaller region
    sheet.bulkSet(5, 5, 10, 10, 3.0);

    // Check that bulk set region was updated
    assertEquals(3.0, sheet.get(5, 5), 0.001);
    assertEquals(3.0, sheet.get(10, 10), 0.001);

    // Check that original cell is unchanged
    assertEquals(5.0, sheet.get(20, 20), 0.001);

    // Check dimensions are maintained
    assertEquals(21, sheet.getWidth());  // 0-20
    assertEquals(21, sheet.getHeight()); // 0-20
  }
}