import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;

import spreadsheet.BetterSpreadSheet;
import spreadsheet.BetterSpreadSheetController;
import spreadsheet.MockBetterSpreadSheet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for BetterSpreadSheetController.
 * Tests both the output messages and the interactions with the model.
 */
public class BetterSpreadSheetControllerTest {

  private StringBuilder log;
  private BetterSpreadSheet mockSheet;
  private StringWriter output;

  @Before
  public void setUp() {
    log = new StringBuilder();
    mockSheet = new MockBetterSpreadSheet(log);
    output = new StringWriter();
  }

  @Test
  public void testWelcomeMessage() {
    Readable input = new StringReader("q");
    BetterSpreadSheetController controller = new BetterSpreadSheetController(mockSheet,
            input, output);

    controller.executeBetterProgram();

    String outputString = output.toString();

    assertTrue(outputString.endsWith("Thank you for using this enhanced program!"));
  }

  @Test
  public void testPrintMenu() {
    Readable input = new StringReader("menu q");
    BetterSpreadSheetController controller = new BetterSpreadSheetController(mockSheet,
            input, output);

    controller.executeBetterProgram();

    String outputString = output.toString();

    assertTrue(outputString.contains("assign-value"));
    assertTrue(outputString.contains("print-value"));
    assertTrue(outputString.contains("bulk-assign"));
    assertTrue(outputString.contains("menu"));
    assertTrue(outputString.contains("q or quit"));
  }

  @Test
  public void testAssignValue() {
    Readable input = new StringReader("assign-value A 1 42.5 q");
    BetterSpreadSheetController controller = new BetterSpreadSheetController(mockSheet,
            input, output);

    controller.executeBetterProgram();

    assertEquals("set(0, 0, 42.5)\n", log.toString());
    assertTrue(output.toString().contains("Set cell (0,0) to 42.5"));
  }

  @Test
  public void testPrintValue() {
    Readable input = new StringReader("print-value B 2 q");
    BetterSpreadSheetController controller = new BetterSpreadSheetController(mockSheet,
            input, output);

    controller.executeBetterProgram();

    assertEquals("get(1,1)\n", log.toString());
    assertTrue(output.toString().contains("Value: 0.0"));
  }

  @Test
  public void testBulkAssign() {
    Readable input = new StringReader("bulk-assign A 1 C 3 10.5 q");
    BetterSpreadSheetController controller = new BetterSpreadSheetController(mockSheet,
            input, output);

    controller.executeBetterProgram();

    assertEquals("bulkSet(0, 0, 2, 2, 10.5)\n", log.toString());
    assertTrue(output.toString().contains("Bulk assigned 10.5 to region (0,0) to (2,2)"));
  }

  @Test
  public void testInvalidInstruction() {
    Readable input = new StringReader("invalid-command q");
    BetterSpreadSheetController controller = new BetterSpreadSheetController(mockSheet,
            input, output);

    controller.executeBetterProgram();

    assertTrue(output.toString().contains("Undefined instruction: invalid-command"));
  }

  @Test
  public void testInvalidRowLetter() {
    Readable input = new StringReader("assign-value 123 1 10 q");
    BetterSpreadSheetController controller = new BetterSpreadSheetController(mockSheet,
            input, output);

    controller.executeBetterProgram();

    assertTrue(output.toString().contains("Error: Invalid row"));
  }

  @Test
  public void testComplexRowLetter() {
    Readable input = new StringReader("print-value ABC 1 q");
    BetterSpreadSheetController controller = new BetterSpreadSheetController(mockSheet,
            input, output);

    controller.executeBetterProgram();

    assertEquals("get(730,0)\n", log.toString());
  }

}