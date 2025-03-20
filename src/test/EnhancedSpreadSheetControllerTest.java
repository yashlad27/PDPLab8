import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import spreadsheet.MockSpreadSheet;
import spreadsheet.SpreadSheet;
import spreadsheet.SpreadSheetController;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Enhanced test class for SpreadSheetController with additional edge cases.
 */
public class EnhancedSpreadSheetControllerTest {

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
  public void testMultipleInstructions() {
    Readable input = new StringReader("assign-value A 1 10.5 print-value A 1 q");
    SpreadSheetController controller = new SpreadSheetController(mockSheet, input, output);

    controller.executeProgram();

    String expectedLog = "set(0, 0, 10.5)\nget(0,0)\n";
    assertEquals(expectedLog, log.toString());
  }

  @Test
  public void testNegativeRowColumn() {
    Readable input = new StringReader("assign-value A -1 10.5 q");
    SpreadSheetController controller = new SpreadSheetController(mockSheet, input, output);

    controller.executeProgram();

    assertTrue(output.toString().contains("Error:"));
  }

  @Test
  public void testGetRowNumWithMixedCase() {
    Readable input = new StringReader("assign-value aB 1 15.0 q");
    SpreadSheetController controller = new SpreadSheetController(mockSheet, input, output);

    controller.executeProgram();

    assertEquals("set(27, 0, 15.0)\n", log.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullSheet() {
    new SpreadSheetController(null, new StringReader(""), output);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullReadable() {
    new SpreadSheetController(mockSheet, null, output);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullAppendable() {
    new SpreadSheetController(mockSheet, new StringReader(""), null);
  }

  @Test(expected = IllegalStateException.class)
  public void testAppendableFailure() {
    Appendable failingAppendable = new Appendable() {
      @Override
      public Appendable append(CharSequence csq) throws IOException {
        throw new IOException("Simulated IO failure");
      }

      @Override
      public Appendable append(CharSequence csq, int start, int end) throws IOException {
        throw new IOException("Simulated IO failure");
      }

      @Override
      public Appendable append(char c) throws IOException {
        throw new IOException("Simulated IO failure");
      }
    };

    SpreadSheetController controller = new SpreadSheetController(mockSheet,
            new StringReader("q"), failingAppendable);
    controller.executeProgram();
  }
}

