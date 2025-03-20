import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;

import spreadsheet.MockSpreadSheet;
import spreadsheet.SpreadSheet;
import spreadsheet.SpreadSheetController;

import static org.junit.Assert.assertEquals;

/**
 * Test class for SpreadSheetController's interactions with the SpreadSheet model.
 */
public class SpreadSheetControllerInteractionTest {

  private StringBuilder log;
  private SpreadSheet mockSheet;
  private StringWriter output;

  @Before
  public void setUp() {
    log = new StringBuilder();
    mockSheet = new MockSpreadSheet(log);
    output = new StringWriter();
  }

  @Test
  public void testAssignValue() {
    // Input: assign-value A 1 42.5 q
    // This should set cell A1 (row 0, col 0) to 42.5, then quit
    Readable input = new StringReader("assign-value A 1 42.5 q");
    SpreadSheetController controller = new SpreadSheetController(mockSheet, input, output);

    controller.go();

    // Verify the log contains the correct call to set()
    assertEquals("set(0, 0, 42.5)\n", log.toString());
  }

  @Test
  public void testPrintValue() {
    // Input: print-value B 2 q
    // This should get the value at B2 (row 1, col 1), then quit
    Readable input = new StringReader("print-value B 2 q");
    SpreadSheetController controller = new SpreadSheetController(mockSheet, input, output);

    controller.go();

    // Verify the log contains the correct call to get()
    assertEquals("get(1,1)\n", log.toString());
  }

  @Test
  public void testRowLetterConversion() {
    // Test that multi-letter rows are correctly converted
    // Input: assign-value AA 1 100 q
    // This should set cell AA1 (row 26, col 0) to 100, then quit
    Readable input = new StringReader("assign-value AA 1 100 q");
    SpreadSheetController controller = new SpreadSheetController(mockSheet, input, output);

    controller.go();

    // Verify the log contains the correct row number (26 for AA)
    assertEquals("set(26, 0, 100.0)\n", log.toString());
  }

  @Test
  public void testColumnNumberOffset() {
    // Test that column numbers (starting from 1 in user input) are correctly
    // converted to 0-based indices for the spreadsheet
    // Input: assign-value A 5 25 q
    // This should set cell A5 (row 0, col 4) to 25, then quit
    Readable input = new StringReader("assign-value A 5 25 q");
    SpreadSheetController controller = new SpreadSheetController(mockSheet, input, output);

    controller.go();

    // Verify the log contains the correct column number (4 for input 5)
    assertEquals("set(0, 4, 25.0)\n", log.toString());
  }
}