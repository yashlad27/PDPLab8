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
    sheet.bulkSet(0, 0, 0, 0, 42.5);
    assertEquals(42.5, sheet.get(0, 0), 0.001);
  }

  @Test
  public void testBulkSetMultipleCells() {
    sheet.bulkSet(1, 2, 2, 4, 10.0);

    for (int row = 1; row <= 2; row++) {
      for (int col = 2; col <= 4; col++) {
        assertEquals(10.0, sheet.get(row, col), 0.001);
        assertFalse(sheet.isEmpty(row, col));
      }
    }

    assertEquals(0.0, sheet.get(0, 0), 0.001);
    assertEquals(0.0, sheet.get(3, 3), 0.001);
  }

  @Test
  public void testBulkSetUpdatesWidthAndHeight() {
    sheet.bulkSet(5, 7, 10, 12, 1.0);

    assertEquals(13, sheet.getWidth());
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
    sheet.set(2, 2, 5.0);
    sheet.set(2, 3, 10.0);

    sheet.bulkSet(1, 1, 3, 3, 7.5);

    assertEquals(7.5, sheet.get(2, 2), 0.001);
    assertEquals(7.5, sheet.get(2, 3), 0.001);

    assertEquals(7.5, sheet.get(1, 1), 0.001);
    assertEquals(7.5, sheet.get(1, 2), 0.001);
    assertEquals(7.5, sheet.get(1, 3), 0.001);
    assertEquals(7.5, sheet.get(3, 1), 0.001);
    assertEquals(7.5, sheet.get(3, 2), 0.001);
    assertEquals(7.5, sheet.get(3, 3), 0.001);
  }

  @Test
  public void testBulkSetZeroValue() {
    sheet.set(1, 1, 5.0);

    sheet.bulkSet(0, 0, 2, 2, 0.0);

    assertFalse(sheet.isEmpty(1, 1));
    assertFalse(sheet.isEmpty(0, 0));
    assertFalse(sheet.isEmpty(2, 2));

    assertTrue(sheet.isEmpty(3, 3));
  }

  @Test
  public void testBulkSetLargeRegion() {
    sheet.bulkSet(0, 0, 9, 9, 1.0);

    assertEquals(10, sheet.getWidth());
    assertEquals(10, sheet.getHeight());

    assertEquals(1.0, sheet.get(5, 5), 0.001);
    assertEquals(1.0, sheet.get(0, 9), 0.001);
    assertEquals(1.0, sheet.get(9, 0), 0.001);
  }

  @Test
  public void testBulkSetEqualStartAndEndCoordinates() {
    sheet.bulkSet(5, 5, 5, 5, 3.14);

    assertEquals(3.14, sheet.get(5, 5), 0.001);
    assertTrue(sheet.isEmpty(5, 4));
    assertTrue(sheet.isEmpty(5, 6));
    assertTrue(sheet.isEmpty(4, 5));
    assertTrue(sheet.isEmpty(6, 5));

    assertEquals(6, sheet.getWidth());
    assertEquals(6, sheet.getHeight());
  }

  @Test
  public void testNonContiguousRegions() {
    sheet.bulkSet(0, 0, 2, 2, 1.0);
    sheet.bulkSet(5, 5, 7, 7, 2.0);

    assertEquals(1.0, sheet.get(0, 0), 0.001);
    assertEquals(1.0, sheet.get(2, 2), 0.001);

    assertEquals(2.0, sheet.get(5, 5), 0.001);
    assertEquals(2.0, sheet.get(7, 7), 0.001);

    assertTrue(sheet.isEmpty(3, 3));
    assertTrue(sheet.isEmpty(4, 4));

    assertEquals(8, sheet.getWidth());
    assertEquals(8, sheet.getHeight());
  }

  @Test
  public void testBulkSetWithExistingLargerSpreadSheet() {
    sheet.set(20, 20, 5.0);

    sheet.bulkSet(5, 5, 10, 10, 3.0);

    assertEquals(3.0, sheet.get(5, 5), 0.001);
    assertEquals(3.0, sheet.get(10, 10), 0.001);

    assertEquals(5.0, sheet.get(20, 20), 0.001);

    assertEquals(21, sheet.getWidth());
    assertEquals(21, sheet.getHeight());
  }
}