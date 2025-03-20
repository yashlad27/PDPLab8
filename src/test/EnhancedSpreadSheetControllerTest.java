import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.IOException;

import spreadsheet.MockSpreadSheet;
import spreadsheet.SpreadSheet;
import spreadsheet.SpreadSheetController;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
    // Test multiple instructions in sequence
    Readable input = new StringReader("assign-value A 1 10.5 print-value A 1 q");
    SpreadSheetController controller = new SpreadSheetController(mockSheet, input, output);

    controller.go();

    String expectedLog = "set(0, 0, 10.5)\nget(0,0)\n";
    assertEquals(expectedLog, log.toString());
  }

  @Test
  public void testNegativeRowColumn() {
    // Test handling of negative row/column values, which should result in an error
    Readable input = new StringReader("assign-value A -1 10.5 q");
    SpreadSheetController controller = new SpreadSheetController(mockSheet, input, output);

    controller.go();

    // The controller should catch the NumberFormatException or pass along IllegalArgumentException
    assertTrue(output.toString().contains("Error:"));
  }

  @Test
  public void testGetRowNumWithMixedCase() {
    // Test that row letters in mixed case are handled correctly
    Readable input = new StringReader("assign-value aB 1 15.0 q");
    SpreadSheetController controller = new SpreadSheetController(mockSheet, input, output);

    controller.go();

    // aB should be treated the same as AB (row 27)
    assertEquals("set(27, 0, 15.0)\n", log.toString());
  }

//  @Test
//  public void testGetRowNumDirectly() {
//    // Test the getRowNum method directly for various inputs
//    SpreadSheetController controller = new SpreadSheetController(mockSheet, new StringReader(""), output);
//
//    assertEquals(0, controller.getRowNum("a"));
//    assertEquals(1, controller.getRowNum("b"));
//    assertEquals(25, controller.getRowNum("z"));
//    assertEquals(26, controller.getRowNum("aa"));
//    assertEquals(27, controller.getRowNum("ab"));
//    assertEquals(51, controller.getRowNum("az"));
//    assertEquals(52, controller.getRowNum("ba"));
//    assertEquals(701, controller.getRowNum("zz"));
//    assertEquals(702, controller.getRowNum("aaa"));
//  }
//
//  @Test(expected = IllegalArgumentException.class)
//  public void testGetRowNumWithInvalidChar() {
//    // Test that non-alphabetic characters in row strings are rejected
//    SpreadSheetController controller = new SpreadSheetController(mockSheet, new StringReader(""), output);
//    controller.getRowNum("a1");
//  }
//
//  @Test(expected = IllegalArgumentException.class)
//  public void testGetRowNumWithEmptyString() {
//    // Test that empty strings are rejected
//    SpreadSheetController controller = new SpreadSheetController(mockSheet, new StringReader(""), output);
//    controller.getRowNum("");
//  }
//
//  @Test
//  public void testPrintMenuDirectly() {
//    // Test the printMenu method directly
//    SpreadSheetController controller = new SpreadSheetController(mockSheet, new StringReader(""), output);
//
//    controller.printMenu();
//
//    String outputString = output.toString();
//    assertTrue(outputString.contains("assign-value"));
//    assertTrue(outputString.contains("print-value"));
//    assertTrue(outputString.contains("menu"));
//    assertTrue(outputString.contains("q or quit"));
//  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullSheet() {
    // Test that constructor rejects null spreadsheet
    new SpreadSheetController(null, new StringReader(""), output);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullReadable() {
    // Test that constructor rejects null readable
    new SpreadSheetController(mockSheet, null, output);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullAppendable() {
    // Test that constructor rejects null appendable
    new SpreadSheetController(mockSheet, new StringReader(""), null);
  }

  @Test(expected = IllegalStateException.class)
  public void testAppendableFailure() {
    // Test that IOException during append is properly wrapped in IllegalStateException
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

    SpreadSheetController controller = new SpreadSheetController(mockSheet, new StringReader("q"), failingAppendable);
    controller.go(); // Should throw IllegalStateException
  }
}

/**
 * Helper class that will throw IOException when append is called.
 * Used to test error handling in the controller.
 */
class FailingAppendable implements Appendable {
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
}