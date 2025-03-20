import org.junit.Before;
import org.junit.Test;

import spreadsheet.BetterSpreadSheet;
import spreadsheet.MockBetterSpreadSheet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Test class for MockBetterSpreadSheet.
 */
public class MockBetterSpreadSheetTest {

  private StringBuilder log;
  private BetterSpreadSheet mockSheet;

  @Before
  public void setUp() {
    log = new StringBuilder();
    mockSheet = new MockBetterSpreadSheet(log);
  }

  @Test
  public void testDefaultConstructor() {
    assertEquals(0.0, mockSheet.get(0, 0), 0.001);
    assertTrue(mockSheet.isEmpty(0, 0));
    assertEquals(0, mockSheet.getWidth());
    assertEquals(0, mockSheet.getHeight());

    String expectedLog = "get(0,0)\nisEmpty(0, 0)\ngetWidth()\ngetHeight()\n";
    assertEquals(expectedLog, log.toString());
  }

  @Test
  public void testParameterizedConstructor() {
    BetterSpreadSheet customMock = new MockBetterSpreadSheet(
            log, 42.0, false, 10, 20);

    log.setLength(0);

    assertEquals(42.0, customMock.get(5, 5), 0.001);
    assertFalse(customMock.isEmpty(5, 5));
    assertEquals(10, customMock.getWidth());
    assertEquals(20, customMock.getHeight());

    String expectedLog = "get(5,5)\nisEmpty(5, 5)\ngetWidth()\ngetHeight()\n";
    assertEquals(expectedLog, log.toString());
  }

  @Test
  public void testSet() {
    mockSheet.set(1, 2, 3.5);
    assertEquals("set(1, 2, 3.5)\n", log.toString());
  }

  @Test
  public void testBulkSet() {
    mockSheet.bulkSet(0, 0, 2, 2, 5.0);
    assertEquals("bulkSet(0, 0, 2, 2, 5.0)\n", log.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBulkSetNegativeRow() {
    mockSheet.bulkSet(-1, 0, 2, 2, 5.0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBulkSetNegativeCol() {
    mockSheet.bulkSet(0, -1, 2, 2, 5.0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBulkSetInvalidRange() {
    mockSheet.bulkSet(3, 0, 2, 2, 5.0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNegativeRow() {
    mockSheet.get(-1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNegativeCol() {
    mockSheet.get(0, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetNegativeRow() {
    mockSheet.set(-1, 0, 5.0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetNegativeCol() {
    mockSheet.set(0, -1, 5.0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsEmptyNegativeRow() {
    mockSheet.isEmpty(-1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsEmptyNegativeCol() {
    mockSheet.isEmpty(0, -1);
  }
}