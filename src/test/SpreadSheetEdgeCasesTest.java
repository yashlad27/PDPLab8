import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;

import spreadsheet.MockSpreadSheet;
import spreadsheet.SparseSpreadSheet;
import spreadsheet.SpreadSheetController;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class focusing on edge cases that might be missed by regular tests.
 * These help catch subtle mutations in the code.
 */
public class SpreadSheetEdgeCasesTest {

  private StringBuilder log;
  private MockSpreadSheet mockSheet;
  private StringWriter output;

  @Before
  public void setUp() {
    log = new StringBuilder();
    mockSheet = new MockSpreadSheet(log);
    output = new StringWriter();
  }

  @Test
  public void testEmptyRowString() {
    Readable input = new StringReader("print-value  1 q");
    SpreadSheetController controller = new SpreadSheetController(mockSheet, input, output);

    controller.executeProgram();

    assertTrue(output.toString().contains("Error:"));
  }

  @Test
  public void testSingleCellMaxValues() {
    SparseSpreadSheet sheet = new SparseSpreadSheet();

    sheet.set(1000, 1000, Double.MAX_VALUE);
    assertEquals(Double.MAX_VALUE, sheet.get(1000, 1000), 0.001);

    sheet.set(2000, 2000, Double.MIN_VALUE);
    assertEquals(Double.MIN_VALUE, sheet.get(2000, 2000), 0.001);

    sheet.set(3000, 3000, Double.NaN);
    assertTrue(Double.isNaN(sheet.get(3000, 3000)));

    sheet.set(4000, 4000, Double.POSITIVE_INFINITY);
    assertEquals(Double.POSITIVE_INFINITY, sheet.get(4000, 4000), 0.001);

    assertEquals(4001, sheet.getHeight());
    assertEquals(4001, sheet.getWidth());
  }

  @Test
  public void testMultipleInvalidCommandsWithRecovery() {
    String input = "invalid-cmd1\n" + "menu\n" + "print-value Z 999\n" +
            "assign-value A 1 42.5\n" + "print-value A 1\n" + "q\n";

    Readable readable = new StringReader(input);
    SpreadSheetController controller = new SpreadSheetController(mockSheet, readable, output);

    controller.executeProgram();

    String outputStr = output.toString();

    assertTrue(outputStr.contains("Undefined instruction: invalid-cmd1"));

    assertTrue(outputStr.contains("Supported user instructions are:"));

    assertTrue(log.toString().contains("set(0, 0, 42.5)"));
    assertTrue(log.toString().contains("get(0,0)"));
  }

  @Test
  public void testAmbiguousInput() {
    Readable input = new StringReader("assign-value a 1 0 q");
    SpreadSheetController controller = new SpreadSheetController(mockSheet, input, output);

    controller.executeProgram();

    assertEquals("set(0, 0, 0.0)\n", log.toString());
  }

  @Test
  public void testCellPositionEquality() {
    SparseSpreadSheet sheet = new SparseSpreadSheet();

    sheet.set(10, 10, 1.0);
    sheet.set(10, 10, 2.0);
    sheet.set(10, 10, 3.0);

    assertEquals(3.0, sheet.get(10, 10), 0.001);

    assertEquals(11, sheet.getWidth());
    assertEquals(11, sheet.getHeight());
  }

  /**
   * Utility method to count occurrences of a substring.
   */
  private int countOccurrences(String str, String substr) {
    int count = 0;
    int lastIndex = 0;

    while (lastIndex != -1) {
      lastIndex = str.indexOf(substr, lastIndex);
      if (lastIndex != -1) {
        count++;
        lastIndex += substr.length();
      }
    }

    return count;
  }
}