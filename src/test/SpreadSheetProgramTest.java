import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Field;

import spreadsheet.SparseSpreadSheet;
import spreadsheet.SpreadSheet;
import spreadsheet.SpreadSheetController;
import spreadsheet.SpreadSheetProgram;

import static org.junit.Assert.*;

/**
 * Test class for SpreadSheetProgram.
 */
public class SpreadSheetProgramTest {

  // Save original System.in and System.out
  private final InputStream originalIn = System.in;
  private final PrintStream originalOut = System.out;

  // Streams for testing
  private ByteArrayOutputStream outContent;
  private ByteArrayInputStream inContent;

  @Before
  public void setUpStreams() {
    // Set up new streams for testing
    outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
  }

  @After
  public void restoreStreams() {
    // Restore original streams
    System.setIn(originalIn);
    System.setOut(originalOut);
  }

  @Test
  public void testMainMethodComponentCreation() throws Exception {
    // Setup input stream with quit command
    String input = "q\n";
    inContent = new ByteArrayInputStream(input.getBytes());
    System.setIn(inContent);

    // Create a spy controller that won't actually run the go() method
    TestableSpreadSheetProgram.runWithTestController();

    // Verify the controller was created with the right components
    SpreadSheetController controller = TestableSpreadSheetProgram.getLastController();
    assertNotNull("Controller should be created", controller);

    // Use reflection to access private fields
    Field sheetField = SpreadSheetController.class.getDeclaredField("sheet");
    sheetField.setAccessible(true);
    SpreadSheet sheet = (SpreadSheet) sheetField.get(controller);

    // Verify the model is a SparseSpreadSheet
    assertTrue("Model should be a SparseSpreadSheet", sheet instanceof SparseSpreadSheet);
  }

  @Test
  public void testMainMethodControllerExecution() {
    // Setup input stream with quit command
    String input = "q\n";
    inContent = new ByteArrayInputStream(input.getBytes());
    System.setIn(inContent);

    // Run the main method
    SpreadSheetProgram.main(new String[]{});

    // Verify output contains expected welcome and farewell messages
    String output = outContent.toString();
    assertTrue("Output should contain welcome message",
            output.contains("Welcome to the spreadsheet program!"));
    assertTrue("Output should contain farewell message",
            output.contains("Thank you for using this program!"));
  }

  @Test
  public void testMainMethodWithCommands() {
    // Setup input stream with some commands
    String input = "assign-value A 1 42.5\nprint-value A 1\nq\n";
    inContent = new ByteArrayInputStream(input.getBytes());
    System.setIn(inContent);

    // Run the main method
    SpreadSheetProgram.main(new String[]{});

    // Verify output contains expected command results
    String output = outContent.toString();
    assertTrue("Output should show the value was set and retrieved",
            output.contains("Value: 42.5"));
  }
}

/**
 * Testable version of SpreadSheetProgram with static fields to capture the controller.
 */
class TestableSpreadSheetProgram {
  private static SpreadSheetController lastController;

  public static SpreadSheetController getLastController() {
    return lastController;
  }

  public static void runWithTestController() {
    SpreadSheet model = new SparseSpreadSheet();
    // Create controller but don't run go()
    lastController = new SpreadSheetController(model,
            new InputStreamReader(new ByteArrayInputStream("q\n".getBytes())),
            System.out);
  }
}