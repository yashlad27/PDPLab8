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
    Readable input = new StringReader("assign-value A 1 42.5 q");
    SpreadSheetController controller = new SpreadSheetController(mockSheet, input, output);

    controller.executeProgram();

    assertEquals("set(0, 0, 42.5)\n", log.toString());
  }

  @Test
  public void testPrintValue() {
    Readable input = new StringReader("print-value B 2 q");
    SpreadSheetController controller = new SpreadSheetController(mockSheet, input, output);

    controller.executeProgram();

    assertEquals("get(1,1)\n", log.toString());
  }

  @Test
  public void testRowLetterConversion() {
    Readable input = new StringReader("assign-value AA 1 100 q");
    SpreadSheetController controller = new SpreadSheetController(mockSheet, input, output);

    controller.executeProgram();

    assertEquals("set(26, 0, 100.0)\n", log.toString());
  }

  @Test
  public void testColumnNumberOffset() {
    Readable input = new StringReader("assign-value A 5 25 q");
    SpreadSheetController controller = new SpreadSheetController(mockSheet, input, output);

    controller.executeProgram();

    assertEquals("set(0, 4, 25.0)\n", log.toString());
  }
}