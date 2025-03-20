import org.junit.Before;
import org.junit.Test;

import spreadsheet.SparseSpreadSheet;
import spreadsheet.SpreadSheet;

import static org.junit.Assert.*;

/**
 * Extended test class for SparseSpreadSheet to improve coverage.
 */
public class ExSparseSpreadSheetTest {

  private SpreadSheet sheet;

  @Before
  public void setUp() {
    sheet = new SparseSpreadSheet();
  }

  @Test
  public void testNewSpreadsheetDimensions() {
    // A new spreadsheet should have zero dimensions
    assertEquals(0, sheet.getWidth());
    assertEquals(0, sheet.getHeight());
  }

  @Test
  public void testSetUpdatesWidth() {
    // Setting a cell should update width if needed
    sheet.set(0, 5, 10.0);
    assertEquals(6, sheet.getWidth()); // Width is column + 1
  }

  @Test
  public void testSetUpdatesHeight() {
    // Setting a cell should update height if needed
    sheet.set(5, 0, 10.0);
    assertEquals(6, sheet.getHeight()); // Height is row + 1
  }

  @Test
  public void testSetDoesNotDecreaseWidth() {
    // Set a cell to establish width
    sheet.set(0, 10, 5.0);
    assertEquals(11, sheet.getWidth());

    // Setting a cell with lower column should not decrease width
    sheet.set(0, 5, 10.0);
    assertEquals(11, sheet.getWidth());
  }

  @Test
  public void testSetDoesNotDecreaseHeight() {
    // Set a cell to establish height
    sheet.set(10, 0, 5.0);
    assertEquals(11, sheet.getHeight());

    // Setting a cell with lower row should not decrease height
    sheet.set(5, 0, 10.0);
    assertEquals(11, sheet.getHeight());
  }

  @Test
  public void testGetEmptyCell() {
    // Getting an empty cell should return 0
    assertEquals(0.0, sheet.get(100, 100), 0.001);
  }

  @Test
  public void testIsEmptyNewCell() {
    // New cells should be empty
    assertTrue(sheet.isEmpty(5, 5));
  }

  @Test
  public void testIsEmptyAfterSet() {
    // After setting a cell, it should not be empty
    sheet.set(5, 5, 10.0);
    assertFalse(sheet.isEmpty(5, 5));
  }

  @Test
  public void testSetZero() {
    // Setting a cell to 0.0 should make it non-empty
    sheet.set(5, 5, 0.0);
    assertFalse(sheet.isEmpty(5, 5));
    assertEquals(0.0, sheet.get(5, 5), 0.001);
  }

  @Test
  public void testMultipleSetsSameCell() {
    // Setting a cell multiple times should update its value
    sheet.set(5, 5, 10.0);
    assertEquals(10.0, sheet.get(5, 5), 0.001);

    sheet.set(5, 5, 20.0);
    assertEquals(20.0, sheet.get(5, 5), 0.001);

    sheet.set(5, 5, 30.0);
    assertEquals(30.0, sheet.get(5, 5), 0.001);
  }

  @Test
  public void testMultipleCells() {
    // Test setting multiple cells
    sheet.set(0, 0, 1.0);
    sheet.set(0, 1, 2.0);
    sheet.set(1, 0, 3.0);
    sheet.set(1, 1, 4.0);

    // Check all cells have correct values
    assertEquals(1.0, sheet.get(0, 0), 0.001);
    assertEquals(2.0, sheet.get(0, 1), 0.001);
    assertEquals(3.0, sheet.get(1, 0), 0.001);
    assertEquals(4.0, sheet.get(1, 1), 0.001);

    // Check dimensions
    assertEquals(2, sheet.getWidth());
    assertEquals(2, sheet.getHeight());
  }

  @Test
  public void testNonContiguousCells() {
    // Test setting cells that are not adjacent
    sheet.set(0, 0, 1.0);
    sheet.set(10, 10, 2.0);

    // Check values
    assertEquals(1.0, sheet.get(0, 0), 0.001);
    assertEquals(2.0, sheet.get(10, 10), 0.001);

    // Check cells in between are empty
    assertTrue(sheet.isEmpty(5, 5));

    // Check dimensions
    assertEquals(11, sheet.getWidth());
    assertEquals(11, sheet.getHeight());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNegativeRow() {
    sheet.get(-1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNegativeCol() {
    sheet.get(0, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetNegativeRow() {
    sheet.set(-1, 0, 5.0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetNegativeCol() {
    sheet.set(0, -1, 5.0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsEmptyNegativeRow() {
    sheet.isEmpty(-1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsEmptyNegativeCol() {
    sheet.isEmpty(0, -1);
  }
}