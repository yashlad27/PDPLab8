import org.junit.Before;
import org.junit.Test;

import spreadsheet.SparseSpreadSheet;
import spreadsheet.SpreadSheet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


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
    assertEquals(0, sheet.getWidth());
    assertEquals(0, sheet.getHeight());
  }

  @Test
  public void testSetUpdatesWidth() {
    sheet.set(0, 5, 10.0);
    assertEquals(6, sheet.getWidth());
  }

  @Test
  public void testSetUpdatesHeight() {
    sheet.set(5, 0, 10.0);
    assertEquals(6, sheet.getHeight());
  }

  @Test
  public void testSetDoesNotDecreaseWidth() {
    sheet.set(0, 10, 5.0);
    assertEquals(11, sheet.getWidth());

    sheet.set(0, 5, 10.0);
    assertEquals(11, sheet.getWidth());
  }

  @Test
  public void testSetDoesNotDecreaseHeight() {
    sheet.set(10, 0, 5.0);
    assertEquals(11, sheet.getHeight());

    sheet.set(5, 0, 10.0);
    assertEquals(11, sheet.getHeight());
  }

  @Test
  public void testGetEmptyCell() {
    assertEquals(0.0, sheet.get(100, 100), 0.001);
  }

  @Test
  public void testIsEmptyNewCell() {
    assertTrue(sheet.isEmpty(5, 5));
  }

  @Test
  public void testIsEmptyAfterSet() {
    sheet.set(5, 5, 10.0);
    assertFalse(sheet.isEmpty(5, 5));
  }

  @Test
  public void testSetZero() {
    sheet.set(5, 5, 0.0);
    assertFalse(sheet.isEmpty(5, 5));
    assertEquals(0.0, sheet.get(5, 5), 0.001);
  }

  @Test
  public void testMultipleSetsSameCell() {
    sheet.set(5, 5, 10.0);
    assertEquals(10.0, sheet.get(5, 5), 0.001);

    sheet.set(5, 5, 20.0);
    assertEquals(20.0, sheet.get(5, 5), 0.001);

    sheet.set(5, 5, 30.0);
    assertEquals(30.0, sheet.get(5, 5), 0.001);
  }

  @Test
  public void testMultipleCells() {
    sheet.set(0, 0, 1.0);
    sheet.set(0, 1, 2.0);
    sheet.set(1, 0, 3.0);
    sheet.set(1, 1, 4.0);

    assertEquals(1.0, sheet.get(0, 0), 0.001);
    assertEquals(2.0, sheet.get(0, 1), 0.001);
    assertEquals(3.0, sheet.get(1, 0), 0.001);
    assertEquals(4.0, sheet.get(1, 1), 0.001);

    assertEquals(2, sheet.getWidth());
    assertEquals(2, sheet.getHeight());
  }

  @Test
  public void testNonContiguousCells() {
    sheet.set(0, 0, 1.0);
    sheet.set(10, 10, 2.0);

    assertEquals(1.0, sheet.get(0, 0), 0.001);
    assertEquals(2.0, sheet.get(10, 10), 0.001);

    assertTrue(sheet.isEmpty(5, 5));

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