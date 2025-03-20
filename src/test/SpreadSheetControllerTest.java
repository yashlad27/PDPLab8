import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;

import spreadsheet.SparseSpreadSheet;
import spreadsheet.SpreadSheet;
import spreadsheet.SpreadSheetController;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for SpreadSheetController.
 */
public class SpreadSheetControllerTest {

  private SpreadSheet sheet;
  private StringWriter output;

  @Before
  public void setUp() {
    sheet = new SparseSpreadSheet();
    output = new StringWriter();
  }

  @Test
  public void testWelcomeMessage() {
    Readable input = new StringReader("q");
    SpreadSheetController controller = new SpreadSheetController(sheet, input, output);

    controller.executeProgram();

    String outputString = output.toString();
    String[] lines = outputString.split(System.lineSeparator());
    assertEquals("Welcome to the spreadsheet program!", lines[0]);
  }

  @Test
  public void testFarewellMessage() {
    Readable input = new StringReader("q");
    SpreadSheetController controller = new SpreadSheetController(sheet, input, output);

    controller.executeProgram();

    String outputString = output.toString();

    assertTrue(outputString.endsWith("Thank you for using this program!"));
  }

}